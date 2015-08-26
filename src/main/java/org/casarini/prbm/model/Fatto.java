/* Fatto.java
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


public class Fatto extends Scheda implements java.io.Serializable
{
	private static final long serialVersionUID = -1230839803436756387L;

	public String descrizione;
	public String dove;
	public String persone;
	public String cosa;
	public String perche;
	public String negativo;
	public String conseguenze;
	public String suscitato;
	public String imparato;
	public String audio;

    public Fatto()
    {
        super();
		descrizione=new String("");
		dove=new String("");
		persone=new String("");
		cosa=new String("");
		perche=new String("");
		negativo=new String("");
		conseguenze=new String("");
		suscitato=new String("");
		imparato=new String("");
		immagine=new String("");
		audio=new String("");
		video=new String("");
    }
    
	public Scheda copy()
	{
		Fatto s=new Fatto();
		s.didascalia=didascalia;
		s.icona=icona;
		s.timestamp=timestamp;
		s.note=note;

		s.descrizione=descrizione;
		s.dove=dove;
		s.persone=persone;
		s.cosa=cosa;
		s.perche=perche;
		s.negativo=negativo;
		s.conseguenze=conseguenze;
		s.suscitato=suscitato;
		s.imparato=imparato;
		s.immagine=immagine;
		s.audio=audio;
		s.video=video;

		return (Scheda)s;
	}
    
    public void toHTML(String file, String template)
    {
        String sTipo="Fatto di Cronaca";
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
        if(audio.length()>0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.audio", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.audio.src", audio.substring(audio.lastIndexOf(File.separatorChar)+1), 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.audio", null, 0, null));
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
        if(descrizione.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.descrizione", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.descrizione.value", descrizione, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.descrizione", null, 0, null));
        if(dove.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.dove", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.dove.value", dove, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.dove", null, 0, null));
        if(persone.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.persone", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.persone.value", persone, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.persone", null, 0, null));
        if(cosa.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.cosa", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.cosa.value", cosa, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.cosa", null, 0, null));
        if(perche.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.perche", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.perche.value", perche, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.perche", null, 0, null));
        if(negativo.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.negativo", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.negativo.value", negativo, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.negativo", null, 0, null));
        if(conseguenze.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.conseguenze", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.conseguenze.value", conseguenze, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.conseguenze", null, 0, null));
        if(suscitato.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.suscitato", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.suscitato.value", suscitato, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.suscitato", null, 0, null));
        if(imparato.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.imparato", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.imparato.value", imparato, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.imparato", null, 0, null));
        parser = new PRBMParser("template" + File.separator + template + File.separator + "cronaca.tmpl", file, nodes);
        parser.parse();

        if(immagine.length()!=0)
        {
            File f=new File(immagine);
            if(f.exists())
                DiskUtil.copyFile(immagine,dir+immagine.substring(immagine.lastIndexOf(File.separatorChar)+1));
            else
                System.out.println("Errore");
        }
        if(audio.length()!=0)
        {
            File f=new File(audio);
            if(f.exists())
                DiskUtil.copyFile(audio,dir+audio.substring(audio.lastIndexOf(File.separatorChar)+1));
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