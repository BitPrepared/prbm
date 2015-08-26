/* SetPRBBox.java
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
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import org.casarini.prbm.gui.PRB;
import org.casarini.prbm.gui.component.CTextField;
import org.casarini.prbm.model.PRBParam;
import org.casarini.prbm.model.TimeStamp;

public class SetPRBBox extends Dialog implements ActionListener
{
	private static final long serialVersionUID = -7532552794321704523L;

	PRBParam param;
    PRB parent;
    CTextField tfGiorno,tfMese,tfAnno;

    public SetPRBBox(PRB parent,PRBParam param)
    {
	    super((Frame)parent, "Parametri del PRB", true);
	    setBackground(Color.lightGray);

        this.param=param;
        this.parent=(PRB)parent;


	    //{{INIT_CONTROL
		setLayout(null);
		setVisible(false);
		setSize(378,346);
		e_title = new java.awt.TextField(21);
		e_title.setBounds(147,27,175,19);
		add(e_title);
		e_site = new java.awt.TextField(26);
		e_site.setBounds(147,53,217,19);
		add(e_site);
		tfGiorno = new CTextField(CTextField.CTF_NUM,2);
		tfGiorno.setBounds(147,79,24,18);
		add(tfGiorno);
		tfMese = new CTextField(CTextField.CTF_NUM,2);
		tfMese.setBounds(177,79,24,18);
		add(tfMese);
		tfAnno = new CTextField(CTextField.CTF_NUM,4);
		tfAnno.setBounds(207,79,44,18);
		add(tfAnno);
		Label label2a1 = new Label("/");
		label2a1.setBounds(173,82,6,12);
		add(label2a1);
		Label label2a2 = new Label("/");
		label2a2.setBounds(203,82,6,12);
		add(label2a2);

		e_autori=new java.awt.TextArea();
		e_autori.setBounds(147,130,217,48);
		add(e_autori);
		e_note=new java.awt.TextArea();
		e_note.setBounds(147,180,217,59);
		add(e_note);
		e_dir = new java.awt.TextField(26);
		e_dir.setBounds(147,252,217,19);
		add(e_dir);
		ext = new CheckboxGroup();
		c_htm = new java.awt.Checkbox(".htm", ext, false);
		c_htm.setBounds(186,284,56,13);
		add(c_htm);
		c_html = new java.awt.Checkbox(".html", ext, false);
		c_html.setBounds(240,284,105,12);
		add(c_html);
		OKButton = new java.awt.Button();
		OKButton.setLabel("OK");
		OKButton.setBounds(77,310,91,26);
		add(OKButton);
		XButton = new java.awt.Button();
		XButton.setLabel("Annulla");
		XButton.setBounds(203,310,91,26);
		add(XButton);
		l_title = new java.awt.Label("Titolo :",Label.RIGHT);
		l_title.setBounds(70,33,70,13);
		add(l_title);
		l_site = new java.awt.Label("Località :",Label.RIGHT);
		l_site.setBounds(70,59,70,13);
		add(l_site);
		l_data = new java.awt.Label("Data della rilevazione :",Label.RIGHT);
		l_data.setBounds(7,85,133,13);
		add(l_data);
		l_modifica = new java.awt.Label("Data ultima modifica :",Label.RIGHT);
		l_modifica.setBounds(14,111,126,13);
		add(l_modifica);
		l_autori = new java.awt.Label("Autori :",Label.RIGHT);
		l_autori.setBounds(70,135,70,13);
		add(l_autori);
		l_note = new java.awt.Label("Note :",Label.RIGHT);
		l_note.setBounds(70,183,70,13);
		add(l_note);
		l_dir = new java.awt.Label("Directory di output :",Label.RIGHT);
		l_dir.setBounds(7,258,133,13);
		add(l_dir);
		label1 = new java.awt.Label("Estensione finale dei file HTML");
		label1.setBounds(6,284,174,12);
		add(label1);
		setTitle("");
		//}}

    	setResizable(false);
        e_title.setText(param.title);
        e_title.setColumns(10);
		if(param.data.length()==0)
		{
			Date d=new Date(System.currentTimeMillis());
			SimpleDateFormat formatter = new SimpleDateFormat ("dd-MM-yyyy-hh-mm-ss");
			param.data = formatter.format(d);
		}

		if(param.data.length()!=0)
		{
		    StringTokenizer t=new StringTokenizer(param.data,"-");
		    tfGiorno.setText(t.nextToken());
		    tfMese.setText(t.nextToken());
		    tfAnno.setText(t.nextToken());
		}
		Label l_um=new Label();
		if(param.modifica.length()!=0)
		{
		    StringTokenizer t=new StringTokenizer(param.modifica,"-");
		    String temp=new String();
		    temp=t.nextToken();
		    temp+="/";
		    temp+=t.nextToken();
		    temp+="/";
		    temp+=t.nextToken();
		    l_um.setText(temp);
		}
		else
		    l_um.setText("nessuna");
		l_um.setBounds(150,111,126,13);
		add(l_um);
        e_site.setText(param.site);
        e_autori.setText(param.autori);
        e_note.setText(param.note);
        e_dir.setText(param.dir);
        if(param.ext)
            c_html.setState(true);
        else
            c_htm.setState(true);

    	OKButton.addActionListener(this);
    	XButton.addActionListener(this);
    	
    	SymKey aSymKey = new SymKey();
    	tfGiorno.addKeyListener(aSymKey);
		tfMese.addKeyListener(aSymKey);
		tfAnno.addKeyListener(aSymKey);

       	Rectangle bounds = getParent().getBounds();
    	Rectangle abounds = getBounds();

    	setLocation(bounds.x + (bounds.width - abounds.width)/ 2,
    	     bounds.y + (bounds.height - abounds.height)/2);

    	super.setVisible(true);
	}


    public void actionPerformed(ActionEvent e)
    {
        String cmd=e.getActionCommand();
        if(cmd.equalsIgnoreCase("Ok"))
  	    	clickedOkButton();
  	    else if(cmd.equalsIgnoreCase("Annulla"))
  	    	clickedXButton();
  	}

    //{{DECLARE_CONTROLS
	java.awt.TextField e_title;
	java.awt.TextField e_site;
	java.awt.TextField e_data;
	java.awt.TextField e_modifica;
	java.awt.TextField e_dir;
	java.awt.TextArea e_note;
	java.awt.TextArea e_autori;
	java.awt.Checkbox c_htm;
	CheckboxGroup ext;
	java.awt.Checkbox c_html;
	java.awt.Button OKButton;
	java.awt.Button XButton;
	java.awt.Label l_title;
	java.awt.Label l_site;
	java.awt.Label l_data;
	java.awt.Label l_modifica;
	java.awt.Label l_autori;
	java.awt.Label l_note;
	java.awt.Label label1;
	java.awt.Label l_dir;
	//}}

    public void clickedOkButton()
    {
        param.title=e_title.getText();
        param.site=e_site.getText();
        if(tfGiorno.getText().length()!=0||tfMese.getText().length()!=0||tfAnno.getText().length()!=0)
        {
            TimeStamp t=new TimeStamp(tfGiorno.getText(),tfMese.getText(),tfAnno.getText(),"0","0","0");
            if(t.validate())
                param.data=t.toString();
            else
            {
                new AttentionDialog(parent,"ATTENZIONE!","Data errata.");
    		    tfGiorno.requestFocus();
	            return;
	        }
	    }
	    else
	        param.data="";

        param.autori=e_autori.getText();
        param.note=e_note.getText();
        param.dir=e_dir.getText();
        if(c_html.getState())
            param.ext=true;
        else
            param.ext=false;

        parent.setParam(param);
        parent.setPrbModified(true);
        setVisible(false);
        dispose();
    }
    public void clickedXButton()
    {
        setVisible(false);
        dispose();
    }
 
    class SymKey extends java.awt.event.KeyAdapter
	{
	}
}