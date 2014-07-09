package rero.dck.items;

import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import rero.config.ClientState;
import rero.dck.DItem;
import rero.dck.SuperInput;

public class CheckboxInput extends SuperInput implements ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected LinkedList enabledTrue = new LinkedList();
	protected LinkedList enabledFalse = new LinkedList();
	protected JCheckBox box;

	protected boolean defaultVal;

	public CheckboxInput(String var, boolean defaultVar, String _label, char mnemonic) {
		this(var, defaultVar, _label, mnemonic, FlowLayout.LEFT);
	}

	public CheckboxInput(String var, boolean defaultVar, String _label, char mnemonic, int alignment) {
		setLayout(new FlowLayout(alignment, 0, 0));

		box = new JCheckBox(_label);
		box.addChangeListener(this);

		add(box);
		box.setMnemonic(mnemonic);

		setPreferredSize(box.getPreferredSize());

		variable = var;
		defaultVal = defaultVar;
	}

	@Override
	public void stateChanged(ChangeEvent ev) {
		handleDependents();
		notifyParent();
	}

	public void addDependent(DItem item) {
		enabledTrue.add(item);
	}

	public void addAntiDependent(DItem item) {
		enabledFalse.add(item);
	}

	@Override
	public void save() {
		// System.out.println("Saving: " + getVariable());

		ClientState.getClientState().setOption(getVariable(), box.isSelected());
	}

	@Override
	public int getEstimatedWidth() {
		return 0;
	}

	@Override
	public void setAlignWidth(int width) {}

	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	public void refresh() {
		// System.out.println("Refreshing: " + getVariable());

		box.setSelected(ClientState.getClientState().isOption(getVariable(), defaultVal));
		handleDependents();
	}

	public void handleDependents() {
		Iterator i = enabledTrue.iterator();
		while (i.hasNext()) {
			DItem temp = (DItem) i.next();
			temp.setEnabled(box.isSelected());
		}

		i = enabledFalse.iterator();
		while (i.hasNext()) {
			DItem temp = (DItem) i.next();
			temp.setEnabled(!box.isSelected());
		}
	}

	public JCheckBox getCheckBox() {
		return box;
	}
}
