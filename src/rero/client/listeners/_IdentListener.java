package rero.client.listeners;

import java.util.HashMap;

import rero.bridges.event.ScriptedEventListener;
import rero.ident.IdentDaemon;
import rero.ident.IdentListener;

public class _IdentListener extends ScriptedEventListener implements IdentListener {
	public _IdentListener() {}

	@Override
	public void identRequest(String host, String text) {
		HashMap eventData = new HashMap();
		eventData.put("$event", "ident");
		eventData.put("$data", host + " " + text);
		eventData.put("$parms", text);

		// are we using the protocol dispatcher?

		dispatchEvent(eventData);
	}

	@Override
	public void setupListener() {
		IdentDaemon.getIdentDaemon().addIdentListener(this);
	}
}
