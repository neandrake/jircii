package rero.dck.items;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import rero.config.ClientState;
import rero.dck.SuperInput;

public class OptionInput extends SuperInput implements ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JLabel label;
	protected String defaultVal;

	protected JComboBox select;

	public OptionInput(String var, String _defaultVal, String values[], String _label, char mnemonic, int rightGap) {
		label = new JLabel(_label);

		setLayout(new BorderLayout());

		select = new JComboBox(values);
		select.setEditable(true);

		add(label, BorderLayout.WEST);
		add(select, BorderLayout.CENTER);

		if (rightGap > 0) {
			JPanel temp = new JPanel();
			temp.setPreferredSize(new Dimension(rightGap, 0));

			add(temp, BorderLayout.EAST);
		}

		label.setDisplayedMnemonic(mnemonic);
		select.addItemListener(this);

		variable = var;

		defaultVal = _defaultVal;
	}

	@Override
	public void setEnabled(boolean b) {
		Component[] blah = getComponents();
		for (int x = 0; x < blah.length; x++) {
			blah[x].setEnabled(b);
		}

		super.setEnabled(b);
	}

	@Override
	public void save() {
		ClientState.getClientState().setString(getVariable(), select.getSelectedItem().toString());
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
		select.setSelectedItem(ClientState.getClientState().getString(getVariable(), defaultVal));
	}

	@Override
	public void itemStateChanged(ItemEvent ev) {
		notifyParent();
	}
}
