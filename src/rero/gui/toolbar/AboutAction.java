package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import rero.gui.SessionManager;

public class AboutAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		if (ev.isShiftDown() && ev.isControlDown()) {
			SessionManager.getGlobalCapabilities().showCoolAbout();
		} else {
			SessionManager.getGlobalCapabilities().showAboutDialog();
		}
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
