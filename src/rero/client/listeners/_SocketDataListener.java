package rero.client.listeners;

import java.util.HashMap;

import rero.bridges.event.ScriptedEventListener;
import rero.ircfw.interfaces.ChatListener;
import rero.net.SocketConnection;
import rero.net.SocketEvent;
import rero.net.interfaces.SocketDataListener;

public class _SocketDataListener extends ScriptedEventListener implements SocketDataListener {
	protected SocketConnection socket;

	public _SocketDataListener(SocketConnection _socket) {
		socket = _socket;
	}

	@Override
	public void socketDataRead(SocketEvent ev) {
		HashMap eventData = new HashMap();
		eventData.put("$parms", ev.message);
		eventData.put("$data", ev.data.hostname + " " + ev.message);
		eventData.put("$server", ev.data.hostname);
		eventData.put("$port", ev.data.port + "");

		if ((dispatchEvent(eventData) & ChatListener.EVENT_HALT) == ChatListener.EVENT_HALT) {
			ev.valid = false;
		}
	}

	@Override
	public void setupListener() {
		socket.addSocketDataListener(this);
	}
}
