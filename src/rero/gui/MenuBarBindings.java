package rero.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBarBindings extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuBarBindings() {
		JMenu menu = new JMenu("Connection");
		menu.setMnemonic('c');

		JMenuItem item = new JMenuItem("Test");
		menu.add(item);

		add(menu);

		menu = new JMenu("View");
		menu.setMnemonic('v');

		add(menu);

		menu = new JMenu("Window");
		menu.setMnemonic('w');

		add(menu);

		menu = new JMenu("Help");
		menu.setMnemonic('h');

		add(menu);
	}
}
