/* AttentionDialog.java
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

package org.casarini.prbm.gui.dialog;
import java.awt.*;
import java.awt.event.*;

public class AttentionDialog extends Dialog implements ActionListener
{
	private static final long serialVersionUID = 6710363066677840783L;

	Label label1;
	Button okButton;

	public AttentionDialog(Frame parent, String title, String message)
	{
		super(parent, title, true);
		setBackground(Color.lightGray);
        Font font=new Font("Dialog", Font.BOLD, 12);
    	addNotify();
		label1 = new Label(message);
		Panel p1=new Panel();
		p1.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		p1.add(label1);
		add(p1,"Center");
		
		okButton = new Button("OK");
		okButton.setFont(font);
		okButton.addActionListener(this);
		Panel p=new Panel();
		p.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		p.add(okButton);
		add(p,"South");
		this.pack();

		Rectangle bounds = getParent().getBounds();
		Rectangle abounds = getBounds();

		setLocation(bounds.x + (bounds.width - abounds.width)/ 2,
			        bounds.y + (bounds.height - abounds.height)/2);

		super.setVisible(true);
	}

	public void display(){;}

    public void actionPerformed(ActionEvent e)
    {
            setVisible(false);
            dispose();
    }
}