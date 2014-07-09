package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import jircii.app.Application;

public class DCCAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Application.getInstance().getCapabilities().showPreferences("DCC Options");
	}

	@Override
	public String getDescription() {
		return "DCC Options";
	}

	@Override
	public int getIndex() {
		return 24;
	}
}
