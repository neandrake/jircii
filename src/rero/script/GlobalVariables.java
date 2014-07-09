/**
 * 
 *
 *
 **/

package rero.script;

import java.util.HashMap;

import sleep.interfaces.Variable;
import sleep.runtime.Scalar;

public class GlobalVariables implements Variable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected HashMap data = new HashMap();
	protected Variable alternateVariables = null;

	public void setOtherVariables(Variable _alternateVariables) {
		alternateVariables = _alternateVariables;
	}

	@Override
	public boolean scalarExists(String key) {
		if (alternateVariables != null && alternateVariables.scalarExists(key)) {
			return true;
		}

		return data.containsKey(key);
	}

	@Override
	public Scalar getScalar(String key) {
		if (alternateVariables != null && alternateVariables.scalarExists(key)) {
			return alternateVariables.getScalar(key);
		}

		return (Scalar) data.get(key);
	}

	@Override
	public Scalar putScalar(String key, Scalar value) {
		return (Scalar) data.put(key, value);
	}

	@Override
	public void removeScalar(String key) {
		data.remove(key);
	}

	@Override
	public Variable createLocalVariableContainer() // create our variable container using our hashmap as the base of it.
	{ // this way the user has access to all of the "local" variables for an
		return new LocalVariables(); // event. This should also do the $0 $1- $2-3 $-4 stuff
	}

	@Override
	public Variable createInternalVariableContainer() {
		return new GlobalVariables();
	}
}
