/* Intervista.java
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


public class Intervista extends Scheda implements java.io.Serializable
{
	private static final long serialVersionUID = 5451614891176456876L;

	public String nominativo;
	public int eta;
	public String professione;
	public String statoCivile;
	public String provenienza;
	public String veste;
	public String perche;
	public String come;
	public String statoAnimo;
	public String gradimento;
	public String affidabilita;
	public String fedelta;
	public String rapporto;
	public String trascrizione;
	public String audio;

    public Intervista()
    {
        super();
		nominativo=new String("");
		eta=-1;
		professione=new String("");
		statoCivile=new String("");
		provenienza=new String("");
		veste=new String("");
		perche=new String("");
		come=new String("");
		statoAnimo=new String("");
		gradimento=new String("");
		affidabilita=new String("");
		fedelta=new String("");
		rapporto=new String("");
		trascrizione=new String("");
		immagine=new String("");
		audio=new String("");
		video=new String("");
    }
    
	public Scheda copy()
	{
		Intervista s=new Intervista();
		s.didascalia=didascalia;
		s.icona=icona;
		s.timestamp=timestamp;
		s.note=note;

		s.nominativo=nominativo;
		s.eta=eta;
		s.professione=professione;
		s.statoCivile=statoCivile;
		s.provenienza=provenienza;
		s.veste=veste;
		s.perche=perche;
		s.come=come;
		s.statoAnimo=statoAnimo;
		s.gradimento=gradimento;
		s.affidabilita=affidabilita;
		s.fedelta=fedelta;
		s.rapporto=rapporto;
		s.trascrizione=trascrizione;
		s.immagine=immagine;
		s.audio=audio;
		s.video=video;

		return (Scheda)s;
	}
    
    public void toHTML(String file, String template)
    {
        String sTipo="Intervista";
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
        if(nominativo.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.nominativo", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.nominativo.value", nominativo, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.nominativo", null, 0, null));
        if(eta>=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.eta", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.eta.value", ""+eta, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.eta", null, 0, null));
        if(professione.length()>0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.professione", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.professione.value", professione, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.professione", null, 0, null));
        if(statoCivile.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.statoCivile", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.statoCivile.value", statoCivile, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.statoCivile", null, 0, null));
        if(provenienza.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.provenienza", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.provenienza.value", provenienza, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.provenienza", null, 0, null));
        if(veste.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.veste", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.veste.value", veste, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.veste", null, 0, null));
        if(perche.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.perche", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.perche.value", perche, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.perche", null, 0, null));
        if(come.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.come", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.come.value", come, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.come", null, 0, null));
        if(statoAnimo.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.statoAnimo", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.statoAnimo.value", statoAnimo, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.statoAnimo", null, 0, null));
        if(gradimento.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.gradimento", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.gradimento.value", gradimento, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.gradimento", null, 0, null));
        if(affidabilita.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.affidabilita", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.affidabilita.value", affidabilita, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.affidabilita", null, 0, null));
        if(fedelta.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.fedelta", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.fedelta.value", fedelta, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.fedelta", null, 0, null));
        if(rapporto.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.rapporto", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.rapporto.value", rapporto, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.rapporto", null, 0, null));
        if(trascrizione.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.trascrizione", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.trascrizione.value", trascrizione, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.trascrizione", null, 0, null));
        parser = new PRBMParser("template" + File.separator + template + File.separator+ "intervista.tmpl", file, nodes);
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