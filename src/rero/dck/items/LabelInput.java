package rero.dck.items;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import rero.dck.DItem;
import rero.dck.DParent;

public class LabelInput extends JPanel implements DItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JTextPane label;

	public LabelInput(String text, int width) {
		setLayout(new BorderLayout());

		label = new JTextPane();
		label.setText(text);
		label.setBorder(null);
		label.setOpaque(false);
		label.setEditable(false);

		add(label, BorderLayout.CENTER);

		JPanel gap = new JPanel();
		gap.setPreferredSize(new Dimension(width, 0));
		add(gap, BorderLayout.EAST);

		gap = new JPanel();
		gap.setPreferredSize(new Dimension(width, 0));
		add(gap, BorderLayout.WEST);
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
