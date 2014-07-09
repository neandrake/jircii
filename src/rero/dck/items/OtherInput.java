package rero.dck.items;

import javax.swing.JComponent;

import rero.dck.DItem;
import rero.dck.DParent;

public class OtherInput implements DItem {
	protected JComponent other;

	public OtherInput(JComponent _other) {
		other = _other;
	}

	@Override
	public void setEnabled(boolean b) {
		other.setEnabled(b);
	}

	@Override
	public void save() {}

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
		return other;
	}

	@Override
	public void refresh() {}
}
