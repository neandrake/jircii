package rero.client.listeners;

import rero.bridges.event.EventBridge;
import rero.bridges.event.ScriptedEventListener;
import rero.client.Feature;

/**
 * setup and register the internal client events... may as well keep it in one place. I want the frameworks to be isolated
 * from eachother as best as possible so that is why this stuff is in the client package. The client package is more or less
 * for tieing stuff together
 */

public class InternalEvents extends Feature {
	@Override
	public void init() {
		EventBridge bridge = (EventBridge) getCapabilities().getDataStructure("eventBridge");

		ScriptedEventListener connect = new _SocketStatusListener(getCapabilities().getSocketConnection(), true);
		ScriptedEventListener disconnect = new _SocketStatusListener(getCapabilities().getSocketConnection(), false);
		ScriptedEventListener raw = new _SocketDataListener(getCapabilities().getSocketConnection());
		ScriptedEventListener ident = new _IdentListener();

		bridge.registerEvent("connect", connect);
		bridge.registerEvent("disconnect", disconnect);
		bridge.registerEvent("raw", raw);
		bridge.registerEvent("ident", ident);
	}
}
