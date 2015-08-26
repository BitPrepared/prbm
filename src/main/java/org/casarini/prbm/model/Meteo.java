/* Meteo.java
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


public class Meteo extends Scheda implements java.io.Serializable
{
	private static final long serialVersionUID = -5865297013175770423L;

	public int temperatura;
	public int umidita;
	public int pressione;
	public String dirVento;
	public String intVento;
	public String intPioggia;
	public String torbPioggia;
	public float phPioggia;
	public String intNeve;
	public String tipoNeve;
	public String torbNeve;
	public float phNeve;
	public String intGrandine;
	public float diamGrandine;
	public String torbGrandine;
	public float phGrandine;
	public int angSole;
	public String posSole;
	public String anomSole;
	public int visNebbia;
	public String tipoNebbia;
	public String tipoNubi;
	public String velocNubi;
	public String eventi;
	public String audio;
    
    public Meteo()
    {
        super();
		temperatura=-65535;
		umidita=-1;
		pressione=-1;
		dirVento=new String("");
		intVento=new String("");
		intPioggia=new String("");
		torbPioggia=new String("");
		phPioggia=-1;
		intNeve=new String("");
		tipoNeve=new String("");
		torbNeve=new String("");
		phNeve=-1;
		intGrandine=new String("");
		diamGrandine=-1;
		torbGrandine=new String("");
		phGrandine=-1;
		angSole=-1;
		posSole=new String("");
		anomSole=new String("");
		visNebbia=-1;
		tipoNebbia=new String("");
		tipoNubi=new String("");
		velocNubi=new String("");
		eventi=new String("");
        immagine=new String("");
        audio=new String("");
        video=new String("");
    }
    
	public Scheda copy()
	{
		Meteo s=new Meteo();
		s.didascalia=didascalia;
		s.icona=icona;
		s.timestamp=timestamp;
		s.note=note;

		s.temperatura=temperatura;
		s.umidita=umidita;
		s.pressione=pressione;
		s.dirVento=dirVento;
		s.intVento=intVento;
		s.intPioggia=intPioggia;
		s.torbPioggia=torbPioggia;
		s.phPioggia=phPioggia;
		s.intNeve=intNeve;
		s.tipoNeve=tipoNeve;
		s.torbNeve=torbNeve;
		s.phNeve=phNeve;
		s.intGrandine=intGrandine;
		s.diamGrandine=diamGrandine;
		s.torbGrandine=torbGrandine;
		s.phGrandine=phGrandine;
		s.angSole=angSole;
		s.posSole=posSole;
		s.anomSole=anomSole;
		s.visNebbia=visNebbia;
		s.tipoNebbia=tipoNebbia;
		s.tipoNubi=tipoNubi;
		s.velocNubi=velocNubi;
		s.eventi=eventi;
		s.immagine=immagine;
		s.audio=audio;
		s.video=video;

		return (Scheda)s;
	}
    
    public void toHTML(String file, String outputDir)
    {
        String sTipo="Meteo";
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
        if(temperatura>-65535)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.temperatura", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.temperatura.value", ""+temperatura, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.temperatura", null, 0, null));
        if(umidita>=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.umidita", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.umidita.value", ""+umidita, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.umidita", null, 0, null));
        if(pressione>=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.pressione", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.pressione.value", ""+pressione, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.pressione", null, 0, null));
        if(dirVento.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.dirVento", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.dirVento.value", dirVento, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.dirVento", null, 0, null));
        if(intVento.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.intVento", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.intVento.value", intVento, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.intVento", null, 0, null));
        if(intPioggia.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.intPioggia", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.intPioggia.value", intPioggia, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.intPioggia", null, 0, null));
        if(torbPioggia.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.torbPioggia", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.torbPioggia.value", torbPioggia, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.torbPioggia", null, 0, null));
        if(phPioggia>=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.phPioggia", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.phPioggia.value", ""+phPioggia, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.phPioggia", null, 0, null));
        if(intNeve.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.intNeve", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.intNeve.value", intNeve, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.intNeve", null, 0, null));
        if(tipoNeve.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.tipoNeve", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.tipoNeve.value", tipoNeve, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.tipoNeve", null, 0, null));
        if(torbNeve.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.torbNeve", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.torbNeve.value", torbNeve, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.torbNeve", null, 0, null));
        if(phNeve>=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.phNeve", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.phNeve.value", ""+phNeve, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.phNeve", null, 0, null));
        if(intGrandine.length()!=-0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.intGrandine", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.intGrandine.value", intGrandine, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.intGrandine", null, 0, null));
        if(diamGrandine>=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.diamGrandine", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.diamGrandine.value", ""+diamGrandine, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.diamGrandine", null, 0, null));
        if(torbGrandine.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.torbGrandine", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.torbGrandine.value", torbGrandine, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.torbGrandine", null, 0, null));
        if(phGrandine>=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.phGrandine", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.phGrandine.value", ""+phGrandine, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.phGrandine", null, 0, null));
        if(angSole>=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.angSole", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.angSole.value", ""+angSole, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.angSole", null, 0, null));
        if(posSole.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.posSole", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.posSole.value", posSole, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.posSole", null, 0, null));
        if(anomSole.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.anomSole", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.anomSole.value", anomSole, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.anomSole", null, 0, null));
        if(visNebbia>=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.visNebbia", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.visNebbia.value", ""+visNebbia, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.visNebbia", null, 0, null));
        if(tipoNebbia.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.tipoNebbia", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.tipoNebbia.value", tipoNebbia, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.tipoNebbia", null, 0, null));
        if(tipoNubi.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.tipoNubi", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.tipoNubi.value", tipoNubi, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.tipoNubi", null, 0, null));
        if(velocNubi.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.velocNubi", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.velocNubi.value", velocNubi, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.velocNubi", null, 0, null));
        if(eventi.length()!=0)
        {
            nodes.addElement(new PRBMParserNode('I',"scheda.eventi", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"scheda.eventi.value", eventi, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"scheda.eventi", null, 0, null));
        parser = new PRBMParser(outputDir + File.separator + "meteo.tmpl", file, nodes);
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