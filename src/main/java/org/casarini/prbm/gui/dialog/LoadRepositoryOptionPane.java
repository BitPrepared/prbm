/* LoadRepositoryOptionPane.java
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

import org.casarini.prbm.gui.PRB;

/**
 * Dialog modale da visualizzare se viene selezionata l'azione per caricare un
 * nuovo raccoglitore da disco e quello attuale e' in stato modificato.
 * 
 * TODO questa classe come la NewPRBOptionPane e altri OptionPane modali devono
 * essere riscritti sotto una unica classe che gestisca tutti i casi, tipo la
 * JOptionPane delle Swing.
 *
 * @author Paolo Casarini <paolo@casarini.org>
 */
public class LoadRepositoryOptionPane extends Dialog implements ActionListener {

	private static final long serialVersionUID = -28046351008137682L;

	/**
     * Frame principale rispetto a cui essere modale
     */
    PRB parent;

    /**
     * Risultato della dialog
     */
    private int result = PRB.IDCANCEL;

    public LoadRepositoryOptionPane(PRB parent) {
        super((Frame) parent, "Carica Raccoglitore...", true);
        setResizable(false);
        setBackground(Color.lightGray);

        this.parent = parent;

        //{{INIT_CONTROLS
        setLayout(null);
        addNotify();
        setSize(getInsets().left + getInsets().right + 379, getInsets().top
                + getInsets().bottom + 102);
        Button OKButton = new Button("OK");
        add(OKButton);
        OKButton.setBounds(getInsets().left + 68, getInsets().top + 65, 91, 26);
        Button NOButton = new Button("NO");
        add(NOButton);
        NOButton
                .setBounds(getInsets().left + 215, getInsets().top + 65, 91, 26);
        Label label1 = new Label(
                "Caricando un nuovo Repository, si perderanno le modifiche non");
        add(label1);
        label1.setBounds(getInsets().left + 7, getInsets().top + 13, 357, 13);
        Label label2 = new Label(
                "salvate del repository corrente. Vuoi continuare?");
        add(label2);
        label2.setBounds(getInsets().left + 7, getInsets().top + 33, 357, 13);
        //}}
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
     * @return <tt>PRB.IDOK</tt> se e' stato premuto il bottone per la risposta affermativa; <tt>IDCANCEL</tt> altrimenti.
     */
    public int getResult() {
        return result;
    }
}