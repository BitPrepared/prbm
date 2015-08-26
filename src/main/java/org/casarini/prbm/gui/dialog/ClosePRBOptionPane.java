/* ClosePRBOptionPane.java
 * 
 * Copyright (C) 2007 Paolo Casarini <paolo@casarini.org>
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

import org.casarini.prbm.gui.PRB;

public class ClosePRBOptionPane extends Dialog implements ActionListener {

	private static final long serialVersionUID = -4307978254868442803L;
	private static final int LINE_HEIGHT = 20;

	/**
     * Frame principale rispetto a cui essere modale
     */
    PRB parent;

    /**
     * Risultato della dialog
     */
    private int result = PRB.IDCANCEL;

    public ClosePRBOptionPane(PRB parent, String title, String message) {
        super((Frame) parent, title, true);
        setResizable(false);
        setBackground(Color.lightGray);

        this.parent = parent;
        
        String[] msgLines = message.split("\\n");

        // TODO: il layout è da rifare in maniera più flessibile
        setLayout(null);
        addNotify();
        setSize(getInsets().left + getInsets().right + 379, getInsets().top
                + getInsets().bottom + 62 + (msgLines.length * LINE_HEIGHT));
        Button OKButton = new Button("OK");
        add(OKButton);
        OKButton.setBounds(getInsets().left + 68, getInsets().top + 25 + (msgLines.length * LINE_HEIGHT), 91, 26);
        Button NOButton = new Button("NO");
        add(NOButton);
        NOButton.setBounds(getInsets().left + 215, getInsets().top + 25 + (msgLines.length * LINE_HEIGHT), 91, 26);
        
        for (int i = 0; i < msgLines.length; i++) {
        	Label label = new Label(msgLines[i]);
        	add(label);
        	label.setBounds(getInsets().left + 7, getInsets().top + 13 +(i * LINE_HEIGHT), 357, 13);
        }

        OKButton.addActionListener(this);
        NOButton.addActionListener(this);
    }

    public void setVisible(boolean visible) {
        if (visible) {
            Rectangle bounds = getParent().getBounds();
            Rectangle abounds = getBounds();
    
            setLocation(bounds.x + (bounds.width - abounds.width) / 2, bounds.y
                    + (bounds.height - abounds.height) / 2);
        }
        super.setVisible(visible);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equalsIgnoreCase("Ok"))
            result = PRB.IDOK;
        else if (cmd.equalsIgnoreCase("No"))
            result = PRB.IDCANCEL;
        setVisible(false);
    }

    /**
     * Restituisce il risultato della dialog.
     * 
     * @return <tt>PRB.IDOK</tt> se e' stato premuto il bottone per la
     *         risposta affermativa; <tt>IDCANCEL</tt> altrimenti.
     */
    public int getResult() {
        return result;
    }
}

