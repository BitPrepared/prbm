/* Tabella.java
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
import java.util.Vector;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

import org.casarini.prbm.gui.PRB;
import org.casarini.prbm.gui.dialog.DialogEditAlbero;
import org.casarini.prbm.gui.dialog.DialogEditAmbienteNaturale;
import org.casarini.prbm.gui.dialog.DialogEditCuriosita;
import org.casarini.prbm.gui.dialog.DialogEditFatto;
import org.casarini.prbm.gui.dialog.DialogEditFauna;
import org.casarini.prbm.gui.dialog.DialogEditFiore;
import org.casarini.prbm.gui.dialog.DialogEditIntervista;
import org.casarini.prbm.gui.dialog.DialogEditMeteo;
import org.casarini.prbm.gui.dialog.DialogEditMonumento;
import org.casarini.prbm.gui.dialog.DialogEditPaesaggio;
import org.casarini.prbm.gui.dialog.DialogEditStep;
import org.casarini.prbm.gui.dialog.TemplateBox;
import org.casarini.prbm.model.Albero;
import org.casarini.prbm.model.AmbienteNaturale;
import org.casarini.prbm.model.Curiosita;
import org.casarini.prbm.model.DataTable;
import org.casarini.prbm.model.Fatto;
import org.casarini.prbm.model.Fauna;
import org.casarini.prbm.model.Fiore;
import org.casarini.prbm.model.Intervista;
import org.casarini.prbm.model.Meteo;
import org.casarini.prbm.model.Monumento;
import org.casarini.prbm.model.PRBParam;
import org.casarini.prbm.model.Passo;
import org.casarini.prbm.model.Resource;
import org.casarini.prbm.model.Scheda;
import org.casarini.prbm.model.TimeStamp;
import org.casarini.prbm.parser.PRBMParser;
import org.casarini.prbm.parser.PRBMParserNode;
import org.casarini.prbm.util.DiskUtil;
import org.casarini.prbm.util.IconFactory;


public class Tabella extends Canvas implements MouseListener,ActionListener
{
	private static final long serialVersionUID = 6325002874090623699L;

	//costanti
    public static final int HRES=15;                    //altezza risorsa
    public static final int NRESDEF=3;                  //numero di risorse di default
    public static final int HCOLDEF = HRES*NRESDEF;     //altezza cella di default
    public static final int RICALCOLA = 1;
    
    //DATA
    int m_row;
    PRB parent;
    DataTable dt;

    //dimensioni della griglia
    public int m_size_col;          //larghezza delle colonne laterali
    int m_size_col_centre;          //larghezza colonna centrale
    int m_inithspace;               //spazio in alto
    int m_initvspace;               //spazio a sinistra

    //posizioni per il ridisegno
    int[] m_poscol=new int[5];      //posizione di stampa delle linee orizzontali
    Vector m_posrow;                //posizione di stampa delle linee verticali

    //Double-buffering
    Graphics m_Gbuffer;             //Graphics associato
    Image m_Ibuffer;                //immagine per il double-buffering

    //Font&Immagini
    Font m_FontBold;                //Font grassetto
    public Font m_FontNormal;       //Font normale
    public Image icone[]=new Image[10];    //icone per le risorse

    //gestione selezione
    boolean m_selected=false;       //se e' selezionata una cella
    int m_sel_col=-1;               //colonna selezionata
    int m_sel_passo=-1;             //passo selezionato
    int m_sel_res=-1;               //risorsa selezionata
    int m_sx1,m_sx2,m_sy1,m_sy2;    //coordinate rettangolo selezionato

    //gestione tasto destro
    boolean m_premuto=false;        //se e' stato premuto il tasto destro

	//gestione della copia
	public Resource clipboard = null;

    //gestione menu popup
    String[] reslabel = new String[]{"Modifica","Cancella","Copia","Incolla Prima","Incolla Dopo"};
    String[] rescommand = new String[]{"rmodifica","rcancella","rcopia","rincolla_p","rincolla_d"};
    String[] residlabel = new String[]{"Simbolo Paesaggio","Fiore/Erba","Albero/Arbusto","Fauna","Ambiente Naturale","Meteo","Monumento/Luogo Storico","Intervista","Fatto di Cronaca","Curiosita'/Osservazione"};
    String[] residcommand = new String[]{"PAE","FIO","ALB","FAU","AMB","MET","MON","INT","CRO","CUR"};
    String[] passolabel = new String[]{"Modifica","Cancella","Elimina tutte le schede"};
    String[] passocommand = new String[]{"modifica","cancella","elimina tutte le risorse"};
    String[] passoinslabel = new String[]{"Prima","Dopo"};
    String[] passoinscommand = new String[]{"prima","dopo"};
    PopupMenu m_Mres,m_Mpasso;

/*****************************************************************************/
/* Costruttore della classe                                                  */
/*****************************************************************************/
    public Tabella(PRB parent,int size_col,int size_col_centre)
    {
        super();

        //init var di istanza
        m_row=1;
        m_posrow=new Vector();
        dt=new DataTable();
        this.parent=parent;
        m_size_col=size_col;
        m_size_col_centre=size_col_centre;
        m_inithspace=20;
        m_initvspace=5;

        m_FontBold= new Font("TimesNewRoman",Font.BOLD,12);
        m_FontNormal= new Font("TimesNewRoman",Font.PLAIN,12);


        m_poscol[0]=m_inithspace;
        m_poscol[1]=m_inithspace+m_size_col;
        m_poscol[2]=m_inithspace+2*m_size_col+m_size_col_centre;
        m_poscol[3]=m_inithspace+3*m_size_col+m_size_col_centre;
        m_poscol[4]=m_inithspace+2*m_size_col;

        resize();

        //andiamoci a prendere le immagini
        for(int i = 0; i < 10; i++) {
            icone[i] = IconFactory.getInstance().getImage("ico"+Integer.toString(i)+".gif");
        }

        //creiamoci i menu
        m_Mres=new PopupMenu();
        Menu resid_p=new Menu("Aggiungi Prima ");
        m_Mres.add(resid_p);
        for(int i=0;i<residlabel.length;i++)
        {
            MenuItem mi=new MenuItem(residlabel[i]);
            mi.setActionCommand(residcommand[i]+"_P");
            mi.addActionListener(this);
            resid_p.add(mi);
        }
        Menu resid_d=new Menu("Aggiungi Dopo ");
        m_Mres.add(resid_d);
        for(int i=0;i<residlabel.length;i++)
        {
            MenuItem mi=new MenuItem(residlabel[i]);
            mi.setActionCommand(residcommand[i]+"_D");
            mi.addActionListener(this);
            resid_d.add(mi);
        }
        for(int i=0;i<reslabel.length;i++)
        {
            MenuItem mi=new MenuItem(reslabel[i]);
            mi.setActionCommand(rescommand[i]);
            mi.addActionListener(this);
            m_Mres.add(mi);
        }

        m_Mpasso=new PopupMenu();
        Menu passoins=new Menu("Aggiungi");
        m_Mpasso.add(passoins);
        for(int i=0;i<passoinslabel.length;i++)
        {
            MenuItem mi=new MenuItem(passoinslabel[i]);
            mi.setActionCommand(passoinscommand[i]);
            mi.addActionListener(this);
            passoins.add(mi);
        }
        for(int i=0;i<passolabel.length;i++)
        {
            MenuItem mi=new MenuItem(passolabel[i]);
            mi.setActionCommand(passocommand[i]);
            mi.addActionListener(this);
            m_Mpasso.add(mi);
        }
        this.add(m_Mpasso);
        this.add(m_Mres);
        this.addMouseListener(this);

        /*andra' tolta*/
        init();
    }

    void init()
    {
        /*dt.addPasso(23,100,10,1);
        dt.addPasso(69,45,3,2);
        dt.addPasso(125,32,2,3);
        dt.addPasso(320,156,15,4);
        dt.addPasso(15,52,5,5);
        dt.addPasso(280,62,45,6);
        dt.addPasso(45,853,20,7);
        dt.addPasso(90,210,20,8);
        m_row=9;
        resize();*/
    }

/*****************************************************************************/
/* resize: calcola le altezze dei passi e setta la dimensione dell'oggetto   */
/*****************************************************************************/
    public void resize()
    {
        setHeightRow();
//        int max_y=((Integer)m_posrow.elementAt(m_posrow.size()-1)).intValue();
		setSize(670,4096);
//        setSize(m_poscol[3]+m_size_col+m_inithspace,max_y+m_initvspace);
        parent.c_scrollpane.doLayout();
    }

/*****************************************************************************/
/* setHeightRow: calcola le altezze di stampa le linne orrizzontali          */
/*****************************************************************************/
    void setHeightRow()
    {
        m_posrow.removeAllElements();
        int lasty=m_initvspace;
        m_posrow.setSize(m_row);
        m_posrow.insertElementAt(new Integer(lasty),m_row);
        for(int i=m_row-1;i>=0;i--)
        {
            lasty+=4;
            if(dt.getMaxHeight(i)>NRESDEF)
                lasty+=(dt.getMaxHeight(i)*HRES);
            else
                lasty+=HCOLDEF;
            m_posrow.setElementAt(new Integer(lasty),i);
        }
    }

/*****************************************************************************/
/* fillResource: ridisegna tutte le risorse                                  */
/*****************************************************************************/
    void fillResource(int mode)
    {
        for(int i=0;i<m_row;i++)
            for(int j=0;j<4;j++)
                for(int k=0;k<dt.getSizeCol(j,i);k++)
                {
                    Resource r=dt.getResource(j,i,k);
                    if(mode==RICALCOLA) r.updateImg(this);
                    m_Gbuffer.drawImage(r.n,m_poscol[j]+2,vRowGet(i+1)+2+(k*HRES),this);
                }
    }

    void drawNet()
    {
        int my=0;
        Passo passo;

        for(int i=0;i<m_row;i++)
        {
            passo=dt.getPasso(i);
            my=vRowGet(i);
            m_Gbuffer.drawLine(m_inithspace,my,4*m_size_col+m_inithspace+m_size_col_centre,my);
            m_Gbuffer.setFont(m_FontBold);
            if(i<9)
                m_Gbuffer.drawString(""+(i+1),m_inithspace-8,my);
            else
                m_Gbuffer.drawString(""+(i+1),m_inithspace-15,my);

            m_Gbuffer.setFont(m_FontNormal);
            m_Gbuffer.drawString(""+passo.azimut+"a'N",m_inithspace+2*m_size_col+2,my-2);
            m_Gbuffer.drawString(""+passo.metri+" metri",m_inithspace+2*m_size_col+2,my-17);
            m_Gbuffer.drawString(""+passo.tempo+" min",m_inithspace+2*m_size_col+2,my-32);
        }
        my=vRowGet(m_row);
        m_Gbuffer.drawLine(m_inithspace,my,4*m_size_col+m_inithspace+m_size_col_centre,my);

        my=vRowGet(0);
        m_Gbuffer.drawLine(m_poscol[0],m_initvspace,m_poscol[0],my);
        m_Gbuffer.drawLine(m_poscol[1],m_initvspace,m_poscol[1],my);
        m_Gbuffer.drawLine(m_poscol[4],m_initvspace,m_poscol[4],my);
        m_Gbuffer.drawLine(m_poscol[2],m_initvspace,m_poscol[2],my);
        m_Gbuffer.drawLine(m_poscol[3],m_initvspace,m_poscol[3],my);
        m_Gbuffer.drawLine(m_poscol[3]+m_size_col,m_initvspace,m_poscol[3]+m_size_col,my);
    }


/*****************************************************************************/
/* paintDataStep(int): disegna i dati relativi a un passo                         */
/*****************************************************************************/
    void paintDataStep(int i)
    {
        Graphics g=this.getGraphics();

        Passo passo=dt.getPasso(i);
        int my=vRowGet(i);

        g.setFont(m_FontNormal);
        g.clearRect(m_inithspace+2*m_size_col+1,my-HCOLDEF-1,m_size_col_centre-2,HCOLDEF);
        g.drawString(""+passo.azimut+"a'N",m_inithspace+2*m_size_col+2,my-2);
        g.drawString(""+passo.metri+" metri",m_inithspace+2*m_size_col+2,my-17);
        g.drawString(""+passo.tempo+" min",m_inithspace+2*m_size_col+2,my-32);

        m_Gbuffer.setFont(m_FontNormal);
        m_Gbuffer.clearRect(m_inithspace+2*m_size_col+1,my-HCOLDEF-1,m_size_col_centre-2,HCOLDEF);
        m_Gbuffer.drawString(""+passo.azimut+"a'N",m_inithspace+2*m_size_col+2,my-2);
        m_Gbuffer.drawString(""+passo.metri+" metri",m_inithspace+2*m_size_col+2,my-17);
        m_Gbuffer.drawString(""+passo.tempo+" min",m_inithspace+2*m_size_col+2,my-32);
    }


/*****************************************************************************/
/* paintSelection: colora la selezione corrente                              */
/*****************************************************************************/
    void paintSelection()
    {
        Graphics g=this.getGraphics();

        if(m_sel_res!=-1)
        {
            Resource r=dt.getResource(m_sel_col,m_sel_passo,m_sel_res);
            g.drawImage(r.y,m_sx1+2,m_sy1+2+(m_sel_res*HRES),this);
            m_Gbuffer.drawImage(r.y,m_sx1+2,m_sy1+2+(m_sel_res*HRES),this);
        }

        g.drawRect(m_sx1+1,m_sy1+1,m_sx2-2,m_sy2-2);
        g.drawRect(m_sx1-1,m_sy1-1,m_sx2+2,m_sy2+2);
        m_Gbuffer.drawRect(m_sx1+1,m_sy1+1,m_sx2-2,m_sy2-2);
        m_Gbuffer.drawRect(m_sx1-1,m_sy1-1,m_sx2+2,m_sy2+2);
    }

/*****************************************************************************/
/* clearSelection: cancella la selezione su video e nei dati                 */
/*****************************************************************************/
    void clearSelection()
    {
        Graphics g=this.getGraphics();

        //deselezione la cella selezionata
        g.setColor(Color.white);
        g.drawRect(m_sx1+1,m_sy1+1,m_sx2-2,m_sy2-2);
        g.drawRect(m_sx1-1,m_sy1-1,m_sx2+2,m_sy2+2);
        g.setColor(Color.black);
        m_Gbuffer.setColor(Color.white);
        m_Gbuffer.drawRect(m_sx1+1,m_sy1+1,m_sx2-2,m_sy2-2);
        m_Gbuffer.drawRect(m_sx1-1,m_sy1-1,m_sx2+2,m_sy2+2);
        m_Gbuffer.setColor(Color.black);
        if(m_sel_res!=-1)
        {
            Resource r=dt.getResource(m_sel_col,m_sel_passo,m_sel_res);
            g.drawImage(r.n,m_sx1+2,m_sy1+2+(m_sel_res*HRES),this);
            m_Gbuffer.drawImage(r.n,m_sx1+2,m_sy1+2+(m_sel_res*HRES),this);
        }
        m_selected=false;
        m_sel_passo=-1;
        m_sel_col=-1;
        m_sel_res=-1;
    }


/*****************************************************************************/
/* setSelection: setta le variabili m_sel_col,m_sel_passo,m_sel_res in base  */
/* il punto x,y                                                              */
/*****************************************************************************/
    void setSelection(int x,int y)
    {
        //boccheggio la posizione di click nella griglia
        int col=-1,passo=-1;
        if(x>=m_poscol[0]&&x<m_poscol[1])
            col=0;
        else if(x>=m_poscol[1]&&x<m_poscol[4])
            col=1;
        else if(x>=m_poscol[4]&&x<m_poscol[2])
            col=4;
        else if(x>=m_poscol[2]&&x<m_poscol[3])
            col=2;
        else if(x>=m_poscol[3]&&x<m_poscol[3]+m_size_col)
            col=3;

        if(col!=-1)
        {
            boolean done=false;
            for(int i=0;i<m_row&&!done;i++)
                if(y>vRowGet(i+1)&&y<=vRowGet(i))
                {
                    passo=i;
                    done=true;
                }
            if(passo!=-1)
            {
                m_selected=true;
                m_sel_passo=passo;
                m_sel_col=col;

                if(m_sel_col==4)
                {
                    m_sx1=m_inithspace;
                    m_sx2=4*m_size_col+m_size_col_centre;
                }
                else
                {
                    m_sx1=m_poscol[col];
                    m_sx2=m_size_col;
                }
                m_sy1=vRowGet(passo+1);
                m_sy2=vRowGet(passo)-m_sy1;

                m_sel_res=(y-m_sy1-2)/HRES;
                if(m_sel_col==4||m_sel_res>=dt.getSizeCol(m_sel_col,m_sel_passo))
                    m_sel_res=-1;
            }
        }
    }


/*****************************************************************************/
/* addNewResourse: aggiunge una risorsa di tipo type e titolo title nella    */
/* nella cella selezionata                                                   */
/*****************************************************************************/
    Coordinate addNewResource(int type,String title,boolean prima)
    {
        int pos,oldmaxh,h;
        Resource r;
		Coordinate ret;

        oldmaxh=dt.getMaxHeight(m_sel_passo);
        if(m_sel_res==-1)
            if(prima)
                pos=0;
            else
                pos=dt.getSizeCol(m_sel_col,m_sel_passo);
        else
            if(prima)
                pos=m_sel_res;
            else
                pos=m_sel_res+1;
        dt.addResource(this,type,title,m_sel_col,m_sel_passo,pos);
		ret=new Coordinate(m_sel_col,m_sel_passo,pos);

		h=dt.getSizeCol(m_sel_col,m_sel_passo);
        if(h>NRESDEF&&h>oldmaxh)
        {
            m_Gbuffer.clearRect(0,0,670,4096);
            resize();
            drawNet();
            fillResource(0);
            m_sy2+=HRES;
        }
        else
        {
            Graphics g=getGraphics();
            for(int i=0;i<h;i++)
            {
                r=dt.getResource(m_sel_col,m_sel_passo,i);
                g.drawImage(r.n,m_sx1+2,m_sy1+2+(i*HRES),this);
                m_Gbuffer.drawImage(r.n,m_sx1+2,m_sy1+2+(i*HRES),this);
            }
        }
        paintSelection();
        paint(getGraphics());
        parent.setPrbModified(true);
		return ret;
    }

/*****************************************************************************/
/* addClipboardResourse: aggiunge la risorsa che e' nella Clipboard nella    */
/* cella selezionata                                                         */
/*****************************************************************************/
    void addClipboardResource(boolean prima)
    {
        int pos,oldmaxh,h;
        Resource r=clipboard.copyRes(this);

        oldmaxh=dt.getMaxHeight(m_sel_passo);
        if(m_sel_res==-1)
            if(prima)
                pos=0;
            else
                pos=dt.getSizeCol(m_sel_col,m_sel_passo);
        else
            if(prima)
                pos=m_sel_res;
            else
                pos=m_sel_res+1;

		dt.addResource(r,m_sel_col,m_sel_passo,pos);

		h=dt.getSizeCol(m_sel_col,m_sel_passo);
        if(h>NRESDEF&&h>oldmaxh)
        {
            m_Gbuffer.clearRect(0,0,670,4096);
            resize();
            drawNet();
            fillResource(0);
            m_sy2+=HRES;
        }
        else
        {
            Graphics g=getGraphics();
            for(int i=0;i<h;i++)
            {
                r=dt.getResource(m_sel_col,m_sel_passo,i);
                g.drawImage(r.n,m_sx1+2,m_sy1+2+(i*HRES),this);
                m_Gbuffer.drawImage(r.n,m_sx1+2,m_sy1+2+(i*HRES),this);
            }
        }
        paintSelection();
        paint(getGraphics());
		repaint();
        parent.setPrbModified(true);
    }
/*****************************************************************************/
/* deleteResourse: toglie risorsa selezionata                                */
/*****************************************************************************/
    void deleteResource()
    {
        int oldmaxh=dt.getMaxHeight(m_sel_passo);
        Resource r;

        dt.cutResource(m_sel_col,m_sel_passo,m_sel_res);
        if(oldmaxh>NRESDEF&&oldmaxh!=dt.getMaxHeight(m_sel_passo))
        {
            m_Gbuffer.clearRect(0,0,670,4096);
            fillResource(0);
            resize();
            drawNet();
            m_sy2-=HRES;
        }
        else
        {
            Graphics g=getGraphics();
            m_Gbuffer.clearRect(m_sx1+2,m_sy1+2,m_sx2-3,m_sy2-3);
            g.clearRect(m_sx1+2,m_sy1+2,m_sx2-3,m_sy2-3);
            int h=dt.getSizeCol(m_sel_col,m_sel_passo);
            for(int i=0;i<h;i++)
            {
                r=dt.getResource(m_sel_col,m_sel_passo,i);
                g.drawImage(r.n,m_sx1+2,m_sy1+2+(i*HRES),this);
                m_Gbuffer.drawImage(r.n,m_sx1+2,m_sy1+2+(i*HRES),this);
            }
        }
        m_sel_res=-1;
        paintSelection();
        paint(getGraphics());
        parent.setPrbModified(true);
    }

/*****************************************************************************/
/* addNewStep:                                                               */
/*****************************************************************************/
    void addNewStep(int azimut, int metri, int tempo, boolean prima)
    {
        int pos;
        if(prima)
        {
            pos=m_sel_passo;
            m_sy1+=m_sy2;
            m_sy2=HCOLDEF+4;
            m_sel_passo++;
        }
        else
        {
            pos=m_sel_passo+1;
            m_sy2=HCOLDEF+4;
        }
        dt.addPasso(azimut,metri,tempo,pos);
        m_row++;
        m_Gbuffer.clearRect(0,0,670,4096);
        resize();
        drawNet();
        fillResource(0);
        paintSelection();
        paint(getGraphics());
        parent.setPrbModified(true);
    }

/*****************************************************************************/
/* deleteStep: toglie il passo selezionato                                   */
/*****************************************************************************/
    void deleteStep()
    {
        dt.cutPasso(m_sel_passo);
        m_row--;
        m_Gbuffer.clearRect(0,0,670,4096);
        resize();
        drawNet();
        fillResource(0);
        m_sel_passo=-1;
        m_selected=false;
        paint(getGraphics());
        parent.setPrbModified(true);
    }
/*****************************************************************************/
/* deleteAllResource: toglie dal passo selezionato tutte le risorse          */
/*****************************************************************************/
    void deleteAllResource()
    {
        dt.delAllResource(m_sel_passo);
        m_Gbuffer.clearRect(0,0,670,4096);
        resize();
        drawNet();
        fillResource(0);
        m_sy2=vRowGet(m_sel_passo)-vRowGet(m_sel_passo+1);
        paintSelection();
        paint(getGraphics());
        parent.setPrbModified(true);
    }
 
    public void paint(Graphics g)
    {
        if(m_Gbuffer==null)
        {
            m_Ibuffer=this.createImage(670,4096);
            m_Gbuffer=m_Ibuffer.getGraphics();
            drawNet();
        }
        g.drawImage(m_Ibuffer,0,0,this);
    }

    public void processMouseEvent(MouseEvent e)
    {
        if(e.isPopupTrigger())
            m_premuto=true;
        super.processMouseEvent(e);
    }

    //START implementazione MouseListener
    public void mouseClicked(MouseEvent e)
    {
        if(m_selected)
            clearSelection();

        setSelection(e.getX(),e.getY());
        //System.out.println("setSelection "+m_sel_col+" "+m_sel_passo+" "+m_sel_res);
        if(m_sel_passo!=-1&&m_sel_col!=-1)
		{
            paintSelection();
			if(!m_premuto && e.getClickCount()>1)
            if(m_sel_col!=4)
            {
                if(m_sel_res!=-1)
                {
					Resource rs=dt.getResource(m_sel_col,m_sel_passo,m_sel_res);
					//System.out.println("rmodifica "+m_sel_col+" "+m_sel_passo+" "+m_sel_res);
					boolean modify=true;
					switch(rs.type)
					{
						case 0:
							DialogEditPaesaggio editPae;
							editPae = new DialogEditPaesaggio(parent,rs);
							editPae.edit();
							break;
						case 1:
							DialogEditFiore editFiore;
							editFiore = new DialogEditFiore(parent,rs);
							editFiore.edit();
							break;
						case 2:
							DialogEditAlbero editAlbero;
							editAlbero = new DialogEditAlbero(parent,rs);
							editAlbero.edit();
							break;
						case 3:
							DialogEditFauna editFauna;
							editFauna = new DialogEditFauna(parent,rs);
							editFauna.edit();
							break;
						case 4:
							DialogEditAmbienteNaturale editAmbienteNaturale;
							editAmbienteNaturale = new DialogEditAmbienteNaturale(parent,rs);
							editAmbienteNaturale.edit();
							break;
						case 5:
							DialogEditMeteo editMeteo;
							editMeteo = new DialogEditMeteo(parent,rs);
							editMeteo.edit();
							break;
						case 6:
							DialogEditMonumento editMonumento;
							editMonumento = new DialogEditMonumento(parent,rs);
							editMonumento.edit();
							break;
						case 7:
							DialogEditIntervista editIntervista;
							editIntervista = new DialogEditIntervista(parent,rs);
							editIntervista.edit();
							break;
						case 8:
							DialogEditFatto editFatto;
							editFatto = new DialogEditFatto(parent,rs);
							editFatto.edit();
							break;
						case 9:
							DialogEditCuriosita editCuriosita;
							editCuriosita = new DialogEditCuriosita(parent,rs);
							editCuriosita.edit();
							break;
						default:
							modify=false;
					}
					if(modify)
					{
						rs.updateImg(this);
						m_Gbuffer.drawImage(rs.y,m_poscol[m_sel_col]+2,vRowGet(m_sel_passo+1)+2+(m_sel_res*HRES),this);
                        parent.setPrbModified(true);
					}
				}
			}
			else
			{
				DialogEditStep editStep;
				editStep = new DialogEditStep(parent,(Passo)dt.getPasso(m_sel_passo));
				editStep.edit();
				paintDataStep(m_sel_passo);
			}
		}

        if(m_premuto && m_sel_col!=-1 && m_sel_passo!=-1)
        {
            m_premuto=false;
			if(m_sel_col!=4)
            {
				if(dt.getSizeCol(m_sel_col,m_sel_passo)>0)
				{
					m_Mres.getItem(0).setLabel("Aggiungi Prima");
                    m_Mres.getItem(1).setEnabled(true);
				}
				else
				{
					m_Mres.getItem(0).setLabel("Aggiungi");
                    m_Mres.getItem(1).setEnabled(false);
				}

                if(m_sel_res==-1)
                {
                    m_Mres.getItem(2).setEnabled(false);
                    m_Mres.getItem(3).setEnabled(false);
                    m_Mres.getItem(4).setEnabled(false);
				}
                else
                {
                    m_Mres.getItem(2).setEnabled(true);
                    m_Mres.getItem(3).setEnabled(true);
                    m_Mres.getItem(4).setEnabled(true);
                }
				if(clipboard==null)
				{
					m_Mres.getItem(5).setEnabled(false);
					m_Mres.getItem(6).setEnabled(false);
				}
				else
				{
					m_Mres.getItem(5).setEnabled(true);
					m_Mres.getItem(6).setEnabled(true);
				}

                m_Mres.show(this,e.getX(),e.getY());
            }
            else
            {
                //System.out.println("colonne presenti: "+m_row);
                if(m_row>1)
                    m_Mpasso.getItem(2).setEnabled(true);
                else
                    m_Mpasso.getItem(2).setEnabled(false);

                m_Mpasso.show(this,e.getX(),e.getY());
            }
        }
    }
	public void mouseEntered(MouseEvent e){;}
	public void mouseExited(MouseEvent e){;}
	public void mousePressed(MouseEvent e){;}
	public void mouseReleased(MouseEvent e){;}

    //START implementazione ActionListeners
    public void actionPerformed(ActionEvent e)
    {
        String command=e.getActionCommand();
		Coordinate coord;
		Resource rs;

        //Aggiungi Risorsa Prima
        if(command.equals("PAE_P"))
		{
            coord=addNewResource(0,"Paesaggio",true);
    	    rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditPaesaggio editPae;
           	editPae = new DialogEditPaesaggio(parent,rs);
    	    editPae.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("FIO_P"))
		{
            coord=addNewResource(1,"Fiore/Erba",true);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditFiore editFiore;
        	editFiore = new DialogEditFiore(parent,rs);
            editFiore.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("ALB_P"))
		{
            coord=addNewResource(2,"Albero/Arbusto",true);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditAlbero editAlbero;
        	editAlbero = new DialogEditAlbero(parent,rs);
            editAlbero.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("FAU_P"))
		{
            coord=addNewResource(3,"Fauna",true);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditFauna editFauna;
        	editFauna = new DialogEditFauna(parent,rs);
            editFauna.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("AMB_P"))
		{
            coord=addNewResource(4,"Ambiente Naturale",true);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditAmbienteNaturale editAmbienteNaturale;
            editAmbienteNaturale = new DialogEditAmbienteNaturale(parent,rs);
            editAmbienteNaturale.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("MET_P"))
		{
            coord=addNewResource(5,"Meteo",true);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditMeteo editMeteo;
            editMeteo = new DialogEditMeteo(parent,rs);
            editMeteo.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("MON_P"))
		{
            coord=addNewResource(6,"Monumento/Luogo Storico",true);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditMonumento editMonumento;
            editMonumento = new DialogEditMonumento(parent,rs);
            editMonumento.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("INT_P"))
		{
            coord=addNewResource(7,"Intervista",true);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditIntervista editIntervista;
            editIntervista = new DialogEditIntervista(parent,rs);
            editIntervista.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("CRO_P"))
		{
            coord=addNewResource(8,"Fatto di Cronaca",true);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditFatto editFatto;
            editFatto = new DialogEditFatto(parent,rs);
            editFatto.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("CUR_P"))
		{
            coord=addNewResource(9,"Curiosita'/Osservazione",true);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditCuriosita editCuriosita;
            editCuriosita = new DialogEditCuriosita(parent,rs);
            editCuriosita.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        //Aggiungi Risorsa Dopo
        if(command.equals("PAE_D"))
		{
            coord=addNewResource(0,"Paesaggio",false);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
      	    DialogEditPaesaggio editPae;
            editPae = new DialogEditPaesaggio(parent,rs);
    	    editPae.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("FIO_D"))
		{
            coord=addNewResource(1,"Fiore/Erba",false);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditFiore editFiore;
        	editFiore = new DialogEditFiore(parent,rs);
            editFiore.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("ALB_D"))
		{
            coord=addNewResource(2,"Albero/Arbusto",false);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditAlbero editAlbero;
        	editAlbero = new DialogEditAlbero(parent,rs);
            editAlbero.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("FAU_D"))
		{
            coord=addNewResource(3,"Fauna",false);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditFauna editFauna;
        	editFauna = new DialogEditFauna(parent,rs);
            editFauna.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("AMB_D"))
		{
            coord=addNewResource(4,"Ambiente Naturale",false);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditAmbienteNaturale editAmbienteNaturale;
            editAmbienteNaturale = new DialogEditAmbienteNaturale(parent,rs);
            editAmbienteNaturale.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("MET_D"))
		{
            coord=addNewResource(5,"Meteo",false);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditMeteo editMeteo;
            editMeteo = new DialogEditMeteo(parent,rs);
            editMeteo.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("MON_D"))
		{
            coord=addNewResource(6,"Monumento/Luogo Storico",false);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditMonumento editMonumento;
            editMonumento = new DialogEditMonumento(parent,rs);
            editMonumento.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("INT_D"))
		{
            coord=addNewResource(7,"Intervista",false);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditIntervista editIntervista;
            editIntervista = new DialogEditIntervista(parent,rs);
            editIntervista.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("CRO_D"))
		{
            coord=addNewResource(8,"Fatto di Cronaca",false);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditFatto editFatto;
            editFatto = new DialogEditFatto(parent,rs);
            editFatto.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}
        else if(command.equals("CUR_D"))
		{
            coord=addNewResource(9,"Curiosita'/Osservazione",false);
			rs=dt.getResource(coord.sel_col,coord.sel_passo,coord.sel_res);
            DialogEditCuriosita editCuriosita;
            editCuriosita = new DialogEditCuriosita(parent,rs);
            editCuriosita.edit();
            rs.updateImg(this);
            m_Gbuffer.drawImage(rs.n,m_poscol[coord.sel_col]+2,vRowGet(coord.sel_passo+1)+2+(coord.sel_res*HRES),this);
			repaint();
		}

        else if(command.equals("rmodifica"))
        {
    	    rs=dt.getResource(m_sel_col,m_sel_passo,m_sel_res);
			//System.out.println("rmodifica "+m_sel_col+" "+m_sel_passo+" "+m_sel_res);
    	    boolean modify=true;
            switch(rs.type)
            {
                case 0:
            	    DialogEditPaesaggio editPae;
                	editPae = new DialogEditPaesaggio(parent,rs);
    	            editPae.edit();
    	            break;
    	        case 1:
               	    DialogEditFiore editFiore;
        	        editFiore = new DialogEditFiore(parent,rs);
            	    editFiore.edit();
            	    break;
            	case 2:
               	    DialogEditAlbero editAlbero;
        	        editAlbero = new DialogEditAlbero(parent,rs);
            	    editAlbero.edit();
            	    break;
            	case 3:
               	    DialogEditFauna editFauna;
        	        editFauna = new DialogEditFauna(parent,rs);
            	    editFauna.edit();
            	    break;
            	case 4:
            	    DialogEditAmbienteNaturale editAmbienteNaturale;
            	    editAmbienteNaturale = new DialogEditAmbienteNaturale(parent,rs);
            	    editAmbienteNaturale.edit();
            	    break;
            	case 5:
            	    DialogEditMeteo editMeteo;
            	    editMeteo = new DialogEditMeteo(parent,rs);
            	    editMeteo.edit();
            	    break;
            	case 6:
            	    DialogEditMonumento editMonumento;
            	    editMonumento = new DialogEditMonumento(parent,rs);
            	    editMonumento.edit();
            	    break;
            	case 7:
            	    DialogEditIntervista editIntervista;
            	    editIntervista = new DialogEditIntervista(parent,rs);
            	    editIntervista.edit();
            	    break;
            	case 8:
            	    DialogEditFatto editFatto;
            	    editFatto = new DialogEditFatto(parent,rs);
            	    editFatto.edit();
            	    break;
            	case 9:
            	    DialogEditCuriosita editCuriosita;
            	    editCuriosita = new DialogEditCuriosita(parent,rs);
            	    editCuriosita.edit();
            	    break;
           	    default:
           	        modify=false;
            }
            if(modify)
            {
                rs.updateImg(this);
                m_Gbuffer.drawImage(rs.y,m_poscol[m_sel_col]+2,vRowGet(m_sel_passo+1)+2+(m_sel_res*HRES),this);
                parent.setPrbModified(true);
            }
        }

        else if(command.equals("rcancella"))
            deleteResource();
        else if(command.equals("rcopia"))
		{
    		clipboard=dt.getResource(m_sel_col,m_sel_passo,m_sel_res).copyRes(this);
		}
		else if(command.equals("rincolla_p"))
			addClipboardResource(true);
		else if(command.equals("rincolla_d"))
			addClipboardResource(false);
        else if(command.equals("prima"))
            addNewStep(0,0,0,true);
        else if(command.equals("dopo"))
            addNewStep(0,0,0,false);
        else if(command.equals("modifica"))
        {
    	    DialogEditStep editStep;
        	editStep = new DialogEditStep(parent,(Passo)dt.getPasso(m_sel_passo));
    	    editStep.edit();
    	    paintDataStep(m_sel_passo);
        }
        else if(command.equals("cancella"))
            deleteStep();
        else if(command.equals("elimina tutte le risorse"))
            deleteAllResource();

    }


    //metodi per la modifica della vettore row
    void vRowIns(int h)
    {
        Integer height= new Integer(h);
        m_posrow.addElement((Object)height);
    }
    int vRowGet(int num)
    {
        return ((Integer)m_posrow.elementAt(num)).intValue();
    }

    public DataTable getDataTable()
    {
        return dt;
    }
    public void setDataTable(DataTable newdt)
    {
        dt=null;
        dt=newdt;
        m_row=dt.data.size();
        m_Gbuffer.clearRect(0,0,670,4096);
        resize();
        drawNet();
        fillResource(RICALCOLA);
    }
    
    public void toHTML(PRBParam param, String template)
    {
        
        //imposto estensione
        String ext;
        if(param.ext)
            ext=new String(".html");
        else
            ext=new String(".htm");
        
        
        // posizione corrente del jar
        String home = System.getProperty("user.dir"); 
		try {
			File homefile = new File(PRB.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			home = homefile.getAbsolutePath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
        
        //imposto directory di output
        String dir;
        if(param.dir.length()!=0)
        	dir=param.dir;
        else
        	dir=home + File.separator + "output";

        File dirfile=new File(dir);
        if(!dirfile.isAbsolute())
        {
        	dir=home + File.separator + dir;
        	dirfile=new File(dir);
        }
        if(!dirfile.exists()||!dirfile.isDirectory())
        {
        	dirfile.mkdirs();
        }

        System.out.println("---- INIZIO OUTPUT ----");
        System.out.println("HOME: "+home);
		System.out.println("TARGET: "+dir);

        //preparo la dir di output
		creaOutputDir(home, dir,"guida");
        creaOutputDir(home, dir,"guida/icone");
        creaOutputDir(home, dir,"immagini");
        creaOutputDir(home, dir,"icone");
        creaOutputDir(home, dir,"icone/alb");
        creaOutputDir(home, dir,"icone/amb");
        creaOutputDir(home, dir,"icone/cro");
        creaOutputDir(home, dir,"icone/cur");
        creaOutputDir(home, dir,"icone/fau");
        creaOutputDir(home, dir,"icone/fio");
        creaOutputDir(home, dir,"icone/int");
        creaOutputDir(home, dir,"icone/met");
        creaOutputDir(home, dir,"icone/mon");
		creaOutputDir(home, dir,"paesaggi");

        //copio tutti i file della directory del template tranne quelli con estensione .tmpl
        
		File[] list;
        URL templateInterno = TemplateBox.class.getResource("/template");
        File fileCheckInterno = new File(templateInterno.getPath() + File.separator + template);
		if( fileCheckInterno.exists() ) {
			home = templateInterno.getPath();
        	list = fileCheckInterno.listFiles();	
        } else {
        	File fileCheckEsterno = new File(home + File.separator + "template" + File.separator + template);
        	list = fileCheckEsterno.listFiles();
        }
        
        for(int i=0;i<list.length;i++) {
			File fileCorrente = list[i];
			System.out.println("Analizzo "+fileCorrente.getAbsolutePath());
			if(fileCorrente.isFile() && !fileCorrente.getName().endsWith(".tmpl"))
        		DiskUtil.copyFile(fileCorrente.getAbsolutePath(),dir+File.separator+fileCorrente.getName());
		}

        String[] ncols={"SL","SV","DV","DL"};
        for(int i=0;i<m_row;i++)
            for(int j=0;j<4;j++)
                for(int k=0;k<dt.getSizeCol(j,i);k++)
                {
                    System.out.print("*");
                    String start=""+i;
                    if(start.length()<2)
                    	start="0"+start;
                    Resource r=dt.getResource(j,i,k);
                    String dest = home + File.separator + template;
                    switch(r.type)
                    {
                        case 0:
                            Scheda s0=(Scheda)r.scheda;
                            s0.html=start+ncols[j]+"PAE"+k+ext;
                            s0.toHTML(dir+"/"+s0.html,dest);
                            break;
                        case 1:
                            Fiore s1=(Fiore)r.scheda;
                            s1.html=start+ncols[j]+"FIO"+k+ext;
                            s1.toHTML(dir+"/"+s1.html,dest);
                            break;
                        case 2:
                            Albero s2=(Albero)r.scheda;
                            s2.html=start+ncols[j]+"ALB"+k+ext;
                            s2.toHTML(dir+"/"+s2.html,dest);
                            break;
                        case 3:
                            Fauna s3=(Fauna)r.scheda;
                            s3.html=start+ncols[j]+"FAU"+k+ext;
                            s3.toHTML(dir+"/"+s3.html,dest);
                            break;
                        case 4:
                            AmbienteNaturale s4=(AmbienteNaturale)r.scheda;
                            s4.html=start+ncols[j]+"AMB"+k+ext;
                            s4.toHTML(dir+"/"+s4.html,dest);
                            break;
                        case 5:
                            Meteo s5=(Meteo)r.scheda;
                            s5.html=start+ncols[j]+"MET"+k+ext;
                            s5.toHTML(dir+"/"+s5.html,dest);
                            break;
                        case 6:
                            Monumento s6=(Monumento)r.scheda;
                            s6.html=start+ncols[j]+"MON"+k+ext;
                            s6.toHTML(dir+"/"+s6.html,dest);
                            break;
                        case 7:
                            Intervista s7=(Intervista)r.scheda;
                            s7.html=start+ncols[j]+"INT"+k+ext;
                            s7.toHTML(dir+"/"+s7.html,dest);
                            break;
                        case 8:
                            Fatto s8=(Fatto)r.scheda;
                            s8.html=start+ncols[j]+"CRO"+k+ext;
                            s8.toHTML(dir+"/"+s8.html,dest);
                            break;
                        case 9:
                            Curiosita s9=(Curiosita)r.scheda;
                            s9.html=start+ncols[j]+"CUR"+k+ext;
                            s9.toHTML(dir+"/"+s9.html,dest);
                            break;
                    }
                }

        Vector nodes;
        Vector tnodes,ttnodes;
        PRBMParser parser;
        PRBMParserNode tnode;

        //INFO.HTML
        nodes = new Vector();
        nodes.addElement(new PRBMParserNode('S',"percorso.titolo", param.title, 0, null));
        nodes.addElement(new PRBMParserNode('S',"percorso.luogo", param.site, 0, null));
        nodes.addElement(new PRBMParserNode('S',"percorso.dataora", TimeStamp.toHtml(param.data), 0, null));
        if(param.autori.length()>0)
        {
            nodes.addElement(new PRBMParserNode('I',"percorso.autori", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"percorso.autori.value", param.autori, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"percorso.autori", null, 0, null));
        if(param.note.length()>0)
        {
            nodes.addElement(new PRBMParserNode('I',"percorso.note", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"percorso.note.value", param.note, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"percorso.note", null, 0, null));
        if(param.dir.length()>0)
        {
            nodes.addElement(new PRBMParserNode('I',"percorso.dir", null, 1, null));
            nodes.addElement(new PRBMParserNode('S',"percorso.dir.value", param.dir, 0, null));
        }
        else
            nodes.addElement(new PRBMParserNode('I',"percorso.dir", null, 0, null));
        parser = new PRBMParser(home + File.separator + template + File.separator + "info.tmpl", dir + "/info.html", nodes);
        parser.parse();

        //INDEX.HTML
        //Nodi generali
        nodes = new Vector();
        nodes.addElement(new PRBMParserNode('S',"programma.nome", "Percorso Rettificato Belga", 0, null));
        nodes.addElement(new PRBMParserNode('S',"programma.guida.link", "guida/index.html", 0, null));
        nodes.addElement(new PRBMParserNode('S',"percorso.titolo", param.title, 0, null));
        nodes.addElement(new PRBMParserNode('S',"percorso.luogo", param.site, 0, null));
        nodes.addElement(new PRBMParserNode('S',"percorso.dataora", TimeStamp.toHtml(param.data), 0, null));

        //Nodi traccia
        String[] cols = new String[4];
        cols[0]="traccia.sxf";
        cols[1]="traccia.sxn";
        cols[2]="traccia.dxn";
        cols[3]="traccia.dxf";
        tnodes = new Vector();
        for(int i=m_row-1; i >= 0; i--)
        {
            Passo p=dt.getPasso(i);
            tnode = new PRBMParserNode('S',"traccia.data.dir",Integer.toString(p.azimut),i+1,null);
            tnodes.addElement(tnode);
            tnode = new PRBMParserNode('S',"traccia.data.dist",Integer.toString(p.metri),i+1,null);
            tnodes.addElement(tnode);
            tnode = new PRBMParserNode('S',"traccia.data.min",Integer.toString(p.tempo),i+1,null);
            tnodes.addElement(tnode);

            for (int col=0; col<4; col++)
            {
                int lun=p.getSizeCol(col);
                if(lun!=0)
                {
                    ttnodes = new Vector();
                    PRBMParserNode ttnode;
                    tnode = new PRBMParserNode('I',cols[col],null,1,null);
                    tnodes.addElement(tnode);
                    for(int k=0; k<lun; k++)
                    {
                        Resource r=p.getResource(col,k);
                        ttnode = new PRBMParserNode('S',cols[col]+".scheda.ico.src",r.scheda.icona,lun-k,null);
                        ttnodes.addElement(ttnode);
                        ttnode = new PRBMParserNode('S',cols[col]+".scheda.link",r.scheda.html,lun-k,null);
                        ttnodes.addElement(ttnode);
                        ttnode = new PRBMParserNode('S',cols[col]+".scheda.titolo",r.scheda.didascalia,lun-k,null);
                        ttnodes.addElement(ttnode);
                    }
                    tnodes.addElement(new PRBMParserNode('R',cols[col]+".scheda",null,lun,ttnodes));
                }
                else {
                    tnode = new PRBMParserNode('I',cols[col],null,0,null);
                    tnodes.addElement(tnode);
                }
            }
        }
        nodes.addElement(new PRBMParserNode('R',"traccia",null,m_row,tnodes));
        parser = new PRBMParser(home + File.separator + template + File.separator + "index.tmpl", dir+"/index.html", nodes);
        parser.parse();
    }

	private void creaOutputDir(String baseDir, String targetDir, String innerDir) {
		
		File[] list;
		File diricone=new File(targetDir+File.separator+innerDir);
        diricone.mkdir();
		list = ( new File(baseDir + File.separator + "finale" + File.separator + innerDir)).listFiles();
        for(int i=0;i<list.length;i++)
        	if(list[i].isFile())
        		DiskUtil.copyFile(baseDir + File.separator + "finale"+File.separator+innerDir+File.separator+list[i].getName(),targetDir+File.separator+innerDir+File.separator+list[i].getName());
	}
    
    String r_html(Passo passo,int col)
    {
        int lun=passo.getSizeCol(col);
        if(lun==0)
        {
            return "&nbsp;";
        }
        String ret="<table border=0>\n";
        Resource r;
        for(int i=0;i<lun;i++)
        {
            r=passo.getResource(col,i);
            ret+="<tr><td><img src=\""+r.scheda.icona+"\" border=0></td><td><a href=\""+r.scheda.html+"\">"+r.scheda.didascalia+"</a></td></tr>";
        }
        ret+="</table>";
        return ret;
    }
}

class Coordinate
{
	int sel_col;
	int sel_passo;
	int sel_res;

	public Coordinate(int m_sel_col, int m_sel_passo, int m_sel_res)
	{
		sel_col=m_sel_col;
		sel_passo=m_sel_passo;
		sel_res=m_sel_res;
	}
}
