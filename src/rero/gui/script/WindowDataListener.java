package rero.gui.script;

import java.util.HashMap;

import rero.bridges.event.ScriptedEventListener;
import rero.gui.UICapabilities;
import rero.ircfw.interfaces.ChatListener;

public class WindowDataListener extends ScriptedEventListener {
	protected UICapabilities gui;

	public WindowDataListener(UICapabilities _gui) {
		gui = _gui;
	}

	public boolean shouldContinue(String window, String text) {
		HashMap eventData = new HashMap();

		eventData.put("$parms", text);
		eventData.put("$data", window + " " + text);
		eventData.put("$window", window);

		return (dispatchEvent(eventData) != ChatListener.EVENT_HALT);
	}

	@Override
	public void setupListener() {
		gui.setListener(this);
	}
}
