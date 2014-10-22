package beaudoin.apps;

import beaudoin.apps.gui.GUI;

public class Main {

	public static void main(String[] args) throws Exception {
		GUI gui = GUI.get();
		gui.setVisible(true);
	}

}