package rero.client.functions;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import rero.client.Feature;
import rero.ircfw.Channel;
import rero.ircfw.InternalDataList;
import rero.ircfw.User;
import rero.util.StringUtils;
import sleep.interfaces.Function;
import sleep.interfaces.Loadable;
import sleep.interfaces.Predicate;
import sleep.runtime.Scalar;
import sleep.runtime.ScriptInstance;
import sleep.runtime.SleepUtils;

public class UserOperators extends Feature implements Predicate, Function, Loadable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected InternalDataList data;

	@Override
	public void init() {
		getCapabilities().getScriptCore().addBridge(this);

		data = (InternalDataList) getCapabilities().getDataStructure("clientInformation");
	}

	@Override
	public void scriptLoaded(ScriptInstance script) {
		String[] contents = new String[] { "&searchAddressList", "&getChannels", "&getAddress", "&getIdleTime", "-isidle", };

		for (int x = 0; x < contents.length; x++) {
			script.getScriptEnvironment().getEnvironment().put(contents[x], this);
		}
	}

	@Override
	public void scriptUnloaded(ScriptInstance script) {}

	@Override
	public Scalar evaluate(String function, ScriptInstance script, Stack locals) {
		User user = null;

		if (function.equals("&searchAddressList")) {
			if (locals.size() != 1) {
				return null;
			}

			String pattern = ((Scalar) locals.pop()).getValue().toString();

			Set rv = new HashSet();

			Iterator i = data.getAllUsers().iterator();
			while (i.hasNext()) {
				User temp = (User) i.next();
				if (StringUtils.iswm(pattern, temp.getFullAddress())) {
					rv.add(temp.getNick());
				}
			}
			return SleepUtils.getArrayWrapper(rv);
		}

		if (locals.size() != 1) {
			user = data.getMyUser();
		} else {
			String nick = ((Scalar) locals.pop()).getValue().toString();
			if (data.isUser(nick)) {
				user = data.getUser(nick);
			} else {
				return SleepUtils.getEmptyScalar();
			}
		}

		if (function.equals("&getChannels")) {
			Set rv = new HashSet();
			Iterator i = user.getChannels().iterator();
			while (i.hasNext()) {
				rv.add(((Channel) i.next()).getName());
			}
			return SleepUtils.getArrayWrapper(rv);
		}

		if (function.equals("&getAddress")) {
			return SleepUtils.getScalar(user.getAddress());
		}

		if (function.equals("&getIdleTime")) {
			return SleepUtils.getScalar(user.getIdleTime());
		}

		return null;
	}

	@Override
	public boolean decide(String predicate, ScriptInstance script, Stack terms) {
		if (terms.size() != 1) {
			return false;
		}

		String nick = ((Scalar) terms.pop()).getValue().toString();

		if (predicate.equals("-isidle") && data.isUser(nick)) {
			return (data.getUser(nick).getIdleTime() > (60 * 5));
		}

		return false;
	}
}
