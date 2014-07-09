package rero.bridges.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import rero.ircfw.interfaces.ChatListener;

public abstract class EventChatListener implements ChatListener {
	protected List listeners = new LinkedList();
	protected int storedFlags = 0; // no default flags.

	public void setFlags(int flag) {
		storedFlags = storedFlags | flag;
	}

	public void addListener(CodeSnippet code) {
		listeners.add(code);
	}

	@Override
	public int fireChatEvent(HashMap eventDescription) {
		Iterator i = listeners.iterator();
		while (i.hasNext()) {
			int value = ((CodeSnippet) i.next()).execute(eventDescription);

			if ((value & ChatListener.REMOVE_LISTENER) == ChatListener.REMOVE_LISTENER) {
				i.remove();
			}

			if ((value & ChatListener.EVENT_HALT) == ChatListener.EVENT_HALT) {
				return ChatListener.EVENT_HALT | storedFlags;
			}
		}

		return ChatListener.EVENT_DONE | storedFlags;
	}

	@Override
	public abstract boolean isChatEvent(String eventId, HashMap eventDescription);
}
