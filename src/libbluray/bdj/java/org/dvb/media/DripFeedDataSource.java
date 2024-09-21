/*
 * This file is part of libbluray
 * Copyright (C) 2010  William Hahne
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

package org.dvb.media;

import javax.media.MediaLocator;
import javax.media.Time;

import javax.media.protocol.DataSource;

public class DripFeedDataSource extends DataSource {
    public DripFeedDataSource() {
        SecurityManager security = System.getSecurityManager();
        if (security != null)
            security.checkPermission(new DripFeedPermission("*"));
    }

    public DripFeedDataSource(MediaLocator source) {
        SecurityManager security = System.getSecurityManager();
        if (security != null)
            security.checkPermission(new DripFeedPermission("*"));
        setLocator(source);
    }

    public void feed(byte[] clip_part) {
        org.videolan.Logger.unimplemented(DripFeedDataSource.class.getName(), "feed");
    }

    public String getContentType() {
        return "video/dvb.mpeg.drip";
    }

    public void connect() {
        org.videolan.Logger.unimplemented(DripFeedDataSource.class.getName(), "connect");
    }

    public void disconnect() {
        org.videolan.Logger.unimplemented(DripFeedDataSource.class.getName(), "disconnect");
    }

    public void start() {
        org.videolan.Logger.unimplemented(DripFeedDataSource.class.getName(), "start");
    }

    public void stop() {
        org.videolan.Logger.unimplemented(DripFeedDataSource.class.getName(), "stop");
    }

    public Time getDuration() {
        org.videolan.Logger.unimplemented(DripFeedDataSource.class.getName(), "getDuration");
        return null;
    }

    public Object[] getControls() {
        org.videolan.Logger.unimplemented(DripFeedDataSource.class.getName(), "getControls");
        return null;
    }

    public Object getControl(String controlType) {
        org.videolan.Logger.unimplemented(DripFeedDataSource.class.getName(), "getControls");
        return null;
    }

    public void setLocator(MediaLocator source) {
        if ((source != null) && ("dripfeed://".equalsIgnoreCase(source.toExternalForm())))
            super.setLocator(source);
    }
}
