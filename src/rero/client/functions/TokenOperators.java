package rero.client.functions;

import java.util.Stack;

import rero.client.Feature;
import rero.util.TokenizedString;
import sleep.bridges.BridgeUtilities;
import sleep.interfaces.Function;
import sleep.interfaces.Loadable;
import sleep.interfaces.Predicate;
import sleep.runtime.Scalar;
import sleep.runtime.ScriptInstance;
import sleep.runtime.SleepUtils;

public class TokenOperators extends Feature implements Loadable {
	@Override
	public void init() {
		getCapabilities().getScriptCore().addBridge(this);
	}

	@Override
	public void scriptLoaded(ScriptInstance script) {
		script.getScriptEnvironment().getEnvironment().put("&tokenize", new tokenize());
		script.getScriptEnvironment().getEnvironment().put("&getToken", new getToken());
		script.getScriptEnvironment().getEnvironment().put("&getTokenFrom", new getTokenFrom());
		script.getScriptEnvironment().getEnvironment().put("&getTokenTo", new getTokenTo());
		script.getScriptEnvironment().getEnvironment().put("&getTokenRange", new getTokenRange());

		script.getScriptEnvironment().getEnvironment().put("&getAllTokens", new getAllTokens());

		script.getScriptEnvironment().getEnvironment().put("&getTotalTokens", new getTotalTokens());
		script.getScriptEnvironment().getEnvironment().put("&findToken", new findToken());

		script.getScriptEnvironment().getEnvironment().put("istoken", new isToken());
	}

	@Override
	public void scriptUnloaded(ScriptInstance script) {}

	private static TokenizedString extractTokens(Stack locals) {
		return (TokenizedString) BridgeUtilities.getObject(locals);
	}

	private static class tokenize implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			String text = BridgeUtilities.getString(locals, "");
			String delim = BridgeUtilities.getString(locals, " ");

			return SleepUtils.getScalar(new TokenizedString(text, delim));
		}
	}

	private static class getToken implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			TokenizedString temp = extractTokens(locals);
			int x = BridgeUtilities.getInt(locals, 0);

			return SleepUtils.getScalar(temp.getToken(x));
		}
	}

	private static class getTokenFrom implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			TokenizedString temp = extractTokens(locals);
			int x = BridgeUtilities.getInt(locals, 0);

			return SleepUtils.getScalar(temp.getTokenFrom(x));
		}
	}

	private static class getTokenTo implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			TokenizedString temp = extractTokens(locals);
			int x = BridgeUtilities.getInt(locals, 0);

			return SleepUtils.getScalar(temp.getTokenTo(x));
		}
	}

	private static class getTokenRange implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			TokenizedString temp = extractTokens(locals);
			int x = BridgeUtilities.getInt(locals, 0);
			int y = BridgeUtilities.getInt(locals, 0);

			return SleepUtils.getScalar(temp.getTokenRange(x, y));
		}
	}

	private static class getTotalTokens implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			TokenizedString temp = extractTokens(locals);
			return SleepUtils.getScalar(temp.getTotalTokens());
		}
	}

	private static class findToken implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			TokenizedString temp = extractTokens(locals);
			String findme = BridgeUtilities.getString(locals, "");

			return SleepUtils.getScalar(temp.findToken(findme));
		}
	}

	private static class getAllTokens implements Function {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar evaluate(String f, ScriptInstance si, Stack locals) {
			TokenizedString temp = extractTokens(locals);
			Scalar rv = SleepUtils.getArrayScalar();

			for (int x = 0; x < temp.getTotalTokens(); x++) {
				rv.getArray().push(SleepUtils.getScalar(temp.getToken(x)));
			}

			return rv;
		}
	}

	private static class isToken implements Predicate {
		@Override
		public boolean decide(String f, ScriptInstance si, Stack locals) {
			TokenizedString right = extractTokens(locals);
			String left = BridgeUtilities.getString(locals, "");

			return right.isToken(left);
		}
	}
}
