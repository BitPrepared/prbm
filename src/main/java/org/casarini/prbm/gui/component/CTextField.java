/* CTextField.java
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

package org.casarini.prbm.gui.component;
import java.awt.*;
import java.awt.event.*;  

public class CTextField extends TextField
{
	private static final long serialVersionUID = 259854385567548824L;

	public static final int CTF_ALL=0;
    public static final int CTF_NUM=1;
    public static final int CTF_INT=2;
	public static final int CTF_FLOAT=3;
    int ctype;
    int len;
    
    public CTextField(String s,int n,int type,int len)
    {
        super(s,n);
        this.ctype=type;
        this.len=len;
    }
    public CTextField(String s,int type,int len)
    {
        super(s);
        this.ctype=type;
        this.len=len;
    }
    public CTextField(int n,int type,int len)
    {
        super(n);
        this.ctype=type;
        this.len=len;
    }
    public CTextField(int type,int len)
    {
        super();
        this.ctype=type;
        this.len=len;
    }
    
    protected void processKeyEvent(KeyEvent e)
    {
        int type=Character.getType(e.getKeyChar());
		char value=e.getKeyChar();
        if(getText().length()>=len&&type!=Character.CONTROL)
            e.consume();
        else
            switch(ctype)
            {
                case CTF_ALL:
                    super.processKeyEvent(e);
                    break;
                case CTF_NUM:
                    if(type==Character.DECIMAL_DIGIT_NUMBER ||
                       type==Character.CONTROL)
                        super.processKeyEvent(e);
                    else
                        e.consume();
                    break;
                case CTF_INT:
                    if(type==Character.DECIMAL_DIGIT_NUMBER ||
                       type==Character.CONTROL||
					   value=='-' || value=='+')
						if((value=='-' || value=='+') && getText().length()!=0)
							e.consume();
						else
							super.processKeyEvent(e);
                    else
                        e.consume();
                    break;
                case CTF_FLOAT:
                    if(type==Character.DECIMAL_DIGIT_NUMBER ||
                       type==Character.CONTROL ||
					   value=='.')
						if(value=='.' && (getText().length()==0 || getText().length()==len-1 || getText().indexOf('.')!=-1))
							e.consume();
						else
							super.processKeyEvent(e);
                    else
                        e.consume();
                    break;
                default:
                    super.processKeyEvent(e);
            }
    }    
}