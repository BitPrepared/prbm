/* DialogEditPaesaggio.java
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
import java.io.File;
import java.util.Vector;
import java.util.StringTokenizer;

import org.casarini.prbm.gui.PRB;
import org.casarini.prbm.gui.component.CTextField;
import org.casarini.prbm.gui.component.DimButton;
import org.casarini.prbm.gui.component.IconViewer;
import org.casarini.prbm.model.Resource;
import org.casarini.prbm.model.TimeStamp;
import org.casarini.prbm.util.IconFactory;


public class DialogEditPaesaggio extends DefaultDialogEdit
{
	private static final long serialVersionUID = -7163576880980877540L;
	
	private static String[][] paesaggi = {
		{"Paesaggio generico", "paesaggi/standard.gif"},
		{"abbeveratoio", "paesaggi/abbeveratoio.gif"},
		{"abbeveratoio con fontana", "paesaggi/abbeveratoio con fontana.gif"},
		{"acquedotto", "paesaggi/acquedotto.gif"},
		{"aeroporto", "paesaggi/aeroporto.gif"},
		{"antenna per telecom.", "paesaggi/antennatelecom.gif"},
		{"autostrada", "paesaggi/autostra.gif"},
		{"baracca", "paesaggi/baracca.gif"},
		{"campo da tennis", "paesaggi/campo_tennis.gif"},
		{"capanna", "paesaggi/capanna.gif"},
		{"cappella", "paesaggi/cappella.gif"},
		{"casa in muratura", "paesaggi/casamura.gif"},
		{"cascata", "paesaggi/cascata.gif"},
		{"casello autostradale", "paesaggi/casello.gif"},
		{"castello", "paesaggi/castello.gif"},
		{"centrale idroelettrica", "paesaggi/centrale idroelettrica.gif"},
		{"centrale termoelettrica", "paesaggi/centrale termoelettrica.gif"},
		{"chiesa", "paesaggi/chiesa.gif"},
		{"cimitero", "paesaggi/cimitero.gif"},
		{"cisterna", "paesaggi/cisterna.gif"},
		{"croce isolata", "paesaggi/crocefisso.gif"},
		{"diga", "paesaggi/diga.gif"},
		{"fabbrica", "paesaggi/fabbrica.gif"},
		{"faro", "paesaggi/faro.gif"},
		{"ferrovia", "paesaggi/ferrovia.gif"},	
		{"filo spinato", "paesaggi/filospinato.gif"},
		{"fiume grosso", "paesaggi/fiume grosso.gif"},
		{"fiume piccolo", "paesaggi/fiume piccolo.gif"},
		{"fontana", "paesaggi/fontana.gif"},
		{"fumaiolo", "paesaggi/fumaiolo.gif"},
		{"funicolare", "paesaggi/funicolare.gif"},
		{"funivia", "paesaggi/funivia.gif"},
		{"galleria autostradale", "paesaggi/galleria autostrada.gif"},
		{"galleria ferroviaria", "paesaggi/gallferro.gif"},		
		{"galleria stradale", "paesaggi/galleria stradale.gif"},
		{"grotta", "paesaggi/grotta.gif"},
		{"idroscalo", "paesaggi/idroscalo.gif"},
		{"laghetto", "paesaggi/laghetto.gif"},
		{"lago", "paesaggi/lago.gif"},
		{"miniera", "paesaggi/miniera.gif"},
		{"monumento notevole", "paesaggi/monumento notevole.gif"},
		{"mulattiera", "paesaggi/mulattiera.gif"},
		{"muro a calce", "paesaggi/muroacalce.gif"},
		{"muro a secco o maceria", "paesaggi/muroasecco.gif"},
		{"muro di sostegno", "paesaggi/murosostegno.gif"},
		{"palizzata/staccionata", "paesaggi/staccionata.gif"},
		{"passaggio a livello", "paesaggi/passlivello.gif"},	
		{"pietra o colonna indicatrice", "paesaggi/pietra o colonna indicatrice.gif"},
		{"ponte autostradale", "paesaggi/ponteautostrada.gif"},
		{"ponte di barche", "paesaggi/pontebarche.gif"},
		{"ponte ferroviario", "paesaggi/ponteferro.gif"},		
		{"ponte pedonale", "paesaggi/pontepedonale.gif"},
		{"ponte stradale", "paesaggi/ponte stradale.gif"},
		{"porto", "paesaggi/porto.gif"},
		{"pozzo d'acqua", "paesaggi/pozzo d'acqua.gif"},
		{"pozzo di petrolio/gas", "paesaggi/pozzopetrolio.gif"},
		{"punto di quota", "paesaggi/punto di quota.gif"},
		{"rudere", "paesaggi/rudere.gif"},
		{"sciovia (skylift)", "paesaggi/sciovia.gif"},
		{"seggiovia", "paesaggi/seggiovia.gif"},
		{"sentiero", "paesaggi/sentiero.gif"},
		{"siepe", "paesaggi/siepe.gif"},
		{"sorgente d'acqua", "paesaggi/sorgente d'acqua.gif"},
		{"stazione di rifornimento", "paesaggi/stazione di rifornimento.gif"},
		{"stazione ferroviaria", "paesaggi/stazferro.gif"},		
		{"strada", "paesaggi/strada.gif"},
		{"strada carreggiabile", "paesaggi/strada carreggiabile.gif"},
		{"tabernacolo", "paesaggi/tabernacolo.gif"},
		{"teleferica", "paesaggi/teleferica.gif"},		
		{"traliccio corrente", "paesaggi/traliccio_corrente.gif"},
		{"torre", "paesaggi/torre.gif"},
		{"torrente", "paesaggi/torrente.gif"},
		{"villa antica", "paesaggi/villaantica.gif"},		
	};

	Font fontN,fontB;
    Vector<Image> imgIcone;
    Resource rs;
    PRB parent;
    
	public DialogEditPaesaggio(PRB parent,Resource rs)
	{
		super(parent, true);
		this.rs=rs;
		this.parent=parent;
		imgIcone=new Vector<Image>();
		setBackground(Color.lightGray);

		fontN=new Font("Dialog", Font.PLAIN, 12);
		fontB=new Font("Dialog", Font.BOLD, 12);

		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		setLayout(null);
		setVisible(false);
		setSize(getInsets().left + getInsets().right + 467, getInsets().top + getInsets().bottom + 448);
		tfTitolo = new CTextField(CTextField.CTF_ALL,50);
		tfTitolo.setBounds(getInsets().left + 81,getInsets().top + 39,380,18);
		tfTitolo.setBackground(Color.yellow);
		add(tfTitolo);
		label1 = new java.awt.Label("Proprietà scheda Paesaggio",Label.CENTER);
		label1.setBounds(getInsets().left + 7,getInsets().top + 6,453,18);
		label1.setFont(new Font("Dialog", Font.BOLD, 14));
		add(label1);
		label2 = new java.awt.Label("Didascalia:");
		label2.setBounds(getInsets().left + 12,getInsets().top + 42,64,12);
		label2.setFont(fontB);
		add(label2);
		label3 = new java.awt.Label("Elemento Paesaggistico:");
		label3.setBounds(getInsets().left + 12,getInsets().top + 66,200,14);
		label3.setFont(fontB);
		add(label3);
		label4 = new java.awt.Label("Data rilevamento:");
		label4.setBounds(getInsets().left + 228,getInsets().top + 84,102,12);
		label4.setFont(fontB);
		add(label4);
		tfGiorno = new CTextField(CTextField.CTF_NUM,2);
		tfGiorno.setBounds(getInsets().left + 336,getInsets().top + 81,24,18);
		add(tfGiorno);
		tfMese = new CTextField(CTextField.CTF_NUM,2);
		tfMese.setBounds(getInsets().left + 366,getInsets().top + 81,24,18);
		add(tfMese);
		tfAnno = new CTextField(CTextField.CTF_NUM,4);
		tfAnno.setBounds(getInsets().left + 396,getInsets().top + 81,44,18);
		add(tfAnno);
		label5 = new java.awt.Label("/");
		label5.setBounds(getInsets().left + 360,getInsets().top + 84,6,12);
		label5.setFont(fontB);
		add(label5);
		label6 = new java.awt.Label("/");
		label6.setBounds(getInsets().left + 390,getInsets().top + 84,6,12);
		label6.setFont(fontB);
		add(label6);
		label7 = new java.awt.Label("Ora rilevamento:");
		label7.setBounds(getInsets().left + 234,getInsets().top + 108,96,12);
		label7.setFont(fontB);
		add(label7);
		tfOra = new CTextField(CTextField.CTF_NUM,2);
		tfOra.setBounds(getInsets().left + 336,getInsets().top + 105,24,18);
		add(tfOra);
		tfMinuti = new CTextField(CTextField.CTF_NUM,2);
		tfMinuti.setBounds(getInsets().left + 366,getInsets().top + 105,24,18);
		add(tfMinuti);
		tfSecondi = new CTextField(CTextField.CTF_NUM,2);
		tfSecondi.setBounds(getInsets().left + 396,getInsets().top + 105,24,18);
		add(tfSecondi);
		label8 = new java.awt.Label(":");
		label8.setBounds(getInsets().left + 360,getInsets().top + 108,6,12);
		label8.setFont(fontB);
		add(label8);
		label9 = new java.awt.Label(":");
		label9.setBounds(getInsets().left + 390,getInsets().top + 108,6,12);
		label9.setFont(fontB);
		add(label9);
		label10 = new java.awt.Label("Note:");
		label10.setBounds(getInsets().left + 12,getInsets().top + 321,30,12);
		label10.setFont(fontB);
		add(label10);
		textArea1 = new java.awt.TextArea();
		textArea1.setBounds(getInsets().left + 48,getInsets().top + 318,411,75);
		add(textArea1);
		lIcona = new java.awt.List(0,false);
		add(lIcona);
		lIcona.setBounds(getInsets().left + 12,getInsets().top + 84,186,180);
		okbutton = new java.awt.Button();
		okbutton.setActionCommand("button");
		okbutton.setLabel("OK");
		okbutton.setBounds(getInsets().left + 160,getInsets().top + 408,62,24);
		okbutton.setFont(fontB);
		okbutton.setBackground(new Color(12632256));
		add(okbutton);
		cancelbutton = new java.awt.Button();
		cancelbutton.setActionCommand("button");
		cancelbutton.setLabel("Annulla");
		cancelbutton.setBounds(getInsets().left + 244,getInsets().top + 408,62,24);
		cancelbutton.setFont(fontB);
		cancelbutton.setBackground(new Color(12632256));
		add(cancelbutton);
		cIcona = new IconViewer();
		cIcona.setBounds(getInsets().left + 298,getInsets().top + 198,60,60);
		add(cIcona);
		label11 = new java.awt.Label("Preview Icona",Label.CENTER);
		label11.setBounds(getInsets().left + 204,getInsets().top + 171,250,16);
		label11.setFont(fontB);
		add(label11);
		Label label12= new Label("Immagine:");
		label12.setBounds(9,274,60,12);
		label12.setFont(fontB);
		add(label12);
		tfImmagine = new CTextField(CTextField.CTF_ALL,255);
		tfImmagine.setBounds(75,271,318,18);
		add(tfImmagine);
		browse=new DimButton(62,20);
		browse.setLabel("Sfoglia");
		browse.setBounds(396,270,62,20);
		add(browse);
		Label label13 = new Label("Video:");
		label13.setBounds(9,296,40,12);
		label13.setFont(fontB);
		add(label13);
		tfVideo = new CTextField(CTextField.CTF_ALL,255);
		tfVideo.setBounds(55,293,338,18);
		add(tfVideo);
		browseV=new DimButton(62,20);
		browseV.setLabel("Sfoglia");
		browseV.setBounds(396,292,62,20);
		add(browseV);
		setTitle("Modifica Scheda Paesaggio");
		//}}
		
        for(int i=0; i < paesaggi.length; i++) {
        	lIcona.add(paesaggi[i][0]);
        	imgIcone.addElement(IconFactory.getInstance().getImage(paesaggi[i][1]));
        }
        
        
		//{{REGISTER_LISTENERS
		SymItem lSymItem = new SymItem();
		lIcona.addItemListener(lSymItem);
		SymMouse aSymMouse = new SymMouse();
		okbutton.addMouseListener(aSymMouse);
		cancelbutton.addMouseListener(aSymMouse);
		SymKey aSymKey = new SymKey();
		tfTitolo.addKeyListener(aSymKey);
		tfGiorno.addKeyListener(aSymKey);
		tfMese.addKeyListener(aSymKey);
		tfAnno.addKeyListener(aSymKey);
		tfOra.addKeyListener(aSymKey);
		tfMinuti.addKeyListener(aSymKey);
		tfSecondi.addKeyListener(aSymKey);
        browse.addMouseListener(aSymMouse);
        browseV.addMouseListener(aSymMouse);
		//}}
		
		//Init dei campi
		tfTitolo.setText(rs.scheda.didascalia);
		if(rs.scheda.timestamp.length()==0)
		{
			rs.scheda.timestamp=parent.c_param.data;
		}
		if(rs.scheda.timestamp.length()!=0)
		{
		    StringTokenizer t=new StringTokenizer(rs.scheda.timestamp,"-");
		    tfGiorno.setText(t.nextToken());
		    tfMese.setText(t.nextToken());
		    tfAnno.setText(t.nextToken());
		    tfOra.setText(t.nextToken());
		    tfMinuti.setText(t.nextToken());
		    tfSecondi.setText(t.nextToken());
		}
		int sel = -1;
		for (int i = 0; i < paesaggi.length; i++) {
			if (rs.scheda.icona.equals(paesaggi[i][1])) {
				sel = i;
				break;
			}
		}		
		if(sel != -1)
		{
  		    cIcona.setImage((Image)imgIcone.elementAt(sel));
            lIcona.select(sel);
		}
		else
		{
  		    cIcona.setImage((Image)imgIcone.elementAt(0));
            lIcona.select(0);
        }
        tfImmagine.setText(rs.scheda.immagine);
        tfVideo.setText(rs.scheda.video);
		textArea1.setText(rs.scheda.note);
	}
	
	public void addNotify()
	{
  	    // Record the size of the window prior to calling parents addNotify.
	    Dimension d = getSize();

		super.addNotify();

		if (fComponentsAdjusted)
			return;

		// Adjust components according to the getInsets
		setSize(getInsets().left + getInsets().right + d.width, getInsets().top + getInsets().bottom + d.height);
		Component components[] = getComponents();
		for (int i = 0; i < components.length; i++)
		{
			Point p = components[i].getLocation();
			p.translate(getInsets().left, getInsets().top);
			components[i].setLocation(p);
		}
		fComponentsAdjusted = true;
	}

    // Used for addNotify check.
	boolean fComponentsAdjusted = false;

	//{{DECLARE_CONTROLS
	CTextField tfTitolo;
	java.awt.Label label1;
	java.awt.Label label2;
	java.awt.Label label3;
	java.awt.Label label4;
	CTextField tfGiorno;
	CTextField tfMese;
	CTextField tfAnno;
	java.awt.Label label5;
	java.awt.Label label6;
	java.awt.Label label7;
	CTextField tfOra;
	CTextField tfMinuti;
	CTextField tfSecondi;
	java.awt.Label label8;
	java.awt.Label label9;
	java.awt.Label label10;
	java.awt.TextArea textArea1;
	java.awt.List lIcona;
	java.awt.Button okbutton;
	java.awt.Button cancelbutton;
	IconViewer cIcona;
	java.awt.Label label11;
    CTextField tfImmagine;
    CTextField tfVideo;
    DimButton browse, browseV;
	//}}

	class SymItem implements java.awt.event.ItemListener
	{
		public void itemStateChanged(java.awt.event.ItemEvent event)
		{
			Object object = event.getSource();
			if (object == lIcona)
				lIcona_ItemStateChanged(event);
		}
	}

	void lIcona_ItemStateChanged(java.awt.event.ItemEvent event) {
        int i = lIcona.getSelectedIndex();
        if (i != -1) {
        	cIcona.setImage((Image)imgIcone.elementAt(i));
        	cIcona.repaint();
        }
	}

	class SymMouse extends java.awt.event.MouseAdapter
	{
		public void mouseClicked(java.awt.event.MouseEvent event)
		{
			Object object = event.getSource();
			if (object == okbutton)
				okbutton_MouseClick(event);
			else if (object == cancelbutton)
				cancelbutton_MouseClick(event);
			else if (object == browse)
				browseFile(tfImmagine,"Scegli l'immagine...");
			else if (object == browseV)
				browseFile(tfVideo,"Scegli il video...");
		}
	}

	void okbutton_MouseClick(java.awt.event.MouseEvent event)
	{
	    String didascalia;
	    if((didascalia=tfTitolo.getText()).length()==0)
	    {
	        AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il campo Didascalia non può essere vuoto.");
		    d.display();
		}
		else if(lIcona.getSelectedIndex() == -1)
	    {
	        AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Deve essere selezionata una icona.");
		    d.display();
		}
		else
		{
            rs.title=didascalia;
            rs.scheda.didascalia=didascalia;
            rs.scheda.icona = paesaggi[lIcona.getSelectedIndex()][1];
            if(tfGiorno.getText().length()!=0||tfMese.getText().length()!=0||
               tfAnno.getText().length()!=0||tfOra.getText().length()!=0||
               tfMinuti.getText().length()!=0||tfSecondi.getText().length()!=0)
            {
               TimeStamp t=new TimeStamp(tfGiorno.getText(),tfMese.getText(),tfAnno.getText(),
                                        tfOra.getText(),tfMinuti.getText(),tfSecondi.getText());
                if(t.validate())
                    rs.scheda.timestamp=t.toString();
                else
                {
	                AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Data/Ora errata.");
		            d.display();
		            return;
		        }
		    }
		    else
		        rs.scheda.timestamp="";
            rs.scheda.note=textArea1.getText();
			File f;
			if(tfImmagine.getText().length()!=0)
			{
				f=new File(tfImmagine.getText());
				if(f.exists() && f.isAbsolute() && f.isFile())
		            rs.scheda.immagine=tfImmagine.getText();
				else
				{
	                AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il file immagine "+tfImmagine.getText()+" non è corretto!");
					d.display();
		            return;
				}
			} else {
				rs.scheda.immagine = "";
			}
			if(tfVideo.getText().length()!=0)
			{
				f=new File(tfVideo.getText());
				if(f.exists() && f.isAbsolute() && f.isFile())
		            rs.scheda.video=tfVideo.getText();
				else
				{
	                AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il file video "+tfVideo.getText()+" non è corretto!");
					d.display();
		            return;
				}
			} else {
				rs.scheda.video = "";
			}
    		//{{CONNECTION
    		// Hide the Dialog
    		setVisible(false);
    		//}}
    		dispose();
        }
	}

	void cancelbutton_MouseClick(java.awt.event.MouseEvent event)
	{
		// to do: code goes here.
	    
		//{{CONNECTION
		// Hide the Dialog
		setVisible(false);
		//}}
		dispose();
	}

    class SymKey extends java.awt.event.KeyAdapter
	{
//		public void keyTyped(java.awt.event.KeyEvent event)
//		{
//			Object object = event.getSource();
//          ...
//		}
	}

}