package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import jircii.app.Application;

public class HelpAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Application.getInstance().getCapabilities().showHelp("Help");
	}

	@Override
	public String getDescription() {
		return "View jIRCii Help";
	}

	@Override
	public int getIndex() {
		return 35;
	}
}
