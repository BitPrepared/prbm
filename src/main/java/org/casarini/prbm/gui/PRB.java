/* PRB.java
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

package org.casarini.prbm.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.text.SimpleDateFormat;

import org.casarini.prbm.gui.component.Tabella;
import org.casarini.prbm.gui.dialog.AboutBox;
import org.casarini.prbm.gui.dialog.AttentionDialog;
import org.casarini.prbm.gui.dialog.ClosePRBOptionPane;
import org.casarini.prbm.gui.dialog.LoadRepositoryOptionPane;
import org.casarini.prbm.gui.dialog.PRBRepositoryDialog;
import org.casarini.prbm.gui.dialog.QuitBox;
import org.casarini.prbm.gui.dialog.SetPRBBox;
import org.casarini.prbm.gui.dialog.TemplateBox;
import org.casarini.prbm.model.DataTable;
import org.casarini.prbm.model.PRBParam;
import org.casarini.prbm.model.PRBRepositoryData;
import org.casarini.prbm.util.RunnerConfig;

public class PRB extends Frame implements WindowListener, ActionListener, ComponentListener {
	private static final long serialVersionUID = 3914113082433881542L;

	private static final String APP_TITLE_PREFIX = "PRB - Percorso Rettificato Belga - ";
    
    public static final String MENU_PRB_NEW = "Nuovo";
    public static final String MENU_PRB_OPEN = "Apri...";
    public static final String MENU_PRB_SAVE = "Salva";
    public static final String MENU_PRB_SAVEAS = "Salva con nome...";
    public static final String MENU_PRB_EXIT = "Uscita (Bim Bum...)";
    
    public static final String MENU_REPOS_OPEN = "Apri Raccoglitore...";
    
    private static final String SAVE_OPTION_ON_NEW = "Creando un nuovo documento PRB, si perderanno le modifiche\nnon salvate del documento corrente. Vuoi continuare?";
    private static final String SAVE_OPTION_ON_OPEN = "Aprendo un documento PRB salvato, si perderanno le modifiche\nnon salvate del documento corrente. Vuoi continuare?";
    private static final String SAVE_OPTION_ON_EXIT = "Uscendo dal programma, si perderanno le modifiche\nnon salvate del documento corrente. Vuoi continuare?";
    private static final String SAVE_OPTION_ON_EXITR = "Uscendo dal programma, si perderanno le modifiche\nnon salvate del raccoglitore corrente. Vuoi continuare?";
    
    /**
     * Costante usata per individuare una risposta positiva su un option pane
     */
    public static final int IDOK = 1;
    /**
     * Costante usata per individuare una risposta negativa su un option pane
     */
    public static final int IDCANCEL = 0;
    /**
     * Path del percorso rettificato belga attualmente aperto
     */
    private String prbPath;
    /**
     * Flag per indicare se il percorso rettificato belga attuale e' in stato:
     * modificato.
     */
    private boolean prbModified;

    /**
     * Dialog del raccoglitore
     */
    PRBRepositoryDialog repositoryDialog;
    /**
     * Path del repository attualmente aperto
     */
    private String repositoryPath;
    /**
     * Flag per indicare se il raccolgitore attuale e' in stato: modificato
     */
    private boolean repositoryModified;

    //componenti per la gestione dei dati
    public PRBParam c_param; //Parametri globali del PRB

    MenuItem mf_new, mf_load, mf_save, mf_saveas, mf_set, mf_final, mf_exit;

    MenuItem ms_show, ms_hide, ms_load, ms_save, ms_saveas;

    MenuItem mg_help1, mg_help2, mg_supp, mg_info;

    //componenti grafici
    Tabella c_tab;

    public ScrollPane c_scrollpane;

    FilenameFilter c_filter;

    FilenameFilter c_rfilter;

    public PRB() {
        super();
        setSize(700, 500);

        MenuBar mb = new MenuBar();
        Menu fileMenu = new Menu("File");
        fileMenu.add(mf_new = new MenuItem(MENU_PRB_NEW));
        fileMenu.add(mf_load = new MenuItem(MENU_PRB_OPEN));
        fileMenu.add(mf_save = new MenuItem(MENU_PRB_SAVE));
        fileMenu.add(mf_saveas = new MenuItem(MENU_PRB_SAVEAS));
        fileMenu.addSeparator();
        fileMenu.add(mf_set = new MenuItem("Parametri del PRB"));
        fileMenu.addSeparator();
        fileMenu.add(mf_final = new MenuItem("Crea Presentazione Finale"));
        fileMenu.addSeparator();
        fileMenu.add(mf_exit = new MenuItem("Uscita (Bim Bum...)"));
        mb.add(fileMenu);
        Menu strumentiMenu = new Menu("Strumenti");
        strumentiMenu.add(ms_show = new MenuItem("Mostra Raccoglitore"));
        strumentiMenu.add(ms_hide = new MenuItem("Nascondi Raccoglitore"));
        strumentiMenu.addSeparator();
        strumentiMenu.add(ms_load = new MenuItem(MENU_REPOS_OPEN));
        strumentiMenu.add(ms_save = new MenuItem("Salva Raccoglitore"));
        strumentiMenu.add(ms_saveas = new MenuItem(
                "Salva Raccoglitore con nome..."));
        mb.add(strumentiMenu);
        Menu guidaMenu = new Menu("Guida");
        guidaMenu.add(mg_help1 = new MenuItem("Guida al programma"));
        guidaMenu.add(mg_help2 = new MenuItem("Spiegazione sul PRB"));
        guidaMenu.addSeparator();
        guidaMenu.add(mg_supp = new MenuItem("Supporto al Programma"));
        guidaMenu.add(mg_info = new MenuItem("Informazioni sul programma"));
        mb.add(guidaMenu);
        setMenuBar(mb);

        mf_new.addActionListener(this);
        mf_load.addActionListener(this);
        mf_save.addActionListener(this);
        mf_saveas.addActionListener(this);
        mf_set.addActionListener(this);
        mf_final.addActionListener(this);
        mf_exit.addActionListener(this);
        ms_show.addActionListener(this);
        ms_hide.addActionListener(this);
        ms_load.addActionListener(this);
        ms_save.addActionListener(this);
        ms_saveas.addActionListener(this);
        mg_supp.addActionListener(this);
        mg_info.addActionListener(this);
        this.addWindowListener(this);
        this.addComponentListener(this);

        c_filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".prb"))
                    return true;
                else
                    return (new File(dir, name)).isDirectory();
            }
        };

        c_rfilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".prbr"))
                    return true;
                else
                    return (new File(dir, name)).isDirectory();
            }
        };

        c_scrollpane = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        c_tab = new Tabella(this, 140, 70);
        c_scrollpane.add(c_tab);
        this.add(c_scrollpane, "Center");

        // inizializzazione repository
        repositoryDialog = new PRBRepositoryDialog(this, c_tab);
        repositoryPath = null;
        setRepositoryModified(false);

        // inizializzazione percorso rettificato belga
        prbPath = null;
        setPrbModified(false);
        updateTitle();
        
        reset();

        setLocation(50, 50);
        setVisible(true);
    }

    public void setParam(PRBParam param) {
        c_param.copyFrom(param);
    }

    public void reset() {
        c_param = new PRBParam();
    }

    // START implementazione ComponentListener
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    // START implementazione WindowListener
    public void windowClosing(WindowEvent e) {
    	selectedExit();
    }
    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    // STOP implementazione WindowListener

    //START implementazione ActionListener
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals(MENU_PRB_NEW))
            selectedNew();
        else if (cmd.equals(MENU_PRB_OPEN))
            selectedOpen();
        else if (cmd.equals(MENU_PRB_SAVE))
            selectedSave();
        else if (cmd.equals(MENU_PRB_SAVEAS))
            selectedSaveAs();
        else if (cmd.equalsIgnoreCase("Parametri del PRB")) {
            new SetPRBBox(this, c_param);
        } else if (cmd.equalsIgnoreCase("Crea Presentazione Finale"))
            selectedCreate();
        else if (cmd.equals(MENU_PRB_EXIT)) {
        	selectedExit();
        } else if (cmd.equalsIgnoreCase("Mostra Raccoglitore")) {
            repositoryDialog.setVisible(true);
        } else if (cmd.equalsIgnoreCase("Nascondi Raccoglitore")) {
            repositoryDialog.setVisible(false);
        } else if (cmd.equals(MENU_REPOS_OPEN)) {
            selectedLoadRepository();
        } else if (cmd.equalsIgnoreCase("Salva Raccoglitore")) {
            selectedSaveRepository();
        } else if (cmd.equals("Salva Raccoglitore con nome...")) {
            selectedSaveAsRepository();
        } else if (cmd.equalsIgnoreCase("Informazioni sul programma")) {
            new AboutBox(this);
        } else if (cmd.equalsIgnoreCase("Supporto al Programma")) {
        	// TODO
        }

    }

    //STOP implementazione ActionListener

    public static void main(String args[]) {
    	RunnerConfig.getInstance();
        new PRB();
    }
    
    private void selectedExit() {
    	int prbOption = IDOK;
    	int repositoryOption = IDOK;
    	
        if (isPrbModified()) {
            ClosePRBOptionPane optionPane = new ClosePRBOptionPane(this, MENU_PRB_EXIT, SAVE_OPTION_ON_EXIT);
            optionPane.setVisible(true);
            prbOption = optionPane.getResult();
            optionPane.dispose();
        }
        if (prbOption == IDOK && isRepositoryModified()) {
            ClosePRBOptionPane optionPane = new ClosePRBOptionPane(this, MENU_PRB_EXIT, SAVE_OPTION_ON_EXITR);
            optionPane.setVisible(true);
            prbOption = optionPane.getResult();
            optionPane.dispose();
        }
        
        if (prbOption == IDCANCEL || repositoryOption == IDCANCEL) {
            return;
        }
        
        new QuitBox(this);
    }

    public void selectedNew() {    	
        if (isPrbModified()) {
            ClosePRBOptionPane optionPane = new ClosePRBOptionPane(this, MENU_PRB_NEW, SAVE_OPTION_ON_NEW);
            optionPane.setVisible(true);
            int prbOption = optionPane.getResult();
            optionPane.dispose();
            if (prbOption == IDCANCEL) {
                return;
            }
        }

        reset();
        c_scrollpane.remove(c_tab);
        c_tab = null;
        c_tab = new Tabella(this, 140, 70);
        c_scrollpane.add(c_tab);
        c_tab.resize();
        c_tab.paint(c_tab.getGraphics());

        prbPath = null;
        setPrbModified(false);
        updateTitle();
    }

    public void selectedOpen() {  	
        if (isPrbModified()) {
            ClosePRBOptionPane optionPane = new ClosePRBOptionPane(this, MENU_PRB_OPEN, SAVE_OPTION_ON_OPEN);
            optionPane.setVisible(true);
            int prbOption = optionPane.getResult();
            optionPane.dispose();
            if (prbOption == IDCANCEL) {
                return;
            }
        }
        
        FileDialog f = new FileDialog(this, MENU_PRB_OPEN, FileDialog.LOAD);
        f.setFilenameFilter(c_filter);
        f.setFile("*.prb");
        f.setVisible(true);
        if (f.getFile() != null) {
            String filename = f.getDirectory() + f.getFile();
            try {
                FileInputStream fis = new FileInputStream(filename);
                GZIPInputStream gzis = new GZIPInputStream(fis);
                ObjectInputStream in = new ObjectInputStream(gzis);
                PRBParam p = (PRBParam) in.readObject();
                DataTable dt = (DataTable) in.readObject();
                in.close();
                c_tab.setDataTable(dt);
                c_param = p;
                c_tab.resize();
                c_tab.paint(c_tab.getGraphics());

                prbPath = filename;
                setPrbModified(false);
                updateTitle();
            } catch (Exception e) {
                new AttentionDialog(this, "Apertura non riuscita", e.toString());
            }
        }
    }

    public void selectedCreate() {
        TemplateBox tb = new TemplateBox(this);
        String res = tb.getResult();
        if (res.length() > 0)
            c_tab.toHTML(c_param, res);
        tb.dispose();
    }

    public void selectedSave() {
        if (prbPath == null) {
            selectedSaveAs();
        } else {
            // sistema l'estensione se mancante
            if (!prbPath.substring(prbPath.length() - 4).equalsIgnoreCase(".prb")) {
                prbPath = prbPath + ".prb";
            }
            
            // aggiorna la data di ultima modifica
            Date d = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "dd-MM-yyyy-hh-mm-ss");
            String dateString = formatter.format(d);
            c_param.modifica = dateString;
            
            try {
                // scrive su disco il Percorso Rettificato Belga
                FileOutputStream fos = new FileOutputStream(prbPath);
                GZIPOutputStream gzos = new GZIPOutputStream(fos);
                ObjectOutputStream out = new ObjectOutputStream(gzos);
                out.writeObject(c_param);
                out.writeObject(c_tab.getDataTable());
                out.flush();
                out.close();
                
                // aggiorna l'interfaccia
                setPrbModified(false);
                updateTitle();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void selectedSaveAs() {
        FileDialog f = new FileDialog(this, MENU_PRB_SAVEAS, FileDialog.SAVE);
        f.setFilenameFilter(c_rfilter);
        f.setFile("*.prb");
        f.setVisible(true);
        if (f.getFile() != null) {
            prbPath = f.getDirectory() + f.getFile();
            selectedSave();
        }
    }

    public void selectedSaveRepository() {
        if (repositoryPath == null) {
            selectedSaveAsRepository();
        } else {
            // sistema l'estensione se mancante
            if (!repositoryPath.substring(repositoryPath.length() - 5).equalsIgnoreCase(".prbr")) {
            	repositoryPath = repositoryPath + ".prbr";
            }
            
            try {
                // recupera il repository e lo salva su disco
                PRBRepositoryData dr = this.repositoryDialog.c_repository.dr;
                FileOutputStream fos = new FileOutputStream(repositoryPath);
                GZIPOutputStream gzos = new GZIPOutputStream(fos);
                ObjectOutputStream out = new ObjectOutputStream(gzos);
                out.writeObject(dr);
                out.flush();
                out.close();
                
                // aggiorna l'interfaccia
                repositoryDialog.setTitle(repositoryPath);
                setRepositoryModified(false);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void selectedSaveAsRepository() {
        FileDialog f = new FileDialog(this, "Salva Raccogliotore",
                FileDialog.SAVE);
        f.setFilenameFilter(c_rfilter);
        f.setFile("*.prbr");
        f.setVisible(true);
        if (f.getFile() != null) {
            repositoryPath = f.getDirectory() + f.getFile();
            selectedSaveRepository();
        }
    }

    public void selectedLoadRepository() {
        if(isRepositoryModified()) {
            LoadRepositoryOptionPane optionPane = new LoadRepositoryOptionPane(this);
            optionPane.setVisible(true);
            if (optionPane.getResult() == IDCANCEL) {
                return;
            }
            optionPane.dispose();
        }
            
        FileDialog f = new FileDialog(this, MENU_REPOS_OPEN,
                FileDialog.LOAD);
        f.setFilenameFilter(c_rfilter);
        f.setFile("*.prbr");
        f.setVisible(true);
        if (f.getFile() != null) {
            repositoryPath = f.getDirectory() + f.getFile();
            
            try {
                FileInputStream fis = new FileInputStream(repositoryPath);
                GZIPInputStream gzis = new GZIPInputStream(fis);
                ObjectInputStream in = new ObjectInputStream(gzis);
                PRBRepositoryData dr = (PRBRepositoryData) in.readObject();
                in.close();
                repositoryDialog.setVisible(true);
                repositoryDialog.c_repository
                        .paint(repositoryDialog.c_repository.getGraphics());
                repositoryDialog.c_repository.setRepositoryData(dr);
                repositoryDialog.c_repository.resize();
                
                repositoryDialog.setTitle(repositoryPath);
                setRepositoryModified(false);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
    /**
     * Restituisce il valore del flag che indica se il repository attuale
     * e' in stato modificato.
     * @return <tt>true</tt> se il repository e' in stato modifica; <tt>false</tt> altrimenti.
     */
    public boolean isRepositoryModified() {
        return repositoryModified;
    }
    /**
     * Imposta il valore del flag che indica se il repository attuale e' in
     * stato di modifica. AGgiorna l'intefaccia al cambiamento di stato
     * avvenuto.
     * @param repositoryModified Il valore da assegnare al flag.
     */
    public void setRepositoryModified(boolean repositoryModified) {
        this.repositoryModified = repositoryModified;
        ms_save.setEnabled(repositoryModified);
    }
    /**
     * @return Restituisce il repositoryPath.
     */
    public String getRepositoryPath() {
        return repositoryPath;
    }
    /**
     * Restituisce il valore del flag che indica se il percorso rettificato
     * belga attuale e' in stato di modifica o meno.
     * @return <tt>true</tt> se il percorso rettificato belga attuale e' in stato di modifica; <tt>false</tt> altrimenti.
     */
    public boolean isPrbModified() {
        return prbModified;
    }
    /**
     * Imposta il valore del flag che indica se il Percorso Rettificato Belga
     * attuale e' in stato di modifica. Aggiorna l'interfaccia al cambiamento
     * di stato avvenuto.
     * @param prbModified Il valore da assegnare al flag.
     */
    public void setPrbModified(boolean prbModified) {
        this.prbModified = prbModified;
        mf_save.setEnabled(prbModified);
    }
    
    /**
     * Aggiorna il titolo del Frame in base al valore attuale del prbPath.
     */
    private void updateTitle() {
        if (prbPath == null) {
            setTitle(APP_TITLE_PREFIX + "<senza nome>");
        } else {
            setTitle(APP_TITLE_PREFIX + prbPath.substring(prbPath.lastIndexOf(File.separatorChar)+1));        
        }
    }
    
}

