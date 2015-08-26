/* DiskUtil.java
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

package org.casarini.prbm.util;

import java.io.*;

public class DiskUtil
{
    public static void copyFile(String s,String d)
    {
        byte[] buffer = new byte[4096];
        int letti;
        FileInputStream fs;
        FileOutputStream fd;
        
        try
        {
            fs=new FileInputStream(s);
            fd=new FileOutputStream(d);
            letti=fs.read(buffer);
            while(letti!=-1&&letti!=0)
            {
                    fd.write(buffer,0,letti);
                    letti=fs.read(buffer);
            }
            fd.flush();
            fs.close();
            fd.close();
        }
        catch (Exception e) {System.out.println(e);}
    }

    public static String getRealDir(String dir)
    {
    	File dirfile=new File(dir);
    	File homefile=new File("");
        String home=homefile.getAbsolutePath();

        if(!dirfile.isAbsolute())
        {
        	dir = home + File.separator + dir;
        	dirfile=new File(dir);
        }

    	if(dirfile.exists()&&dirfile.isDirectory())
    		return dir;
    	else
    		return ".";
    }
}