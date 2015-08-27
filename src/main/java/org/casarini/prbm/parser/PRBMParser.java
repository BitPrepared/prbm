/* PRBMParser.java
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
import java.io.*;
import java.util.*;

public class PRBMParser
{
    private String tmplfile;
    private String outfile;
    private Vector nodes;
    RandomAccessFile fd_tmpl, fd_out;

    private Stack stack;
    private Stack stackif;
    private boolean ifjump=false;

    public PRBMParser(String tmplfile, String outfile, Vector nodes) {
    	
    	System.out.println("Parso "+tmplfile+ " e scrivo su "+outfile);
    	
        this.tmplfile=tmplfile;
        this.outfile=outfile;
        this.nodes=nodes;

        stack = new Stack();
        stackif = new Stack();
    }

    public boolean parse() {
        byte[] buffer = new byte[1024];

        // apertura dei file
        try
        {
        	fd_tmpl = new RandomAccessFile(tmplfile, "r");
            fd_out = new RandomAccessFile(outfile,"rw");
        }
        catch (FileNotFoundException fnfe)
        {
            System.out.println(fnfe.toString());
            return false;
        }

        long offset=0;
        boolean eof=false;
        boolean copy=true;

        try
        {
            int ichar;
        	while ((ichar = fd_tmpl.read())!=-1 && !eof)
        	{
                if (ichar == '<')
                {
                    buffer[0]='<';
                    offset=fd_tmpl.getFilePointer();
                    fd_tmpl.read(buffer, 1, 6);
                    if((new String(buffer,0,7)).equals("<?PRBM "))
                    {
                        copy = false;
                        boolean done=false;
                        for (int i=7;!done && (ichar = fd_tmpl.read())!=-1;i++)
                        {
                            buffer[i]=(byte)ichar;
                            if((new String(buffer,i-1,2)).equals("?>"))
                            {
                                done = true;
                                processCmd(new String(buffer,0,i+1));
                            }
                        }
                    }
                    else {
                        ichar = '<';
                        fd_tmpl.seek(offset);
                    }
                }

                if(copy && !ifjump)
                    fd_out.writeByte(ichar);
                else
                    copy = true;
        	}

            //chiusura files
            fd_tmpl.close();
            fd_out.close();
        }
        catch (IOException ioe)
        {
            System.out.println(ioe.toString());
        }

        return true;
    }

    void processCmd(String unparsed_cmd)
    {
        int pos = unparsed_cmd.indexOf(' ',7);
        String cmd = unparsed_cmd.substring(7,pos);
        String arg = unparsed_cmd.substring(pos+1,unparsed_cmd.length()-2);
        
        if (!ifjump && cmd.equalsIgnoreCase("subst"))
        {
            try
            {
                String toWrite = getSubst(arg,null);
                if(toWrite != null)
                    fd_out.write(toWrite.getBytes());
                else
                    System.out.println("node not found: "+cmd+" "+arg);
            }
            catch (IOException ioe)
            {
                System.out.println(ioe.toString());
            }
        }
        else if (!ifjump && cmd.equalsIgnoreCase("substr"))
        {
            try
            {
                PRBMParserEntryStack es = (PRBMParserEntryStack)stack.peek();
                String toWrite = getSubstR(arg,null,es.getCounter());
                if(toWrite != null)
                    fd_out.write(toWrite.getBytes());
                else
                    System.out.println("node not found: "+cmd+" "+arg);
            }
            catch (IOException ioe)
            {
                System.out.println(ioe.toString());
            }
        }
        else if (!ifjump && cmd.equalsIgnoreCase("calc_dim"))
        {
            try {
                int param_start = arg.indexOf('(', 0);
                if (param_start == -1) {
                	throw new IOException("No parameters found for calc_dim.");
                }
                int param_stop = arg.indexOf(')', param_start);
                if (param_stop == -1) {
                	throw new IOException("No parameters found for calc_dim.");
                }
                StringTokenizer st = new StringTokenizer(arg.substring(param_start + 1, param_stop), ",");
                if (st.countTokens() != 2) {
                	throw new IOException("The parameters for calc_dim must be two");
                }
                String specified_width = st.nextToken();
                int max_width = -1;
                if (!specified_width.trim().equals("*")) {
                	try {
                		max_width = Integer.parseInt(specified_width.trim());
                	} catch(NumberFormatException nfe) {
                		throw new IOException("The width specified should be a number o the * character");
                	}
                }
                String specified_height = st.nextToken();
                int max_height = -1;
                if (!specified_height.trim().equals("*")) {
                	try {
                		max_height = Integer.parseInt(specified_height.trim());
                	} catch(NumberFormatException nfe) {
                		throw new IOException("The height specified should be a number o the * character");
                	}
                }
                
                String toWrite = getCalcDim(arg.substring(0, param_start), max_width, max_height);
                if(toWrite != null)
                    fd_out.write(toWrite.getBytes());
                else
                    System.out.println("node not found: "+cmd+" "+arg);
            }
            catch (IOException ioe)
            {
                System.out.println(ioe.toString());
            }
        }
        else if (!ifjump && cmd.equalsIgnoreCase("repeat"))
        {
            try
            {
                PRBMParserNode node = getNode(arg, null);
                if(node != null)
                {
                    PRBMParserEntryStack es = new PRBMParserEntryStack (arg, node, fd_tmpl.getFilePointer(), node.getLevel());
                    stack.push(es);
                }
                else
                    System.out.println("node not found: "+cmd+" "+arg);

            }
            catch (IOException ioe)
            {
                System.out.println(ioe.toString());
            }
        }
        else if (!ifjump && cmd.equalsIgnoreCase("endrpt"))
        {
            PRBMParserNode node = getNode(arg, null);
            if(node!=null)
            {
                PRBMParserEntryStack es = (PRBMParserEntryStack)stack.peek();
                if (es == null || !es.getArg().equals(node.getArg())) {
                    System.out.println("endrpt don't match a repeat");
                }
                else {
                    int cnt=es.getCounter();
                    if(cnt != 1) {
                        try
                        {
                            es.setCounter(cnt-1);
                            fd_tmpl.seek(es.getStartOffset());
                        }
                        catch (IOException ioe)
                        {
                            System.out.println(ioe.toString());
                        }
                    }
                    else
                        stack.pop();
                }
                if(stack.empty())
                    nodes.removeElement(node);
                else {
                    es=(PRBMParserEntryStack)stack.peek();
                    es.getNode().getNodes().removeElement(node);
                }
            }
            else
                System.out.println("node not found: "+cmd+" "+arg);
        }
        else if (!ifjump && cmd.equalsIgnoreCase("if"))
        {
            try
            {
                PRBMParserNode node = getNode(arg, null);
                if(node != null)
                {
                    PRBMParserEntryStack es = new PRBMParserEntryStack (arg, node, fd_tmpl.getFilePointer(), node.getLevel());
                    stackif.push(es);
                    if(node.getLevel()==0)
                        ifjump=true;
                }
                else
                    System.out.println("node not found: "+cmd+" "+arg);
            }
            catch (IOException ioe)
            {
                System.out.println(ioe.toString());
            }
        }
        else if (cmd.equalsIgnoreCase("else"))
        {
            PRBMParserNode node = getNode(arg, null);
            if(node!=null)
            {
                PRBMParserEntryStack es = (PRBMParserEntryStack)stackif.peek();
                if (es == null || !es.getArg().equals(node.getArg()))
                    System.out.println("else don't match a if");
                else
                    ifjump=!ifjump;
            }
            else
                System.out.println("node not found: "+cmd+" "+arg);
        }
        else if (cmd.equalsIgnoreCase("endif"))
        {
            PRBMParserNode node = getNode(arg, null);
            if(node!=null)
            {
                PRBMParserEntryStack es = (PRBMParserEntryStack)stackif.peek();
                if (es == null || !es.getArg().equals(node.getArg())) {
                    System.out.println("endif don't match an if");
                }
                else {
                    ifjump=false;
                    stackif.pop();
                }
                if(stack.empty())
                    nodes.removeElement(node);
                else {
                    es=(PRBMParserEntryStack)stack.peek();
                    es.getNode().getNodes().removeElement(node);
                }
            }
            else
                System.out.println("node not found: "+cmd+" "+arg);
        }
    }

    String getSubst(String arg, Vector tree)
    {
        String ret = null;
        if (tree == null)
            tree = nodes;

        for (int i=0; i<tree.size() && ret == null; i++)
        {
            PRBMParserNode node = (PRBMParserNode) tree.elementAt(i);
            if(node.getLevel() == 0 && node.getArg().equals(arg) )
                ret = node.getValue();
            if(ret == null && node.getNodes() != null)
                ret = getSubst(arg, node.getNodes());
        }

        return ret;
    }

    String getCalcDim(String arg, int height, int width)
    {
        String ret = null;

        for (int i=0; i<nodes.size() && ret == null; i++)
        {
            PRBMParserNode node = (PRBMParserNode) nodes.elementAt(i);
            if(node.getLevel() == 0 && node.getArg().equals(arg) )
                ret = ((PRBMParserImgDimensionNode)node).getValue(height, width);
        }

        return ret;
    }
    
    String getSubstR(String arg, Vector tree, int counter)
    {
        String ret = null;
        if (tree == null)
            tree = nodes;

        for (int i=0; i<tree.size() && ret == null; i++)
        {
            PRBMParserNode node = (PRBMParserNode) tree.elementAt(i);
            if(node.getLevel() == counter && node.getArg().equals(arg) )
                ret = node.getValue();
            if(ret == null && node.getNodes() != null)
                ret = getSubstR(arg, node.getNodes(),counter);
        }

        return ret;
    }

    PRBMParserNode getNode(String arg, Vector tree)
    {
        PRBMParserNode ret = null;
        if (tree == null)
            tree = nodes;

        for (int i=0; i<tree.size() && ret == null; i++)
        {
            PRBMParserNode node = (PRBMParserNode) tree.elementAt(i);
            if(node.getArg().equals(arg))
                ret = node;
            if(ret == null && node.getNodes() != null)
                ret = getNode(arg, node.getNodes());
        }

        return ret;
    }

};
