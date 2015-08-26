/* RunnerConfig.java
 * 
 * Copyright (C) 2007 Paolo Casarini <paolo@casarini.org>
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RunnerConfig {
	private static final String PRB_LAST_MEDIA_PATH = "prb.lastMediaPath";
	
	private static final String PRB_PROPERTIES_FILE = "prbm.properties";
	
	private static RunnerConfig instance;
	private Properties props;
	
	/**
	 * Costruttore privato per impedire l'instanziazione.
	 */
	private RunnerConfig() {
		InputStream in = null;
    	props = new Properties();
        try {
            in = new FileInputStream(PRB_PROPERTIES_FILE);
            props.load(in); // Can throw IOException
        } catch (FileNotFoundException fnfe) {
            System.out.println(PRB_PROPERTIES_FILE + " file non trovato");
        } catch(IOException ioe) {
            System.out.println("Errore nel caricamento del file " + PRB_PROPERTIES_FILE + ": " + ioe.getMessage());
        } finally {
            if (in != null) try { in.close (); } catch (Throwable ignore) {}
        }
	}
	
	public void save() {
		try {
			props.store(new FileOutputStream(PRB_PROPERTIES_FILE), null);
		} catch (IOException ioe) {
			System.out.println("Errore nella scrittura del file " + PRB_PROPERTIES_FILE + ": " + ioe.getMessage());
		}
	}
	
	public static RunnerConfig getInstance() {
		if (instance == null) {
			instance = new RunnerConfig(); 
		}
		
		return instance;
	}
	
	private String getProperty(String key) {
		if (props.containsKey(key)) {
			return props.getProperty(key);
		}
		return null;
	}
	
	private void setProperty(String key, String value) {
		props.setProperty(key, value);
	}
	
	public String getLastMediaPath() {
		String dir = getProperty(PRB_LAST_MEDIA_PATH);
		if (dir != null) {
			File fd = new File(dir);
			if (!fd.exists() || !fd.isDirectory()) {
				dir = null;
			}
		}
		
		return dir;
	}
	
	public void setLastMediaPath(String value) {
		setProperty(PRB_LAST_MEDIA_PATH, value);
	}
}
