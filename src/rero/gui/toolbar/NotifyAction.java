package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import rero.client.Capabilities;
import rero.gui.SessionManager;

public class NotifyAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();

		SessionManager.getGlobalCapabilities().getActiveSession().executeCommand("/NOTIFY");
	}

	@Override
	public String getDescription() {
		return "Show notify list";
	}

	@Override
	public int getIndex() {
		return 27;
	}
}
