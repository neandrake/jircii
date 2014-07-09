package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import jircii.app.Application;

public class IgnoreAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Application.getInstance().getCapabilities().showPreferences("Ignore Setup");
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
