package jircii.app;

import rero.client.DataStructures;
import rero.client.user.UserHandler;
import rero.config.ClientDefaults;
import rero.config.ClientState;
import rero.dialogs.AboutWindow;
import rero.dialogs.HelpWindow;
import rero.dialogs.OptionWindow;
import rero.gui.IRCSession;
import rero.gui.SessionManager;

public class SystemCapabilities {
	private Application application;
	
	public SystemCapabilities(Application application) {
		this.application = application;
	}
	
	public void showAbout() {
		AboutWindow.initialize(application.getWindow());
		AboutWindow.showDialog(null);
	}
	
	public void showPreferences(String selectedItem) {
		OptionWindow.initialize(application.getWindow());
		OptionWindow.showDialog(null);
		OptionWindow.displaySpecificDialog(selectedItem);
	}
	
	public void showHelp(String params) {
		HelpWindow.initialize(application.getWindow());
		HelpWindow.showDialog(null);
	}
	
	public void requestAttention() {
		Boolean critical = new Boolean(ClientState.getClientState().isOption("option.attention.osx.bouncedock.repeat", ClientDefaults.attention_osx_bouncedock_repeat));
		application.getNativeSystem().requestAttention(critical);
	}
	
	public void quit(SystemQuitHandler quitHandler) {
		if (quitHandler == null) {
			quitHandler = new SystemQuitHandler() {
				@Override
				public void performQuit() {
					application.getWindow().dispose();
				}
				
				@Override
				public void cancelQuit() {}
			};
		}
		SessionManager sessionManager = application.getWindow().getSessionManager();
		for (int x = 0; x < sessionManager.getTabCount(); x++) {
			IRCSession temp = sessionManager.getSession(sessionManager.getComponentAt(x));

			temp.getCapabilities().injectEvent("EXIT");

			if (temp.getCapabilities().isConnected()) {
				((UserHandler) temp.getCapabilities().getDataStructure(DataStructures.UserHandler)).runAlias("QUIT", "");
			}
		}

		ClientState.getClientState().setBounds("desktop.bounds", application.getWindow().getBounds());
		ClientState.getClientState().sync();
		
		quitHandler.performQuit();
	}
}
