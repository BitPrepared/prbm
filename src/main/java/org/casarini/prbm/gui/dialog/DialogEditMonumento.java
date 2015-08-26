/* DialogEditMonumento.java
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
import java.util.StringTokenizer;

import org.casarini.prbm.gui.PRB;
import org.casarini.prbm.gui.component.CTextField;
import org.casarini.prbm.gui.component.DimButton;
import org.casarini.prbm.gui.component.IconViewer;
import org.casarini.prbm.model.Monumento;
import org.casarini.prbm.model.Resource;
import org.casarini.prbm.model.TimeStamp;
import org.casarini.prbm.util.IconFactory;
import org.casarini.prbm.util.XMLDirectoryList;


public class DialogEditMonumento extends DefaultDialogEdit
{
	private static final long serialVersionUID = -7209445128480485056L;

	Panel panel,panel1,panel2,panel3,panel4;
    DimButton okbutton,cancelbutton,bPrev,bSucc,browse,browseV,browseA,iPrev,iSucc;
    CardLayout lay;
    Font fontN,fontB;
    CTextField tfDidascalia,tfImmagine,tfVideo;
    CTextField tfGiorno,tfMese,tfAnno,tfOra,tfMinuti,tfSecondi;
	CTextField tfDenominazione,tfComune,tfTipoRilevanza,tfDataCostruzione,tfDataAvvenimento;
    TextArea taDescrizione,taProprieta,taStato,taUso,taFattoCostruire;
    TextArea taPercheCostruire,taMateriali,taOpere,taPercheAvvenimento,taRaccontoAvvenimento;
	IconViewer cIcona;
    Image icona;
    Monumento scheda;
    PRB parent;
    Resource rs;
    int numlay=1;
	public static final String icoDir="icone/mon/";
	String icone[];
	int iIcona=0;
    
	public DialogEditMonumento(PRB parent,Resource rs)
	{
		super(parent, true);
		this.parent=parent;
		this.rs=rs;
		this.setTitle("Modifica scheda Monumento/Luogo Storico");
		scheda=(Monumento)rs.scheda;
		setBackground(Color.lightGray);
		XMLDirectoryList dl = new XMLDirectoryList(File.separator+icoDir);
		icone = dl.list();
		if(scheda.icona.length()!=0)
		{
			boolean done=false;
			int i;
			for(i=0;i<icone.length&&!done;i++)
				if(scheda.icona.equals(icoDir+icone[i]))
					done=true;
			if(done)
				iIcona=i-1;
			else
				iIcona=0;
		}
		else
			iIcona=0;
		scheda.icona=icoDir+icone[iIcona];
		
		fontN=new Font("Dialog", Font.PLAIN, 12);
		fontB=new Font("Dialog", Font.BOLD, 12);

		icona = IconFactory.getInstance().getImage(File.separator+scheda.icona);

		setLayout(new BorderLayout(5,5));
		setVisible(false);
		setSize(getInsets().left + getInsets().right + 500, getInsets().top + getInsets().bottom + 425);
		Label label1 = new Label("Proprieta' scheda Monumento/Luogo Storico",Label.CENTER);
		label1.setFont(new Font("Dialog", Font.BOLD, 14));
		add("North",label1);
		
		panel=new Panel();
 		panel.setLayout(lay=new CardLayout());
 		add("Center",panel);

		panel1=new Panel();
		panel1.setLayout(null);
		panel1.setSize(470,310);
		cIcona = new IconViewer();
		cIcona.setBounds(430,15,52,52);
  		cIcona.setImage(icona);
		panel1.add(cIcona);
		Label label0 = new Label("Icona");
		label0.setBounds(440,0,250,16);
		label0.setFont(fontB);
		panel1.add(label0);
 
		Label label2 = new Label("Didascalia:");
		label2.setBounds(9,7,63,12);
		label2.setFont(fontB);
		panel1.add(label2);
		tfDidascalia = new CTextField(CTextField.CTF_ALL,50);
		tfDidascalia.setBounds(78,4,336,18);
		tfDidascalia.setBackground(Color.yellow);
		panel1.add(tfDidascalia);
		Label label2a = new Label("Data rilevamento:");
		label2a.setBounds(9,30,102,12);
		label2a.setFont(fontB);
		panel1.add(label2a);
		tfGiorno = new CTextField(CTextField.CTF_NUM,2);
		tfGiorno.setBounds(117,27,24,18);
		panel1.add(tfGiorno);
		tfMese = new CTextField(CTextField.CTF_NUM,2);
		tfMese.setBounds(147,27,24,18);
		panel1.add(tfMese);
		tfAnno = new CTextField(CTextField.CTF_NUM,4);
		tfAnno.setBounds(177,27,44,18);
		panel1.add(tfAnno);
		Label label2a1 = new Label("/");
		label2a1.setBounds(143,30,6,12);
		label2a1.setFont(fontB);
		panel1.add(label2a1);
		Label label2a2 = new Label("/");
		label2a2.setBounds(173,30,6,12);
		label2a2.setFont(fontB);
		panel1.add(label2a2);
		Label label2b = new Label("Ora rilevamento:");
		label2b.setBounds(231,30,96,12);
		label2b.setFont(fontB);
		panel1.add(label2b);
		tfOra = new CTextField(CTextField.CTF_NUM,2);
		tfOra.setBounds(330,27,24,18);
		panel1.add(tfOra);
		tfMinuti = new CTextField(CTextField.CTF_NUM,2);
		tfMinuti.setBounds(360,27,24,18);
		panel1.add(tfMinuti);
		tfSecondi = new CTextField(CTextField.CTF_NUM,2);
		tfSecondi.setBounds(390,27,24,18);
		panel1.add(tfSecondi);
		Label label2b1 = new Label(":");
		label2b1.setBounds(356,30,6,12);
		label2b1.setFont(fontB);
		panel1.add(label2b1);
		Label label2b2 = new Label(":");
		label2b2.setBounds(386,30,6,12);
		label2b2.setFont(fontB);
		panel1.add(label2b2);
		Label label3 = new Label("Denominazione:");
		label3.setBounds(9,53,90,12);
		label3.setFont(fontB);
		panel1.add(label3);
		tfDenominazione = new CTextField(CTextField.CTF_ALL,50);
		tfDenominazione.setBounds(105,50,309,18);
		tfDenominazione.setBackground(Color.yellow);
		panel1.add(tfDenominazione);
		Label label4 = new Label("Descrizione:");
		label4.setBounds(9,73,110,14);
		label4.setFont(fontB);
		panel1.add(label4);
		taDescrizione = new TextArea();
		taDescrizione.setBounds(9,90,476,54);
		taDescrizione.setBackground(Color.yellow);
		panel1.add(taDescrizione);
		Label label5 = new Label("Comune e prov. in cui a' sito:");
		label5.setBounds(9,153,160,14);
		label5.setFont(fontB);
		panel1.add(label5);
		tfComune= new CTextField(CTextField.CTF_ALL,50);
		tfComune.setBounds(173,150,312,18);
		panel1.add(tfComune);
		Label label6 = new Label("Tipo di rilevanza:");
		label6.setBounds(9,177,95,14);
		label6.setFont(fontB);
		panel1.add(label6);
		tfTipoRilevanza= new CTextField(CTextField.CTF_ALL,50);
		tfTipoRilevanza.setBounds(108,174,377,18);
		panel1.add(tfTipoRilevanza);
		Label label7 = new Label("Proprieta' attuale:");
		label7.setBounds(9,200,110,14);
		label7.setFont(fontB);
		panel1.add(label7);
		taProprieta = new TextArea();
		taProprieta.setBounds(9,217,476,54);
		panel1.add(taProprieta);
		Label label30= new Label("Immagine:");
		label30.setBounds(9,281,60,14);
		label30.setFont(fontB);
		panel1.add(label30);
		tfImmagine = new CTextField(CTextField.CTF_ALL,255);
		tfImmagine.setBounds(75,278,345,18);
		panel1.add(tfImmagine);
		browse=new DimButton(62,20);
		browse.setLabel("Sfoglia");
		browse.setBounds(423,277,62,20);
		panel1.add(browse);
		Label label31 = new Label("Video:");
		label31.setBounds(9,305,40,14);
		label31.setFont(fontB);
		panel1.add(label31);
		tfVideo = new CTextField(CTextField.CTF_ALL,255);
		tfVideo.setBounds(55,302,365,18);
		panel1.add(tfVideo);
		browseV=new DimButton(62,20);
		browseV.setLabel("Sfoglia");
		browseV.setBounds(423,301,62,20);
		panel1.add(browseV);
		iPrev = new DimButton(16,14);
		iPrev.setActionCommand("iprev");
		iPrev.setLabel("<");
		iPrev.setSize(16,14);
		iPrev.setBounds(430,69,26,14);
		iPrev.setBackground(new Color(12632256));
		panel1.add(iPrev);
		iSucc = new DimButton(16,14);
		iSucc.setActionCommand("isucc");
		iSucc.setLabel(">");
		iSucc.setBackground(new Color(12632256));
		iSucc.setBounds(456,69,26,14);
		panel1.add(iSucc);
		setEnabledIconButtons();
		panel.add("1",panel1);
		
		panel2=new Panel();
		panel2.setLayout(null);
		Label label9 = new Label("Monumento - Fatto costruire da:");
		label9.setBounds(9,0,300,12);
		label9.setFont(fontB);
		panel2.add(label9);
		taFattoCostruire = new java.awt.TextArea();
		taFattoCostruire.setBounds(9,15,476,54);
		panel2.add(taFattoCostruire);
		Label label10 = new Label("Monumento - Percha' lo fece costruire:");
		label10.setBounds(9,72,300,12);
		label10.setFont(fontB);
		panel2.add(label10);
		taPercheCostruire = new TextArea();
		taPercheCostruire.setBounds(9,87,476,54);
		panel2.add(taPercheCostruire);
		Label label11 = new Label("Monumento - Materiali usati per la costruzione:");
		label11.setBounds(9,144,300,12);
		label11.setFont(fontB);
		panel2.add(label11);
		taMateriali = new TextArea();
		taMateriali.setBounds(9,159,476,54);
		panel2.add(taMateriali);
		Label label12 = new Label("Monumento - Opere d'arte presenti:");
		label12.setBounds(9,216,300,12);
		label12.setFont(fontB);
		panel2.add(label12);
		taOpere = new TextArea();
		taOpere.setBounds(9,231,476,54);
		panel2.add(taOpere);
		Label label14 = new Label("Monumento - Data della prima costruzione:");
		label14.setBounds(9,297,242,12);
		label14.setFont(fontB);
		panel2.add(label14);
		tfDataCostruzione = new CTextField(CTextField.CTF_ALL,50);
		tfDataCostruzione.setBounds(255,293,230,18);
		panel2.add(tfDataCostruzione);
		panel.add("2",panel2);

		panel3=new Panel();
		Label label15 = new Label("Luogo Storico - Data principale avvenimento:");
		label15.setBounds(9,3,255,14);
		label15.setFont(fontB);
		panel3.add(label15);
		tfDataAvvenimento = new CTextField(CTextField.CTF_ALL,255);
		tfDataAvvenimento.setBounds(268,0,217,18);
		panel3.add(tfDataAvvenimento);
		panel3.setLayout(null);
		Label label16 = new Label("Luogo Storico - Da cosa a' scaturito l'avvenimento:");
		label16.setBounds(9,27,300,14);
		label16.setFont(fontB);
		panel3.add(label16);
		taPercheAvvenimento = new java.awt.TextArea();
		taPercheAvvenimento.setBounds(9,42,476,54);
		panel3.add(taPercheAvvenimento);
		Label label17 = new Label("Luogo Storico - Racconto dell'avvenimento:");
		label17.setBounds(9,99,300,14);
		label17.setFont(fontB);
		panel3.add(label17);
		taRaccontoAvvenimento = new TextArea();
		taRaccontoAvvenimento.setBounds(9,114,476,54);
		panel3.add(taRaccontoAvvenimento);
		Label label18 = new Label("Uso Attuale:");
		label18.setBounds(9,171,300,14);
		label18.setFont(fontB);
		panel3.add(label18);
		taUso = new TextArea();
		taUso.setBounds(9,186,476,54);
		panel3.add(taUso);
		Label label19 = new Label("Stato di manutenzione:");
		label19.setBounds(9,243,300,14);
		label19.setFont(fontB);
		panel3.add(label19);
		taStato = new TextArea();
		taStato.setBounds(9,257,476,54);
		panel3.add(taStato);
		panel.add("3",panel3);

		Panel bPanel=new Panel();
		Panel b1Panel=new Panel();
		Panel b2Panel=new Panel();
		b1Panel.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
		b2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT,0,0));
		bPanel.setLayout(new BorderLayout(1,1));
		bPrev = new DimButton(16,14);
		bPrev.setActionCommand("prev");
		bPrev.setLabel("<");
		bPrev.setSize(16,14);
		bPrev.setBackground(new Color(12632256));
		b2Panel.add(bPrev);
		bSucc = new DimButton(16,14);
		bSucc.setActionCommand("succ");
		bSucc.setLabel(">");
		bSucc.setBackground(new Color(12632256));
		b2Panel.add(bSucc);
		okbutton = new DimButton(62,24);
		okbutton.setActionCommand("OK");
		okbutton.setLabel("OK");
		okbutton.setFont(new Font("Dialog", Font.BOLD, 12));
		okbutton.setBackground(new Color(12632256));
		b1Panel.add(okbutton);
		cancelbutton = new DimButton(62,24);
		cancelbutton.setActionCommand("KO");
		cancelbutton.setLabel("Annulla");
		cancelbutton.setFont(new Font("Dialog", Font.BOLD, 12));
		cancelbutton.setBackground(new Color(12632256));
		b1Panel.add(cancelbutton);
	    bPrev.setEnabled(false);   	    
	    bSucc.setEnabled(true);
	    bPanel.add("Center",b1Panel);
	    bPanel.add("North",b2Panel);
	    add("South",bPanel);

		//LISTENERS
		SymMouse aSymMouse = new SymMouse();
		okbutton.addMouseListener(aSymMouse);
		cancelbutton.addMouseListener(aSymMouse);
        bPrev.addMouseListener(aSymMouse);
        bSucc.addMouseListener(aSymMouse);
		browse.addMouseListener(aSymMouse);
		browseV.addMouseListener(aSymMouse);
		SymKey aSymKey = new SymKey();
		tfDidascalia.addKeyListener(aSymKey);
		tfGiorno.addKeyListener(aSymKey);
		tfMese.addKeyListener(aSymKey);
		tfAnno.addKeyListener(aSymKey);
		tfOra.addKeyListener(aSymKey);
		tfMinuti.addKeyListener(aSymKey);
		tfSecondi.addKeyListener(aSymKey);
		tfDenominazione.addKeyListener(aSymKey);
		tfComune.addKeyListener(aSymKey);
		tfTipoRilevanza.addKeyListener(aSymKey);
		tfDataCostruzione.addKeyListener(aSymKey);
		tfDataAvvenimento.addKeyListener(aSymKey);
		tfImmagine.addKeyListener(aSymKey);
		tfVideo.addKeyListener(aSymKey);
		iPrev.addMouseListener(aSymMouse);
        iSucc.addMouseListener(aSymMouse);
        
   		//Init dei campi
		tfDidascalia.setText(scheda.didascalia);
		if(rs.scheda.timestamp.length()==0)
		{
			rs.scheda.timestamp=parent.c_param.data;
		}
		if(rs.scheda.timestamp.length()!=0)
		{
		    StringTokenizer t=new StringTokenizer(scheda.timestamp,"-");
		    tfGiorno.setText(t.nextToken());
		    tfMese.setText(t.nextToken());
		    tfAnno.setText(t.nextToken());
		    tfOra.setText(t.nextToken());
		    tfMinuti.setText(t.nextToken());
		    tfSecondi.setText(t.nextToken());
		}
		tfDenominazione.setText(scheda.denominazione);
		tfComune.setText(scheda.comune);
		tfTipoRilevanza.setText(scheda.tipoRilevanza);
		tfDataCostruzione.setText(scheda.dataCostruzione);
		tfDataAvvenimento.setText(scheda.dataAvvenimento);
		taDescrizione.setText(scheda.descrizione);
		taProprieta.setText(scheda.proprieta);
		taStato.setText(scheda.stato);
		taUso.setText(scheda.uso);
		taFattoCostruire.setText(scheda.fattoCostruire);
		taPercheCostruire.setText(scheda.percheCostruire);
		taMateriali.setText(scheda.materiali);
		taOpere.setText(scheda.opere);
		taPercheAvvenimento.setText(scheda.percheAvvenimento);
		taRaccontoAvvenimento.setText(scheda.raccontoAvvenimento);
		tfImmagine.setText(scheda.immagine);
		tfVideo.setText(scheda.video);
	}

	//mette a posto l'attivazione dei bottoni per l'icona
	void setEnabledIconButtons()
	{
		if(icone.length<=1)
		{
			iPrev.setEnabled(false);
			iSucc.setEnabled(false);
		}
		else
		{
			if(iIcona==0)
			{
				iPrev.setEnabled(false);
				iSucc.setEnabled(true);
			}
			else if(iIcona==icone.length-1)
			{
				iPrev.setEnabled(true);
				iSucc.setEnabled(false);
			}
			else
			{
				iPrev.setEnabled(true);
				iSucc.setEnabled(true);
			}
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
			else if (object == bPrev)
				bPrev_MouseClick(event);
			else if (object == bSucc)
				bSucc_MouseClick(event);
			else if (object == browse)
				browseFile(tfImmagine,"Scegli l'immagine...");
			else if (object == browseV)
				browseFile(tfVideo,"Scegli il video...");
			else if (object == iPrev)
				iPrev_MouseClick();
			else if (object == iSucc)
				iSucc_MouseClick();
		}
	}

	void iPrev_MouseClick()
	{
		iIcona--;
		scheda.icona=icoDir+icone[iIcona];

        icona = IconFactory.getInstance().getImage(scheda.icona);
  		cIcona.setImage(icona);
		cIcona.repaint();

		setEnabledIconButtons();
	}

	void iSucc_MouseClick()
	{
		iIcona++;
		scheda.icona=icoDir+icone[iIcona];

        icona = IconFactory.getInstance().getImage(scheda.icona);
  		cIcona.setImage(icona);
		cIcona.repaint();

		setEnabledIconButtons();
	}

	void okbutton_MouseClick(java.awt.event.MouseEvent event)
	{
	    String didascalia,denominazione,descrizione;
	    if((didascalia=tfDidascalia.getText()).length()==0)
	    {
	        AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il campo Didascalia non pua' essere vuoto.");
		    d.display();
		}
	    else if((denominazione=tfDenominazione.getText()).length()==0)
	    {
	        AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il campo Denominazione non pua' essere vuoto.");
		    d.display();
		}
	    else if((descrizione=taDescrizione.getText()).length()==0)
	    {
	        AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il campo Descrizione non pua' essere vuoto.");
		    d.display();
		}
		else
		{
            rs.title=didascalia;
            scheda.didascalia=didascalia;
			scheda.denominazione=denominazione;
			scheda.descrizione=descrizione;
            if(tfGiorno.getText().length()!=0||tfMese.getText().length()!=0||
               tfAnno.getText().length()!=0||tfOra.getText().length()!=0||
               tfMinuti.getText().length()!=0||tfSecondi.getText().length()!=0)
            {
               TimeStamp t=new TimeStamp(tfGiorno.getText(),tfMese.getText(),tfAnno.getText(),
                                        tfOra.getText(),tfMinuti.getText(),tfSecondi.getText());
                if(t.validate())
                    scheda.timestamp=t.toString();
                else
                {
	                AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Data/Ora errata.");
		            d.display();
        		    if(numlay!=1){lay.first(panel);numlay=1;}
        		    tfGiorno.requestFocus();
		            return;
		        }
		    }
		    else
		        scheda.timestamp="";

			scheda.comune=tfComune.getText();
			scheda.tipoRilevanza=tfTipoRilevanza.getText();
			scheda.dataCostruzione=tfDataCostruzione.getText();
			scheda.dataAvvenimento=tfDataAvvenimento.getText();
			scheda.proprieta=taProprieta.getText();
			scheda.stato=taStato.getText();
			scheda.uso=taUso.getText();
			scheda.fattoCostruire=taFattoCostruire.getText();
			scheda.percheCostruire=taPercheCostruire.getText();
			scheda.materiali=taMateriali.getText();
			scheda.opere=taOpere.getText();
			scheda.percheAvvenimento=taPercheAvvenimento.getText();
			scheda.raccontoAvvenimento=taRaccontoAvvenimento.getText();
			File f;
			if(tfImmagine.getText().length()!=0)
			{
				f=new File(tfImmagine.getText());
				if(f.exists() && f.isAbsolute() && f.isFile())
		            scheda.immagine=tfImmagine.getText();
				else
				{
	                AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il file immagine "+tfImmagine.getText()+" non a' corretto!");
					d.display();
		            return;
				}
			} else {
				scheda.immagine = "";
			}
			if(tfVideo.getText().length()!=0)
			{
				f=new File(tfVideo.getText());
				if(f.exists() && f.isAbsolute() && f.isFile())
		            scheda.video=tfVideo.getText();
				else
				{
	                AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il file video "+tfVideo.getText()+" non a' corretto!");
					d.display();
		            return;
				}
			} else {
				scheda.video = "";
			}
	
    		setVisible(false);
    		dispose();
    	}
	}

	void cancelbutton_MouseClick(java.awt.event.MouseEvent event)
	{
		setVisible(false);
		dispose();
	}

	void bPrev_MouseClick(java.awt.event.MouseEvent event)
	{
	    lay.previous(panel);
	    if(numlay==2)
	    {
    	    bPrev.setEnabled(false);
	        bSucc.setEnabled(true);
	        tfDidascalia.requestFocus();
	    }
	    else if(numlay==3)
	    {
    	    bPrev.setEnabled(true);
	        bSucc.setEnabled(true);
	        taFattoCostruire.requestFocus();
	    }
	    numlay--;
	}
	void bSucc_MouseClick(java.awt.event.MouseEvent event)
	{
   	    lay.next(panel);
   	    if(numlay==1)
   	    {
    	    bPrev.setEnabled(true);   	    
	        bSucc.setEnabled(true);
	        taFattoCostruire.requestFocus();
	    }
	    else if(numlay==2)
   	    {
    	    bPrev.setEnabled(true);   	    
	        bSucc.setEnabled(false);
	        tfDataAvvenimento.requestFocus();
	    }
	    numlay++;
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