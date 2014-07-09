package rero.dck.items;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.nio.charset.Charset;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import rero.config.ClientState;
import rero.dck.SuperInput;

public class CharsetInput extends SuperInput {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String DEFAULT_CHARSET = "Platform Default";

	protected JComboBox name;
	protected boolean listing = true;
	protected JLabel label;

	public CharsetInput(String _variable, String aLabel, char mnemonic, int rightGap) {
		variable = _variable;

		setLayout(new BorderLayout());

		name = new JComboBox();
		name.addItem("Loading Charsets...");

		add(name, BorderLayout.CENTER);

		if (rightGap > 0) {
			JPanel temp = new JPanel();
			temp.setPreferredSize(new Dimension(rightGap, 0));

			add(temp, BorderLayout.EAST);
		}

		label = new JLabel("  " + aLabel + " ");
		label.setDisplayedMnemonic(mnemonic);

		add(label, BorderLayout.WEST);
	}

	@Override
	public void setAlignWidth(int width) {
		label.setPreferredSize(new Dimension(width, 0));
		revalidate();
	}

	@Override
	public void save() {
		ClientState.getClientState().setString(getVariable(), name.getSelectedItem().toString());
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	public int getEstimatedWidth() {
		return (int) label.getPreferredSize().getWidth();
	}

	@Override
	public void refresh() {
		if (!listing) {
			name.setSelectedItem(ClientState.getClientState().getString(getVariable(), DEFAULT_CHARSET));
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					name.addItem(DEFAULT_CHARSET);

					Iterator i = Charset.availableCharsets().keySet().iterator();

					while (i.hasNext()) {
						name.addItem(i.next().toString());
					}

					name.removeItemAt(0);
					listing = false;
					refresh();
					revalidate();
				}
			});
		}
	}
}
