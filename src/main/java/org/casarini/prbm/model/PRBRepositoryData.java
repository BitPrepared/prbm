/* PRBRepositoryData.java
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
import java.util.Vector;

import org.casarini.prbm.gui.component.PRBRepository;
import org.casarini.prbm.gui.component.Tabella;
import org.casarini.prbm.gui.util.YellowFilter;

public class PRBRepositoryData implements java.io.Serializable
{
	private static final long serialVersionUID = 5157335478189500370L;

	public Vector resources;

    public PRBRepositoryData()
    {
        resources = new Vector();
    }

    public Resource getResource(int res)
    {
        return (Resource)resources.elementAt(res);
    }

    public int getHeight()
    {
        int max=0;
        if(!resources.isEmpty())
            max = resources.size();
        return max;
    }
    
    public Resource addResource(PRBRepository c, int type, String title, int pos)
    {
        Resource r = new Resource();
        Tabella t = c.parent.tab;
        r.type=type;
        r.title=title;
        Image normal=t.createImage(t.m_size_col-4,16);
        Graphics gnormal= normal.getGraphics();
        gnormal.drawImage(t.icone[type],1,1,t);
        gnormal.setFont(t.m_FontNormal);
        gnormal.drawString(title,17,13);
        ImageFilter f=new YellowFilter();
        ImageProducer producer=new FilteredImageSource(normal.getSource(),f);
        Image yellow=t.createImage(producer);
        r.n=normal;
        r.y=yellow;
        
        switch(type)
        {
            case 0:
                Scheda d0=new Scheda();
                r.scheda=d0;
                break;
            case 1:
                Fiore d1=new Fiore();
                r.scheda=(Scheda)d1;
                break;
            case 2:
                Albero d2=new Albero();
                r.scheda=(Scheda)d2;
                break;
            case 3:
                Fauna d3=new Fauna();
                r.scheda=(Scheda)d3;
                break;
            case 4:
                AmbienteNaturale d4=new AmbienteNaturale();
                r.scheda=(Scheda)d4;
                break;
            case 5:
                Meteo d5=new Meteo();
                r.scheda=(Scheda)d5;
                break;
            case 6:
                Monumento d6=new Monumento();
                r.scheda=(Scheda)d6;
                break;
            case 7:
                Intervista d7=new Intervista();
                r.scheda=(Scheda)d7;
                break;
            case 8:
                Fatto d8=new Fatto();
                r.scheda=(Scheda)d8;
                break;
            case 9:
                Curiosita d9=new Curiosita();
                r.scheda=(Scheda)d9;
                break;
        }

        resources.insertElementAt((Object)r,pos);

        return r;
    }
    public Resource addResource(Resource r, int pos)
	{
        resources.insertElementAt((Object)r, pos);
		return r;
	}
    public Resource cutResource(int pos)
    {
        Resource r=(Resource)resources.elementAt(pos);
        resources.removeElementAt(pos);
        return r;
    }
    
    public void delAllResource()
    {
        resources.removeAllElements();
    }
}
