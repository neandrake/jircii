package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import rero.client.Capabilities;
import rero.gui.SessionManager;

public class ListAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();

		SessionManager.getGlobalCapabilities().getActiveSession().executeCommand("/list -gui");
	}

	@Override
	public String getDescription() {
		return "List Channels";
	}

	@Override
	public int getIndex() {
		return 7;
	}
}
