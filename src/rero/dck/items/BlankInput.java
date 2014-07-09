package rero.dck.items;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

import rero.dck.DItem;
import rero.dck.DParent;

public class BlankInput extends JPanel implements DItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(0, 5);
	}
}
