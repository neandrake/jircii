package rero.dialogs;

import rero.config.ClientDefaults;
import rero.dck.DMain;

public class ClientOptions extends DMain {
	@Override
	public String getTitle() {
		return "Client Options";
	}

	@Override
	public String getDescription() {
		return "Client Options";
	}

	@Override
	public void setupDialog() {
		addBlankSpace();

		addCheckboxInput("update.ial", ClientDefaults.option_showmotd, "Update IAL on channel join", 'I');
		addCheckboxInput("option.reconnect", ClientDefaults.option_reconnect, "Auto-reconnect when disconnected", 'r');
	}
}
