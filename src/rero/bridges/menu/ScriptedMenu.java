package rero.bridges.menu;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import rero.script.ScriptCore;
import sleep.engine.Block;
import sleep.runtime.ScriptInstance;

public class ScriptedMenu extends JMenu implements MenuListener, MenuBridgeParent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected LinkedList code;

	public ScriptedMenu(ScriptInstance _owner, String _label, Block _code) {
		if (_label.indexOf('&') > -1) {
			setText(_label.substring(0, _label.indexOf('&')) + _label.substring(_label.indexOf('&') + 1, _label.length()));
			setMnemonic(_label.charAt(_label.indexOf('&') + 1));
		} else {
			setText(_label);
		}

		code = new LinkedList();
		installCode(_owner, _code);

		addMenuListener(this);
	}

	public JPopupMenu getScriptedPopupMenu() {
		return new ScriptedPopupMenu(code);
	}

	public void installCode(ScriptInstance _owner, Block _code) {
		code.add(new CodeSnippet(_owner, _code));
	}

	public boolean isValidCode() {
		Iterator i = code.iterator();
		while (i.hasNext()) {
			CodeSnippet temp = (CodeSnippet) i.next();
			if (!temp.getOwner().isLoaded()) {
				i.remove();
			}
		}

		return code.size() > 0;
	}

	@Override
	public void menuSelected(MenuEvent e) {
		MenuBridge.SetParent(this);

		Iterator i = code.iterator();
		while (i.hasNext()) {
			CodeSnippet temp = (CodeSnippet) i.next();
			if (temp.getOwner().isLoaded()) {
				ScriptCore.runCode(temp.getOwner(), temp.getBlock(), ScriptedPopupMenu.getMenuData());
			}
		}

		MenuBridge.FinishParent();
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		removeAll();
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		removeAll();
	}
}
