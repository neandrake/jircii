package rero.gui.script;

import java.util.HashMap;

import rero.bridges.event.ScriptedEventListener;
import rero.gui.input.InputListener;
import rero.gui.input.UserInputEvent;
import rero.util.ClientUtils;

public class WindowInputListener extends ScriptedEventListener implements InputListener {
	@Override
	public void onInput(UserInputEvent ev) {
		HashMap eventData = ClientUtils.getEventHashMap("-", ev.text);

		if (dispatchEvent(eventData) == rero.ircfw.interfaces.ChatListener.EVENT_HALT) {
			ev.consume();
		}
	}

	@Override
	public void setupListener() {
		// already setup by default *shrug*
	}
}
