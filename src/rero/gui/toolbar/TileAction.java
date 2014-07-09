package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import rero.gui.SessionManager;
import rero.gui.mdi.ClientDesktop;

public class TileAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		((ClientDesktop) SessionManager.getGlobalCapabilities().getActiveSession().getDesktop()).tileWindows();
	}

	@Override
	public String getDescription() {
		return "Tile Windows";
	}

	@Override
	public int getIndex() {
		return 31;
	}
}
