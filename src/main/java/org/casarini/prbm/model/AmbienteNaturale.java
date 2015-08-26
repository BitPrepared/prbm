/* AmbienteNaturale.java
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


public class AmbienteNaturale extends Scheda implements java.io.Serializable
{
	private static final long serialVersionUID = 892931624802455818L;

	public String localita;
    public String desGenerale;
    public String habitat;
    public String flora;
    public String fauna;
    public String uomo;
    public String okPiaciuto;
    public String koPiaciuto;
    public String positivo;
    public String negativo;
    public String audio;
    
    public AmbienteNaturale()
    {
        super();
        localita=new String("");
        desGenerale=new String("");
        habitat=new String("");
        flora=new String("");
        fauna=new String("");
        uomo=new String("");
        okPiaciuto=new String("");
        koPiaciuto=new String("");
        positivo=new String("");
        negativo=new String("");
        immagine=new String("");
        audio=new String("");
        video=new String("");
    }
    
	public Scheda copy()
	{
		AmbienteNaturale s=new AmbienteNaturale();
		s.didascalia=didascalia;
		s.icona=icona;
		s.timestamp=timestamp;
		s.note=note;

		s.localita=localita;
		s.desGenerale=desGenerale;
		s.habitat=habitat;
		s.flora=flora;
		s.fauna=fauna;
		s.uomo=uomo;
		s.okPiaciuto=okPiaciuto;
		s.koPiaciuto=koPiaciuto;
		s.positivo=positivo;
		s.negativo=negativo;
		s.immagine=immagine;
		s.audio=audio;
		s.video=video;

		return (Scheda)s;
	}
    
    public void toHTML(String file, String template)
    {
        String sTipo="Ambiente Naturale";
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
        if(localita.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.localita", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.localita.value", localita, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.localita", null, 0, null));
        if(desGenerale.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.desGenerale", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.desGenerale.value", desGenerale, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.desGenerale", null, 0, null));
        if(habitat.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.habitat", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.habitat.value", habitat, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.habitat", null, 0, null));
        if(flora.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.flora", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.flora.value", flora, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.flora", null, 0, null));
        if(fauna.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.fauna", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.fauna.value", fauna, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.fauna", null, 0, null));
        if(uomo.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.uomo", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.uomo.value", uomo, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.uomo", null, 0, null));
        if(okPiaciuto.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.okPiaciuto", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.okPiaciuto.value", okPiaciuto, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.okPiaciuto", null, 0, null));
        if(koPiaciuto.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.koPiaciuto", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.koPiaciuto.value", koPiaciuto, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.koPiaciuto", null, 0, null));
        if(positivo.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.positivo", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.positivo.value", positivo, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.positivo", null, 0, null));
        if(negativo.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.negativo", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.negativo.value", negativo, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.negativo", null, 0, null));
        parser = new PRBMParser("template" + File.separator + template + File.separator + "ambnat.tmpl", file, nodes);
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