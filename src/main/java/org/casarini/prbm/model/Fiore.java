/* Fiore.java
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

package org.casarini.prbm.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Vector;

import org.casarini.prbm.parser.PRBMParser;
import org.casarini.prbm.parser.PRBMParserImgDimensionNode;
import org.casarini.prbm.parser.PRBMParserNode;
import org.casarini.prbm.util.DiskUtil;
import org.casarini.prbm.util.ImageInfo;


public class Fiore extends Scheda implements java.io.Serializable
{
	private static final long serialVersionUID = 3738616291117333148L;

	public String nomeComune;
    public String nomeScientifico;
    public String desFiore;
    public int nPetali;
    public String desFoglia;
    public int altezza;
    public String habitat;
    public String distribuzione;
    public String proprieta;
    public String curiosita;
    public String imgerbario;
    
    public Fiore()
    {
        super();
        nomeComune=new String("");
        nomeScientifico=new String("");
        desFiore=new String("");
        nPetali=-1;
        desFoglia=new String("");
        altezza=-1;
        habitat=new String("");
        distribuzione=new String("");
        proprieta=new String("");
        curiosita=new String("");
        immagine=new String("");
        video=new String("");
        imgerbario = new String("");
    }
    
	public Scheda copy()
	{
		Fiore s=new Fiore();
		s.didascalia=didascalia;
		s.icona=icona;
		s.timestamp=timestamp;
		s.note=note;

		s.nomeComune=nomeComune;
		s.nomeScientifico=nomeScientifico;
		s.desFiore=desFiore;
		s.nPetali=nPetali;
		s.desFoglia=desFoglia;
		s.altezza=altezza;
		s.habitat=habitat;
		s.distribuzione=distribuzione;
		s.proprieta=proprieta;
		s.curiosita=curiosita;
		s.immagine=immagine;
		s.video=video;
		s.imgerbario = imgerbario;

		return (Scheda)s;
	}

	public void toHTML(String file, String template)
    {
        String sTipo="Fiore/Erba";
        String sIcoTipo=icona;
		String dir=file.substring(0,file.lastIndexOf("/")+1);

        Vector nodes;
        PRBMParser parser;

        nodes = new Vector();
        nodes.addElement(new PRBMParserNode('S',"scheda.ico.src", sIcoTipo, 0, null));
        nodes.addElement(new PRBMParserNode('S',"scheda.tipo", sTipo, 0, null));
        nodes.addElement(new PRBMParserNode('S',"scheda.didascalia", didascalia, 0, null));
        if(immagine.length()>0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.immagine", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.immagine.src", immagine.substring(immagine.lastIndexOf(File.separatorChar)+1), 0, null));
        	ImageInfo ii = new ImageInfo();
        	try {
        		ii.setInput(new FileInputStream(immagine));
				if (!ii.check()) {
					System.err.println("Not a supported image file format.");
				} else {
					nodes.addElement(new PRBMParserImgDimensionNode('C', "scheda.immagine.dimensioni", null, 0, null, ii.getWidth(), ii.getHeight()));
				}
        	} catch(FileNotFoundException fnfe) {
				System.err.println("File not found: " + immagine);
        	}
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.immagine", null, 0, null));
        if(imgerbario.length()>0)
        {
                nodes.addElement(new PRBMParserNode('I',"scheda.imgerbario", null, 1, null));
                nodes.addElement(new PRBMParserNode('S',"scheda.imgerbario.src", imgerbario.substring(imgerbario.lastIndexOf(File.separatorChar)+1), 0, null));
            	ImageInfo ii = new ImageInfo();
            	try {
            		ii.setInput(new FileInputStream(imgerbario));
    				if (!ii.check()) {
    					System.err.println("Not a supported image file format.");
    				} else {
    					nodes.addElement(new PRBMParserImgDimensionNode('C', "scheda.imgerbario.dimensioni", null, 0, null, ii.getWidth(), ii.getHeight()));
    				}
            	} catch(FileNotFoundException fnfe) {
    				System.err.println("File not found: " + imgerbario);
            	}
            }
            else
                nodes.addElement(new PRBMParserNode('I',"scheda.imgerbario", null, 0, null));
        if(video.length()>0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.video", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.video.src", video.substring(video.lastIndexOf(File.separatorChar)+1), 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.video", null, 0, null));
        if(timestamp.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.dataora", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.dataora.value", TimeStamp.toHtml(timestamp), 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.dataora", null, 0, null));
        if(nomeComune.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.nomeComune", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.nomeComune.value", nomeComune, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.nomeComune", null, 0, null));
        if(nomeScientifico.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.nomeScientifico", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.nomeScientifico.value", nomeScientifico, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.nomeScientifico", null, 0, null));
        if(desFiore.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.desFiore", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.desFiore.value", desFiore, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.desFiore", null, 0, null));
        if(nPetali!=-1)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.nPetali", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.nPetali.value", Integer.toString(nPetali), 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.nPetali", null, 0, null));
        if(desFoglia.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.desFoglia", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.desFoglia.value", desFoglia, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.desFoglia", null, 0, null));
        if(altezza!=-1)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.altezza", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.altezza.value", Integer.toString(altezza), 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.altezza", null, 0, null));
        if(habitat.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.habitat", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.habitat.value", habitat, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.habitat", null, 0, null));
        if(distribuzione.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.distribuzione", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.distribuzione.value", distribuzione, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.distribuzione", null, 0, null));
        if(curiosita.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.curiosita", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.curiosita.value", curiosita, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.curiosita", null, 0, null));
        parser = new PRBMParser("template" + File.separator + template + File.separator + "fiore.tmpl", file, nodes);
        parser.parse();

        if(immagine.length()!=0)
        {
            File f=new File(immagine);
            if(f.exists())
                DiskUtil.copyFile(immagine,dir+immagine.substring(immagine.lastIndexOf(File.separatorChar)+1));
            else
                System.out.println("Errore");
        }
        if(imgerbario.length()!=0)
        {
            File f=new File(imgerbario);
            if(f.exists())
                DiskUtil.copyFile(imgerbario,dir+imgerbario.substring(imgerbario.lastIndexOf(File.separatorChar)+1));
            else
                System.out.println("Errore");
        }
        if(video.length()!=0)
        {
            File f=new File(video);
            if(f.exists())
                DiskUtil.copyFile(video,dir+video.substring(video.lastIndexOf(File.separatorChar)+1));
            else
                System.out.println("Errore");
        }
    }

}