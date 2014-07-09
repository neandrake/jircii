package rero.dck.items;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import rero.config.ClientState;
import rero.dck.FileLink;
import rero.dck.SuperInput;

public class DirectoryInput extends SuperInput implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JLabel label;
	protected FileLink text;
	protected String value;
	protected JFileChooser chooser;

	protected boolean directory;

	public DirectoryInput(String _variable, String _value, String _label, char mnemonic, int inset) {
		text = new FileLink();
		label = new JLabel(_label);

		text.addActionListener(this);

		setLayout(new BorderLayout(2, 2));

		add(label, BorderLayout.WEST);
		add(text, BorderLayout.CENTER);

		label.setDisplayedMnemonic(mnemonic);

		variable = _variable;
		value = _value;

		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, inset));
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (chooser == null) {
			chooser = new JFileChooser();
		}

		chooser.setApproveButtonText("Select Directory");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (chooser.showDialog(this, null) == JFileChooser.APPROVE_OPTION) {
			text.setText(chooser.getSelectedFile().getAbsolutePath());
		}

		notifyParent();
	}

	@Override
	public void save() {
		ClientState.getClientState().setString(getVariable(), text.getText());
	}

	@Override
	public int getEstimatedWidth() {
		return (int) label.getPreferredSize().getWidth();
	}

	@Override
	public void setAlignWidth(int width) {
		label.setPreferredSize(new Dimension(width, 0));
		revalidate();
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	public void refresh() {
		text.setText(ClientState.getClientState().getString(getVariable(), value));
	}
}
