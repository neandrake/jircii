package rero.dck.items;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import rero.config.ClientState;
import rero.dck.SuperInput;

public class StringInput extends SuperInput {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JLabel label;
	protected String value;
	protected JTextField text;

	public StringInput(String var, String _value, String _label, int rightGap, char mnemonic, float font_size) {
		label = new JLabel(_label);

		if (font_size > 0.0) {
			Font font = UIManager.getFont("Label.font").deriveFont(font_size);
			label.setFont(font);
		}

		setLayout(new BorderLayout());

		text = new JTextField();

		add(label, BorderLayout.WEST);
		add(text, BorderLayout.CENTER);

		if (rightGap > 0) {
			JPanel temp = new JPanel();
			temp.setPreferredSize(new Dimension(rightGap, 0));

			add(temp, BorderLayout.EAST);
		}

		label.setDisplayedMnemonic(mnemonic);

		variable = var;
		value = _value;
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
