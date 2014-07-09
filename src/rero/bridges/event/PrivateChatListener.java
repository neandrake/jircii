package rero.bridges.event;

import java.util.HashMap;

import rero.ircfw.interfaces.FrameworkConstants;
import rero.util.ClientUtils;

public class PrivateChatListener extends EventChatListener {
	protected String eventId;

	public PrivateChatListener(String id) {
		eventId = id.toUpperCase();
	}

	@Override
	public boolean isChatEvent(String _eventId, HashMap eventDescription) {
		String target = (String) eventDescription.get(FrameworkConstants.$TARGET$);
		return eventId.equals(_eventId.toUpperCase()) && !ClientUtils.isChannel(target);
	}
}
