/*
 * This file is part of libbluray
 * Copyright (C) 2014  libbluray
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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

class BDGraphics extends BDGraphicsBase {
    BDGraphics(BDGraphics g) {
        super(g);
    }

    BDGraphics(BDRootWindow window) {
        super(window);
    }

    BDGraphics(BDImage image) {
        super(image);
    }

	@Override
	public void draw(Shape s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawString(String str, float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawString(AttributedCharacterIterator iterator, float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawGlyphVector(GlyphVector g, float x, float y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fill(Shape s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPaint(Paint paint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRenderingHint(Key hintKey, Object hintValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getRenderingHint(Key hintKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRenderingHints(Map<?, ?> hints) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRenderingHints(Map<?, ?> hints) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RenderingHints getRenderingHints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void translate(double tx, double ty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotate(double theta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotate(double theta, double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scale(double sx, double sy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shear(double shx, double shy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transform(AffineTransform Tx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTransform(AffineTransform Tx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AffineTransform getTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Paint getPaint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBackground(Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getBackground() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clip(Shape s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FontRenderContext getFontRenderContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFont(Font font) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FontMetrics getFontMetrics(Font f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setClip(Shape clip) {
		// TODO Auto-generated method stub
		
	}
}

