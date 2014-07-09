package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import rero.gui.SessionManager;

public class IgnoreAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		SessionManager.getGlobalCapabilities().showOptionDialog("Ignore Setup");
	}

	@Override
	public String getDescription() {
		return "Edit Ignore List";
	}

	@Override
	public int getIndex() {
		return 28;
	}
}
