package rero.dialogs;

import rero.dck.DMain;

public class NotifyDialog extends DMain {
	@Override
	public String getTitle() {
		return "Notify Setup";
	}

	@Override
	public String getDescription() {
		return "Notify List Setup";
	}

	@Override
	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();
		addLabel("The following users are on your notify list:", 30);
		addBlankSpace();
		addListInput("notify.users", "Add Notify User", "User to add to notify list?", 80, 125);
	}
}
