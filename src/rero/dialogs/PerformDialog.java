package rero.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import rero.config.ClientState;
import rero.dck.DItem;
import rero.dck.DMain;
import rero.dck.DParent;
import rero.dck.items.CheckboxInput;
import rero.dck.items.NetworkSelect;
import rero.dck.items.TextInput;

public class PerformDialog extends DMain implements DParent, ActionListener {
	@Override
	public String getTitle() {
		return "Perform";
	}

	@Override
	public String getDescription() {
		return "Perform on Connect";
	}

	protected String current = NetworkSelect.ALL_NETWORKS;

	@Override
	public void actionPerformed(ActionEvent ev) {
		itemc.save();
		current = ev.getActionCommand();
	}

	@Override
	public void notifyParent(String variable) {
		ClientState.getClientState().fireChange("perform");
		itemc.refresh();
	}

	@Override
	public String getVariable(String variable) {
		return "perform." + current.toLowerCase();
	}

	protected DItem itemb, itemc;
	protected CheckboxInput itema;

	@Override
	public JComponent getDialog() {
		JPanel dialog = new JPanel();

		setupLayout(dialog);
		setupDialog();

		dialog.add(itema.getComponent(), BorderLayout.SOUTH);

		dialog.add(itemb.getComponent(), BorderLayout.NORTH);
		dialog.add(itemc.getComponent(), BorderLayout.CENTER);

		return dialog;
	}

	@Override
	public JComponent setupLayout(JComponent component) {
		component.setLayout(new BorderLayout(3, 3));
		component.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		return component;
	}

	@Override
	public void setupDialog() {
		itema = addCheckboxInput("perform.enabled", false, "Perform these commands when connecting", 'P', FlowLayout.LEFT);

		itemb = addNetworkSelector("perform.networks", "perform.cnetwork");
		itemc = addTextInput(".perform", 5); // doesn't really matter

		((NetworkSelect) itemb).addActionListener(this);
		((NetworkSelect) itemb).addDeleteListener((TextInput) itemc);

		itemb.setParent(this);
		itemc.setParent(this);

		itema.addDependent(itemb);
		itema.addDependent(itemc);
	}

	@Override
	public void refresh() {
		current = NetworkSelect.ALL_NETWORKS;
		itemc.refresh();
		super.refresh();
	}
}
