package rero.client.functions;

import java.util.Stack;

import rero.client.Feature;
import rero.util.TimerListener;
import sleep.bridges.BridgeUtilities;
import sleep.bridges.SleepClosure;
import sleep.interfaces.Function;
import sleep.interfaces.Loadable;
import sleep.runtime.Scalar;
import sleep.runtime.ScriptInstance;
import sleep.runtime.SleepUtils;

public class TimerOperators extends Feature implements Loadable {
	@Override
	public void init() {
		getCapabilities().getScriptCore().addBridge(this);
	}

	@Override
	public void scriptLoaded(ScriptInstance script) {
		script.getScriptEnvironment().getEnvironment().put("&addTimer", new addTimer());
		script.getScriptEnvironment().getEnvironment().put("&stopTimer", new stopTimer());
		script.getScriptEnvironment().getEnvironment().put("&setTimerResolution", new setResolution());
	}

	@Override
	public void scriptUnloaded(ScriptInstance script) {}

	private class addTimer implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			int repeats = -1;

			SleepClosure func = BridgeUtilities.getFunction(locals, si);
			int time = BridgeUtilities.getInt(locals);

			if (!locals.isEmpty()) {
				repeats = BridgeUtilities.getInt(locals);
			}

			ScriptedTimer timer;

			if (!locals.isEmpty()) {
				timer = new ScriptedTimer(func, si, BridgeUtilities.getScalar(locals));
			} else {
				timer = new ScriptedTimer(func, si, null);
			}

			getCapabilities().getTimer().addTimer(timer, time, repeats);

			return SleepUtils.getScalar(timer);
		}
	}

	private class stopTimer implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			ScriptedTimer timer = (ScriptedTimer) BridgeUtilities.getObject(locals);

			getCapabilities().getTimer().stopTimer(timer);

			return SleepUtils.getEmptyScalar();
		}
	}

	private class setResolution implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			getCapabilities().getTimer().setResolution(BridgeUtilities.getInt(locals));
			return SleepUtils.getEmptyScalar();
		}
	}

	private static class ScriptedTimer implements TimerListener {
		protected ScriptInstance si;
		protected SleepClosure func;
		protected Scalar args;

		public ScriptedTimer(SleepClosure f, ScriptInstance script, Scalar a) {
			func = f;
			si = script;
			args = a;
		}

		@Override
		public void timerExecute() {
			if (si == null || !si.isLoaded()) {
				args = null;
				si = null;
				func = null;
				return;
			}

			Stack arg_stack = new Stack();
			if (args != null) {
				arg_stack.push(args);
			}

			func.callClosure("timer", si, arg_stack);
		}
	}
}
