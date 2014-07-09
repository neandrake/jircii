package rero.dialogs.toolkit;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import rero.dck.SmallButton;

public class FileField extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JTextField text;
	protected SmallButton button;
	protected JFileChooser chooser;

	protected boolean directory;

	public FileField(File value, boolean _directory) {
		text = new JTextField();
		button = new SmallButton(text.getBorder(), "Click to open a file chooser");

		button.addActionListener(this);

		setLayout(new BorderLayout(2, 2));

		add(text, BorderLayout.CENTER);
		add(button, BorderLayout.EAST);

		text.setText(value.getAbsolutePath());

		directory = _directory;
	}

	public File getSelectedFile() {
		return new File(text.getText());
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (chooser == null) {
			chooser = new JFileChooser();
		}

		if (directory) {
			chooser.setApproveButtonText("Select Directory");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		} else {
			chooser.setApproveButtonText("Select File");
		}

		chooser.setSelectedFile(new File(text.getText()));

		if (chooser.showDialog(this, null) == JFileChooser.APPROVE_OPTION) {
			text.setText(chooser.getSelectedFile().getAbsolutePath());
			text.requestFocus();
		}
	}
}
