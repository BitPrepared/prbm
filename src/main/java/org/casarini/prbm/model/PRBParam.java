/* PRBParam.java
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

public class PRBParam implements java.io.Serializable
{
	private static final long serialVersionUID = -343745672022010476L;

	public String title;
    public String site;
    public boolean ext;
    public String data;
    public String modifica;
    public String autori;
    public String note;
    public String dir;

    public PRBParam()
    {
        this("","",false,"","","","","");
    }
    public PRBParam(String title,String site,boolean ext,String data,String modifica,String autori,String note,String dir)
    {
        this.title=title;
        this.site=site;
        this.ext=ext;
        this.data=data;
        this.modifica=modifica;
        this.autori=autori;
        this.note=note;
        this.dir=dir;
    }

    public void copyFrom(PRBParam param)
    {
        this.title=param.title;
        this.site=param.site;
        this.ext=param.ext;
        this.data=param.data;
        this.modifica=param.modifica;
        this.autori=param.autori;
        this.note=param.note;
        this.dir=param.dir;
    }
}