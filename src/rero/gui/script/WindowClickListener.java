package rero.gui.script;

import java.util.HashMap;

import rero.bridges.event.ScriptedEventListener;
import text.event.ClickEvent;
import text.event.ClickListener;

public class WindowClickListener extends ScriptedEventListener implements ClickListener {
	@Override
	public void wordClicked(ClickEvent ev) {
		HashMap eventData = new HashMap();

		eventData.put("$item", ev.getClickedText());
		eventData.put("$parms", ev.getContext());
		eventData.put("$data", ev.getClickedText() + " " + ev.getContext());
		eventData.put("$mouse", ev.getEvent());
		eventData.put("$clicks", new Integer(ev.getEvent().getClickCount()).toString());

		if (dispatchEvent(eventData) == rero.ircfw.interfaces.ChatListener.EVENT_HALT) {
			ev.consume();
		}
	}

	@Override
	public void setupListener() {
		// already setup by default *shrug*
	}
}
