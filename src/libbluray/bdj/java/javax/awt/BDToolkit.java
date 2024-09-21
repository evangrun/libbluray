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

package javax.awt;

import java.awt.*;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.Font;
import java.awt.datatransfer.Clipboard;
import java.awt.font.TextAttribute;
import java.awt.im.InputMethodHighlight;
import java.util.Map;
import java.util.Properties;

import javax.awt.NullGraphics;

public class BDToolkit extends BDToolkitBase {

    public BDToolkit () {
    }

    Graphics getGraphics(Window window) {
        if (!(window instanceof BDRootWindow)) {
            return new NullGraphics(window);
        }
        return new BDWindowGraphics((BDRootWindow)window);
    }

    public void sync() {
        Window window = ((BDGraphicsDevice)getLocalGraphicsEnvironment().getDefaultScreenDevice()).getWindow();
        if (window instanceof BDRootWindow)
            ((BDRootWindow)window).sync();
    }

	@Override
	public FontMetrics getFontMetrics(Font font) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintJob getPrintJob(Frame frame, String jobtitle, Properties props) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clipboard getSystemClipboard() throws HeadlessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isModalityTypeSupported(ModalityType modalityType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isModalExclusionTypeSupported(ModalExclusionType modalExclusionType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<TextAttribute, ?> mapInputMethodHighlight(InputMethodHighlight highlight) throws HeadlessException {
		// TODO Auto-generated method stub
		return null;
	}
}
