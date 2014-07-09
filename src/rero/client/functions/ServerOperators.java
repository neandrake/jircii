package rero.client.functions;

import java.util.Stack;

import rero.client.Feature;
import rero.dialogs.server.Server;
import rero.dialogs.server.ServerData;
import sleep.bridges.BridgeUtilities;
import sleep.interfaces.Function;
import sleep.interfaces.Loadable;
import sleep.runtime.Scalar;
import sleep.runtime.ScriptInstance;
import sleep.runtime.SleepUtils;

public class ServerOperators extends Feature implements Loadable {
	@Override
	public void init() {
		getCapabilities().getScriptCore().addBridge(this);
	}

	@Override
	public void scriptLoaded(ScriptInstance script) {
		script.getScriptEnvironment().getEnvironment().put("&getAllServers", new getAllServers());
		script.getScriptEnvironment().getEnvironment().put("&getAllNetworks", new getAllNetworks());

		script.getScriptEnvironment().getEnvironment().put("&getServerInfo", new getServerInfo());
		script.getScriptEnvironment().getEnvironment().put("&getServersForNetwork", new getServersForNetwork());

		script.getScriptEnvironment().getEnvironment().put("&serverInfoHost", new serverInfoHost());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoPortRange", new serverInfoPorts());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoNetwork", new serverInfoNetwork());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoIsSecure", new serverInfoIsSecure());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoPassword", new serverInfoPassword());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoDescription", new serverInfoDescription());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoConnectPort", new serverInfoConnectPort());
		script.getScriptEnvironment().getEnvironment().put("&serverInfoCommand", new serverInfoCommand());
	}

	@Override
	public void scriptUnloaded(ScriptInstance script) {}

	private static class getAllServers implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			return SleepUtils.getArrayWrapper(ServerData.getServerData().getAllServers());
		}
	}

	private static class getAllNetworks implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			return SleepUtils.getArrayWrapper(ServerData.getServerData().getGroups());
		}
	}

	private static class getServerInfo implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			String temp = BridgeUtilities.getString(locals, "");
			if (temp == null) {
				return SleepUtils.getEmptyScalar();
			}
			return SleepUtils.getScalar(ServerData.getServerData().getServerByName(temp).toString());
		}
	}

	private static class getServersForNetwork implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			String temp = BridgeUtilities.getString(locals, "");
			if (temp == null) {
				return SleepUtils.getEmptyScalar();
			}
			return SleepUtils.getArrayWrapper(ServerData.getServerData().getGroup(temp).getServers());
		}
	}

	private static class serverInfoPassword implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			Server temp = Server.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) {
				return SleepUtils.getEmptyScalar();
			}
			return SleepUtils.getScalar(temp.getPassword());
		}
	}

	private static class serverInfoDescription implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			Server temp = Server.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) {
				return SleepUtils.getEmptyScalar();
			}
			return SleepUtils.getScalar(temp.getDescription());
		}
	}

	private static class serverInfoConnectPort implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			Server temp = Server.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) {
				return SleepUtils.getEmptyScalar();
			}
			return SleepUtils.getScalar(temp.getConnectPort());
		}
	}

	private static class serverInfoCommand implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			Server temp = Server.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) {
				return SleepUtils.getEmptyScalar();
			}
			return SleepUtils.getScalar(temp.getCommand());
		}
	}

	private static class serverInfoHost implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			Server temp = Server.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) {
				return SleepUtils.getEmptyScalar();
			}
			return SleepUtils.getScalar(temp.getHost());
		}
	}

	private static class serverInfoPorts implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			Server temp = Server.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) {
				return SleepUtils.getEmptyScalar();
			}
			return SleepUtils.getScalar(temp.getPorts());
		}
	}

	private static class serverInfoNetwork implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			Server temp = Server.decode(BridgeUtilities.getString(locals, ""));

			if (temp == null) {
				return SleepUtils.getEmptyScalar();
			}

			return SleepUtils.getScalar(temp.getNetwork());
		}
	}

	private static class serverInfoIsSecure implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			Server temp = Server.decode(BridgeUtilities.getString(locals, ""));
			if (temp == null) {
				return SleepUtils.getEmptyScalar();
			}

			if (temp.isSecure()) {
				return SleepUtils.getScalar(1);
			}

			return SleepUtils.getEmptyScalar();
		}
	}
}
