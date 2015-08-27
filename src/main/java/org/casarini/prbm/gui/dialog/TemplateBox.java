/* TemplateBox.java
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
import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.casarini.prbm.gui.PRB;
import org.casarini.prbm.util.StaticLoader;

public class TemplateBox extends Dialog implements ActionListener
{
	private static final long serialVersionUID = 3407355198908995138L;

	private Button yesButton;
    private Button noButton;
    private Choice template;
    private String result;

    public TemplateBox(Frame parent) throws IOException
    {
	    super(parent, "Scelta del template...", true);
	    setResizable(false);

        result=new String("");

    	//setLayout(null);
    	// Disabling due to NullPointerException
    	
    	addNotify();
    	setSize(350,135);
        Label label = new Label("Scegli il template che vuoi utilizzare per l'esportazione:");
        add(label);
        label.setBounds(5,25,340,24);
        template = new Choice();
        add(template);
        template.setBounds(75,55,200,24);

        Set<String> templateDisponibili = new HashSet<String>();
        
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        if(!jarFile.isFile()) { 
	        
	        //interno
	        URL templateInterno = StaticLoader.loadResource("/template");
	        
	        System.out.println("Cerco in "+templateInterno.getFile());        
	        File[] filesList = new File(templateInterno.getFile()).listFiles();
	        if ( null != filesList ) {
		        for(int i=0;i<filesList.length;i++)
		        	if(filesList[i].isDirectory()) {
		        		String name = filesList[i].getName();
		        		System.out.println("->"+name);
						templateDisponibili.add(name);
		        	}
	        }
	        
        } else {
	        List<String> elenco = StaticLoader.listDir("/template");
	        for (String element : elenco) {
	        	templateDisponibili.add(element);
			}
        }
        
        // posizione corrente del jar
		try {
			File homefile = new File(PRB.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			String home = homefile.getParent() + File.separator + "template";
			System.out.println("Cerco in "+home);
			File[] list=(new File(home)).listFiles();
			if ( list != null ) {
		        for(int i=0;i<list.length;i++)
		        	if(list[i].isDirectory())
		        		templateDisponibili.add(list[i].getName());
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
        
        for(String name : templateDisponibili) {
        	template.addItem(name);
        }
        
        template.select(0);
        yesButton=new Button("Esporta");
    	add(yesButton);
    	yesButton.setBounds(100,90,60,24);
    	noButton=new Button("Annulla");
    	add(noButton);
    	noButton.setBounds(190,90,60,24);

    	yesButton.addActionListener(this);
       	noButton.addActionListener(this);

    	Rectangle bounds = getParent().getBounds();
    	Rectangle abounds = getBounds();
    	setLocation(bounds.x + (bounds.width - abounds.width)/ 2,bounds.y + (bounds.height - abounds.height)/2);
    	setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        String cmd=e.getActionCommand();
        if(cmd.equalsIgnoreCase("Esporta"))
        {
       	    setVisible(false);
            result=template.getSelectedItem();
        }
        else if(cmd.equalsIgnoreCase("Annulla"))
        {
    	    setVisible(false);
    	}
    }

    public String getResult()
    {
        return result;
    }
}
