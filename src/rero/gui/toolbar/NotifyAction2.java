package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import rero.gui.SessionManager;

public class NotifyAction2 implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		SessionManager.getGlobalCapabilities().showOptionDialog("Notify Setup");
	}

	@Override
	public String getDescription() {
		return "Edit Notify List";
	}

	@Override
	public int getIndex() {
		return 26;
	}
}
