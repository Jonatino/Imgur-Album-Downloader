package beaudoin.apps.gui.impl;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import javax.swing.JPanel;

import beaudoin.apps.gui.GUI;

public class FilePanel extends JPanel implements DropTargetListener {

	private static final long serialVersionUID = -5866093322425462222L;

	@Override
	public void drop(DropTargetDropEvent dtde) {
		try {
			Transferable tr = dtde.getTransferable();
			DataFlavor[] flavors = tr.getTransferDataFlavors();
			dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			String path = tr.getTransferData(flavors[0]).toString();
			path = path.substring(1, path.length() - 1);

			GUI.get().updateDirectoryText(path);
		} catch (Exception e) {
			dtde.rejectDrop();
		}
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub

	}

}
