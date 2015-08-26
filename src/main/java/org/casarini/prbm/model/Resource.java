/* Resource.java
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

import java.awt.*;
import java.awt.image.*;

import org.casarini.prbm.gui.component.Tabella;
import org.casarini.prbm.gui.util.YellowFilter;

public class Resource implements java.io.Serializable
{
	private static final long serialVersionUID = 7772083251620634064L;

	public int type;
    public String title;
    public transient Image n, y;
    public Scheda scheda;
    
    public void updateImg(Tabella t)
    {
        Image normal=t.createImage(t.m_size_col-4,16);
        Graphics gnormal= normal.getGraphics();
        gnormal.drawImage(t.icone[type],1,1,t);
        gnormal.setFont(t.m_FontNormal);
        gnormal.drawString(title,17,13);
        ImageFilter f=new YellowFilter();
        ImageProducer producer=new FilteredImageSource(normal.getSource(),f);
        Image yellow=t.createImage(producer);
        this.n=normal;
        this.y=yellow;
    }

	public Resource copyRes(Tabella t)
	{
		Resource ret=new Resource();
		ret.type=this.type;
		ret.title=this.title;
		ret.n=(Image)t.createImage(this.n.getSource());
		ret.y=(Image)t.createImage(this.y.getSource());

        switch(type)
        {
            case 0:
                ret.scheda=scheda.copy();
                break;
            case 1:
                ret.scheda=(Fiore)scheda.copy();
                break;
            case 2:
                ret.scheda=(Albero)scheda.copy();
                break;
            case 3:
                ret.scheda=(Fauna)scheda.copy();
                break;
            case 4:
                ret.scheda=(AmbienteNaturale)scheda.copy();
                break;
            case 5:
                ret.scheda=(Meteo)scheda.copy();
                break;
            case 6:
                ret.scheda=(Monumento)scheda.copy();
                break;
            case 7:
                ret.scheda=(Intervista)scheda.copy();
                break;
            case 8:
                ret.scheda=(Fatto)scheda.copy();
                break;
            case 9:
                ret.scheda=(Curiosita)scheda.copy();
                break;
        }

		return ret;
	}
}
