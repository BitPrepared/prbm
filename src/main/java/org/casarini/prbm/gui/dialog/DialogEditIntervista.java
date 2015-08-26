/* DialogEditIntervista.java
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
import org.casarini.prbm.model.Intervista;
import org.casarini.prbm.model.Resource;
import org.casarini.prbm.model.TimeStamp;
import org.casarini.prbm.util.IconFactory;
import org.casarini.prbm.util.XMLDirectoryList;


public class DialogEditIntervista extends DefaultDialogEdit
{
	private static final long serialVersionUID = 8347697555307156172L;
	
	Panel panel,panel1,panel2,panel3,panel4;
    DimButton okbutton,cancelbutton,bPrev,bSucc,browse,browseV,browseA,iPrev,iSucc;
    CardLayout lay;
    Font fontN,fontB;
    CTextField tfDidascalia,tfImmagine,tfVideo,tfAudio,tfNominativo,tfEta;
    CTextField tfGiorno,tfMese,tfAnno,tfOra,tfMinuti,tfSecondi;
	TextArea taProfessione,taStatoCivile,taProvenienza,taVeste,taPerche,taCome;
	TextArea taStatoAnimo,taGradimento,taAffidabilita,taFedelta,taRapporto,taTrascrizione;
	IconViewer cIcona;
    Image icona;
    Intervista scheda;
    PRB parent;
    Resource rs;
    int numlay=1;
	public static final String icoDir="icone/int/";
	String icone[];
	int iIcona=0;
    
	public DialogEditIntervista(PRB parent,Resource rs)
	{
		super(parent, true);
		this.parent=parent;
		this.rs=rs;
		this.setTitle("Modifica scheda Intervista");
		scheda=(Intervista)rs.scheda;
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
		setSize(getInsets().left + getInsets().right + 500, getInsets().top + getInsets().bottom + 425);
		Label label1 = new Label("Proprietà scheda Intervista",Label.CENTER);
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
		Label label3 = new Label("Nominativo:");
		label3.setBounds(9,53,65,12);
		label3.setFont(fontB);
		panel1.add(label3);
		tfNominativo = new CTextField(CTextField.CTF_ALL,100);
		tfNominativo.setBounds(80,50,334,18);
		tfNominativo.setBackground(Color.yellow);
		panel1.add(tfNominativo);
		Label label4 = new Label("Età:");
		label4.setBounds(9,76,20,12);
		label4.setFont(fontB);
		panel1.add(label4);
		tfEta = new CTextField(CTextField.CTF_NUM,3);
		tfEta.setBounds(35,73,35,18);
		panel1.add(tfEta);
		Label label5 = new Label("Professione:");
		label5.setBounds(9,99,99,12);
		label5.setFont(fontB);
		panel1.add(label5);
		taProfessione = new TextArea();
		taProfessione.setBounds(9,114,476,54);
		panel1.add(taProfessione);
		Label label6 = new Label("Stato Civile:");
		label6.setBounds(9,178,102,12);
		label6.setFont(fontB);
		panel1.add(label6);
		taStatoCivile = new TextArea();
		taStatoCivile.setBounds(9,191,476,54);
		panel1.add(taStatoCivile);
		Label label7 = new Label("E' del luogo o è forestiero?");
		label7.setBounds(9,257,150,12);
		label7.setFont(fontB);
		panel1.add(label7);
		taProvenienza = new TextArea();
		taProvenienza.setBounds(9,270,476,54);
		panel1.add(taProvenienza);
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
		Label label9 = new Label("E' stato intervistato in veste di:");
		label9.setBounds(9,4,250,12);
		label9.setFont(fontB);
		panel2.add(label9);
		taVeste = new java.awt.TextArea();
		taVeste.setBounds(9,19,476,54);
		panel2.add(taVeste);
		Label label10 = new Label("Perchè lo si è intervistato:");
		label10.setBounds(9,84,250,12);
		label10.setFont(fontB);
		panel2.add(label10);
		taPerche = new TextArea();
		taPerche.setBounds(9,99,476,54);
		panel2.add(taPerche);
		Label label11 = new Label("Come si è arrivati a lui:");
		label11.setBounds(9,164,250,12);
		label11.setFont(fontB);
		panel2.add(label11);
		taCome = new TextArea();
		taCome.setBounds(9,179,476,54);
		panel2.add(taCome);
		Label label12 = new Label("Stato d'animo dell'intervistato:");
		label12.setBounds(9,244,250,12);
		label12.setFont(fontB);
		panel2.add(label12);
		taStatoAnimo = new TextArea();
		taStatoAnimo.setBounds(9,259,476,54);
		panel2.add(taStatoAnimo);
		panel.add("2",panel2);

		panel3=new Panel();
		panel3.setLayout(null);
		Label label13 = new Label("Gradimento dell'intervistato:");
		label13.setBounds(9,4,250,12);
		label13.setFont(fontB);
		panel3.add(label13);
		taGradimento = new java.awt.TextArea();
		taGradimento.setBounds(9,19,476,54);
		panel3.add(taGradimento);
		Label label14 = new Label("Affidabilità del racconto:");
		label14.setBounds(9,84,250,12);
		label14.setFont(fontB);
		panel3.add(label14);
		taAffidabilita = new TextArea();
		taAffidabilita.setBounds(9,99,476,54);
		panel3.add(taAffidabilita);
		Label label15 = new Label("Fedeltà del racconto:");
		label15.setBounds(9,164,250,12);
		label15.setFont(fontB);
		panel3.add(label15);
		taFedelta = new TextArea();
		taFedelta.setBounds(9,179,476,54);
		panel3.add(taFedelta);
		Label label16 = new Label("Rapporto con l'intervistato:");
		label16.setBounds(9,244,250,12);
		label16.setFont(fontB);
		panel3.add(label16);
		taRapporto = new TextArea();
		taRapporto.setBounds(9,259,476,54);
		panel3.add(taRapporto);
		panel.add("3",panel3);
		
		panel4=new Panel();
		panel4.setLayout(null);
		Label label17 = new Label("Trascrizione dell'intervista:");
		label17.setBounds(9,4,250,12);
		label17.setFont(fontB);
		panel4.add(label17);
		taTrascrizione = new java.awt.TextArea();
		taTrascrizione.setBounds(9,19,476,225);
		panel4.add(taTrascrizione);
		Label label30= new Label("Immagine:");
		label30.setBounds(9,256,60,14);
		label30.setFont(fontB);
		panel4.add(label30);
		tfImmagine = new CTextField(CTextField.CTF_ALL,255);
		tfImmagine.setBounds(75,253,345,18);
		panel4.add(tfImmagine);
		browse=new DimButton(62,20);
		browse.setLabel("Sfoglia");
		browse.setBounds(423,252,62,20);
		panel4.add(browse);
		Label label31 = new Label("Audio:");
		label31.setBounds(9,281,40,14);
		label31.setFont(fontB);
		panel4.add(label31);
		tfAudio = new CTextField(CTextField.CTF_ALL,255);
		tfAudio.setBounds(55,278,365,18);
		panel4.add(tfAudio);
		browseA=new DimButton(62,20);
		browseA.setLabel("Sfoglia");
		browseA.setBounds(423,277,62,20);
		panel4.add(browseA);
		Label label32 = new Label("Video:");
		label32.setBounds(9,305,40,14);
		label32.setFont(fontB);
		panel4.add(label32);
		tfVideo = new CTextField(CTextField.CTF_ALL,255);
		tfVideo.setBounds(55,302,365,18);
		panel4.add(tfVideo);
		browseV=new DimButton(62,20);
		browseV.setLabel("Sfoglia");
		browseV.setBounds(423,301,62,20);
		panel4.add(browseV);
		panel.add("4",panel4);

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
		browseA.addMouseListener(aSymMouse);
		SymKey aSymKey = new SymKey();
		tfDidascalia.addKeyListener(aSymKey);
		tfGiorno.addKeyListener(aSymKey);
		tfMese.addKeyListener(aSymKey);
		tfAnno.addKeyListener(aSymKey);
		tfOra.addKeyListener(aSymKey);
		tfMinuti.addKeyListener(aSymKey);
		tfSecondi.addKeyListener(aSymKey);
		tfNominativo.addKeyListener(aSymKey);
		tfEta.addKeyListener(aSymKey);
		tfImmagine.addKeyListener(aSymKey);
		tfVideo.addKeyListener(aSymKey);
		tfAudio.addKeyListener(aSymKey);
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
		tfNominativo.setText(scheda.nominativo);
		if(scheda.eta>=0)
			tfEta.setText(""+scheda.eta);
		taProfessione.setText(scheda.professione);
		taStatoCivile.setText(scheda.statoCivile);
		taProvenienza.setText(scheda.provenienza);
		taVeste.setText(scheda.veste);
		taPerche.setText(scheda.perche);
		taCome.setText(scheda.come);
		taStatoAnimo.setText(scheda.statoAnimo);
		taGradimento.setText(scheda.gradimento);
		taAffidabilita.setText(scheda.affidabilita);
		taFedelta.setText(scheda.fedelta);
		taRapporto.setText(scheda.rapporto);
		taTrascrizione.setText(scheda.trascrizione);
		tfImmagine.setText(scheda.immagine);
		tfVideo.setText(scheda.video);
		tfAudio.setText(scheda.audio);
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
			else if (object == browseA)
				browseFile(tfAudio,"Scegli l'audio...");
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
	    String didascalia,nominativo;
	    if((didascalia=tfDidascalia.getText()).length()==0)
	    {
	        AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il campo Didascalia non può essere vuoto.");
		    d.display();
		}
	    else if((nominativo=tfNominativo.getText()).length()==0)
	    {
	        AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il campo Nominativo non può essere vuoto.");
		    d.display();
		}
		else
		{
            rs.title=didascalia;
            scheda.didascalia=didascalia;
			scheda.nominativo=nominativo;
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
			String temp;
			if((temp=tfEta.getText()).length()!=0)
			    scheda.eta=Integer.parseInt(temp);
			scheda.professione=taProfessione.getText();
			scheda.statoCivile=taStatoCivile.getText();
			scheda.provenienza=taProvenienza.getText();
			scheda.veste=taVeste.getText();
			scheda.perche=taPerche.getText();
			scheda.come=taCome.getText();
			scheda.statoAnimo=taStatoAnimo.getText();
			scheda.gradimento=taGradimento.getText();
			scheda.affidabilita=taAffidabilita.getText();
			scheda.fedelta=taFedelta.getText();
			scheda.rapporto=taRapporto.getText();
			scheda.trascrizione=taTrascrizione.getText();
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
			if(tfAudio.getText().length()!=0)
			{
				f=new File(tfAudio.getText());
				if(f.exists() && f.isAbsolute() && f.isFile())
		            scheda.audio=tfAudio.getText();
				else
				{
	                AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il file audio "+tfAudio.getText()+" non è corretto!");
					d.display();
		            return;
				}
			} else {
				scheda.audio = "";
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
	        taVeste.requestFocus();
	    }
	    else if(numlay==4)
	    {
    	    bPrev.setEnabled(true);
	        bSucc.setEnabled(true);
	        taGradimento.requestFocus();
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
	        taVeste.requestFocus();
	    }
	    else if(numlay==2)
   	    {
    	    bPrev.setEnabled(true);   	    
	        bSucc.setEnabled(true);
	        taGradimento.requestFocus();
	    }
	    else if(numlay==3)
   	    {
    	    bPrev.setEnabled(true);   	    
	        bSucc.setEnabled(false);
	        taTrascrizione.requestFocus();
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