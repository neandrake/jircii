package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import rero.gui.SessionManager;

public class OptionsAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		SessionManager.getGlobalCapabilities().showOptionDialog("");
	}

	@Override
	public String getDescription() {
		return "View and edit options";
	}

	@Override
	public int getIndex() {
		return 4;
	}
}
