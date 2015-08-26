/* TimeStamp.java
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

import java.util.StringTokenizer;

public class TimeStamp
{
    int giorno,mese,anno;
    int ora,minuti,secondi;
    
    public TimeStamp()
    {
        giorno=mese=ora=3;
        anno=1974;
        minuti=30;
        secondi=00;
    }
    public TimeStamp(String sg,String sm,String sa,String sora,String smin,String ssec)
    {
        if(sg.length()!=0)
            giorno=Integer.parseInt(sg);
        else
            giorno=0;
        if(sm.length()!=0)
            mese=Integer.parseInt(sm);
        else
            mese=0;
        if(sa.length()!=0)
            anno=Integer.parseInt(sa);
        else
            anno=0;
        if(sora.length()!=0)
            ora=Integer.parseInt(sora);
        else
            ora=-1;
        if(smin.length()!=0)
            minuti=Integer.parseInt(smin);
        else
            minuti=-1;
        if(ssec.length()!=0)
            secondi=Integer.parseInt(ssec);
        else
            secondi=-1;
    }
    
    public boolean validate()
    {
        boolean ok=true;
        if((giorno<1||giorno>31)||(mese<1||mese>12)||(anno<1900||anno>2100))
            ok=false;
        if((ora<0||ora>23)||(minuti<0||minuti>59)||(secondi<0||secondi>59))
            ok=false;
        if((mese==4||mese==6||mese==9||mese==11)&&giorno>30)
            ok=false;
        if(mese==2&&giorno>29)
            ok=false;
        if(!(anno%4==0||anno%100==0)&&mese==2&&giorno>28)
            ok=false;
        return ok;
    }
    public String toString()
    {
        String s;
        s=""+giorno+"-"+mese+"-"+anno+"-"+ora+"-"+minuti+"-"+secondi;
        return s;
    }

    public static String toHtml(String s)
    {
    	if(s.length()>0)
    	{
	    	StringTokenizer st=new StringTokenizer(s,"-");
	    	String ret=new String("");
	    	ret+=st.nextToken();
	    	ret+="/";
	    	ret+=st.nextToken();
	    	ret+="/";
	    	ret+=st.nextToken();
	    	ret+=" ";
	    	ret+=st.nextToken();
	    	ret+=":";
	    	ret+=st.nextToken();
	    	ret+=":";
	    	ret+=st.nextToken();
	    	return ret;
	    }
	    else
	    	return s;
    }
}