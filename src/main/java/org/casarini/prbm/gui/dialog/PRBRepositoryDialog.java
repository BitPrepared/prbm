/* PRBRepositoryDialog.java
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
import java.io.File;

import org.casarini.prbm.gui.PRB;
import org.casarini.prbm.gui.component.PRBRepository;
import org.casarini.prbm.gui.component.Tabella;

public class PRBRepositoryDialog extends Dialog implements WindowListener, ActionListener {

	private static final long serialVersionUID = 4838262205624946946L;

	public PRB parent;

    public ScrollPane c_scrollpane;

    public PRBRepository c_repository;

    public Tabella tab;

    MenuItem me_delAll;

    public PRBRepositoryDialog(PRB parent, Tabella tab) {
        super((Frame) parent, (parent.getRepositoryPath() == null) ? "Raccoglitore" : parent.getRepositoryPath(), false);
        this.parent = parent;
        this.tab = tab;

        setSize(170, 300);
        Rectangle bounds = getParent().getBounds();
        setLocation(bounds.x + bounds.width + 50, bounds.y + 50);

        c_scrollpane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        c_repository = new PRBRepository(this, 140);
        c_scrollpane.add(c_repository);
        this.add(c_scrollpane, "Center");

        this.addWindowListener(this);
    }
    
    /**
     * Imposta il titolo di questo dialog dato il path del repository caricato.
     * @param repositoryPath il path completo del repository caricato.
     */
    public void setTitle(String repositoryPath) {
        super.setTitle(repositoryPath.substring(repositoryPath.lastIndexOf(File.separatorChar)+1));        
    }
    
    // START implementazione WindowListener
    public void windowClosing(WindowEvent e) {
        setVisible(false);
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    // STOP implementazione WindowListener3

    //START implementazione ActionListener
    public void actionPerformed(ActionEvent e) {
    }
    //STOP implementazione ActionListener

}