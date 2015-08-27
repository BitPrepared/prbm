package org.casarini.prbm.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.IOUtils;

public final class StaticLoader {

	public static URL loadResource(String name) {
		
		String resP = "";
		
		URL res = Thread.currentThread().getClass().getResource(name);
		if (res != null ){
			resP = res.getPath();
			if ( new File(resP).exists() ){
				return res;
			}
		}
		
		res = Thread.currentThread().getContextClassLoader().getResource(name);
		if (res != null ){
			resP = res.getPath();
			if ( new File(resP).exists() ){
				return res;
			}
		}
		
//		String path = Thread.currentThread().getClass().getResource(name).getFile();
//		if (path != null ){
//			if ( new File(path).exists() ){
//				return new File(path).toURI().toURL();
//			}
//		}
		
		res = ClassLoader.getSystemResource(name);
		if (res != null ){
			resP = res.getPath();
			if ( new File(resP).exists() ){
				return res;
			}
		}
		
		return StaticLoader.class.getResource(name);
	}
	
	public static final List<String> listDir(String nameBase){
		List<String> list = new ArrayList<String>();
		try {
			final File jarFile = new File(StaticLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        final JarFile jar = new JarFile(jarFile);
	        Enumeration<JarEntry> entries = jar.entries();
	        while(entries.hasMoreElements()) {
	        	JarEntry nextElement = entries.nextElement();
				final String nameInner = nextElement.getName();
				String nameSearch = nameBase.replaceFirst(File.separator, "");
	            if (nameInner.startsWith(nameSearch+File.separator)) { //filter according to the path
	                if ( nameInner.endsWith(File.separator) ) {
	                	System.out.println("> "+nameInner);
	                	String replaceFirstString = nameInner.replaceFirst(nameSearch+File.separator, "");
	                	if ( replaceFirstString.endsWith(File.separator) ) {
	                		list.add( replaceFirstString.substring(0,replaceFirstString.length() - 1) );
	                	} else {
	                		//template stesso viene skippato
	                	}
	                }
	            } 
	        }
		} catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	public static File[] listFile(String nameBase) {
		
		System.out.println("ricerca per "+nameBase);
		
		List<File> list = new ArrayList<File>();
		try {
			final File jarFile = new File(StaticLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        final JarFile jar = new JarFile(jarFile);
	        Enumeration<JarEntry> entries = jar.entries();
	        while(entries.hasMoreElements()) {
	        	JarEntry nextElement = entries.nextElement();
				final String nameInner = nextElement.getName();
				
				//finale/guida/
				//->finale/guida/icone/alberguid.gif
//				->finale/guida/icone/caneguid.gif
//				->finale/guida/icone/princ.gif
//				->finale/guida/icone/sfondo.gif
				
				URL res = StaticLoader.loadResource(File.separator+nameInner);
				
				if ( nameInner.startsWith(nameBase) ) {
//					System.out.println(nameBase+"<->"+res.getPath());
					
					if ( nameInner.endsWith(File.separator) ) {
						//Dir 
						System.out.println(nameBase+"->"+res.getPath());
						list.add(new File(File.separator+nameInner+File.separator));
					} else {
						//File
						URL resFile = StaticLoader.loadResource(File.separator+nameInner);
						System.out.println(nameBase+"X->"+res.getPath());
						list.add(new File(File.separator+nameInner));
					}
					
					
				}
				
//				String nameSearch = nameBase.replaceFirst(File.separator, "");
//	            if (nameInner.startsWith(nameSearch+File.separator)) { //filter according to the path
//	                if ( nameInner.endsWith(File.separator) ) {
//	                	System.out.println("> "+nameInner);
//	                	String replaceFirstString = nameInner.replaceFirst(nameSearch+File.separator, "");
//	                	if ( !replaceFirstString.endsWith(File.separator) ) {
//	                		
////	                		list.add(  );
//	                	}
//	                }
//	            } 
	        }
		} catch(Exception e){
			e.printStackTrace();
		}
		return list.toArray(new File[0]);
	}

	public static void copyFileFor(String srcBaseDir, String baseDirOutput) {
		
		// "finale/guida"  
		// /Users/yoghi/Documents/Workspace/prbm/target/output/guida
		
		System.out.println("da jar "+srcBaseDir+ " verso "+baseDirOutput);
		
		try {
			final File jarFile = new File(StaticLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        final JarFile jar = new JarFile(jarFile);
	        Enumeration<JarEntry> entries = jar.entries();
	        while(entries.hasMoreElements()) {
	        	JarEntry nextElement = entries.nextElement();
				final String nameInner = nextElement.getName();
				
				//finale/guida/
				//->finale/guida/icone/alberguid.gif
//				->finale/guida/icone/caneguid.gif
//				->finale/guida/icone/princ.gif
//				->finale/guida/icone/sfondo.gif
				
				URL res = StaticLoader.loadResource(File.separator+nameInner);
				
				if ( nameInner.startsWith(srcBaseDir) ) {
					
					if ( nameInner.endsWith(File.separator) ) {
						//Dir 
						
						// finale/guida/icone/ -> /icone/ 
						
						String nomeEsatto = nameInner.replaceFirst(srcBaseDir+File.separator, "");
						System.out.println("CREO DIRECTORY->"+baseDirOutput+File.separator+nomeEsatto);
						File d = new File(baseDirOutput+File.separator+nomeEsatto);
						d.mkdirs();
					} else {
						//File
						URL resFile = StaticLoader.loadResource(File.separator+nameInner);
						System.out.println(srcBaseDir+" -X->"+res.getPath());
						InputStream sttream = StaticLoader.class.getResourceAsStream(File.separator+nameInner);
						if ( sttream == null ) {
							System.out.println("NULL");
						} else {
							File x = new File(baseDirOutput+File.separator+nameInner.replaceFirst(srcBaseDir, ""));
							System.out.println("CREO FILE->"+x.getAbsolutePath());
							x.createNewFile();
							FileOutputStream output = new FileOutputStream(x);
							IOUtils.copyLarge(sttream, output);
							output.flush();
							output.close();
						}
						
					}
				}
				
//				String nameSearch = nameBase.replaceFirst(File.separator, "");
//	            if (nameInner.startsWith(nameSearch+File.separator)) { //filter according to the path
//	                if ( nameInner.endsWith(File.separator) ) {
//	                	System.out.println("> "+nameInner);
//	                	String replaceFirstString = nameInner.replaceFirst(nameSearch+File.separator, "");
//	                	if ( !replaceFirstString.endsWith(File.separator) ) {
//	                		
////	                		list.add(  );
//	                	}
//	                }
//	            } 
	        }
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
