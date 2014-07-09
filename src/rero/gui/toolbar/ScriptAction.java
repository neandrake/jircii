package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import jircii.app.Application;

public class ScriptAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Application.getInstance().getCapabilities().showPreferences("Script Manager");
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
