package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import jircii.app.Application;
import rero.gui.mdi.ClientDesktop;

public class TileAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		((ClientDesktop) Application.getInstance().getWindow().getSessionManager().getActiveSession().getDesktop()).tileWindows();
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
