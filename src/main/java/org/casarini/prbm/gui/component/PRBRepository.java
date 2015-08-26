/* PRBRepository.java
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

import java.awt.*;
import java.awt.event.*;

import org.casarini.prbm.gui.dialog.DialogEditAlbero;
import org.casarini.prbm.gui.dialog.DialogEditAmbienteNaturale;
import org.casarini.prbm.gui.dialog.DialogEditCuriosita;
import org.casarini.prbm.gui.dialog.DialogEditFatto;
import org.casarini.prbm.gui.dialog.DialogEditFauna;
import org.casarini.prbm.gui.dialog.DialogEditFiore;
import org.casarini.prbm.gui.dialog.DialogEditIntervista;
import org.casarini.prbm.gui.dialog.DialogEditMeteo;
import org.casarini.prbm.gui.dialog.DialogEditMonumento;
import org.casarini.prbm.gui.dialog.DialogEditPaesaggio;
import org.casarini.prbm.gui.dialog.PRBRepositoryDialog;
import org.casarini.prbm.model.PRBRepositoryData;
import org.casarini.prbm.model.Resource;

public class PRBRepository extends Canvas implements MouseListener, ActionListener {
	private static final long serialVersionUID = 7473054035505593972L;

	//costanti
    public static final int HRES = 15; //altezza risorsa

    public static final int RICALCOLA = 1;

    //DATA
    public PRBRepositoryDialog parent;

    public PRBRepositoryData dr;

    //dimensioni della griglia
    int m_size_col; //larghezza della colonna

    int m_inithspace; //spazio in alto

    int m_initvspace; //spazio a sinistra

    //Double-buffering
    Graphics m_Gbuffer; //Graphics associato

    Image m_Ibuffer; //immagine per il double-buffering

    //Font&Immagini
    Font m_FontBold; //Font grassetto

    Font m_FontNormal; //Font normale

    //gestione selezione
    boolean m_selected = false; //se e' selezionata una cella

    int m_sel_res = -1; //risorsa selezionata

    int m_sx1, m_sx2, m_sy1, m_sy2; //coordinate rettangolo selezionato

    //gestione tasto destro
    boolean m_premuto = false; //se e' stato premuto il tasto destro

    //gestione della copia
    Resource clipboard = null;

    //gestione menu popup
    String[] reslabel = new String[] { "Modifica", "Cancella", "Copia",
            "Incolla Prima", "Incolla Dopo" };

    String[] rescommand = new String[] { "rmodifica", "rcancella", "rcopia",
            "rincolla_p", "rincolla_d" };

    String[] residlabel = new String[] { "Simbolo Paesaggio", "Fiore/Erba",
            "Albero/Arbusto", "Fauna", "Ambiente Naturale", "Meteo",
            "Monumento/Luogo Storico", "Intervista", "Fatto di Cronaca",
            "Curiosita'/Osservazione" };

    String[] residcommand = new String[] { "PAE", "FIO", "ALB", "FAU", "AMB",
            "MET", "MON", "INT", "CRO", "CUR" };

    PopupMenu m_Mres;

    /** ************************************************************************** */
    /* Costruttore della classe */
    /** ************************************************************************** */
    public PRBRepository(PRBRepositoryDialog parent, int size_col) {
        super();

        //init var di istanza
        dr = new PRBRepositoryData();
        this.parent = parent;
        m_size_col = size_col;
        m_inithspace = 20;
        m_initvspace = 5;

        m_FontBold = new Font("TimesNewRoman", Font.BOLD, 12);
        m_FontNormal = new Font("TimesNewRoman", Font.PLAIN, 12);

        resize();

        //creiamoci i menu
        m_Mres = new PopupMenu();
        Menu resid_p = new Menu("Aggiungi Prima ");
        m_Mres.add(resid_p);
        for (int i = 0; i < residlabel.length; i++) {
            MenuItem mi = new MenuItem(residlabel[i]);
            mi.setActionCommand(residcommand[i] + "_P");
            mi.addActionListener(this);
            resid_p.add(mi);
        }
        Menu resid_d = new Menu("Aggiungi Dopo ");
        m_Mres.add(resid_d);
        for (int i = 0; i < residlabel.length; i++) {
            MenuItem mi = new MenuItem(residlabel[i]);
            mi.setActionCommand(residcommand[i] + "_D");
            mi.addActionListener(this);
            resid_d.add(mi);
        }
        for (int i = 0; i < reslabel.length; i++) {
            MenuItem mi = new MenuItem(reslabel[i]);
            mi.setActionCommand(rescommand[i]);
            mi.addActionListener(this);
            m_Mres.add(mi);
        }

        this.add(m_Mres);
        this.addMouseListener(this);
    }

    /** ************************************************************************** */
    /* resize: calcola le altezze dei passi e setta la dimensione dell'oggetto */
    /** ************************************************************************** */
    public void resize() {
        setSize(m_size_col, 500);
        parent.c_scrollpane.doLayout();
    }

    /** ************************************************************************** */
    /* fillResource: ridisegna tutte le risorse */
    /** ************************************************************************** */
    void fillResource(int mode) {
        for (int k = 0; k < dr.getHeight(); k++) {
            Resource r = dr.getResource(k);
            if (mode == RICALCOLA)
                r.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(r.n, 0, k * HRES, this);
        }
    }

    /** ************************************************************************** */
    /* paintSelection: colora la selezione corrente */
    /** ************************************************************************** */
    void paintSelection() {
        Graphics g = this.getGraphics();

        if (m_sel_res != -1) {
            Resource r = dr.getResource(m_sel_res);
            g.drawImage(r.y, 0, m_sel_res * HRES, this);
            m_Gbuffer.drawImage(r.y, 0, m_sel_res * HRES, this);
        }
    }

    /** ************************************************************************** */
    /* clearSelection: cancella la selezione su video e nei dati */
    /** ************************************************************************** */
    void clearSelection() {
        Graphics g = this.getGraphics();

        if (m_sel_res != -1) {
            Resource r = dr.getResource(m_sel_res);
            g.drawImage(r.n, 0, m_sel_res * HRES, this);
            m_Gbuffer.drawImage(r.n, 0, m_sel_res * HRES, this);
        }
        m_selected = false;
        m_sel_res = -1;
    }

    /** ************************************************************************** */
    /* setSelection: setta le variabili m_sel_col,m_sel_passo,m_sel_res in base */
    /* il punto x,y */
    /** ************************************************************************** */
    void setSelection(int x, int y) {
        //System.out.println ("Coord: "+x+" "+y);
        if (y >= 0 && y <= dr.getHeight() * HRES) {
            m_sel_res = y / HRES;
            m_selected = true;
        } else {
            m_sel_res = -1;
        }
    }

    /** ************************************************************************** */
    /* addNewResourse: aggiunge una risorsa di tipo type e titolo title nella */
    /* nella cella selezionata */
    /** ************************************************************************** */
    int addNewResource(int type, String title, boolean prima) {
        int pos;

        if (m_sel_res == -1)
            if (prima)
                pos = 0;
            else
                pos = dr.getHeight();
        else if (prima)
            pos = m_sel_res;
        else
            pos = m_sel_res + 1;
        dr.addResource(this, type, title, pos);

        m_Gbuffer.clearRect(0, 0, m_size_col, 500);
        resize();
        fillResource(0);

        paintSelection();
        
        parent.parent.setRepositoryModified(true);
        //        paint(getGraphics());
        return pos;
    }

    /** ************************************************************************** */
    /* addClipboardResourse: aggiunge la risorsa che e' nella Clipboard nella */
    /* cella selezionata */
    /** ************************************************************************** */
    void addClipboardResource(boolean prima) {
        int pos;
        Resource r = parent.tab.clipboard.copyRes(parent.tab);

        if (m_sel_res == -1)
            if (prima)
                pos = 0;
            else
                pos = dr.getHeight();
        else if (prima)
            pos = m_sel_res;
        else
            pos = m_sel_res + 1;
        dr.addResource(r, pos);

        m_Gbuffer.clearRect(0, 0, m_size_col, 500);
        resize();
        fillResource(0);

        paintSelection();
        parent.parent.setRepositoryModified(true);
        //        paint(getGraphics());
        //		return pos;
    }

    /** ************************************************************************** */
    /* deleteResourse: toglie risorsa selezionata */
    /** ************************************************************************** */
    void deleteResource() {
        dr.cutResource(m_sel_res);
        m_Gbuffer.clearRect(0, 0, m_size_col, 500);
        fillResource(0);
        resize();

        m_sel_res = -1;
        m_selected = false;
        paintSelection();

        parent.parent.setRepositoryModified(true);
        //        paint(getGraphics());
    }

    /** ************************************************************************** */
    /* deleteAllResource: toglie dal passo selezionato tutte le risorse */
    /** ************************************************************************** */
    void deleteAllResource() {
        dr.delAllResource();
        m_Gbuffer.clearRect(0, 0, m_size_col, 500);
        resize();
        fillResource(0);
        paintSelection();

        parent.parent.setRepositoryModified(true);
    }

    public void setRepositoryData(PRBRepositoryData newdr) {
        dr = null;
        dr = newdr;
        m_Gbuffer.clearRect(0, 0, m_size_col, 500);
        resize();
        fillResource(RICALCOLA);
    }

    public void paint(Graphics g) {
        if (m_Gbuffer == null) {
            m_Ibuffer = this.createImage(m_size_col, 500);
            m_Gbuffer = m_Ibuffer.getGraphics();
        }
        g.drawImage(m_Ibuffer, 0, 0, this);
    }

    public void processMouseEvent(MouseEvent e) {
        if (e.isPopupTrigger())
            m_premuto = true;
        super.processMouseEvent(e);
    }

    //START implementazione MouseListener
    public void mouseClicked(MouseEvent e) {
        if (m_selected)
            clearSelection();

        setSelection(e.getX(), e.getY());
        if (m_sel_res != -1)
            paintSelection();

        //System.out.println("setSelection "+m_sel_res);
        paintSelection();
        if (!m_premuto && e.getClickCount() > 1)
            if (m_sel_res != -1) {
                Resource rs = dr.getResource(m_sel_res);
                //System.out.println("rmodifica "+m_sel_res);
                boolean modify = true;
                switch (rs.type) {
                case 0:
                    DialogEditPaesaggio editPae;
                    editPae = new DialogEditPaesaggio(parent.parent, rs);
                    editPae.edit();
                    break;
                case 1:
                    DialogEditFiore editFiore;
                    editFiore = new DialogEditFiore(parent.parent, rs);
                    editFiore.edit();
                    break;
                case 2:
                    DialogEditAlbero editAlbero;
                    editAlbero = new DialogEditAlbero(parent.parent, rs);
                    editAlbero.edit();
                    break;
                case 3:
                    DialogEditFauna editFauna;
                    editFauna = new DialogEditFauna(parent.parent, rs);
                    editFauna.edit();
                    break;
                case 4:
                    DialogEditAmbienteNaturale editAmbienteNaturale;
                    editAmbienteNaturale = new DialogEditAmbienteNaturale(
                            parent.parent, rs);
                    editAmbienteNaturale.edit();
                    break;
                case 5:
                    DialogEditMeteo editMeteo;
                    editMeteo = new DialogEditMeteo(parent.parent, rs);
                    editMeteo.edit();
                    break;
                case 6:
                    DialogEditMonumento editMonumento;
                    editMonumento = new DialogEditMonumento(parent.parent, rs);
                    editMonumento.edit();
                    break;
                case 7:
                    DialogEditIntervista editIntervista;
                    editIntervista = new DialogEditIntervista(parent.parent, rs);
                    editIntervista.edit();
                    break;
                case 8:
                    DialogEditFatto editFatto;
                    editFatto = new DialogEditFatto(parent.parent, rs);
                    editFatto.edit();
                    break;
                case 9:
                    DialogEditCuriosita editCuriosita;
                    editCuriosita = new DialogEditCuriosita(parent.parent, rs);
                    editCuriosita.edit();
                    break;
                default:
                    modify = false;
                }
                if (modify) {
                    rs.updateImg(parent.tab);
                    m_Gbuffer.drawImage(rs.y, 0, m_sel_res * HRES, this);
                }
            }

        if (m_premuto) {
            m_premuto = false;
            if (dr.getHeight() > 0) {
                m_Mres.getItem(0).setLabel("Aggiungi Prima");
                m_Mres.getItem(1).setEnabled(true);
            } else {
                m_Mres.getItem(0).setLabel("Aggiungi");
                m_Mres.getItem(1).setEnabled(false);
            }

            if (m_sel_res == -1) {
                m_Mres.getItem(2).setEnabled(false);
                m_Mres.getItem(3).setEnabled(false);
                m_Mres.getItem(4).setEnabled(false);
            } else {
                m_Mres.getItem(2).setEnabled(true);
                m_Mres.getItem(3).setEnabled(true);
                m_Mres.getItem(4).setEnabled(true);
            }
            if (parent.tab.clipboard == null) {
                m_Mres.getItem(5).setEnabled(false);
                m_Mres.getItem(6).setEnabled(false);
            } else {
                m_Mres.getItem(5).setEnabled(true);
                m_Mres.getItem(6).setEnabled(true);
            }

            m_Mres.show(this, e.getX(), e.getY());
        }
    }

    public void mouseEntered(MouseEvent e) {
        ;
    }

    public void mouseExited(MouseEvent e) {
        ;
    }

    public void mousePressed(MouseEvent e) {
        ;
    }

    public void mouseReleased(MouseEvent e) {
        ;
    }

    //START implementazione ActionListeners
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        int pos;
        Resource rs;

        //Aggiungi Risorsa Prima
        if (command.equals("PAE_P")) {
            pos = addNewResource(0, "Paesaggio", true);
            rs = dr.getResource(pos);
            DialogEditPaesaggio editPae;
            editPae = new DialogEditPaesaggio(parent.parent, rs);
            editPae.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("FIO_P")) {
            pos = addNewResource(1, "Fiore/Erba", true);
            rs = dr.getResource(pos);
            DialogEditFiore editFiore;
            editFiore = new DialogEditFiore(parent.parent, rs);
            editFiore.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("ALB_P")) {
            pos = addNewResource(2, "Albero/Arbusto", true);
            rs = dr.getResource(pos);
            DialogEditAlbero editAlbero;
            editAlbero = new DialogEditAlbero(parent.parent, rs);
            editAlbero.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("FAU_P")) {
            pos = addNewResource(3, "Fauna", true);
            rs = dr.getResource(pos);
            DialogEditFauna editFauna;
            editFauna = new DialogEditFauna(parent.parent, rs);
            editFauna.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("AMB_P")) {
            pos = addNewResource(4, "Ambiente Naturale", true);
            rs = dr.getResource(pos);
            DialogEditAmbienteNaturale editAmbienteNaturale;
            editAmbienteNaturale = new DialogEditAmbienteNaturale(
                    parent.parent, rs);
            editAmbienteNaturale.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("MET_P")) {
            pos = addNewResource(5, "Meteo", true);
            rs = dr.getResource(pos);
            DialogEditMeteo editMeteo;
            editMeteo = new DialogEditMeteo(parent.parent, rs);
            editMeteo.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("MON_P")) {
            pos = addNewResource(6, "Monumento/Luogo Storico", true);
            rs = dr.getResource(pos);
            DialogEditMonumento editMonumento;
            editMonumento = new DialogEditMonumento(parent.parent, rs);
            editMonumento.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("INT_P")) {
            pos = addNewResource(7, "Intervista", true);
            rs = dr.getResource(pos);
            DialogEditIntervista editIntervista;
            editIntervista = new DialogEditIntervista(parent.parent, rs);
            editIntervista.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("CRO_P")) {
            pos = addNewResource(8, "Fatto di Cronaca", true);
            rs = dr.getResource(pos);
            DialogEditFatto editFatto;
            editFatto = new DialogEditFatto(parent.parent, rs);
            editFatto.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("CUR_P")) {
            pos = addNewResource(9, "Curiosita'/Osservazione", true);
            rs = dr.getResource(pos);
            DialogEditCuriosita editCuriosita;
            editCuriosita = new DialogEditCuriosita(parent.parent, rs);
            editCuriosita.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        }
        //Aggiungi Risorsa Dopo
        if (command.equals("PAE_D")) {
            pos = addNewResource(0, "Paesaggio", false);
            rs = dr.getResource(pos);
            DialogEditPaesaggio editPae;
            editPae = new DialogEditPaesaggio(parent.parent, rs);
            editPae.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("FIO_D")) {
            pos = addNewResource(1, "Fiore/Erba", false);
            rs = dr.getResource(pos);
            DialogEditFiore editFiore;
            editFiore = new DialogEditFiore(parent.parent, rs);
            editFiore.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("ALB_D")) {
            pos = addNewResource(2, "Albero/Arbusto", false);
            rs = dr.getResource(pos);
            DialogEditAlbero editAlbero;
            editAlbero = new DialogEditAlbero(parent.parent, rs);
            editAlbero.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("FAU_D")) {
            pos = addNewResource(3, "Fauna", false);
            rs = dr.getResource(pos);
            DialogEditFauna editFauna;
            editFauna = new DialogEditFauna(parent.parent, rs);
            editFauna.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("AMB_D")) {
            pos = addNewResource(4, "Ambiente Naturale", false);
            rs = dr.getResource(pos);
            DialogEditAmbienteNaturale editAmbienteNaturale;
            editAmbienteNaturale = new DialogEditAmbienteNaturale(
                    parent.parent, rs);
            editAmbienteNaturale.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("MET_D")) {
            pos = addNewResource(5, "Meteo", false);
            rs = dr.getResource(pos);
            DialogEditMeteo editMeteo;
            editMeteo = new DialogEditMeteo(parent.parent, rs);
            editMeteo.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("MON_D")) {
            pos = addNewResource(6, "Monumento/Luogo Storico", false);
            rs = dr.getResource(pos);
            DialogEditMonumento editMonumento;
            editMonumento = new DialogEditMonumento(parent.parent, rs);
            editMonumento.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("INT_D")) {
            pos = addNewResource(7, "Intervista", false);
            rs = dr.getResource(pos);
            DialogEditIntervista editIntervista;
            editIntervista = new DialogEditIntervista(parent.parent, rs);
            editIntervista.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("CRO_D")) {
            pos = addNewResource(8, "Fatto di Cronaca", false);
            rs = dr.getResource(pos);
            DialogEditFatto editFatto;
            editFatto = new DialogEditFatto(parent.parent, rs);
            editFatto.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        } else if (command.equals("CUR_D")) {
            pos = addNewResource(9, "Curiosita'/Osservazione", false);
            rs = dr.getResource(pos);
            DialogEditCuriosita editCuriosita;
            editCuriosita = new DialogEditCuriosita(parent.parent, rs);
            editCuriosita.edit();
            rs.updateImg(this.parent.tab);
            m_Gbuffer.drawImage(rs.n, 0, pos * HRES, this);
            repaint();
        }

        else if (command.equals("rmodifica")) {
            rs = dr.getResource(m_sel_res);
            //System.out.println("rmodifica "+m_sel_res);
            boolean modify = true;
            switch (rs.type) {
            case 0:
                DialogEditPaesaggio editPae;
                editPae = new DialogEditPaesaggio(parent.parent, rs);
                editPae.edit();
                break;
            case 1:
                DialogEditFiore editFiore;
                editFiore = new DialogEditFiore(parent.parent, rs);
                editFiore.edit();
                break;
            case 2:
                DialogEditAlbero editAlbero;
                editAlbero = new DialogEditAlbero(parent.parent, rs);
                editAlbero.edit();
                break;
            case 3:
                DialogEditFauna editFauna;
                editFauna = new DialogEditFauna(parent.parent, rs);
                editFauna.edit();
                break;
            case 4:
                DialogEditAmbienteNaturale editAmbienteNaturale;
                editAmbienteNaturale = new DialogEditAmbienteNaturale(
                        parent.parent, rs);
                editAmbienteNaturale.edit();
                break;
            case 5:
                DialogEditMeteo editMeteo;
                editMeteo = new DialogEditMeteo(parent.parent, rs);
                editMeteo.edit();
                break;
            case 6:
                DialogEditMonumento editMonumento;
                editMonumento = new DialogEditMonumento(parent.parent, rs);
                editMonumento.edit();
                break;
            case 7:
                DialogEditIntervista editIntervista;
                editIntervista = new DialogEditIntervista(parent.parent, rs);
                editIntervista.edit();
                break;
            case 8:
                DialogEditFatto editFatto;
                editFatto = new DialogEditFatto(parent.parent, rs);
                editFatto.edit();
                break;
            case 9:
                DialogEditCuriosita editCuriosita;
                editCuriosita = new DialogEditCuriosita(parent.parent, rs);
                editCuriosita.edit();
                break;
            default:
                modify = false;
            }
            if (modify) {
                rs.updateImg(parent.tab);
                m_Gbuffer.drawImage(rs.y, 0, m_sel_res * HRES, this);
                parent.parent.setRepositoryModified(true);
            }
        } else if (command.equals("rcancella"))
            deleteResource();
        else if (command.equals("rcopia")) {
            parent.tab.clipboard = dr.getResource(m_sel_res)
                    .copyRes(parent.tab);
        } else if (command.equals("rincolla_p"))
            addClipboardResource(true);
        else if (command.equals("rincolla_d"))
            addClipboardResource(false);
    }
}