/* Scheda.java
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
import java.io.Serializable;
import java.util.Vector;

import org.casarini.prbm.parser.PRBMParser;
import org.casarini.prbm.parser.PRBMParserImgDimensionNode;
import org.casarini.prbm.parser.PRBMParserNode;
import org.casarini.prbm.util.DiskUtil;
import org.casarini.prbm.util.ImageInfo;


public class Scheda implements Serializable
{
	private static final long serialVersionUID = 2698819585273624610L;

	public String didascalia;
    public String icona;
    public String timestamp;
    public String note;
    public String immagine;
    public String video;
    public transient String html;
    
    public Scheda()
    {
        didascalia=new String("");
        icona=new String("");
        timestamp=new String("");
        note=new String("");
        immagine=new String("");
        video=new String("");
    }

	public Scheda copy()
	{
		Scheda s=new Scheda();
		s.didascalia=didascalia;
		s.icona=icona;
		s.timestamp=timestamp;
		s.note=note;
		s.immagine=immagine;
		s.video=video;
		
		return s;
	}
    
    public void toHTML(String destFileName, String workDir)
    {
    	System.out.println("scrivo scheda con nome "+destFileName+" a "+workDir);
    	
        String sTipo="Paesaggio";
        String sIcoTipo=icona;
		String dirDest=destFileName.substring(0,destFileName.lastIndexOf("/")+1);

        Vector nodes;
        PRBMParser parser;

        nodes = new Vector();
        nodes.addElement(new PRBMParserNode('S',"scheda.ico.src", sIcoTipo, 0, null));
        nodes.addElement(new PRBMParserNode('S',"scheda.tipo", sTipo, 0, null));
        nodes.addElement(new PRBMParserNode('S',"scheda.didascalia", didascalia, 0, null));
        System.out.println (didascalia);
        if (immagine == null)
            immagine=new String("");
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
        if (video == null)
            video=new String("");
        if(video.length()>0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.video", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.video.src", video.substring(video.lastIndexOf(File.separatorChar)+1), 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.video", null, 0, null));
        nodes.addElement(new PRBMParserNode('S',"scheda.dataora.value", TimeStamp.toHtml(timestamp), 0, null));
        nodes.addElement(new PRBMParserNode('S',"scheda.note", note, 0, null));
        parser = new PRBMParser(workDir + File.separator + "paesaggio.tmpl", destFileName, nodes);
        parser.parse();

        if(immagine.length()!=0)
        {
            File f=new File(immagine);
            if(f.exists())
                DiskUtil.copyFile(immagine,dirDest+immagine.substring(immagine.lastIndexOf(File.separatorChar)+1));
            else
                System.out.println("Errore");
        }
        if(video.length()!=0)
        {
            File f=new File(video);
            if(f.exists())
                DiskUtil.copyFile(video,dirDest+video.substring(video.lastIndexOf(File.separatorChar)+1));
            else
                System.out.println("Errore");
        }
    }
}