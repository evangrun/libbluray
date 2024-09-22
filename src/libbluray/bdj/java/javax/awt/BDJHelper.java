 /*
 * This file is part of libbluray
 * Copyright (C) 2012  Libbluray
 * Copyright (C) 2013  Petri Hintukainen <phintuka@users.sourceforge.net>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package javax.awt;

import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.InvocationEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.videolan.Logger;

public class BDJHelper {

	public static Object genericInvokeMethod(Object obj, String methodName, Object... params) {
        int paramCount = params.length;
        Method method;
        Object requiredObj = null;
        Class<?>[] classArray = new Class<?>[paramCount];
        for (int i = 0; i < paramCount; i++) {
            classArray[i] = params[i].getClass();
        }
        try {
            method = obj.getClass().getDeclaredMethod(methodName, classArray);
            method.setAccessible(true);
            requiredObj = method.invoke(obj, params);
        } 
        catch (NoSuchMethodException e) 
        {
            e.printStackTrace();
        } 
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        } 
        catch (IllegalAccessException e) {
            e.printStackTrace();
        } 
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return requiredObj;
    }
	
	//	use reflection since we are in a different package
	//	this is reasonably dirty, but well, you are decoding blurays when you are not supposed to
    public static Object getEventDispatchThread(EventQueue eq) {
        if (eq != null) {
        	return genericInvokeMethod(eq, "getDispatchThread");
        }
        return null;
    }

	//	use reflection since we are in a different package
	//	this is reasonably dirty, but well, you are decoding blurays when you are not supposed to
    public static void stopEventQueue(EventQueue eq) {
    	Object t = getEventDispatchThread(eq);
        if (t != null && (Boolean)genericInvokeMethod(t, "isAlive")) {

            final long DISPOSAL_TIMEOUT = 5000;
            final Object notificationLock = new Object();
            Runnable runnable = new Runnable() { public void run() {
                synchronized(notificationLock) {
                    notificationLock.notifyAll();
                }
            } };

            synchronized (notificationLock) {
                eq.postEvent(new InvocationEvent(Toolkit.getDefaultToolkit(), runnable));
                try {
                    notificationLock.wait(DISPOSAL_TIMEOUT);
                } catch (InterruptedException e) {
                }
            }

            genericInvokeMethod(t, "stopDispatching");
            if ((Boolean)genericInvokeMethod(t, "isAlive")) {
            	genericInvokeMethod(t, "interrupt");
            }
            //	wait 1 sec, then try join
        	genericInvokeMethod(t, "join", 1000);
        	//	check again
        	if ((Boolean)genericInvokeMethod(t, "isAlive")) {
                logger.error("stopEventQueue() failed for " + t);
                //org.videolan.PortingHelper.stopThread(t);
            }
        }
    }

    /*
     * Mouse events
     */

    private static int mouseX = 0, mouseY = 0, mouseMask = 0;

    public static boolean postMouseEvent(int x, int y) {
        mouseX = x;
        mouseY = y;
        return postMouseEventImpl(MouseEvent.MOUSE_MOVED, MouseEvent.NOBUTTON);
    }

    public static boolean postMouseEvent(int id) {
        boolean r;

        if (id == MouseEvent.MOUSE_PRESSED)
            mouseMask = MouseEvent.BUTTON1_MASK;

        r = postMouseEventImpl(id, MouseEvent.BUTTON1);

        if (id == MouseEvent.MOUSE_RELEASED)
            mouseMask = 0;

        return r;
    }

    private static boolean postMouseEventImpl(int id, int button) {
        Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getGlobalFocusOwner();
        if (focusOwner != null) {
            EventQueue eq = BDToolkit.getEventQueue(focusOwner);
            if (eq != null) {
                long when = System.currentTimeMillis();
                try {
                    eq.postEvent(new MouseEvent(focusOwner, id, when, mouseMask, mouseX, mouseY,
                                                (id == MouseEvent.MOUSE_CLICKED) ? 1 : 0, false, button));
                    return true;
                } catch (Exception e) {
                    logger.error("postMouseEvent failed: " + e);
                }
            }
        }
        return false;
    }

    /*
     * Key events
     */

    public static boolean postKeyEvent(int id, int modifiers, int keyCode) {
        Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getGlobalFocusOwner();
        if (focusOwner != null) {
            long when = System.currentTimeMillis();
            KeyEvent event;
            try {
                if (id == KeyEvent.KEY_TYPED)
                    event = new KeyEvent(focusOwner, id, when, modifiers, KeyEvent.VK_UNDEFINED, (char)keyCode);
                else
                    event = new KeyEvent(focusOwner, id, when, modifiers, keyCode, KeyEvent.CHAR_UNDEFINED);

                EventQueue eq = BDToolkit.getEventQueue(focusOwner);
                if (eq != null) {
                    eq.postEvent(event);
                    return true;
                }
            } catch (Exception e) {
                logger.error("postKeyEvent failed: " + e);
            }
        } else {
            logger.error("KEY event dropped (no focus owner)");
        }

        return false;
    }

    private static final Logger logger = Logger.getLogger(BDJHelper.class.getName());
}
