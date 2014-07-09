package rero.gui.script;

import java.util.LinkedList;
import java.util.Stack;

import rero.config.ClientState;
import rero.gui.IRCSession;
import rero.gui.windows.ScriptedListDialog;
import sleep.bridges.BridgeUtilities;
import sleep.interfaces.Function;
import sleep.interfaces.Loadable;
import sleep.runtime.Scalar;
import sleep.runtime.ScriptInstance;
import sleep.runtime.SleepUtils;

public class UIOperators implements Function, Loadable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected IRCSession session;

	public UIOperators(IRCSession _session) {
		session = _session;
	}

	@Override
	public void scriptLoaded(ScriptInstance script) {
		String[] contents = new String[] { "&refreshMenubar", "&showOptionDialog", "&showHelpDialog", "&showAboutDialog", "&showSearchDialog" };

		for (int x = 0; x < contents.length; x++) {
			script.getScriptEnvironment().getEnvironment().put(contents[x], this);
		}

		script.getScriptEnvironment().getEnvironment().put("&showSortedList", new openSortedWindow());
		script.getScriptEnvironment().getEnvironment().put("&refreshData", new refreshData());
	}

	private class openSortedWindow implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String name, ScriptInstance script, Stack locals) {
			if (locals.size() < 3) {
				return SleepUtils.getEmptyScalar();
			}

			String title = locals.pop().toString();
			String hook = locals.pop().toString();
			Object data = locals.pop();

			return SleepUtils.getScalar(session.createSortedWindow(title, hook, data, extractData(locals)));
		}
	}

	private static LinkedList extractData(Stack locals) {
		LinkedList data = new LinkedList();

		while (!locals.isEmpty()) {
			data.add(locals.pop().toString());
		}

		return data;
	}

	private static class refreshData implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String name, ScriptInstance script, Stack locals) {
			ScriptedListDialog dialog = (ScriptedListDialog) BridgeUtilities.getObject(locals);

			dialog.refreshData();

			return SleepUtils.getEmptyScalar();
		}
	}

	@Override
	public void scriptUnloaded(ScriptInstance script) {}

	@Override
	public Scalar evaluate(String function, ScriptInstance script, Stack locals) {
		if (function.equals("&refreshMenubar")) {
			ClientState.getClientState().fireChange("loaded.scripts", null);
		} else if (function.equals("&showOptionDialog")) {
			session.getCapabilities().getGlobalCapabilities().showOptionDialog(BridgeUtilities.getString(locals, ""));
		} else if (function.equals("&showHelpDialog")) {
			session.getCapabilities().getGlobalCapabilities().showHelpDialog(BridgeUtilities.getString(locals, ""));
		} else if (function.equals("&showAboutDialog")) {
			session.getCapabilities().getGlobalCapabilities().showAboutDialog();
		} else if (function.equals("&showSearchDialog")) {
			session.getCapabilities().getUserInterface().showSearchDialog(BridgeUtilities.getString(locals, "%STATUS%"));
		}

		return SleepUtils.getEmptyScalar();
	}
}
