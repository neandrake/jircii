package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import jircii.app.Application;

public class NotifyAction2 implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Application.getInstance().getCapabilities().showPreferences("Notify Setup");
	}

	@Override
	public String getDescription() {
		return "Edit Notify List";
	}

	@Override
	public int getIndex() {
		return 26;
	}
}
