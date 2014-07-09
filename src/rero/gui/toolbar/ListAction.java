package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import jircii.app.Application;

public class ListAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Application.getInstance().getWindow().getSessionManager().getActiveSession().executeCommand("/list -gui");
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
