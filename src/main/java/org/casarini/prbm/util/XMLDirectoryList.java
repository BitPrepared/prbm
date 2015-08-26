/* XMLDirectoryList.java
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

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.casarini.prbm.util;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;  

import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Classe per la lettura dei file xml che servono per descrivere il contenuto
 * delle directory delle icone per le varie schede quando sono zippate nel file
 * jar della distribuzione.
 * Il sistema si basa sulla esistenza di un file di nome e struttura data all'
 * interno di ogni directory di icone. Questo file sostituisce con il suo
 * contenuto, le informazioni che non e' possibile con il metodo File::list()
 * quando la directory di cui si vuole conoscere il contenuto e' all'interno
 * di un jar.
 */
public class XMLDirectoryList {
	
	private static final String XML_FILENAME = "filelist.xml";
	
	Document document;
	
	public XMLDirectoryList(String dirname) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(XMLDirectoryList.class.getResource(dirname + XML_FILENAME).openStream());
		} catch (SAXException sxe) {
			// Errore generato durante il parsing
			Exception  x = sxe;
			if (sxe.getException() != null)
			x = sxe.getException();
			x.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public String[] list() {
		String[] result; 
		NodeList fileNodes = document.getElementsByTagName("file");
		int length = fileNodes.getLength();
		result = new String[length];
		for (int i = 0; i < length; i++) {
			result[i] = ((Element)fileNodes.item(i)).getAttribute("name");
		}
		
		return result;
	}
}
