package rero.gui.script;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Stack;

import jircii.app.Application;
import rero.client.Capabilities;
import rero.gui.BuiltInLogger;
import rero.gui.IRCSession;
import rero.gui.SessionManager;
import rero.util.ClientUtils;
import sleep.bridges.BridgeUtilities;
import sleep.interfaces.Function;
import sleep.interfaces.Loadable;
import sleep.runtime.Scalar;
import sleep.runtime.ScriptInstance;
import sleep.runtime.SleepUtils;

public class SessionOperators implements Loadable {
	protected IRCSession session;

	public SessionOperators(IRCSession _session) {
		session = _session;
	}

	private Capabilities getCapabilities() {
		return session.getCapabilities();
	}

	@Override
	public void scriptLoaded(ScriptInstance script) {
		Hashtable env = script.getScriptEnvironment().getEnvironment();

		SessionUI manipulate = new SessionUI();

		env.put("&getSessionId", new getSessionId());
		env.put("&getActiveSessionId", new getActiveSessionId());
		env.put("&activateSession", manipulate);
		env.put("&getTotalSessions", new getTotalSessions());
		env.put("&setSessionColor", manipulate);
		env.put("&getSessionColor", new getSessionColor());
		env.put("&getSessionText", new getSessionText());
		env.put("&setSessionText", manipulate);

		env.put("&callInSession", new callCommandInSession());

		env.put("&getServerHost", new getServerName());
		env.put("&getServerPort", new getServerPort());
		env.put("&getServerNetwork", new getServerNetwork());
		env.put("&getServerPassword", new getServerPassword());
		env.put("&isServerConnected", new isServerConnected());
		env.put("&isServerSecure", new isServerSecure());

		env.put("&getLogFile", new getLogFile());
		env.put("&logMessage", new logMessage());
	}

	private class getLogFile implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			return SleepUtils.getScalar(BuiltInLogger.getLogFileName(getCapabilities().getSocketConnection().getSocketInformation(), BridgeUtilities.getString(locals, "%STATUS%")));
		}
	}

	private class logMessage implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			getCapabilities().getUserInterface().logMessage(BridgeUtilities.getString(locals, "%STATUS%"), BridgeUtilities.getString(locals, ""));
			return SleepUtils.getEmptyScalar();
		}
	}

	private class getServerName implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			return SleepUtils.getScalar(getCapabilities().getSocketConnection().getSocketInformation().hostname);
		}
	}

	private class getServerPort implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			return SleepUtils.getScalar(getCapabilities().getSocketConnection().getSocketInformation().port);
		}
	}

	private class getServerNetwork implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			return SleepUtils.getScalar(getCapabilities().getSocketConnection().getSocketInformation().network);
		}
	}

	private class getServerPassword implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			return SleepUtils.getScalar(getCapabilities().getSocketConnection().getSocketInformation().password);
		}
	}

	private class isServerConnected implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			if (getCapabilities().getSocketConnection().getSocketInformation().isConnected) {
				return SleepUtils.getScalar("true");
			}

			return SleepUtils.getEmptyScalar();
		}
	}

	private class isServerSecure implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			if (getCapabilities().getSocketConnection().getSocketInformation().isSecure) {
				return SleepUtils.getScalar("true");
			}

			return SleepUtils.getEmptyScalar();
		}
	}

	@Override
	public void scriptUnloaded(ScriptInstance script) {}

	private class getSessionText implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			SessionManager sm = Application.getInstance().getWindow().getSessionManager();

			return SleepUtils.getScalar(sm.getTitleAt(BridgeUtilities.getInt(locals, sm.getIndexFor(getCapabilities()))));
		}
	}

	private class callCommandInSession implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			SessionManager sm = Application.getInstance().getWindow().getSessionManager();

			sm.getSpecificSession(BridgeUtilities.getInt(locals, 0)).executeCommand(locals.pop().toString());
			return SleepUtils.getEmptyScalar();
		}
	}

	private class getSessionColor implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			SessionManager sm = Application.getInstance().getWindow().getSessionManager();

			return SleepUtils.getScalar(sm.getForegroundAt(BridgeUtilities.getInt(locals, sm.getIndexFor(getCapabilities()))).getRGB());
		}
	}

	private class getTotalSessions implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			SessionManager sm = Application.getInstance().getWindow().getSessionManager();

			return SleepUtils.getScalar(sm.getTabCount());
		}
	}

	private class getSessionId implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			SessionManager sm = Application.getInstance().getWindow().getSessionManager();

			return SleepUtils.getScalar(sm.getIndexFor(getCapabilities()));
		}
	}

	private class getActiveSessionId implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			SessionManager sm = Application.getInstance().getWindow().getSessionManager();

			return SleepUtils.getScalar(sm.getSelectedIndex());
		}
	}

	private class SessionUI implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(final String f, ScriptInstance si, Stack locals) {
			Scalar value = (Scalar) locals.pop();
			final int asInt = value.intValue();
			final String asString = value.toString();

			ClientUtils.invokeLater(new Runnable() {
				@Override
				public void run() {
					SessionManager sm = Application.getInstance().getWindow().getSessionManager();

					if (f.equals("&activateSession")) {
						sm.setSelectedIndex(asInt);
					}

					if (f.equals("&setSessionColor")) {
						sm.setForegroundAt(sm.getIndexFor(getCapabilities()), Color.decode(asString));
					}

					if (f.equals("&setSessionText")) {
						sm.setTitleAt(sm.getIndexFor(getCapabilities()), asString);
					}
				}
			});

			return SleepUtils.getEmptyScalar();
		}
	}
}
