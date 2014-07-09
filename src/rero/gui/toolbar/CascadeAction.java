package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import rero.gui.SessionManager;
import rero.gui.mdi.ClientDesktop;

public class CascadeAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		((ClientDesktop) SessionManager.getGlobalCapabilities().getActiveSession().getDesktop()).cascadeWindows();
	}

	@Override
	public String getDescription() {
		return "Cascade Windows";
	}

	@Override
	public int getIndex() {
		return 32;
	}
}
