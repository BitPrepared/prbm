/* PRBMParserImgDimensionNode.java
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

package org.casarini.prbm.parser;

import java.util.Vector;

/**
 * Classe per la gestione di nodi per il parsing di template di tipo "calc_dim"
 * che devono gestire la creazione degli attributi HEIGHT e WIDTH del tag IMG
 * in base alle dimensione reali dell'immagini e in base alle indicazioni date
 * dal template.
 */
public class PRBMParserImgDimensionNode extends PRBMParserNode {
	private int height;
	private int width;
	
	/**
	 * Costruttore date le dimensioni reali dell'immagini di cui si deve
	 * produrre il valore degli attributi HEIGHT e WIDTH.
	 * @param cmd   Comando associato al nodo
	 * @param arg   Argomento del comando
	 * @param value Valore associato
	 * @param level Livello di annidamento
	 * @param nodes nodi associati
	 * @param height altezza reale dell'immagine da gestire.
	 * @param width  larghezza reale dell'immagine da gestire.
	 */
    public PRBMParserImgDimensionNode(char cmd, String arg, String value, int level, Vector nodes, int width, int height) {
    	super(cmd, arg, value, level, nodes);
    	this.height = height;
    	this.width = width;
    }
    
    public String getValue(int max_width, int max_height) {
    	int vHeight;
    	int vWidth;
    	
    	if (max_width < this.width) {
    		vWidth = max_width;
    		vHeight = (int)(this.height * ((double)max_width / this.width));
    	} else {
    		vWidth = this.width;
    		vHeight = this.height;
    	}
    	
    	if (max_height < vHeight) {
    		vHeight = max_height;
    		vWidth = (int)(this.width * ((double)max_height / this.height));
    	}
    	
    	return "HEIGHT=\"" + vHeight + "\" WIDTH=\"" + vWidth + "\"";
    }
}
