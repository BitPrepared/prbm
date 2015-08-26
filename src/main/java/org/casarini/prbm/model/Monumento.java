/* Monumento.java
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
import java.io.*;
import java.util.Vector;

import org.casarini.prbm.parser.PRBMParser;
import org.casarini.prbm.parser.PRBMParserImgDimensionNode;
import org.casarini.prbm.parser.PRBMParserNode;
import org.casarini.prbm.util.DiskUtil;
import org.casarini.prbm.util.ImageInfo;


public class Monumento extends Scheda implements java.io.Serializable
{
	private static final long serialVersionUID = 4555746704598415394L;

	public String denominazione;
	public String descrizione;
	public String comune;
	public String tipoRilevanza;
	public String proprieta;
	public String uso;
	public String stato;
	public String curiosita;
	public String fattoCostruire;
	public String percheCostruire;
	public String dataCostruzione;
	public String materiali;
	public String opere;
	public String dataAvvenimento;
	public String percheAvvenimento;
	public String raccontoAvvenimento;
    
    public Monumento()
    {
        super();
		denominazione=new String("");
		descrizione=new String("");
		comune=new String("");
		tipoRilevanza=new String("");
		proprieta=new String("");
		uso=new String("");
		stato=new String("");
		fattoCostruire=new String("");
		percheCostruire=new String("");
		dataCostruzione=new String("");
		materiali=new String("");
		opere=new String("");
		dataAvvenimento=new String("");
		percheAvvenimento=new String("");
		raccontoAvvenimento=new String("");
		immagine=new String("");
		video=new String("");
    }
    
	public Scheda copy()
	{
		Monumento s=new Monumento();
		s.didascalia=didascalia;
		s.icona=icona;
		s.timestamp=timestamp;
		s.note=note;

		s.denominazione=denominazione;
		s.descrizione=descrizione;
		s.comune=comune;
		s.tipoRilevanza=tipoRilevanza;
		s.proprieta=proprieta;
		s.uso=uso;
		s.stato=stato;
		s.fattoCostruire=fattoCostruire;
		s.percheCostruire=percheCostruire;
		s.dataCostruzione=dataCostruzione;
		s.materiali=materiali;
		s.opere=opere;
		s.dataAvvenimento=dataAvvenimento;
		s.percheAvvenimento=percheAvvenimento;
		s.raccontoAvvenimento=raccontoAvvenimento;
		s.immagine=immagine;
		s.video=video;

		return (Scheda)s;
	}
    
    public void toHTML(String file, String outputDir)
    {
        String sTipo="Monumento/Luogo Storico";
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
        if(denominazione.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.denominazione", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.denominazione.value", denominazione, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.denominazione", null, 0, null));
        if(descrizione.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.descrizione", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.descrizione.value", descrizione, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.descrizione", null, 0, null));
        if(comune.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.comune", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.comune.value", comune, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.comune", null, 0, null));
        if(tipoRilevanza.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.tipoRilevanza", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.tipoRilevanza.value", tipoRilevanza, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.tipoRilevanza", null, 0, null));
        if(proprieta.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.proprieta", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.proprieta.value", proprieta, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.proprieta", null, 0, null));
        if(uso.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.uso", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.uso.value", uso, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.uso", null, 0, null));
        if(stato.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.stato", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.stato.value", stato, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.stato", null, 0, null));
        if(fattoCostruire.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.fattoCostruire", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.fattoCostruire.value", fattoCostruire, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.fattoCostruire", null, 0, null));
        if(percheCostruire.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.percheCostruire", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.percheCostruire.value", percheCostruire, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.percheCostruire", null, 0, null));
        if(dataCostruzione.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.dataCostruzione", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.dataCostruzione.value", dataCostruzione, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.dataCostruzione", null, 0, null));
        if(materiali.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.materiali", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.materiali.value", materiali, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.materiali", null, 0, null));
        if(opere.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.opere", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.opere.value", opere, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.opere", null, 0, null));
        if(raccontoAvvenimento.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.raccontoAvvenimento", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.raccontoAvvenimento.value", raccontoAvvenimento, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.raccontoAvvenimento", null, 0, null));
        if(dataAvvenimento.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.dataAvvenimento", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.dataAvvenimento.value", dataAvvenimento, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.dataAvvenimento", null, 0, null));
        if(percheAvvenimento.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.percheAvvenimento", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.percheAvvenimento.value", percheAvvenimento, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.percheAvvenimento", null, 0, null));
        parser = new PRBMParser(outputDir + File.separator + "monumento.tmpl", file, nodes);
        parser.parse();

        if(immagine.length()!=0)
        {
            File f=new File(immagine);
            if(f.exists())
                DiskUtil.copyFile(immagine,dir+immagine.substring(immagine.lastIndexOf(File.separatorChar)+1));
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