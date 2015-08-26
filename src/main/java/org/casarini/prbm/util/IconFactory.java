/* IconFactory.java
 * 
 * Copyright (C) 2005 Paolo Casarini <paolo@casarini.org>
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

package org.casarini.prbm.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.HashMap;

/**
 * Factory e cache delle icone e delle immagini.
 * Questa classe e' implementata secondo il pattern del singleton.
 */
public class IconFactory {
    /**
     * Il package (direcotry) dove vengono messe le risorse (icone, etc.).
     */
    public static final String RESOURCE_DIR = "/org/casarini/prbm/resources/";

    /**
     * L'unica istanza di UsciIconFactory.
     */
    private static final IconFactory INSTANCE = new IconFactory();

    /**
     * Cache delle icone già create. Vi si accede mediante l'identificatore
     * dell'immagine.
     */
    private HashMap images = new HashMap();

    /**
     * Costruttore privato che assicura la non instanziabilita' della classe.
     */
    private IconFactory() {
    }

    /**
     * Restituisce l'unica istanza di UsciIconFactory.
     * @return L'unica istanza di UsciIconFactory.
     */
    public static IconFactory getInstance(){
        return INSTANCE;
    }

    /**
     * Restituisce l'immagine grafica di identificatore dato. L'immagine viene
     * cercata nella cache delle immagini; se non la si trova nella cache,
     * viene creata cercando nel direttorio delle risorse il file .gif avente
     * l'identificatore dato come nome; se tale file non esiste restituisce null.
     * @param  imageIdentifier L'identificatore dell'immagine grafica da restituire.
     * @return L'immagine di identificatore dato se esiste, null altrimenti.
     */
    public Image getImage(String imageIdentifier) {
        synchronized(images) {
            Image image = null;
            Object imageAsObject = images.get(imageIdentifier);
            if (imageAsObject == null) {
                URL imageURL = IconFactory.class.getResource(RESOURCE_DIR + imageIdentifier);
                if (imageURL != null) {
                    image = Toolkit.getDefaultToolkit().createImage(imageURL);
                    images.put(imageIdentifier, image);
                } else {
                	System.out.println(RESOURCE_DIR + imageIdentifier);
                }
            } else {
                image = (Image)imageAsObject;
            }
            return image;
        }
    }
}
