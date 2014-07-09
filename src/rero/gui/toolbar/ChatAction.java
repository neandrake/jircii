package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import jircii.app.Application;

public class ChatAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		String nick = JOptionPane.showInputDialog(Application.getInstance().getWindow(), "Request dcc chat from:", "DCC Chat", JOptionPane.QUESTION_MESSAGE);
		if (nick != null) {
			Application.getInstance().getWindow().getSessionManager().getActiveSession().executeCommand("/DCC chat " + nick);
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
