package rero.client.server;

import java.util.HashMap;

import rero.ircfw.interfaces.ChatListener;

public class UpdateIAL implements ChatListener {
	@Override
	public int fireChatEvent(HashMap eventDescription) {
		String event = (String) eventDescription.get("$event");

		if (event.equals("315")) {
			return REMOVE_LISTENER | EVENT_HALT; // end of a /who reply we got what we wanted
		} else if (event.equals("352")) {
			return EVENT_HALT; // we're still getting the reply, so ignore it for now.
		}

		return EVENT_DONE;
	}

	@Override
	public boolean isChatEvent(String event, HashMap eventDescription) {
		if (event.equals("315")) {
			return true;
		} /* End of /WHO reply */
		if (event.equals("352")) {
			return true;
		} /* /WHO reply */

		return false;
	}
}
