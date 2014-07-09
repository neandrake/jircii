package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import rero.gui.SessionManager;

public class HelpAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		SessionManager.getGlobalCapabilities().showHelpDialog("Help");
	}

	@Override
	public String getDescription() {
		return "View jIRCii Help";
	}

	@Override
	public int getIndex() {
		return 35;
	}
}
