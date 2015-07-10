/*--------------------------------------------------------------------------*
 | Copyright (C) 2014 Christopher Kohlhaas                                  |
 |                                                                          |
 | This program is free software; you can redistribute it and/or modify     |
 | it under the terms of the GNU General Public License as published by the |
 | Free Software Foundation. A copy of the license has been included with   |
 | these distribution in the COPYING file, if not go to www.fsf.org         |
 |                                                                          |
 | As a special exception, you are granted the permissions to link this     |
 | program with every library, which license fulfills the Open Source       |
 | Definition as published by the Open Source Initiative (OSI).             |
 *--------------------------------------------------------------------------*/
package org.rapla.plugin.weekview;

import javax.swing.Icon;

import org.rapla.facade.CalendarModel;
import org.rapla.facade.RaplaComponent;
import org.rapla.framework.RaplaContext;
import org.rapla.framework.RaplaException;
import org.rapla.gui.SwingCalendarView;
import org.rapla.gui.SwingViewFactory;
import org.rapla.gui.images.RaplaImages;

public class WeekViewFactory extends RaplaComponent implements SwingViewFactory
{
    public WeekViewFactory( RaplaContext context ) 
    {
        super( context );
    }

    public final static String WEEK_VIEW = "week";

    public SwingCalendarView createSwingView(RaplaContext context, CalendarModel model, boolean editable) throws RaplaException
    {
        return new SwingWeekCalendar( context, model, editable);
    }

    public String getViewId()
    {
        return WEEK_VIEW;
    }

    public String getName()
    {
        return getString(WEEK_VIEW);
    }

    Icon icon;
    public Icon getIcon()
    {
        if ( icon == null) {
            icon = RaplaImages.getIcon("/org/rapla/plugin/weekview/images/week.png");
        }
        return icon;
    }

    public String getMenuSortKey() {
        return "B";
    }

}

