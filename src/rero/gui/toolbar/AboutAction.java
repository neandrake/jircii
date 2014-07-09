package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import jircii.app.Application;

public class AboutAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Application.getInstance().getCapabilities().showAbout();
	}

	@Override
	public String getDescription() {
		return "About: jIRCii, the ultimate irc client";
	}

	@Override
	public int getIndex() {
		return 36;
	}
}
