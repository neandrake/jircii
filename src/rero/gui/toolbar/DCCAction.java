package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import rero.gui.SessionManager;

public class DCCAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		SessionManager.getGlobalCapabilities().showOptionDialog("DCC Options");
	}

	@Override
	public String getDescription() {
		return "DCC Options";
	}

	@Override
	public int getIndex() {
		return 24;
	}
}
