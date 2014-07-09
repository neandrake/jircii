package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import jircii.app.Application;
import rero.bridges.menu.MenuBridge;
import rero.client.Capabilities;

public class ConnectAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Capabilities client = Application.getInstance().getWindow().getSessionManager().getActiveSession().getCapabilities();

		if (client.isConnected()) {
			Application.getInstance().getWindow().getSessionManager().getActiveSession().executeCommand("/QUIT");
		} else {
			MenuBridge menuManager = (MenuBridge) client.getDataStructure("menuBridge");

			JPopupMenu menu = menuManager.getPrimaryPopup("&Connection");

			if (menu != null) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				ev.consume();
			}
		}
	}

	@Override
	public String getDescription() {
		Capabilities client = Application.getInstance().getWindow().getSessionManager().getActiveSession().getCapabilities();

		if (client.isConnected()) {
			return "Disconnect from server";
		} else {
			return "Connect to a server";
		}
	}

	@Override
	public int getIndex() {
		Capabilities client = Application.getInstance().getWindow().getSessionManager().getActiveSession().getCapabilities();

		if (client.isConnected()) {
			return 1;
		} else {
			return 0;
		}
	}
}
