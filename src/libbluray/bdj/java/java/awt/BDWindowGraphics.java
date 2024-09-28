/*
 * This file is part of libbluray
 * Copyright (C) 2012  libbluray
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

import java.awt.image.ImageObserver;

import org.videolan.Logger;

//  this is called by the xlets to draw things on the screen
//  when something is drawn, we notify the api
//  changes are collected (on a Timer) and then the host is called
//  with the full buffer
public class BDWindowGraphics extends BDGraphics {
    private BDRootWindow window;

    BDWindowGraphics(BDWindowGraphics g) {
        super(g);
        window = g.window;
//        logger.info("Constructed a new BDWindowGraphics for window");
    }

    public BDWindowGraphics(BDRootWindow window) {
        super(window);
//        logger.info("Constructed a new BDWindowGraphics for window");
        this.window = window;
    }

    public Graphics create() {
//        logger.info("Constructed a new BDWindowGraphics");
        return new BDWindowGraphics(this);
    }

    public void clearRect(int x, int y, int w, int h) {
        if (window == null) {
            logger.info("clearRect, but window is NULL!");
            return;
        }
        logger.info("clearRect for window");
        synchronized (window) {
            super.clearRect(x, y, w, h);
            window.notifyChanged();
        }
    }

    public void fillRect(int x, int y, int w, int h) {
        if (window == null) {
            logger.info("fillRect, but window is NULL!");
            return;
        }
        logger.info("fillRect for window");
        synchronized (window) {
            super.fillRect(x, y, w, h);
            window.notifyChanged();
        }
    }

    public void drawRect(int x, int y, int w, int h) {
        if (window == null) {
            logger.info("drawRect, but window is NULL!");
            return;
        }
        logger.info("drawRect for window");
        synchronized (window) {
            super.drawRect(x, y, w, h);
            window.notifyChanged();
        }
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        if (window == null) {
            logger.info("drawLine, but window is NULL!");
            return;
        }
        logger.info("drawLine for window");
        synchronized (window) {
            super.drawLine(x1, y1, x2, y2);
            window.notifyChanged();
        }
    }

    public void copyArea(int x, int y, int w, int h, int dx, int dy) {
        if (window == null) {
            logger.info("copyArea, but window is NULL!");
            return;
        }
        logger.info("copyArea for window");
        synchronized (window) {
            super.copyArea(x, y, w, h, dx, dy);
            window.notifyChanged();
        }
    }

    public void drawPolyline(int xPoints[], int yPoints[], int nPoints) {
        if (window == null) {
            logger.info("drawPolyline, but window is NULL!");
            return;
        }
        logger.info("drawPolyline for window");
        synchronized (window) {
            super.drawPolyline(xPoints, yPoints, nPoints);
            window.notifyChanged();
        }
    }

    public void drawPolygon(int xPoints[], int yPoints[], int nPoints) {
        if (window == null) {
            logger.info("drawPolygon, but window is NULL!");
            return;
        }
        logger.info("drawPolygon for window");
        synchronized (window) {
            super.drawPolygon(xPoints, yPoints, nPoints);
            window.notifyChanged();
        }
    }

    public void fillPolygon(int xPoints[], int yPoints[], int nPoints) {
        if (window == null) {
            logger.info("fillPolygon, but window is NULL!");
            return;
        }
        logger.info("fillPolygon for window");
        synchronized (window) {
            super.fillPolygon(xPoints, yPoints, nPoints);
            window.notifyChanged();
        }
    }

    public void drawOval(int x, int y, int w, int h) {
        if (window == null) {
            logger.info("drawOval, but window is NULL!");
            return;
        }
        logger.info("drawOval for window");
        synchronized (window) {
            super.drawOval(x, y, w, h);
            window.notifyChanged();
        }
    }

    public void fillOval(int x, int y, int w, int h) {
        if (window == null) {
            logger.info("fillOval, but window is NULL!");
            return;
        }
        logger.info("fillOval for window");
        synchronized (window) {
            super.fillOval(x, y, w, h);
            window.notifyChanged();
        }
    }

    public void drawArc(int x, int y, int w, int h, int startAngle, int endAngle) {
        if (window == null) {
            logger.info("drawArc, but window is NULL!");
            return;
        }
        logger.info("drawArc for window");
        synchronized (window) {
            super.drawArc(x, y, w, h, startAngle, endAngle);
            window.notifyChanged();
        }
    }

    public void fillArc(int x, int y, int w, int h, int startAngle, int endAngle) {
        if (window == null) {
            logger.info("fillArc, but window is NULL!");
            return;
        }
        logger.info("fillArc for window");
        synchronized (window) {
            super.fillArc(x, y, w, h, startAngle, endAngle);
            window.notifyChanged();
        }
    }

    public void drawRoundRect(int x, int y, int w, int h, int arcWidth, int arcHeight) {
        if (window == null) {
            logger.info("drawRoundRect, but window is NULL!");
            return;
        }
        logger.info("drawRoundRect for window");
        synchronized (window) {
            super.drawRoundRect(x, y, w, h, arcWidth, arcHeight);
            window.notifyChanged();
        }
    }

    public void fillRoundRect(int x, int y, int w, int h, int arcWidth, int arcHeight) {
        if (window == null) {
            logger.info("fillRoundRect, but window is NULL!");
            return;
        }
        logger.info("fillRoundRect for window");
        synchronized (window) {
            super.fillRoundRect(x, y, w, h, arcWidth, arcHeight);
            window.notifyChanged();
        }
    }

    protected void drawStringN(long ftFace, String string, int x, int y, int rgb) {
        if (window == null) {
            logger.info("drawStringN, but window is NULL!");
            return;
        }
        logger.info("drawStringN for window");
        synchronized (window) {
            super.drawStringN(ftFace, string, x, y, rgb);
            window.notifyChanged();
        }
    }

    //  this is called from baseclass first, so we update the sync with notifyChanged()
    public boolean drawImageN(Image img,
        int dx, int dy, int dw, int dh,
        int sx, int sy, int sw, int sh,
        boolean flipX, boolean flipY,
        Color bg, ImageObserver observer) {

        if (window == null) {
            logger.info("drawImageN, but window is NULL!");
            return false;
        }

        synchronized (window) {
            boolean complete = super.drawImageN(
                img, 
                dx, dy, dw, dh, 
                sx, sy, sw, sh,
                flipX, flipY,
                bg, observer);
            if (complete) {
                window.notifyChanged();
            }
            return complete;
        }
    }

    public void dispose() {
        super.dispose();
        window = null;
    }

    private static final Logger logger = Logger.getLogger(BDWindowGraphics.class.getName());
}
