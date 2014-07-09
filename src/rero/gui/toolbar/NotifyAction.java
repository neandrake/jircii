package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import jircii.app.Application;

public class NotifyAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Application.getInstance().getWindow().getSessionManager().getActiveSession().executeCommand("/NOTIFY");
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
