package rero.dck;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class SuperInput extends JPanel implements DItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String variable;
	protected DParent parent;

	@Override
	public void setEnabled(boolean b) {
		disableComponents(this, b);
		super.setEnabled(b);
	}

	private void disableComponents(Container cont, boolean b) {
		Component[] blah = cont.getComponents();
		for (int x = 0; x < blah.length; x++) {
			blah[x].setEnabled(b);

			if (blah[x] instanceof Container) {
				disableComponents((Container) blah[x], b);
			}
		}
	}

	public String getVariable() {
		if (parent == null) {
			return variable;
		}

		return parent.getVariable(variable);
	}

	public void notifyParent() {
		if (parent != null) {
			save();
			parent.notifyParent(getVariable());
		}
	}

	@Override
	public void setParent(DParent parent) {
		this.parent = parent;
	}

	@Override
	public JComponent getComponent() {
		return this;
	}
}
