/* DialogEditMeteo.java
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
import org.casarini.prbm.model.Meteo;
import org.casarini.prbm.model.Resource;
import org.casarini.prbm.model.TimeStamp;
import org.casarini.prbm.util.IconFactory;
import org.casarini.prbm.util.XMLDirectoryList;


public class DialogEditMeteo extends DefaultDialogEdit
{
	private static final long serialVersionUID = 5927245619601661727L;

	Panel panel,panel1,panel2,panel3,panel4;
    DimButton okbutton,cancelbutton,bPrev,bSucc,browse,browseV,browseA,iPrev,iSucc;
    CardLayout lay;
    Font fontN,fontB;
    CTextField tfDidascalia,tfTemperatura,tfUmidita,tfPressione,tfImmagine,tfVideo,tfAudio;
    CTextField tfGiorno,tfMese,tfAnno,tfOra,tfMinuti,tfSecondi;
	CTextField tfDirVento,tfIntVento,tfPhPioggia,tfPhNeve,tfDiamGrandine,tfPhGrandine;
	CTextField tfAngSole,tfPosSole,tfVisNebbia,tfVelocNubi;
    TextArea taIntPioggia,taTorbPioggia,taIntNeve,taTipoNeve,taTorbNeve,taEventi;
    TextArea taIntGrandine,taTorbGrandine,taAnomSole,taTipoNebbia,taTipoNubi;
    IconViewer cIcona;
    Image icona;
    Meteo scheda;
    PRB parent;
    Resource rs;
    int numlay=1;
	public static final String icoDir="icone/met/";
	String icone[];
	int iIcona=0;
    
	public DialogEditMeteo(PRB parent,Resource rs)
	{
		super(parent, true);
		this.parent=parent;
		this.rs=rs;
		this.setTitle("Modifica scheda Meteo");
		scheda=(Meteo)rs.scheda;
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
		Label label1 = new Label("Proprietà scheda Meteo",Label.CENTER);
		label1.setFont(new Font("Dialog", Font.BOLD, 14));
		add("North",label1);
		
		panel=new Panel();
 		panel.setLayout(lay=new CardLayout());
 		add("Center",panel);

		panel1=new Panel();
		panel1.setLayout(null);
		panel1.setSize(470,310);
		cIcona = new IconViewer();
		cIcona.setBounds(433,0,52,52);
  		cIcona.setImage(icona);
		panel1.add(cIcona);
		//Label label0 = new Label("Icona");
		//label0.setBounds(440,2,250,16);
		//label0.setFont(fontB);
		//panel1.add(label0);
 
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
		Label label3 = new Label("Temperatura:");
		label3.setBounds(9,53,78,12);
		label3.setFont(fontB);
		panel1.add(label3);
		tfTemperatura = new CTextField(CTextField.CTF_INT,3);
		tfTemperatura.setBounds(93,50,35,18);
		panel1.add(tfTemperatura);
		Label label3b = new Label("°C");
		label3b.setBounds(130,53,13,12);
		label3b.setFont(fontB);
		panel1.add(label3b);
		Label label4 = new Label("Umidità:");
		label4.setBounds(162,53,48,12);
		label4.setFont(fontB);
		panel1.add(label4);
		tfUmidita = new CTextField(CTextField.CTF_NUM,3);
		tfUmidita.setBounds(215,50,35,18);
		panel1.add(tfUmidita);
		Label label4b = new Label("%");
		label4b.setBounds(253,53,10,12);
		label4b.setFont(fontB);
		panel1.add(label4b);
		Label label5 = new Label("Pressione:");
		label5.setBounds(280,53,60,12);
		label5.setFont(fontB);
		panel1.add(label5);
		tfPressione = new CTextField(CTextField.CTF_NUM,5);
		tfPressione.setBounds(346,50,50,18);
		panel1.add(tfPressione);
		Label label5b = new Label("mBar");
		label5b.setBounds(399,53,30,12);
		label5b.setFont(fontB);
		panel1.add(label5b);
		Label label6 = new Label("Direzione Vento:");
		label6.setBounds(9,76,92,12);
		label6.setFont(fontB);
		panel1.add(label6);
		tfDirVento = new CTextField(CTextField.CTF_ALL,16);
		tfDirVento.setBounds(104,73,130,18);
		panel1.add(tfDirVento);
		Label label7 = new Label("Velocità Vento:");
		label7.setBounds(245,76,87,12);
		label7.setFont(fontB);
		panel1.add(label7);
		tfIntVento = new CTextField(CTextField.CTF_ALL,20);
		tfIntVento.setBounds(335,73,150,18);
		panel1.add(tfIntVento);
		Label label8 = new Label("Pioggia - intensità:");
		label8.setBounds(9,99,110,14);
		label8.setFont(fontB);
		panel1.add(label8);
		taIntPioggia = new TextArea();
		taIntPioggia.setBounds(9,116,476,54);
		panel1.add(taIntPioggia);
		Label label9 = new Label("Pioggia - torbidità dell'acqua:");
		label9.setBounds(9,181,102,14);
		label9.setFont(fontB);
		panel1.add(label9);
		taTorbPioggia = new TextArea();
		taTorbPioggia.setBounds(9,198,476,54);
		panel1.add(taTorbPioggia);
		Label label10 = new Label("Pioggia - PH dell'acqua:");
		label10.setBounds(9,262,131,14);
		label10.setFont(fontB);
		panel1.add(label10);
		tfPhPioggia= new CTextField(CTextField.CTF_FLOAT,3);
		tfPhPioggia.setBounds(146,259,35,18);
		panel1.add(tfPhPioggia);
		Label label30= new Label("Immagine:");
		label30.setBounds(9,285,60,14);
		label30.setFont(fontB);
		panel1.add(label30);
		tfImmagine = new CTextField(CTextField.CTF_ALL,255);
		tfImmagine.setBounds(75,282,345,18);
		panel1.add(tfImmagine);
		browse=new DimButton(62,20);
		browse.setLabel("Sfoglia");
		browse.setBounds(423,281,62,20);
		panel1.add(browse);
		iPrev = new DimButton(16,14);
		iPrev.setActionCommand("iprev");
		iPrev.setLabel("<");
		iPrev.setSize(16,14);
		iPrev.setBounds(433,54,26,14);
		iPrev.setBackground(new Color(12632256));
		panel1.add(iPrev);
		iSucc = new DimButton(16,14);
		iSucc.setActionCommand("isucc");
		iSucc.setLabel(">");
		iSucc.setBackground(new Color(12632256));
		iSucc.setBounds(459,54,26,14);
		panel1.add(iSucc);
		setEnabledIconButtons();
		panel.add("1",panel1);
		
		panel2=new Panel();
		panel2.setLayout(null);
		Label label12 = new Label("Neve - intensità:");
		label12.setBounds(9,4,150,14);
		label12.setFont(fontB);
		panel2.add(label12);
		taIntNeve = new java.awt.TextArea();
		taIntNeve.setBounds(9,19,476,54);
		panel2.add(taIntNeve);
		Label label13 = new Label("Neve - tipo di fiocco:");
		label13.setBounds(9,84,150,14);
		label13.setFont(fontB);
		panel2.add(label13);
		taTipoNeve = new TextArea();
		taTipoNeve.setBounds(9,99,476,54);
		panel2.add(taTipoNeve);
		Label label14 = new Label("Neve - torbidità dell'acqua disciolta:");
		label14.setBounds(9,164,200,14);
		label14.setFont(fontB);
		panel2.add(label14);
		taTorbNeve = new TextArea();
		taTorbNeve.setBounds(9,179,476,54);
		panel2.add(taTorbNeve);
		Label label15 = new Label("Neve - PH dell'acqua disciolta:");
		label15.setBounds(9,252,170,14);
		label15.setFont(fontB);
		panel2.add(label15);
		tfPhNeve = new CTextField(CTextField.CTF_FLOAT,3);
		tfPhNeve.setBounds(185,249,35,18);
		panel2.add(tfPhNeve);
		Label label31 = new Label("Video:");
		label31.setBounds(9,292,40,14);
		label31.setFont(fontB);
		panel2.add(label31);
		tfVideo = new CTextField(CTextField.CTF_ALL,255);
		tfVideo.setBounds(55,288,365,18);
		panel2.add(tfVideo);
		browseV=new DimButton(62,20);
		browseV.setLabel("Sfoglia");
		browseV.setBounds(423,287,62,20);
		panel2.add(browseV);
		panel.add("2",panel2);

		panel3=new Panel();
		panel3.setLayout(null);
		Label label18 = new Label("Grandine - Intensità:");
		label18.setBounds(9,4,150,14);
		label18.setFont(fontB);
		panel3.add(label18);
		taIntGrandine = new java.awt.TextArea();
		taIntGrandine.setBounds(9,19,476,54);
		panel3.add(taIntGrandine);
		Label label19 = new Label("Grandine - torbidità dell'acqua disciolta:");
		label19.setBounds(9,84,250,14);
		label19.setFont(fontB);
		panel3.add(label19);
		taTorbGrandine = new TextArea();
		taTorbGrandine.setBounds(9,99,476,54);
		panel3.add(taTorbGrandine);
		Label label20 = new Label("Grandine - diametro medio dei chicchi:");
		label20.setBounds(9,163,216,14);
		label20.setFont(fontB);
		panel3.add(label20);
		tfDiamGrandine = new CTextField(CTextField.CTF_FLOAT,3);
		tfDiamGrandine.setBounds(231,160,35,18);
		panel3.add(tfDiamGrandine);
		Label label20b = new Label("cm");
		label20b.setBounds(268,163,20,14);
		label20b.setFont(fontB);
		panel3.add(label20b);
		Label label21 = new Label("Grandine - PH dell'acqua disciolta:");
		label21.setBounds(9,188,192,14);
		label21.setFont(fontB);
		panel3.add(label21);
		tfPhGrandine = new CTextField(CTextField.CTF_FLOAT,3);
		tfPhGrandine.setBounds(207,185,35,18);
		panel3.add(tfPhGrandine);
		Label label22 = new Label("Eventi particolari (trombe d'aria, tornadi, cicloni, tempeste, bufere, ecc.):");
		label22.setBounds(9,213,400,14);
		label22.setFont(fontB);
		panel3.add(label22);
		taEventi = new TextArea();
		taEventi.setBounds(9,230,476,54);
		panel3.add(taEventi);
		Label label32 = new Label("Audio:");
		label32.setBounds(9,295,40,14);
		label32.setFont(fontB);
		panel3.add(label32);
		tfAudio = new CTextField(CTextField.CTF_ALL,255);
		tfAudio.setBounds(55,291,365,18);
		panel3.add(tfAudio);
		browseA=new DimButton(62,20);
		browseA.setLabel("Sfoglia");
		browseA.setBounds(423,290,62,20);
		panel3.add(browseA);
		panel.add("3",panel3);
		
		panel4=new Panel();
		panel4.setLayout(null);
		Label label123 = new Label("Sole - angolazione rispetto al piano orizzontale:");
		label123.setBounds(9,4,270,14);
		label123.setFont(fontB);
		panel4.add(label123);
		tfAngSole = new CTextField(CTextField.CTF_NUM,3);
		tfAngSole.setBounds(279,1,35,18);
		panel4.add(tfAngSole);
		Label label123b = new Label("°");
		label123b.setBounds(317,4,10,14);
		label123b.setFont(fontB);
		panel4.add(label123b);
		Label label24 = new Label("Sole - posizione al momento del rilievo:");
		label24.setBounds(9,29,220,14);
		label24.setFont(fontB);
		panel4.add(label24);
		tfPosSole = new CTextField(CTextField.CTF_ALL,16);
		tfPosSole.setBounds(233,26,130,18);
		panel4.add(tfPosSole);
		Label label25 = new Label("Sole - eventuali anomalie (eclissi, ecc.):");
		label25.setBounds(9,54,250,14);
		label25.setFont(fontB);
		panel4.add(label25);
		taAnomSole = new TextArea();
		taAnomSole.setBounds(9,69,476,54);
		panel4.add(taAnomSole);
		Label label26 = new Label("Nebbia - visibilità media:");
		label26.setBounds(9,132,140,14);
		label26.setFont(fontB);
		panel4.add(label26);
		tfVisNebbia = new CTextField(CTextField.CTF_NUM,4);
		tfVisNebbia.setBounds(150,129,40,18);
		panel4.add(tfVisNebbia);
		Label label26b = new Label("m");
		label26b.setBounds(193,132,140,14);
		label26b.setFont(fontB);
		panel4.add(label26b);
		Label label27 = new Label("Nebbia - tipo di formazione (nebbia, nuvole, evaporazione, ecc.):");
		label27.setBounds(9,154,400,14);
		label27.setFont(fontB);
		panel4.add(label27);
		taTipoNebbia = new TextArea();
		taTipoNebbia.setBounds(9,169,476,54);
		panel4.add(taTipoNebbia);
		Label label28 = new Label("Nubi - velocità di spostamento e direzione:");
		label28.setBounds(9,232,240,14);
		label28.setFont(fontB);
		panel4.add(label28);
		tfVelocNubi = new CTextField(CTextField.CTF_ALL,16);
		tfVelocNubi.setBounds(253,229,130,18);
		panel4.add(tfVelocNubi);
		Label label29 = new Label("Nubi - tipo:");
		label29.setBounds(9,254,250,14);
		label29.setFont(fontB);
		panel4.add(label29);
		taTipoNubi = new TextArea();
		taTipoNubi.setBounds(9,269,476,54);
		panel4.add(taTipoNubi);
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
		iPrev.addMouseListener(aSymMouse);
        iSucc.addMouseListener(aSymMouse);
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
		tfTemperatura.addKeyListener(aSymKey);
		tfUmidita.addKeyListener(aSymKey);
		tfPressione.addKeyListener(aSymKey);
		tfDirVento.addKeyListener(aSymKey);
		tfIntVento.addKeyListener(aSymKey);
		tfPhPioggia.addKeyListener(aSymKey);
		tfPhNeve.addKeyListener(aSymKey);
		tfDiamGrandine.addKeyListener(aSymKey);
		tfPhGrandine.addKeyListener(aSymKey);
		tfAngSole.addKeyListener(aSymKey);
		tfPosSole.addKeyListener(aSymKey);
		tfVisNebbia.addKeyListener(aSymKey);
		tfVelocNubi.addKeyListener(aSymKey);
		tfImmagine.addKeyListener(aSymKey);
		tfVideo.addKeyListener(aSymKey);
		tfAudio.addKeyListener(aSymKey);
        
        
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
		if(scheda.temperatura>-65535)
		    tfTemperatura.setText(""+scheda.temperatura);
		if(scheda.umidita>=0)
		    tfUmidita.setText(""+scheda.umidita);
		if(scheda.pressione>=0)
		    tfPressione.setText(""+scheda.pressione);
		tfDirVento.setText(scheda.dirVento);
		tfIntVento.setText(scheda.intVento);
		taIntPioggia.setText(scheda.intPioggia);
		taTorbPioggia.setText(scheda.torbPioggia);
		if(scheda.phPioggia>=0)
		    tfPhPioggia.setText(""+scheda.phPioggia);
		taIntNeve.setText(scheda.intNeve);
		taTipoNeve.setText(scheda.tipoNeve);
		taTorbNeve.setText(scheda.torbNeve);
		if(scheda.phNeve>=0)
			tfPhNeve.setText(""+scheda.phNeve);
		taIntGrandine.setText(scheda.intGrandine);
		taTorbGrandine.setText(scheda.torbGrandine);
		if(scheda.diamGrandine>=0)
		    tfDiamGrandine.setText(""+scheda.diamGrandine);
		if(scheda.phGrandine>=0)
		    tfPhGrandine.setText(""+scheda.phGrandine);
		taEventi.setText(scheda.eventi);
		if(scheda.angSole>=0)
		    tfAngSole.setText(""+scheda.angSole);
		tfPosSole.setText(scheda.posSole);
		taAnomSole.setText(scheda.anomSole);
		if(scheda.visNebbia>=0)
		    tfVisNebbia.setText(""+scheda.visNebbia);
		taTipoNebbia.setText(scheda.tipoNebbia);
		taTipoNubi.setText(scheda.tipoNubi);
		tfVelocNubi.setText(scheda.velocNubi);
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
	    String didascalia;
	    if((didascalia=tfDidascalia.getText()).length()==0)
	    {
	        AttentionDialog d=new AttentionDialog(parent,"ATTENZIONE!","Il campo Didascalia non può essere vuoto.");
		    d.display();
		}
		else
		{
            rs.title=didascalia;
            scheda.didascalia=didascalia;
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
			if((temp=tfTemperatura.getText()).length()!=0)
			    scheda.temperatura=Integer.parseInt(temp);
			if((temp=tfUmidita.getText()).length()!=0)
			    scheda.umidita=Integer.parseInt(temp);
			if((temp=tfPressione.getText()).length()!=0)
			    scheda.pressione=Integer.parseInt(temp);
			scheda.dirVento=tfDirVento.getText();
			scheda.intVento=tfIntVento.getText();
			scheda.intPioggia=taIntPioggia.getText();
			scheda.torbPioggia=taTorbPioggia.getText();
			if((temp=tfPhPioggia.getText()).length()!=0)
			    scheda.phPioggia=Float.parseFloat(temp);
			scheda.intNeve=taIntNeve.getText();
			scheda.tipoNeve=taTipoNeve.getText();
			scheda.torbNeve=taTorbNeve.getText();
			if((temp=tfPhNeve.getText()).length()!=0)
			    scheda.phNeve=Float.parseFloat(temp);
			scheda.intGrandine=taIntGrandine.getText();
			scheda.torbGrandine=taTorbGrandine.getText();
			if((temp=tfDiamGrandine.getText()).length()!=0)
			    scheda.diamGrandine=Float.parseFloat(temp);
			if((temp=tfPhGrandine.getText()).length()!=0)
			    scheda.phGrandine=Float.parseFloat(temp);
			scheda.eventi=taEventi.getText();
			if((temp=tfAngSole.getText()).length()!=0)
			    scheda.angSole=Integer.parseInt(temp);
			scheda.posSole=tfPosSole.getText();
			scheda.anomSole=taAnomSole.getText();
			if((temp=tfVisNebbia.getText()).length()!=0)
			    scheda.visNebbia=Integer.parseInt(temp);
			scheda.tipoNebbia=taTipoNebbia.getText();
			scheda.tipoNubi=taTipoNubi.getText();
			scheda.velocNubi=tfVelocNubi.getText();
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
	        taIntNeve.requestFocus();
	    }
	    else if(numlay==4)
	    {
    	    bPrev.setEnabled(true);
	        bSucc.setEnabled(true);
	        taIntGrandine.requestFocus();
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
	        taIntNeve.requestFocus();
	    }
	    else if(numlay==2)
   	    {
    	    bPrev.setEnabled(true);   	    
	        bSucc.setEnabled(true);
	        taIntGrandine.requestFocus();
	    }
	    else if(numlay==3)
   	    {
    	    bPrev.setEnabled(true);   	    
	        bSucc.setEnabled(false);
	        tfAngSole.requestFocus();
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