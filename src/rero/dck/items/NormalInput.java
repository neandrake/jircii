package rero.dck.items;

import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import rero.dck.DItem;
import rero.dck.DParent;

public class NormalInput extends JPanel implements DItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JLabel label;

	public NormalInput(String text, int align) {
		setLayout(new FlowLayout(align));

		label = new JLabel(text);

		add(label);
	}

	@Override
	public void setEnabled(boolean b) {}

	@Override
	public void save() {

	}

	@Override
	public void refresh() {}

	@Override
	public int getEstimatedWidth() {
		return 0;
	}

	@Override
	public void setAlignWidth(int width) {}

	@Override
	public void setParent(DParent parent) {

	}

	public void setText(String text) {
		label.setText(text);
	}

	@Override
	public JComponent getComponent() {
		return this;
	}
}
