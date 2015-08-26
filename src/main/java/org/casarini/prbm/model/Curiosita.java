/* Curiosita.java
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


public class Curiosita extends Scheda implements java.io.Serializable
{
	private static final long serialVersionUID = 4013684002977595023L;

	public String descrizione;
    public String perche;
    public String impressioni;
    public String audio;

    public Curiosita()
    {
        super();
        descrizione=new String("");
        perche=new String("");
        impressioni=new String("");
        immagine=new String("");
        audio=new String("");
        video=new String("");
    }
    
	public Scheda copy()
	{
		Curiosita s=new Curiosita();
		s.didascalia=didascalia;
		s.icona=icona;
		s.timestamp=timestamp;
		s.note=note;

 	    s.descrizione=descrizione;
	    s.perche=perche;
	    s.impressioni=impressioni;
	    s.immagine=immagine;
	    s.audio=audio;
	    s.video=video;

		return (Scheda)s;
	}
    
    public void toHTML(String file, String outputDir)
    {
        String sTipo="Curiosita'/Osservazione";
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
        if(perche.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.perche", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.perche.value", perche, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.perche", null, 0, null));
        if(impressioni.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.impressioni", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.impressioni.value", impressioni, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.impressioni", null, 0, null));
        parser = new PRBMParser(outputDir + File.separator + "curiosita.tmpl", file, nodes);
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