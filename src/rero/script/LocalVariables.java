/*
   SLEEP - Simple Language for Environment Extension Purposes
 .---------------------------.
 | sleep.interfaces.Variable |________________________________________________
 |                                                                            |
   Author: Raphael Mudge (rsmudge@mtu.edu)
           http://www.csl.mtu.edu/~rsmudge/
 
   Description: An interface to allow management of scalars itself.  Sleep only
     allows one variable interface to be used per session.  But this is a good 
     way to create global variables and such in Sleep.
 
   Documentation:
 
 * This software is distributed under the artistic license, see license.txt
     for more information. *
 
 |____________________________________________________________________________|
 */

package rero.script;

import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import rero.ircfw.interfaces.FrameworkConstants; // we take advantage of the $parms constant.
import rero.util.StringParser; // gotta love the regex parser. *uNF*
import rero.util.TokenizedString;
import sleep.engine.ObjectUtilities;
import sleep.interfaces.Variable;
import sleep.runtime.Scalar;
import sleep.runtime.SleepUtils;

public class LocalVariables implements Variable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected HashMap data = new HashMap();

	protected static Pattern rangePattern = Pattern.compile("\\$(\\d+)\\-(\\d+)"); // \$(\d+)\-(\d+)
	protected static Pattern rangeFromPattern = Pattern.compile("\\$(\\d+)\\-"); // \$(\d+)\-
	protected static Pattern rangeToPattern = Pattern.compile("\\$\\-(\\d+)"); // \$\-(\d+)
	protected static Pattern normalPattern = Pattern.compile("\\$(\\d+)"); // \$(\d+)

	protected String parmsValue = null;

	public void setDataSource(HashMap _data) {
		//
		// set another hashmap as the data source. We won't alter/remove values already in place
		// but we may add to the hashmap
		//
		Iterator iter = _data.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next().toString();

			if (key.equals(FrameworkConstants.$DATA$)) {
				parmsValue = (String) _data.get(key);
			}

			data.put(key, ObjectUtilities.BuildScalar(true, _data.get(key)));
		}
	}

	@Override
	public boolean scalarExists(String key) {
		if (data.containsKey(key) || "%localData".equals(key)) {
			return true;
		}

		//
		// check for existence of our "dynamic" variables
		//
		return parmsValue != null && ((Character.isDigit(key.charAt(1)) || key.charAt(1) == '-'));
	}

	@Override
	public Scalar getScalar(String key) {
		if ("%localData".equals(key)) {
			return SleepUtils.getHashWrapper(data);
		}

		Scalar temp = (Scalar) data.get(key);

		if (temp == null && parmsValue != null && (Character.isDigit(key.charAt(1)) || key.charAt(1) == '-')) {
			StringParser parser;
			int begin, end;

			TokenizedString tokenizer = new TokenizedString(parmsValue, " ");

			//
			// look for $n-m pattern and return tokens n to m in a sequence
			//
			parser = new StringParser(key, rangePattern);
			if (parser.matches()) {
				begin = Integer.parseInt(parser.getParsedStrings()[0]);
				end = Integer.parseInt(parser.getParsedStrings()[1]);

				return SleepUtils.getScalar(tokenizer.getTokenRange(begin, end));
			}

			//
			// look for $n- pattern and return tokens n on up in a sequence
			//
			parser = new StringParser(key, rangeToPattern);
			if (parser.matches()) {
				begin = Integer.parseInt(parser.getParsedStrings()[0]);

				return SleepUtils.getScalar(tokenizer.getTokenTo(begin));
			}

			//
			// look for $-m pattern and return tokens up to m in a sequence
			//
			parser = new StringParser(key, rangeFromPattern);
			if (parser.matches()) {
				begin = Integer.parseInt(parser.getParsedStrings()[0]);

				return SleepUtils.getScalar(tokenizer.getTokenFrom(begin));
			}

			//
			// look for $n pattern and return token n.
			//
			parser = new StringParser(key, normalPattern);
			if (parser.matches()) {
				begin = Integer.parseInt(parser.getParsedStrings()[0]);

				return SleepUtils.getScalar(tokenizer.getToken(begin));
			}
		}

		return temp;
	}

	@Override
	public Scalar putScalar(String key, Scalar value) {
		return (Scalar) data.put(key, value);
	}

	@Override
	public void removeScalar(String key) {
		// for jIRC scripts we don't want the locals to be "alterable" per se.
	}

	//
	// These two functions are only called in the global instance of the Variable class
	//
	@Override
	public Variable createLocalVariableContainer() {
		return null;
	}

	@Override
	public Variable createInternalVariableContainer() {
		return null;
	}
}
