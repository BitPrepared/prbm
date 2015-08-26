/* DialogEditFiore.java
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
import org.casarini.prbm.model.Fiore;
import org.casarini.prbm.model.Resource;
import org.casarini.prbm.model.TimeStamp;
import org.casarini.prbm.util.IconFactory;
import org.casarini.prbm.util.XMLDirectoryList;


public class DialogEditFiore extends DefaultDialogEdit
{
	private static final long serialVersionUID = -5564741630706822733L;

	Panel panel,panel1,panel2;
    DimButton okbutton,cancelbutton,bPrev,bSucc,browse,browseE,browseV,iPrev,iSucc;
    CardLayout lay;
    Font fontN,fontB;
    CTextField tfDidascalia,tfNomeC,tfNomeS,tfPetali,tfAltezza,tfImmagine,tfVideo,tfImgErbario;
    CTextField tfGiorno,tfMese,tfAnno,tfOra,tfMinuti,tfSecondi;
    TextArea taDesFiore,taDesFoglia,taHabitat,taDistribuzione,taProprieta,taCuriosita;
    IconViewer cIcona;
    Image icona;
    Fiore scheda;
    PRB parent;
    Resource rs;
	public static final String icoDir="icone/fio/";
	String icone[];
	int iIcona=0;
    
	public DialogEditFiore(PRB parent,Resource rs)
	{
		super(parent, true);
		this.parent=parent;
		this.rs=rs;
		this.setTitle("Modifica scheda Fiore/Erba");
		scheda=(Fiore)rs.scheda;
    	setBackground(Color.lightGray);
		XMLDirectoryList dl = new XMLDirectoryList(IconFactory.RESOURCE_DIR + icoDir);
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

        icona = IconFactory.getInstance().getImage(scheda.icona);

		setLayout(new BorderLayout(5,5));
		setVisible(false);
		setSize(getInsets().left + getInsets().right + 500, getInsets().top + getInsets().bottom + 430);
		Label label1 = new Label("Proprietà scheda Fiore/Erba",Label.CENTER);
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
		Label label3 = new Label("Nome Comune:");
		label3.setBounds(9,53,87,12);
		label3.setFont(fontB);
		panel1.add(label3);
		tfNomeC = new CTextField(CTextField.CTF_ALL,50);
		tfNomeC.setBounds(102,50,312,18);
		tfNomeC.setBackground(Color.yellow);
		panel1.add(tfNomeC);
		Label label4 = new Label("Nome Scientifico:");
		label4.setBounds(9,76,102,12);
		label4.setFont(fontB);
		panel1.add(label4);
		tfNomeS = new CTextField(CTextField.CTF_ALL,50);
		tfNomeS.setBounds(117,73,297,18);
		panel1.add(tfNomeS);
		Label label5 = new Label("Descrizione fiore:");
		label5.setBounds(9,99,99,12);
		label5.setFont(fontB);
		panel1.add(label5);
		taDesFiore = new TextArea();
		taDesFiore.setBounds(9,114,476,54);
		taDesFiore.setBackground(Color.yellow);
		panel1.add(taDesFiore);
		Label label6 = new Label("Descrizione foglia:");
		label6.setBounds(9,181,102,12);
		label6.setFont(fontB);
		panel1.add(label6);
		taDesFoglia = new TextArea();
		taDesFoglia.setBounds(9,194,476,54);
		panel1.add(taDesFoglia);
		Label label7 = new Label("Numero Petali:");
		label7.setBounds(9,260,84,12);
		label7.setFont(fontB);
		panel1.add(label7);
		tfPetali = new CTextField(CTextField.CTF_NUM,2);
		tfPetali.setBounds(99,257,41,18);
		panel1.add(tfPetali);
		Label label8 = new Label("Altezza Media:");
		label8.setBounds(156,260,82,12);
		label8.setFont(fontB);
		panel1.add(label8);
		tfAltezza = new CTextField(CTextField.CTF_NUM,3);
		tfAltezza.setBounds(246,257,41,18);
		panel1.add(tfAltezza);
		Label label13= new Label("Immagine:");
		label13.setBounds(9,283,60,12);
		label13.setFont(fontB);
		panel1.add(label13);
		tfImmagine = new CTextField(CTextField.CTF_ALL,255);
		tfImmagine.setBounds(75,280,345,18);
		panel1.add(tfImmagine);
		browse=new DimButton(62,20);
		browse.setLabel("Sfoglia");
		browse.setBounds(423,279,62,20);
		panel1.add(browse);
		Label label17= new Label("Img. Erbario:");
		label17.setBounds(9,306,80,12);
		label17.setFont(fontB);
		panel1.add(label17);
		tfImgErbario = new CTextField(CTextField.CTF_ALL,255);
		tfImgErbario.setBounds(95,303,325,18);
		panel1.add(tfImgErbario);
		browseE=new DimButton(62,20);
		browseE.setLabel("Sfoglia");
		browseE.setBounds(423,302,62,20);
		panel1.add(browseE);
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
		Label label9 = new Label("Habitat Tipico:");
		label9.setBounds(9,0,81,12);
		label9.setFont(fontB);
		panel2.add(label9);
		taHabitat = new java.awt.TextArea();
		taHabitat.setBounds(9,15,476,54);
		panel2.add(taHabitat);
		Label label10 = new Label("Distribuzione:");
		label10.setBounds(9,72,81,12);
		label10.setFont(fontB);
		panel2.add(label10);
		taDistribuzione = new TextArea();
		taDistribuzione.setBounds(9,87,476,54);
		panel2.add(taDistribuzione);
		Label label11 = new Label("Proprietà:");
		label11.setBounds(9,144,81,12);
		label11.setFont(fontB);
		panel2.add(label11);
		taProprieta = new TextArea();
		taProprieta.setBounds(9,159,476,54);
		panel2.add(taProprieta);
		Label label12 = new Label("Curiosità:");
		label12.setBounds(9,216,81,12);
		label12.setFont(fontB);
		panel2.add(label12);
		taCuriosita = new TextArea();
		taCuriosita.setBounds(9,231,476,54);
		panel2.add(taCuriosita);
		Label label14 = new Label("Video:");
		label14.setBounds(9,297,40,12);
		label14.setFont(fontB);
		panel2.add(label14);
		tfVideo = new CTextField(CTextField.CTF_ALL,255);
		tfVideo.setBounds(55,293,365,18);
		panel2.add(tfVideo);
		browseV=new DimButton(62,20);
		browseV.setLabel("Sfoglia");
		browseV.setBounds(423,292,62,20);
		panel2.add(browseV);
		panel.add("2",panel2);
		
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
		browseE.addMouseListener(aSymMouse);
		browseV.addMouseListener(aSymMouse);
		SymKey aSymKey = new SymKey();
		tfDidascalia.addKeyListener(aSymKey);
		tfGiorno.addKeyListener(aSymKey);
		tfMese.addKeyListener(aSymKey);
		tfAnno.addKeyListener(aSymKey);
		tfOra.addKeyListener(aSymKey);
		tfMinuti.addKeyListener(aSymKey);
		tfSecondi.addKeyListener(aSymKey);
		tfNomeC.addKeyListener(aSymKey);
		tfNomeS.addKeyListener(aSymKey);
		tfPetali.addKeyListener(aSymKey);
		tfAltezza.addKeyListener(aSymKey);
		tfImmagine.addKeyListener(aSymKey);
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
		tfNomeC.setText(scheda.nomeComune);
		tfNomeS.setText(scheda.nomeScientifico);
		taDesFiore.setText(scheda.desFiore);
		if(scheda.nPetali>=0)
		    tfPetali.setText(""+scheda.nPetali);
		if(scheda.altezza>=0)
		    tfAltezza.setText(""+scheda.altezza);
		taDesFoglia.setText(scheda.desFoglia);
        taHabitat.setText(scheda.habitat);
        taDistribuzione.setText(scheda.distribuzione);
        taProprieta.setText(scheda.proprieta);
        taCuriosita.setText(scheda.curiosita);
        tfImmagine.setText(scheda.immagine);
        tfImgErbario.setText(scheda.imgerbario);
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
			else if (object == browseE)
				browseFile(tfImgErbario, "Scegli l'immagine per l'erbario...");
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
	    String didascalia,nomeC,des;
	    if((didascalia=tfDidascalia.getText()).length()==0)
	    {
	        AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il campo Didascalia non può essere vuoto.");
		    d.display();
		}
	    else if((nomeC=tfNomeC.getText()).length()==0)
	    {
	        AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il campo Nome Comune non può essere vuoto.");
		    d.display();
		}
	    else if((des=taDesFiore.getText()).length()==0)
	    {
	        AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il campo Descrizione Fiore non può essere vuoto.");
		    d.display();
		}
/*		else if((icona=((String)nomiImg.elementAt(lIcona.getSelectedIndex()))).length()==0)
	    {
	        AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Deve essere selezionata una icona.");
		    d.display();
		}*/
		else
		{
            rs.title=didascalia;
            scheda.didascalia=didascalia;
            scheda.nomeComune=nomeC;
            scheda.desFiore=des;
            //rs.scheda.icona=icona;
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
		            return;
		        }
		    }
		    else
		        scheda.timestamp="";
			scheda.nomeScientifico=tfNomeS.getText();
			String temp;
			if((temp=tfPetali.getText()).length()!=0)
			    scheda.nPetali=Integer.parseInt(temp);
			if((temp=tfAltezza.getText()).length()!=0)
			    scheda.altezza=Integer.parseInt(temp);
    		scheda.desFoglia=taDesFoglia.getText();
            scheda.habitat=taHabitat.getText();
            scheda.distribuzione=taDistribuzione.getText();
            scheda.proprieta=taProprieta.getText();
            scheda.curiosita=taCuriosita.getText();
			File f;
			if(tfImmagine.getText().length()!=0)
			{
				f=new File(tfImmagine.getText());
				if(f.exists() && f.isAbsolute() && f.isFile())
		            scheda.immagine=tfImmagine.getText();
				else
				{
	                AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il file immagine "+tfImmagine.getText()+" non è corretto!");
					d.display();
		            return;
				}
			} else {
				scheda.immagine = "";
			}
			
			if (tfImgErbario.getText().length()!=0) {
				f = new File(tfImgErbario.getText());
				if (f.exists() && f.isAbsolute() && f.isFile()) {
		            scheda.imgerbario = tfImgErbario.getText();
				} else {
	                AttentionDialog d = new AttentionDialog(parent,"ATTENZIONE!","Il file immagine per l'erbario "+tfImmagine.getText()+" non è corretto!");
					d.display();
		            return;
				}
			} else {
				scheda.imgerbario = "";
			}
			
			if(tfVideo.getText().length()!=0)
			{
				f=new File(tfVideo.getText());
				if(f.exists() && f.isAbsolute() && f.isFile())
		            scheda.video=tfVideo.getText();
				else
				{
	                AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il file video "+tfVideo.getText()+" non è corretto!");
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
	    bPrev.setEnabled(false);
	    bSucc.setEnabled(true);
	    tfDidascalia.requestFocus();
	}
	void bSucc_MouseClick(java.awt.event.MouseEvent event)
	{
   	    lay.next(panel);
	    bPrev.setEnabled(true);   	    
	    bSucc.setEnabled(false);
	    taHabitat.requestFocus();
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
