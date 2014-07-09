package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import jircii.app.Application;

public class OptionsAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Application.getInstance().getCapabilities().showPreferences("");
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
