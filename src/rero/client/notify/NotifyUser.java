package rero.client.notify;

import java.util.HashMap;

import rero.client.Feature;
import rero.ircfw.interfaces.ChatListener;
import rero.ircfw.interfaces.FrameworkConstants;

public class NotifyUser extends Feature implements ChatListener, FrameworkConstants {
	protected String nickname;
	protected String address;

	protected long time;
	protected boolean signedon;

	/** a method to tell this object the user has signed off and to take appropriate action */
	public void signOff() {
		signedon = false;
		time = System.currentTimeMillis();

		getCapabilities().injectEvent(":" + nickname + "!" + address + " SIGNOFF :signed off");
	}

	public void signOn() {
		getCapabilities().addTemporaryListener(this);
		getCapabilities().sendln("USERHOST " + nickname);
	}

	/** returns the users nickname */
	public String getNickname() {
		return nickname;
	}

	/** returns the users address */
	public String getAddress() {
		return address;
	}

	/** is the user signed on or not */
	public boolean isSignedOn() {
		return signedon;
	}

	/** returns total amount of time user has been online (in seconds) */
	public long getTimeOnline() {
		return (System.currentTimeMillis() - time) / 1000;
	}

	public NotifyUser(String nick) {
		nickname = nick;
		signedon = false;
	}

	@Override
	public void init() {
		// no sweat..
	}

	@Override
	public int fireChatEvent(HashMap eventDescription) {
		address = (String) eventDescription.get("$address");

		signedon = true;
		time = System.currentTimeMillis();

		getCapabilities().injectEvent(":" + nickname + "!" + address + " SIGNON :signed on");

		return EVENT_HALT | REMOVE_LISTENER;
	}

	@Override
	public boolean isChatEvent(String eventId, HashMap eventDescription) {
		if (eventId.equals("302")) {
			String parms = (String) eventDescription.get("$nick");
			return parms.equals(nickname);
		}

		return false;
	}

	@Override
	public String toString() {
		return nickname;
	}
}
