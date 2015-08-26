package org.casarini.prbm.gui.dialog;

import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import org.casarini.prbm.gui.PRB;
import org.casarini.prbm.util.DiskUtil;
import org.casarini.prbm.util.RunnerConfig;

public abstract class DefaultDialogEdit extends Dialog {
	Frame parent;
	
	DefaultDialogEdit(PRB parent, boolean modal) {
		super(parent, modal);
		this.parent = parent;
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				setVisible(false);
				dispose();
			}
		});
	}
	
    public void edit() {
		Rectangle bounds = getParent().getBounds();
		Rectangle abounds = getBounds();

		setLocation(bounds.x + (bounds.width - abounds.width)/ 2,
			 bounds.y + (bounds.height - abounds.height)/2);

		super.setVisible(true);
	}
    
	protected void browseFile(TextField tf,String title) {
		FileDialog fd = new FileDialog(parent, title, FileDialog.LOAD);
		File iFile;

		//Setto la directory e il nome file all'interno del dialog
		iFile = new File(tf.getText());
		if(iFile.exists() && iFile.isFile() && iFile.isAbsolute()) {
			fd.setDirectory(iFile.getParent());
			fd.setFile(iFile.getName());
		} else if (RunnerConfig.getInstance().getLastMediaPath() != null) {
			fd.setDirectory(RunnerConfig.getInstance().getLastMediaPath());
		} else {
			if(((PRB)parent).c_param.dir.length()>0)
				fd.setDirectory(DiskUtil.getRealDir(((PRB)parent).c_param.dir));
			else
				fd.setDirectory(".");
		}

		//mostro il dialog
		fd.setVisible(true);

		//catturo il nome file
		if(fd.getFile() != null) {
			String strFile = fd.getDirectory() + fd.getFile();
			File oFile = new File(strFile);
			if(!oFile.exists()) {
				AttentionDialog d = new AttentionDialog(parent, "ATTENZIONE!", "Il file " + strFile + " non esiste!!");
				d.display();
			} else {
				tf.setText(strFile);
				RunnerConfig.getInstance().setLastMediaPath(fd.getDirectory());
			}
		}
		fd.dispose();
	}
}
