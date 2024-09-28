/*
 * This file is part of libbluray
 * Copyright (C) 2012  Libbluray
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

package java.awt;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import java.io.IOException;

import org.videolan.Logger;
import org.videolan.Libbluray;

public class BDRootWindow extends Frame {

    public BDRootWindow () {
        super();
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        BDToolkit.setFocusedWindow(this);
    }

    public Area getDirtyArea() {
        return dirty;
    }

    public Font getDefaultFont() {
        return defaultFont;
    }

    public void setDefaultFont(String fontId) {
        if (fontId == null || fontId.equals("*****")) {
            defaultFont = new Font("Serif", Font.BOLD, 20);;
        } else {
            try {
                defaultFont = (new org.dvb.ui.FontFactory()).createFont(fontId);
            } catch (Exception ex) {
                logger.error("Failed setting default font " + fontId + ".otf: " + ex);
            }
        }
        logger.info("setting default font to " + fontId + ".otf (" + defaultFont + ")");
        setFont(defaultFont);
    }


    public void setBounds(int x, int y, int width, int height) {
        if (!isVisible()) {
            if ((width > 0) && (height > 0)) {
                if ((backBuffer == null) || (getWidth() * getHeight() < width * height)) {
                    backBuffer = new int[width * height];
                    Arrays.fill(backBuffer, 0);
                }
            }
            super.setBounds(x, y, width, height);
        } else if (width != getWidth() || height != getHeight()){
            logger.error("setBounds(" + x + "," + y + "," + width + "," + height + ") FAILED: already visible");
        }
    }

    public int[] getBdBackBuffer() {
        return backBuffer;
    }

    public Image getBackBuffer() {
        /* exists only in J2SE */
        logger.unimplemented("getBackBuffer");
        return null;
    }

    private boolean isBackBufferClear() {
        int v = 0;
        for (int i = 0; i < height * width; i++) {
            v = backBuffer[i];
            if(v != 0) {
                i = height * width;
            }
        }
        return v == 0;
    }

    public void notifyChanged() {
        if (!isVisible()) {
            logger.error("notifyChanged(): window not visible");
            return;
        }
        synchronized (this) {
            if (timer == null) {
                logger.error("notifyChanged(): window already disposed");
                return;
            }
            changeCount++;
            if (timerTask == null) {
                timerTask = new RefreshTimerTask(this);
                timer.schedule(timerTask, 40, 40);
            }
        }
    }
    /*
    public void saveRawImage(String dst)
    { 
        int[] rgbArray = backBuffer;

        String filename = dst + "window_c" + counter + "_w" + width + "_h" + height + ".raw";
        counter++;
        
        try { 
            FileOutputStream stream = new FileOutputStream(filename);
            for(int index = 0; index < rgbArray.length; index++)
            {
                int color = rgbArray[index];
                byte argb[] = new byte[] {
                    (byte)((color & 0xff000000) >>> 24), 
                    (byte)((color & 0x00ff0000) >>> 16), 
                    (byte)((color & 0x0000ff00) >>> 8), 
                    (byte)(color & 0x000000ff)
                };
                stream.write(argb);
            }
        }
        catch (IOException e) 
        { 
            e.printStackTrace(); 
        } 
    }   
    private static int counter = 0;
    */
    public void sync() {
        synchronized (this) {
            /* cancel the timer */
            if (timerTask != null) {
                timerTask.cancel();
                timerTask = null;
            }
            changeCount = 0;

            if (!isVisible()) {
                close();
                return;
            }

            //  did something get drawn?
            Area a = dirty.getBoundsAndClear();
            if (!a.isEmpty()) 
            {
                //  we may be out-of-sync since some xlets close after one has started.
                //  so delay opening until we really get something
                if(isBackBufferClear() == true) {
                    //  some xlets draw a menu, then dont close the screen until some preview is done.
                    //  force a close for the menu
                    if(overlayIsOpen == true) {
                        close();                       
                    }
                    return;
                }

                if(overlayIsOpen == false) {
                    Libbluray.updateGraphic(getWidth(), getHeight(), null);
                    overlayIsOpen = true;
                }

                //  update with what we got
                int[] rgbArray = backBuffer;
                Libbluray.updateGraphic(getWidth(), getHeight(), rgbArray,
                                        a.getX0(), a.getY0(), a.getX1(), a.getY1());
            }
        }
    }

    private static class RefreshTimerTask extends TimerTask {
        public RefreshTimerTask(BDRootWindow window) {
            this.window = window;
            this.changeCount = window.changeCount;
        }

        public void run() {
            synchronized (window) {
                if (this.changeCount == window.changeCount)
                    window.sync();
                else
                    this.changeCount = window.changeCount;
            }
        }

        private BDRootWindow window;
        private int changeCount;
    }

    private void close() {
        synchronized (this) {
            if(overlayIsOpen) {
                Libbluray.updateGraphic(0, 0, null);
                overlayIsOpen = false;
            }
        }
    }

    public void setVisible(boolean visible) {

        super.setVisible(visible);

        if (!visible) {
            close();
        }
    }

    /* called when new title starts (window is "created" again) */
    public void clearOverlay() {
        synchronized (this) {
            //  always clear
            dirty.getBoundsAndClear();
            Arrays.fill(backBuffer, 0);

            //  some discs do not set the scene to invisible. so send clear anyway
            //  this will fire a menu close
            if(overlayIsOpen) {
                Libbluray.updateGraphic(0, 0, null);
                overlayIsOpen = false;
            }
        }
    }

    public void dispose()
    {
        logger.info("dispose called for BDRootWindow");
        synchronized (this) {
            if (timerTask != null) {
                timerTask.cancel();
                timerTask = null;
            }
            if (timer != null) {
                timer.cancel();
                timer = null;
            }

            //  call close, so we hide any overlay!
            close();
        }

        if (isVisible()) {
            //  hide window
            hide();
        }

        BDToolkit.setFocusedWindow(null);

        super.dispose();

        backBuffer = null;
    }

    private int[] backBuffer = null;
    private transient Area dirty = new Area();
    private transient int changeCount = 0;
    private transient Timer timer = new Timer();
    private transient TimerTask timerTask = null;
    private Font defaultFont = null;
    private boolean overlayIsOpen = false;

    private static final Logger logger = Logger.getLogger(BDRootWindow.class.getName());

    private static final long serialVersionUID = -8325961861529007953L;
}
