package rero.dialogs;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import rero.dck.DGroup;
import rero.dck.DItem;
import rero.dck.DMain;
import rero.dck.items.ServerList;
import rero.dialogs.server.ServerData;

public class SetupDialog extends DMain {
	protected ServerData data = ServerData.getServerData();
	protected DItem itema, itemb;

	@Override
	public String getTitle() {
		return "jIRCii Setup";
	}

	@Override
	public String getDescription() {
		return "Setup jIRCii";
	}

	@Override
	public JComponent getDialog() {
		JPanel dialog = new JPanel();

		setupLayout(dialog);
		setupDialog();

		dialog.add(itema.getComponent(), BorderLayout.CENTER);
		dialog.add(itemb.getComponent(), BorderLayout.SOUTH);

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
		itema = addOther(new ServerList(data, 0, 150, getCapabilities()));

		itemb = addDialogGroup(new DGroup("User Information", 0) {
			@Override
			public void setupDialog() {
				addStringInput("user.rname", "", " Real Name:  ", 'R', 10);
				addStringInput("user.email", "", " E-mail:  ", 'E', 60);
				addStringInput("user.nick", "", " Nickname:   ", 'N', 60);
				addStringInput("user.altnick", "", " Alt. Nick:  ", 'A', 60);
			}
		});
	}
}
