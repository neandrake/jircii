package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import rero.client.Capabilities;
import rero.gui.SessionManager;

public class SendAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		Capabilities client = SessionManager.getGlobalCapabilities().getActiveSession().getCapabilities();

		String nick = JOptionPane.showInputDialog(SessionManager.getGlobalCapabilities().getFrame(), "Send a file to:", "DCC Send", JOptionPane.QUESTION_MESSAGE);

		if (nick != null) {
			SessionManager.getGlobalCapabilities().getActiveSession().executeCommand("/DCC send " + nick);
		}
	}

	@Override
	public String getDescription() {
		return "Send a file via DCC";
	}

	@Override
	public int getIndex() {
		return 22;
	}
}
