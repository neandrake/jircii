package rero.bridges.event;

import java.util.HashMap;

import rero.script.LocalVariables;
import sleep.engine.Block;
import sleep.runtime.Scalar;
import sleep.runtime.ScriptEnvironment;
import sleep.runtime.ScriptInstance;
import sleep.runtime.SleepUtils;

public class CodeSnippet {
	protected ScriptEnvironment environment;
	protected ScriptInstance si;
	protected Block code;

	public CodeSnippet(Block c, ScriptEnvironment e) {
		code = c;
		environment = e;

		si = environment.getScriptInstance();
	}

	public int getLineNumber() {
		return code.getApproximateLineNumber();
	}

	public boolean isValid() {
		return si.isLoaded();
	}

	public int execute(HashMap eventData) {
		//
		// if the script associated with this listener is no longer valid (i.e. loaded) then umm delete the listener
		//
		if (!isValid()) {
			return rero.ircfw.interfaces.ChatListener.REMOVE_LISTENER;
		}

		Scalar rv;

		synchronized (environment.getScriptVariables()) {
			environment.getScriptVariables().pushLocalLevel();

			LocalVariables locals = (LocalVariables) environment.getScriptVariables().getLocalVariables();
			locals.setDataSource(eventData);

			rv = SleepUtils.runCode(code, environment);

			environment.getScriptVariables().popLocalLevel();
		}

		if (rv == null) {
			return 0;
		}

		return rv.getValue().intValue();
	}
}
