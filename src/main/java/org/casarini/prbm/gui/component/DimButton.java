/* DimButton.java
 * 
 * Copyright (C) 2004 Paolo Casarini <paolo@casarini.org>
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.casarini.prbm.gui.component;
import java.awt.Button;
import java.awt.Dimension;

public class DimButton extends Button
{
	private static final long serialVersionUID = 4758108062894728467L;

	Dimension d;
    public DimButton(int w,int h)
    {
        super();
        d=new Dimension(w,h);
    }
    public Dimension getPreferredSize()
    {
        return d;
    }
}