package rero.ircfw.data;

/* parses the /USERHOST reply...  so its somewhat sane to deal with. */

import java.util.HashMap;
import java.util.regex.Pattern;

import rero.ircfw.interfaces.FrameworkConstants;
import rero.util.StringParser;

public class UserHostParser extends DataEventAction implements FrameworkConstants {
	protected static Pattern userHostPattern = Pattern.compile("(.*?)=(.*)");

	@Override
	public boolean isEvent(HashMap data) {
		return "302".equals(data.get($EVENT$));
	}

	@Override
	public void process(HashMap data) {
		StringParser temp = new StringParser(data.get($PARMS$).toString(), userHostPattern);
		if (temp.matches()) {
			String address = temp.getParsedStrings()[1];
			address = address.trim();

			String nick = temp.getParsedStrings()[0];
			nick = nick.trim();

			if (address.charAt(0) == '+' || address.charAt(0) == '-') {
				address = address.substring(1, address.length());
			}

			if (nick.charAt(nick.length() - 1) == '*') {
				nick = nick.substring(0, nick.length() - 1);
			}

			data.put($NICK$, nick);
			data.put($ADDRESS$, address);
		}
	}
}
