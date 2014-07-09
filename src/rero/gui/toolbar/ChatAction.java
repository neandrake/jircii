package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import rero.client.Capabilities;
import rero.gui.SessionManager;

public class ChatAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();

		String nick = JOptionPane.showInputDialog(SessionManager.getGlobalCapabilities().getFrame(), "Request dcc chat from:", "DCC Chat", JOptionPane.QUESTION_MESSAGE);

		if (nick != null) {
			SessionManager.getGlobalCapabilities().getActiveSession().executeCommand("/DCC chat " + nick);
		}
	}

	@Override
	public String getDescription() {
		return "Request a DCC Chat";
	}

	@Override
	public int getIndex() {
		return 23;
	}
}
