/* PRBMParserEntryStack.java
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
public class PRBMParserEntryStack
{
    private String arg;
    private PRBMParserNode node;
    private long startOffset;
    private int counter;

    public PRBMParserEntryStack(String arg, PRBMParserNode node, long startOffset, int counter)
    {
        this.arg=arg;
        this.node=node;
        this.startOffset=startOffset;
        this.counter=counter;
    }

    public String getArg() {
        return arg;
    }

    public PRBMParserNode getNode() {
        return node;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter=counter;
    }
};