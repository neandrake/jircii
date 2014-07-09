package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import rero.gui.SessionManager;

public class ScriptAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		SessionManager.getGlobalCapabilities().showOptionDialog("Script Manager");
	}

	@Override
	public String getDescription() {
		return "Script Manager";
	}

	@Override
	public int getIndex() {
		return 10;
	}
}
