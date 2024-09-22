package javax.awt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.peer.ButtonPeer;
import java.beans.BeanProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.EventListener;

public class NullComponent extends Component {

    /**
     * The action to be performed once a button has been
     * pressed.  This value may be null.
     * @serial
     * @see #getActionCommand()
     * @see #setActionCommand(String)
     */
    String actionCommand;

    transient ActionListener actionListener;

    private static final String base = "Dummy";
    private static int nameCounter = 0;

    /**
     * Use serialVersionUID from JDK 1.1 for interoperability.
     */
    @Serial
    private static final long serialVersionUID = -8774683716313001058L;

    /**
     * Constructs a button with an empty string for its label.
     *
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public NullComponent() throws HeadlessException {        
    }

    /**
     * Constructs a button with the specified label.
     *
     * @param label  a string label for the button, or
     *               {@code null} for no label
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     * returns true
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public NullComponent(String label) throws HeadlessException {
    }

    /**
     * Construct a name for this component.  Called by getName() when the
     * name is null.
     */
    String constructComponentName() {
		return actionCommand;
    }

    /**
     * Creates the peer of the button.  The button's peer allows the
     * application to change the look of the button without changing
     * its functionality.
     *
     * @see     java.awt.Component#getToolkit()
     */
    public void addNotify() {
    }

    /**
     * Gets the label of this button.
     *
     * @return    the button's label, or {@code null}
     *                if the button has no label.
     * @see       java.awt.Button#setLabel
     */
    public String getLabel() {
        return null;
    }

    /**
     * Sets the button's label to be the specified string.
     *
     * @param     label   the new label, or {@code null}
     *                if the button has no label.
     * @see       java.awt.Button#getLabel
     */
    public void setLabel(String label) {
    }

    /**
     * Sets the command name for the action event fired
     * by this button. By default this action command is
     * set to match the label of the button.
     *
     * @param     command  a string used to set the button's
     *                  action command.
     *            If the string is {@code null} then the action command
     *            is set to match the label of the button.
     * @see       java.awt.event.ActionEvent
     * @since     1.1
     */
    public void setActionCommand(String command) {
    }

    /**
     * Returns the command name of the action event fired by this button.
     * If the command name is {@code null} (default) then this method
     * returns the label of the button.
     *
     * @return the action command name (or label) for this button
     */
    public String getActionCommand() {
        return null;
    }

    /**
     * Adds the specified action listener to receive action events from
     * this button. Action events occur when a user presses or releases
     * the mouse over this button.
     * If l is null, no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * @param         l the action listener
     * @see           #removeActionListener
     * @see           #getActionListeners
     * @see           java.awt.event.ActionListener
     * @since         1.1
     */
    public synchronized void addActionListener(ActionListener l) {
    }

    /**
     * Removes the specified action listener so that it no longer
     * receives action events from this button. Action events occur
     * when a user presses or releases the mouse over this button.
     * If l is null, no exception is thrown and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * @param           l     the action listener
     * @see             #addActionListener
     * @see             #getActionListeners
     * @see             java.awt.event.ActionListener
     * @since           1.1
     */
    public synchronized void removeActionListener(ActionListener l) {
        if (l == null) {
            return;
        }
        actionListener = AWTEventMulticaster.remove(actionListener, l);
    }

    /**
     * Returns an array of all the action listeners
     * registered on this button.
     *
     * @return all of this button's {@code ActionListener}s
     *         or an empty array if no action
     *         listeners are currently registered
     *
     * @see             #addActionListener
     * @see             #removeActionListener
     * @see             java.awt.event.ActionListener
     * @since 1.4
     */
    public synchronized ActionListener[] getActionListeners() {
        return getListeners(ActionListener.class);
    }

    /**
     * Returns an array of all the objects currently registered
     * as <code><em>Foo</em>Listener</code>s
     * upon this {@code Button}.
     * <code><em>Foo</em>Listener</code>s are registered using the
     * <code>add<em>Foo</em>Listener</code> method.
     *
     * <p>
     * You can specify the {@code listenerType} argument
     * with a class literal, such as
     * <code><em>Foo</em>Listener.class</code>.
     * For example, you can query a
     * {@code Button b}
     * for its action listeners with the following code:
     *
     * <pre>ActionListener[] als = (ActionListener[])(b.getListeners(ActionListener.class));</pre>
     *
     * If no such listeners exist, this method returns an empty array.
     *
     * @param listenerType the type of listeners requested; this parameter
     *          should specify an interface that descends from
     *          {@code java.util.EventListener}
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s on this button,
     *          or an empty array if no such
     *          listeners have been added
     * @throws ClassCastException if {@code listenerType}
     *          doesn't specify a class or interface that implements
     *          {@code java.util.EventListener}
     *
     * @see #getActionListeners
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        return super.getListeners(listenerType);
    }

    // REMIND: remove when filtering is done at lower level
    boolean eventEnabled(AWTEvent e) {
    	return false;
    }

    /**
     * Processes events on this button. If an event is
     * an instance of {@code ActionEvent}, this method invokes
     * the {@code processActionEvent} method. Otherwise,
     * it invokes {@code processEvent} on the superclass.
     * <p>Note that if the event parameter is {@code null}
     * the behavior is unspecified and may result in an
     * exception.
     *
     * @param        e the event
     * @see          java.awt.event.ActionEvent
     * @see          java.awt.Button#processActionEvent
     * @since        1.1
     */
    protected void processEvent(AWTEvent e) {
    }

    /**
     * Processes action events occurring on this button
     * by dispatching them to any registered
     * {@code ActionListener} objects.
     * <p>
     * This method is not called unless action events are
     * enabled for this button. Action events are enabled
     * when one of the following occurs:
     * <ul>
     * <li>An {@code ActionListener} object is registered
     * via {@code addActionListener}.
     * <li>Action events are enabled via {@code enableEvents}.
     * </ul>
     * <p>Note that if the event parameter is {@code null}
     * the behavior is unspecified and may result in an
     * exception.
     *
     * @param       e the action event
     * @see         java.awt.event.ActionListener
     * @see         java.awt.Button#addActionListener
     * @see         java.awt.Component#enableEvents
     * @since       1.1
     */
    protected void processActionEvent(ActionEvent e) {
    }

    /**
     * Returns a string representing the state of this {@code Button}.
     * This method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * {@code null}.
     *
     * @return     the parameter string of this button
     */
    protected String paramString() {
        return null;
    }


    /* Serialization support.
     */
    /**
     * Serialized data version.
     * @serial
     */
    private int buttonSerializedDataVersion = 1;

    /**
     * Writes default serializable fields to stream.  Writes
     * a list of serializable {@code ActionListeners}
     * as optional data.  The non-serializable
     * {@code ActionListeners} are detected and
     * no attempt is made to serialize them.
     *
     * @serialData {@code null} terminated sequence of 0 or
     *   more pairs: the pair consists of a {@code String}
     *   and an {@code Object}; the {@code String}
     *   indicates the type of object and is one of the following:
     *   {@code actionListenerK} indicating an
     *     {@code ActionListener} object
     *
     * @param  s the {@code ObjectOutputStream} to write
     * @throws IOException if an I/O error occurs
     * @see AWTEventMulticaster#save(ObjectOutputStream, String, EventListener)
     * @see java.awt.Component#actionListenerK
     * @see #readObject(ObjectInputStream)
     */
    @Serial
    private void writeObject(ObjectOutputStream s)
      throws IOException
    {
    }

    /**
     * Reads the {@code ObjectInputStream} and if
     * it isn't {@code null} adds a listener to
     * receive action events fired by the button.
     * Unrecognized keys or values will be ignored.
     *
     * @param  s the {@code ObjectInputStream} to read
     * @throws ClassNotFoundException if the class of a serialized object could
     *         not be found
     * @throws IOException if an I/O error occurs
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *         returns {@code true}
     *
     * @see #removeActionListener(ActionListener)
     * @see #addActionListener(ActionListener)
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #writeObject(ObjectOutputStream)
     */
    @Serial
    private void readObject(ObjectInputStream s)
      throws ClassNotFoundException, IOException, HeadlessException
    {
    }


}
