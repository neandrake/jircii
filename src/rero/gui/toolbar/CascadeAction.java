package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import jircii.app.Application;
import rero.gui.mdi.ClientDesktop;

public class CascadeAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		((ClientDesktop) Application.getInstance().getWindow().getSessionManager().getActiveSession().getDesktop()).cascadeWindows();
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
