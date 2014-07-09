package rero.gui.toolbar;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import jircii.app.Application;

public class SendAction implements ToolAction {
	@Override
	public void actionPerformed(MouseEvent ev) {
		String nick = JOptionPane.showInputDialog(Application.getInstance().getWindow(), "Send a file to:", "DCC Send", JOptionPane.QUESTION_MESSAGE);
		if (nick != null) {
			Application.getInstance().getWindow().getSessionManager().getActiveSession().executeCommand("/DCC send " + nick);
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
