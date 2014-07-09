package rero.dialogs.toolkit;

import javax.swing.JTextField;

public class PlainLabel extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlainLabel(String text) {
		setBorder(null);
		setEditable(false);
		setOpaque(false);
		setText(text);
	}
}
