package rero.dialogs;

import rero.dck.DMain;

public class IgnoreDialog extends DMain {
	@Override
	public String getTitle() {
		return "Ignore Setup";
	}

	@Override
	public String getDescription() {
		return "Ignore Mask Setup";
	}

	@Override
	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();
		addLabel("The following nick/host masks will be ignored:", 30);
		addBlankSpace();
		addListInput("ignore.masks", "Ignore this mask (nick!user@host):", "Add Ignore Mask", 80, 125);
	}
}
