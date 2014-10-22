package beaudoin.apps.gui.impl;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import beaudoin.apps.gui.GUI;

public class DirectoryChooser extends JFileChooser {

	private static final long serialVersionUID = -2830784651182549274L;

	public DirectoryChooser() {
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		setDialogTitle("Select the destination directory for the album's image(s).");
		setCurrentDirectory(new File(GUI.get().getDirectoryText()));
		setAcceptAllFileFilterUsed(false);
		setApproveButtonText("Select");
		addChoosableFileFilter(new FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}

			@Override
			public String getDescription() {
				return "Directories only";
			}
		});
	}

	public static void open(GUI parent) {
		DirectoryChooser dc = new DirectoryChooser();
		if (dc.showOpenDialog(parent) == 0)
			parent.updateDirectoryText(dc.getSelectedFile().getAbsolutePath());
	}
}
