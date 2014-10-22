package beaudoin.apps.gui;

import java.awt.dnd.DropTarget;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;

import beaudoin.apps.gui.impl.DirectoryChooser;
import beaudoin.apps.gui.impl.FilePanel;
import beaudoin.apps.http.AlbumCrawler;

public final class GUI extends JFrame {

	private static final long serialVersionUID = -3750253335575205139L;

	private JTextField directoryPath, albumURL;
	private JProgressBar progressBar;
	private JButton downloadButton;
	private static GUI singleton;

	public static GUI get() {
		if (singleton == null)
			singleton = new GUI();
		return singleton;
	}
	
	public GUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			setSize(556, 199);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setResizable(true);
			setTitle("Imgur Album Downloader - Beaudoin Apps©");
			getContentPane().setLayout(null);

			FilePanel fileDropPanel = new FilePanel();
			fileDropPanel.setBounds(0, 9, 540, 30);
			fileDropPanel.setLayout(null);
			getContentPane().setDropTarget(new DropTarget(getContentPane(), fileDropPanel));

			JLabel label = new JLabel("Image(s) Destination:");
			label.setBounds(12, 3, 133, 24);
			fileDropPanel.add(label);

			directoryPath = new JTextField();
			directoryPath.setBounds(155, 0, 267, 28);
			fileDropPanel.add(directoryPath);

			JButton browseButton = new JButton("Browse");
			browseButton.setBounds(432, 1, 99, 24);
			browseButton.addActionListener(e -> DirectoryChooser.open(this));
			fileDropPanel.add(browseButton);
			getContentPane().add(fileDropPanel);

			JLabel albumLabel = new JLabel("Album URL:");
			albumLabel.setBounds(12, 52, 133, 24);
			getContentPane().add(albumLabel);

			albumURL = new JTextField();
			albumURL.setBounds(155, 49, 267, 28);
			getContentPane().add(albumURL);

			downloadButton = new JButton("Download");
			downloadButton.setBounds(432, 50, 99, 24);
			getContentPane().add(downloadButton);
			downloadButton.addActionListener(l -> {
				AlbumCrawler ac = AlbumCrawler.create(getAlbumText());
				ac.download(progressBar, getDirectoryText());
			});

			JSeparator separator = new JSeparator();
			separator.setBounds(10, 88, 519, 8);
			getContentPane().add(separator);

			progressBar = new JProgressBar();
			progressBar.setStringPainted(true);
			progressBar.setBounds(10, 98, 519, 50);
			getContentPane().add(progressBar);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateDirectoryText(String text) {
		directoryPath.setText(text);
		directorySelected();
	}

	public void directorySelected() {
		albumURL.setEnabled(true);
		downloadButton.setEnabled(true);
	}

	public String getDirectoryText() {
		return directoryPath.getText();
	}

	public void updateAlbumText(String text) {
		albumURL.setText(text);
	}

	public String getAlbumText() {
		return albumURL.getText();
	}

	public void updateProgress(int perc) {
		progressBar.setValue(perc);
	}

	public void notifyFinished() {
		JOptionPane.showMessageDialog(this, "Done!", "Finished Downloading Album!", JOptionPane.INFORMATION_MESSAGE);
	}

}
