/*
* @(#)NullGraphics.java	1.13 06/10/10
*
* Copyright  1990-2006 Sun Microsystems, Inc. All Rights Reserved.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
* 
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License version
* 2 only, as published by the Free Software Foundation. 
* 
* This program is distributed in the hope that it will be useful, but
* WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* General Public License version 2 for more details (a copy is
* included at /legal/license.txt). 
* 
* You should have received a copy of the GNU General Public License
* version 2 along with this work; if not, write to the Free Software
* Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA 
* 
* Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa
* Clara, CA 95054 or visit www.sun.com if you need additional
* information or have any questions. 
*
*/

package javax.awt;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

/**
A Graphics object which ignores all draw requests. This is useful
to ensure that a displayable, but not visible, component can get a graphics
object to draw onto without drawing onto the screen.
@author Nicholas Allen
@version 1.7, 08/19/02
*/

    public class NullGraphics extends Graphics {
    private Color color;
    private Rectangle clipBounds;

    public NullGraphics(Component component) {
        color = component.getForeground();
        if (color == null)
            color = Color.black;
    }

    private NullGraphics(NullGraphics g) {
        color = g.color;
        clipBounds = g.clipBounds;
    }

    /**
        * Creates a new <code>Graphics</code> object that is
        * a copy of this <code>Graphics</code> object.
        * @return     a new graphics context that is a copy of
        *                       this graphics context.
        * @since      JDK1.0
        */
    public Graphics create() {
        return new NullGraphics(this );
    }

    /**
        * Translates the origin of the graphics context to the point
        * (<i>x</i>,&nbsp;<i>y</i>) in the current coordinate system.
        * Modifies this graphics context so that its new origin corresponds
        * to the point (<i>x</i>,&nbsp;<i>y</i>) in this graphics context's
        * original coordinate system.  All coordinates used in subsequent
        * rendering operations on this graphics context will be relative
        * to this new origin.
        * @param  x   the <i>x</i> coordinate.
        * @param  y   the <i>y</i> coordinate.
        * @since   JDK1.0
        */
    public void translate(int x, int y) {
        if (clipBounds != null) {
            clipBounds.x -= x;
            clipBounds.y -= y;
        }
    }

    /**
        * Gets this graphics context's current color.
        * @return    this graphics context's current color.
        * @see       java.awt.Color
        * @see       java.awt.Graphics#setColor
        * @since     JDK1.0
        */
    public Color getColor() {
        return color;
    }

    /**
        * Sets this graphics context's current color to the specified
        * color. All subsequent graphics operations using this graphics
        * context use this specified color.
        * @param     c   the new rendering color.
        * @see       java.awt.Color
        * @see       java.awt.Graphics#getColor
        * @since     JDK1.0
        */
    public void setColor(Color c) {
        if (c == null)
            color = Color.black;
        else
            color = c;
    }

    /**
        * Sets the paint mode of this graphics context to overwrite the
        * destination with this graphics context's current color.
        * This sets the logical pixel operation function to the paint or
        * overwrite mode.  All subsequent rendering operations will
        * overwrite the destination with the current color.
        * @since   JDK1.0
        */
    public void setPaintMode() {
    }

    /**
        * This method sets the graphics context to xor paint mode using
        * the "exclusive or"  color xorcolor.
        * This specifies that logical pixel operations are performed in the
        * XOR mode, which alternates pixels between the current color and
        * a specified XOR color.
        * <p>
        * When drawing operations are performed, pixels which are the
        * current color are changed to the specified color, and vice versa.
        * <p>
        * Pixels that are of colors other than those two colors are changed
        * in an unpredictable but reversible manner; if the same figure is
        * drawn twice, then all pixels are restored to their original values.
        * <h3>Compatibility</h3>
        * Both PersonalJava and Personal Profile implementations are not required
        * to support this method.
        * <h3>System Properties</h3>
        * The System Property <code>java.awt.graphics.SupportsXorMode</code> is set to
        * <code>"true"</code> or <code>"false"</code> indicating if the platform supports
        * XOR rendering.
        * @param     c1 the XOR alternation color
        * @exception <code>UnsupportedOperationException</code> if the implementation does
        *             not support an XOR paint mode.
        * @since     JDK1.0
        */
    public void setXORMode(Color c1) {
    }

    /**
        * Gets the current font.
        * @return    this graphics context's current font.
        * @see       java.awt.Font
        * @see       java.awt.Graphics#setFont
        * @since     JDK1.0
        */
    public java.awt.Font getFont() {
        return null;
    }
    
    /**
        * Returns the bounding rectangle of the current clipping area.
        * The coordinates in the rectangle are relative to the coordinate
        * system origin of this graphics context.
        * @return      the bounding rectangle of the current clipping area.
        * @see         java.awt.Graphics#getClip
        * @see         java.awt.Graphics#clipRect
        * @see         java.awt.Graphics#setClip(int, int, int, int)
        * @see         java.awt.Graphics#setClip(Shape)
        * @since       JDK1.1
        */
    public Rectangle getClipBounds() {
        return clipBounds;
    }

    /**
        * Intersects the current clip with the specified rectangle.
        * The resulting clipping area is the intersection of the current
        * clipping area and the specified rectangle.
        * This method can only be used to make the current clip smaller.
        * To set the current clip larger, use any of the setClip methods.
        * Rendering operations have no effect outside of the clipping area.
        * @param x the x coordinate of the rectangle to intersect the clip with
        * @param y the y coordinate of the rectangle to intersect the clip with
        * @param width the width of the rectangle to intersect the clip with
        * @param height the height of the rectangle to intersect the clip with
        * @see #setClip(int, int, int, int)
        * @see #setClip(Shape)
        */
    public void clipRect(int x, int y, int width, int height) {
        Rectangle rect = new Rectangle(x, y, width, height);
        if (clipBounds == null)
            clipBounds = rect;
        else
            clipBounds = clipBounds.union(rect);
    }

    /**
        * Sets the current clip to the rectangle specified by the given
        * coordinates.
        * Rendering operations have no effect outside of the clipping area.
        * @param       x the <i>x</i> coordinate of the new clip rectangle.
        * @param       y the <i>y</i> coordinate of the new clip rectangle.
        * @param       width the width of the new clip rectangle.
        * @param       height the height of the new clip rectangle.
        * @see         java.awt.Graphics#clipRect
        * @see         java.awt.Graphics#setClip(Shape)
        * @since       JDK1.1
        */
    public void setClip(int x, int y, int width, int height) {
        clipBounds = new Rectangle(x, y, width, height);
    }

    /**
        * Gets the current clipping area.
        * @return      a <code>Shape</code> object representing the
        *                      current clipping area.
        * @see         java.awt.Graphics#getClipBounds
        * @see         java.awt.Graphics#clipRect
        * @see         java.awt.Graphics#setClip(int, int, int, int)
        * @see         java.awt.Graphics#setClip(Shape)
        * @since       JDK1.1
        */
    public Shape getClip() {
        return clipBounds;
    }

    /**
        * Sets the current clipping area to an arbitrary clip shape.
        * Not all objects which implement the <code>Shape</code>
        * interface can be used to set the clip.  The only
        * <code>Shape</code> objects which are guaranteed to be
        * supported are <code>Shape</code> objects which are
        * obtained via the <code>getClip</code> method and via
        * <code>Rectangle</code> objects.
        * @see         java.awt.Graphics#getClip()
        * @see         java.awt.Graphics#clipRect
        * @see         java.awt.Graphics#setClip(int, int, int, int)
        * @since       JDK1.1
        */
    public void setClip(Shape clip) {
        if (!(clip instanceof  Rectangle))
            throw new AWTError(
                    "Only rectangular clip regions supported");
        clipBounds = (Rectangle) clip;
    }

    /**
        * Copies an area of the component by a distance specified by
        * <code>dx</code> and <code>dy</code>. From the point specified
        * by <code>x</code> and <code>y</code>, this method
        * copies downwards and to the right.  To copy an area of the
        * component to the left or upwards, specify a negative value for
        * <code>dx</code> or <code>dy</code>.
        * If a portion of the source rectangle lies outside the bounds
        * of the component, or is obscured by another window or component,
        * <code>copyArea</code> will be unable to copy the associated
        * pixels. The area that is omitted can be refreshed by calling
        * the component's <code>paint</code> method.
        * @param       x the <i>x</i> coordinate of the source rectangle.
        * @param       y the <i>y</i> coordinate of the source rectangle.
        * @param       width the width of the source rectangle.
        * @param       height the height of the source rectangle.
        * @param       dx the horizontal distance to copy the pixels.
        * @param       dy the vertical distance to copy the pixels.
        * @since       JDK1.0
        */
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
    }

    /**
        * Draws a line, using the current color, between the points
        * <code>(x1,&nbsp;y1)</code> and <code>(x2,&nbsp;y2)</code>
        * in this graphics context's coordinate system.
        * @param   x1  the first point's <i>x</i> coordinate.
        * @param   y1  the first point's <i>y</i> coordinate.
        * @param   x2  the second point's <i>x</i> coordinate.
        * @param   y2  the second point's <i>y</i> coordinate.
        * @since   JDK1.0
        */
    public void drawLine(int x1, int y1, int x2, int y2) {
    }

    /**
        * Fills the specified rectangle.
        * The left and right edges of the rectangle are at
        * <code>x</code> and <code>x&nbsp;+&nbsp;width&nbsp;-&nbsp;1</code>.
        * The top and bottom edges are at
        * <code>y</code> and <code>y&nbsp;+&nbsp;height&nbsp;-&nbsp;1</code>.
        * The resulting rectangle covers an area
        * <code>width</code> pixels wide by
        * <code>height</code> pixels tall.
        * The rectangle is filled using the graphics context's current color.
        * @param         x   the <i>x</i> coordinate
        *                         of the rectangle to be filled.
        * @param         y   the <i>y</i> coordinate
        *                         of the rectangle to be filled.
        * @param         width   the width of the rectangle to be filled.
        * @param         height   the height of the rectangle to be filled.
        * @see           java.awt.Graphics#fillRect
        * @see           java.awt.Graphics#clearRect
        * @since         JDK1.0
        */
    public void fillRect(int x, int y, int width, int height) {
    }

    /**
        * Clears the specified rectangle by filling it with the background
        * color of the current drawing surface. This operation does not
        * use the current paint mode.
        * <p>
        * Beginning with Java&nbsp;1.1, the background color
        * of offscreen images may be system dependent. Applications should
        * use <code>setColor</code> followed by <code>fillRect</code> to
        * ensure that an offscreen image is cleared to a specific color.
        * @param       x the <i>x</i> coordinate of the rectangle to clear.
        * @param       y the <i>y</i> coordinate of the rectangle to clear.
        * @param       width the width of the rectangle to clear.
        * @param       height the height of the rectangle to clear.
        * @see         java.awt.Graphics#fillRect(int, int, int, int)
        * @see         java.awt.Graphics#drawRect
        * @see         java.awt.Graphics#setColor(java.awt.Color)
        * @see         java.awt.Graphics#setPaintMode
        * @see         java.awt.Graphics#setXORMode(java.awt.Color)
        * @since       JDK1.0
        */
    public void clearRect(int x, int y, int width, int height) {
    }

    /**
        * Draws an outlined round-cornered rectangle using this graphics
        * context's current color. The left and right edges of the rectangle
        * are at <code>x</code> and <code>x&nbsp;+&nbsp;width</code>,
        * respectively. The top and bottom edges of the rectangle are at
        * <code>y</code> and <code>y&nbsp;+&nbsp;height</code>.
        * @param      x the <i>x</i> coordinate of the rectangle to be drawn.
        * @param      y the <i>y</i> coordinate of the rectangle to be drawn.
        * @param      width the width of the rectangle to be drawn.
        * @param      height the height of the rectangle to be drawn.
        * @param      arcWidth the horizontal diameter of the arc
        *                    at the four corners.
        * @param      arcHeight the vertical diameter of the arc
        *                    at the four corners.
        * @see        java.awt.Graphics#fillRoundRect
        * @since      JDK1.0
        */
    public void drawRoundRect(int x, int y, int width, int height,
            int arcWidth, int arcHeight) {
    }

    /**
        * Fills the specified rounded corner rectangle with the current color.
        * The left and right edges of the rectangle
        * are at <code>x</code> and <code>x&nbsp;+&nbsp;width&nbsp;-&nbsp;1</code>,
        * respectively. The top and bottom edges of the rectangle are at
        * <code>y</code> and <code>y&nbsp;+&nbsp;height&nbsp;-&nbsp;1</code>.
        * @param       x the <i>x</i> coordinate of the rectangle to be filled.
        * @param       y the <i>y</i> coordinate of the rectangle to be filled.
        * @param       width the width of the rectangle to be filled.
        * @param       height the height of the rectangle to be filled.
        * @param       arcWidth the horizontal diameter
        *                     of the arc at the four corners.
        * @param       arcHeight the vertical diameter
        *                     of the arc at the four corners.
        * @see         java.awt.Graphics#drawRoundRect
        * @since       JDK1.0
        */
    public void fillRoundRect(int x, int y, int width, int height,
            int arcWidth, int arcHeight) {
    }

    public void draw3DRect(int x, int y, int width, int height,
            boolean raised) {
    }

    public void fill3DRect(int x, int y, int width, int height,
            boolean raised) {
    }

    public void drawOval(int x, int y, int width, int height) {
    }

    public void fillOval(int x, int y, int width, int height) {
    }

    public void drawArc(int x, int y, int width, int height,
            int startAngle, int arcAngle) {
    }

    public void fillArc(int x, int y, int width, int height,
            int startAngle, int arcAngle) {
    }

    public void drawPolyline(int xPoints[], int yPoints[], int nPoints) {
    }

    public void drawPolygon(int xPoints[], int yPoints[], int nPoints) {
    }

    public void drawPolygon(Polygon p) {
    }

    public void fillPolygon(int xPoints[], int yPoints[], int nPoints) {
    }

    public void fillPolygon(Polygon p) {
    }

    public void drawString(String str, int x, int y) {
    }

    public void drawString(AttributedCharacterIterator iterator, int x,
            int y) {
    }

    public void drawChars(char data[], int offset, int length, int x,
            int y) {
    }

    public void drawBytes(byte data[], int offset, int length, int x,
            int y) {
    }

    public boolean drawImage(Image img, int x, int y,
            ImageObserver observer) {
        return false;
    }

    public boolean drawImage(Image img, int x, int y, int width,
            int height, ImageObserver observer) {
        return false;
    }

    public boolean drawImage(Image img, int x, int y, Color bgcolor,
            ImageObserver observer) {
        return false;
    }

    public boolean drawImage(Image img, int x, int y, int width,
            int height, Color bgcolor, ImageObserver observer) {
        return false;
    }

    public boolean drawImage(Image img, int dx1, int dy1, int dx2,
            int dy2, int sx1, int sy1, int sx2, int sy2,
            ImageObserver observer) {
        return false;
    }

    public boolean drawImage(Image img, int dx1, int dy1, int dx2,
            int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor,
            ImageObserver observer) {
        return false;
    }

    public void dispose() {
    }

	@Override
	public void setFont(java.awt.Font font) {
	}

	@Override
	public FontMetrics getFontMetrics(java.awt.Font f) {
		return null;
	}
}
