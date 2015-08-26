/* Fauna.java
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


public class Fauna extends Scheda implements java.io.Serializable
{
	private static final long serialVersionUID = 5754584941733183113L;

	public String nomeComune;
    public String nomeScientifico;
    public String desAnimale;
    public String dimensioni;
    public String imgtracce;
    public String destracce;
    public String imgescrementi;
    public String desescrementi;
    public String corteggiamento;
    public String prole;
    public String alimentazione;
    public String catena;
    public String tane;
    public String caccia;
    public String habitat;
    public String distribuzione;
    public String territorialita;
    public String curiosita;
    public String audio;
    
    public Fauna()
    {
        super();
        nomeComune=new String("");
        nomeScientifico=new String("");
        desAnimale=new String("");
        dimensioni=new String("");
        imgtracce=new String("");
        destracce=new String("");
        imgescrementi=new String("");
        desescrementi=new String("");
        corteggiamento=new String("");
        prole=new String("");
        alimentazione=new String("");
        catena=new String("");
        tane=new String("");
        caccia=new String("");
        habitat=new String("");
        distribuzione=new String("");
        territorialita=new String("");
        curiosita=new String("");
        immagine=new String("");
        audio=new String("");
        video=new String("");
    }
    
	public Scheda copy()
	{
		Fauna s=new Fauna();
		s.didascalia=didascalia;
		s.icona=icona;
		s.timestamp=timestamp;
		s.note=note;

		s.nomeComune=nomeComune;
		s.nomeScientifico=nomeScientifico;
		s.desAnimale=desAnimale;
		s.dimensioni=dimensioni;
		s.imgtracce=imgtracce;
		s.destracce=destracce;
		s.imgescrementi=imgescrementi;
		s.desescrementi=desescrementi;
		s.corteggiamento=corteggiamento;
		s.prole=prole;
		s.alimentazione=alimentazione;
		s.catena=catena;
		s.tane=tane;
		s.caccia=caccia;
		s.habitat=habitat;
		s.distribuzione=distribuzione;
		s.territorialita=territorialita;
		s.curiosita=curiosita;
		s.immagine=immagine;
		s.audio=audio;
		s.video=video;

		return (Scheda)s;
	}
    
    public void toHTML(String file, String outputDir)
    {
        String sTipo="Fauna";
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
        if(imgtracce.length()>0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.imgtracce", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.imgtracce.src", imgtracce.substring(imgtracce.lastIndexOf(File.separatorChar)+1), 0, null));
        	ImageInfo ii = new ImageInfo();
        	try {
        		ii.setInput(new FileInputStream(imgtracce));
				if (!ii.check()) {
					System.err.println("Not a supported image file format.");
				} else {
					nodes.addElement(new PRBMParserImgDimensionNode('C', "scheda.imgtracce.dimensioni", null, 0, null, ii.getWidth(), ii.getHeight()));
				}
        	} catch(FileNotFoundException fnfe) {
				System.err.println("File not found: " + imgtracce);
        	}
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.imgtracce", null, 0, null));
        if(imgescrementi.length()>0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.imgescrementi", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.imgescrementi.src", imgescrementi.substring(imgescrementi.lastIndexOf(File.separatorChar)+1), 0, null));
        	ImageInfo ii = new ImageInfo();
        	try {
        		ii.setInput(new FileInputStream(imgescrementi));
				if (!ii.check()) {
					System.err.println("Not a supported image file format.");
				} else {
					nodes.addElement(new PRBMParserImgDimensionNode('C', "scheda.imgescrementi.dimensioni", null, 0, null, ii.getWidth(), ii.getHeight()));
				}
        	} catch(FileNotFoundException fnfe) {
				System.err.println("File not found: " + imgescrementi);
        	}
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.imgescrementi", null, 0, null));
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
        if(desAnimale.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.desAnimale", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.desAnimale.value", desAnimale, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.desAnimale", null, 0, null));
        if(dimensioni.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.dimensioni", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.dimensioni.value", dimensioni, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.dimensioni", null, 0, null));
        if(corteggiamento.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.corteggiamento", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.corteggiamento.value", corteggiamento, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.corteggiamento", null, 0, null));
        if(prole.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.prole", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.prole.value", prole, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.prole", null, 0, null));
        if(alimentazione.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.alimentazione", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.alimentazione.value", alimentazione, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.alimentazione", null, 0, null));
        if(catena.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.catena", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.catena.value", catena, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.catena", null, 0, null));
        if(tane.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.tane", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.tane.value", tane, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.tane", null, 0, null));
        if(caccia.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.caccia", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.caccia.value", caccia, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.caccia", null, 0, null));
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
        if(territorialita.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.territorialita", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.territorialita.value", territorialita, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.territorialita", null, 0, null));
        if(curiosita.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.curiosita", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.curiosita.value", curiosita, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.curiosita", null, 0, null));
        parser = new PRBMParser(outputDir + File.separator + "fauna.tmpl", file, nodes);
        parser.parse();

        if(imgtracce.length()!=0)
        {
            File f=new File(imgtracce);
            if(f.exists())
                DiskUtil.copyFile(imgtracce,dir+imgtracce.substring(imgtracce.lastIndexOf(File.separatorChar)+1));
            else
                System.out.println("Errore");
        }
        if(imgescrementi.length()!=0)
        {
            File f=new File(imgescrementi);
            if(f.exists())
                DiskUtil.copyFile(imgescrementi,dir+imgescrementi.substring(imgescrementi.lastIndexOf(File.separatorChar)+1));
            else
                System.out.println("Errore");
        }
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