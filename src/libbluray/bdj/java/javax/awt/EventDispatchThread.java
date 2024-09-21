package javax.awt;

import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import java.util.ArrayList;

import javax.xml.stream.EventFilter;

import java.awt.EventFilter.FilterAction;

import sun.util.logging.PlatformLogger;

import sun.awt.dnd.SunDragSourceContextPeer;

/**
 * EventDispatchThread is a package-private AWT class which takes
 * events off the EventQueue and dispatches them to the appropriate
 * AWT components.
 *
 * The Thread starts a "permanent" event pump with a call to
 * pumpEvents(Conditional) in its run() method. Event handlers can choose to
 * block this event pump at any time, but should start a new pump (<b>not</b>
 * a new EventDispatchThread) by again calling pumpEvents(Conditional). This
 * secondary event pump will exit automatically as soon as the Conditional
 * evaluate()s to false and an additional Event is pumped and dispatched.
 *
 * @author Tom Ball
 * @author Amy Fowler
 * @author Fred Ecks
 * @author David Mendenhall
 *
 * @since 1.1
 */
public class EventDispatchThread extends Thread {

    private EventQueue theQueue;
    private volatile boolean doDispatch = true;

    private static final int ANY_EVENT = -1;

    private ArrayList<EventFilter> eventFilters = new ArrayList<EventFilter>();

   /**
    * Must always call 5 args super-class constructor passing false
    * to indicate not to inherit locals.
    */
    private EventDispatchThread() {
        throw new UnsupportedOperationException("Must erase locals");
    }

    EventDispatchThread(ThreadGroup group, String name, EventQueue queue) {
        super(group, null, name, 0, false);
        setEventQueue(queue);
    }

    /*
     * Must be called on EDT only, that's why no synchronization
     */
    public void stopDispatching() {
        doDispatch = false;
    }

    public void run() {
        try {
            pumpEvents(new Conditional() {
                public boolean evaluate() {
                    return true;
                }
            });
        } finally {
            getEventQueue().detachDispatchThread(this);
        }
    }

    void pumpEvents(Conditional cond) {
        pumpEvents(ANY_EVENT, cond);
    }

    void pumpEventsForHierarchy(Conditional cond, Component modalComponent) {
        pumpEventsForHierarchy(ANY_EVENT, cond, modalComponent);
    }

    void pumpEvents(int id, Conditional cond) {
        pumpEventsForHierarchy(id, cond, null);
    }

    void pumpEventsForHierarchy(int id, Conditional cond, Component modalComponent) {
        pumpEventsForFilter(id, cond, new HierarchyEventFilter(modalComponent));
    }

    void pumpEventsForFilter(Conditional cond, EventFilter filter) {
        pumpEventsForFilter(ANY_EVENT, cond, filter);
    }

    void pumpEventsForFilter(int id, Conditional cond, EventFilter filter) {
        addEventFilter(filter);
        doDispatch = true;
        while (doDispatch && !isInterrupted() && cond.evaluate()) {
            pumpOneEventForFilters(id);
        }
        removeEventFilter(filter);
    }

    void addEventFilter(EventFilter filter) {
        if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
            eventLog.finest("adding the event filter: " + filter);
        }
        synchronized (eventFilters) {
            if (!eventFilters.contains(filter)) {
                if (filter instanceof ModalEventFilter) {
                    ModalEventFilter newFilter = (ModalEventFilter)filter;
                    int k = 0;
                    for (k = 0; k < eventFilters.size(); k++) {
                        EventFilter f = eventFilters.get(k);
                        if (f instanceof ModalEventFilter) {
                            ModalEventFilter cf = (ModalEventFilter)f;
                            if (cf.compareTo(newFilter) > 0) {
                                break;
                            }
                        }
                    }
                    eventFilters.add(k, filter);
                } else {
                    eventFilters.add(filter);
                }
            }
        }
    }

    void removeEventFilter(EventFilter filter) {
        if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
            eventLog.finest("removing the event filter: " + filter);
        }
        synchronized (eventFilters) {
            eventFilters.remove(filter);
        }
    }

    boolean filterAndCheckEvent(AWTEvent event) {
        boolean eventOK = true;
        synchronized (eventFilters) {
            for (int i = eventFilters.size() - 1; i >= 0; i--) {
                EventFilter f = eventFilters.get(i);
                EventFilter.FilterAction accept = f.acceptEvent(event);
                if (accept == EventFilter.FilterAction.REJECT) {
                    eventOK = false;
                    break;
                } else if (accept == EventFilter.FilterAction.ACCEPT_IMMEDIATELY) {
                    break;
                }
            }
        }
        return eventOK && SunDragSourceContextPeer.checkEvent(event);
    }

    void pumpOneEventForFilters(int id) {
        AWTEvent event = null;
        boolean eventOK = false;
        try {
            EventQueue eq = null;
            do {
                // EventQueue may change during the dispatching
                eq = getEventQueue();

                event = (id == ANY_EVENT) ? eq.getNextEvent() : eq.getNextEvent(id);

                eventOK = filterAndCheckEvent(event);
                if (!eventOK) {
                    event.consume();
                }
            }
            while (eventOK == false);

            if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
                eventLog.finest("Dispatching: " + event);
            }

            eq.dispatchEvent(event);
        }
        catch (InterruptedException interruptedException) {
            doDispatch = false; // AppContext.dispose() interrupts all
                                // Threads in the AppContext
        }
        catch (Throwable e) {
            processException(e);
        }
    }

    private void processException(Throwable e) {
        if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
            eventLog.fine("Processing exception: " + e);
        }
        getUncaughtExceptionHandler().uncaughtException(this, e);
    }

    public synchronized EventQueue getEventQueue() {
        return theQueue;
    }
    public synchronized void setEventQueue(EventQueue eq) {
        theQueue = eq;
    }

    private static class HierarchyEventFilter implements EventFilter {
        private Component modalComponent;
        public HierarchyEventFilter(Component modalComponent) {
            this.modalComponent = modalComponent;
        }
        public FilterAction acceptEvent(AWTEvent event) {
            if (modalComponent != null) {
                int eventID = event.getID();
                boolean mouseEvent = (eventID >= MouseEvent.MOUSE_FIRST) &&
                                     (eventID <= MouseEvent.MOUSE_LAST);
                boolean actionEvent = (eventID >= ActionEvent.ACTION_FIRST) &&
                                      (eventID <= ActionEvent.ACTION_LAST);
                boolean windowClosingEvent = (eventID == WindowEvent.WINDOW_CLOSING);
                /*
                 * filter out MouseEvent and ActionEvent that's outside
                 * the modalComponent hierarchy.
                 * KeyEvent is handled by using enqueueKeyEvent
                 * in Dialog.show
                 */
                if (Component.isInstanceOf(modalComponent, "javax.swing.JInternalFrame")) {
                    /*
                     * Modal internal frames are handled separately. If event is
                     * for some component from another heavyweight than modalComp,
                     * it is accepted. If heavyweight is the same - we still accept
                     * event and perform further filtering in LightweightDispatcher
                     */
                    return windowClosingEvent ? FilterAction.REJECT : FilterAction.ACCEPT;
                }
                if (mouseEvent || actionEvent || windowClosingEvent) {
                    Object o = event.getSource();
                    if (o instanceof Component) {
                        Component c = (Component) o;
                        if (c != modalComponent) {
                            return FilterAction.REJECT;
                        }
                    }
                }
            }
            return FilterAction.ACCEPT;
        }
    }
}