/* QuitBox.java
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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.casarini.prbm.gui.dialog;
import java.awt.*;
import java.awt.event.*;

import org.casarini.prbm.util.RunnerConfig;

public class QuitBox extends Dialog implements ActionListener
{
	private static final long serialVersionUID = -1624053868644371450L;

	Frame parent;
    Button yesButton;
    Button noButton;

    public QuitBox(Frame parent)
    {
	    super(parent, "Bim Bum...", true);
	    setResizable(false);

        this.parent=parent;

    	setLayout(null);
    	addNotify();
    	setSize(257,96);
    	yesButton=new Button("Crack");
    	add(yesButton);
    	yesButton.setBounds(66,46,48,26);
    	noButton=new Button("OoPs");
    	add(noButton);
    	noButton.setBounds(132,46,48,26);

    	yesButton.addActionListener(this);
       	noButton.addActionListener(this);

    	Rectangle bounds = getParent().getBounds();
    	Rectangle abounds = getBounds();
    	setLocation(bounds.x + (bounds.width - abounds.width)/ 2,bounds.y + (bounds.height - abounds.height)/2);
    	setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        String cmd=e.getActionCommand();
        if(cmd.equalsIgnoreCase("Crack"))
        {
       	    setVisible(false);
    	    dispose();
            parent.setVisible(false);
            parent.dispose();
            RunnerConfig.getInstance().save();
            System.exit(0);
        }
        else if(cmd.equalsIgnoreCase("OoPs"))
        {
    	    setVisible(false);
    	    dispose();
    	}
    }
}
