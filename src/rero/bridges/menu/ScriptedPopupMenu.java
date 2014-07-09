package rero.bridges.menu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import rero.script.ScriptCore;
import sleep.engine.Block;
import sleep.runtime.ScriptInstance;

public class ScriptedPopupMenu extends JPopupMenu implements PopupMenuListener, MenuBridgeParent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//
	// Static code for keeping track of current menu data set (truth is again that only one popup menu will be showing at
	// once)
	//
	protected static HashMap MenuData = null;

	public static void SetMenuData(HashMap h) {
		if (h != null) {
			MenuData = h;
		}
	}

	public static HashMap getMenuData() {
		return MenuData;
	}

	public static void FinishMenuData() {
		MenuData = null;
	}

	protected LinkedList code;

	public ScriptedPopupMenu(ScriptInstance _owner, Block _code) {
		code = new LinkedList();
		installCode(_owner, _code);

		addPopupMenuListener(this);
	}

	public ScriptedPopupMenu(LinkedList _code) {
		code = _code;
		addPopupMenuListener(this);
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
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		MenuBridge.SetParent(this);

		Iterator i = code.iterator();
		while (i.hasNext()) {
			CodeSnippet temp = (CodeSnippet) i.next();
			if (temp.getOwner().isLoaded()) {
				ScriptCore.runCode(temp.getOwner(), temp.getBlock(), MenuData);
			} else {
				i.remove();
			}
		}

		MenuBridge.FinishParent();
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		removeAll();
	}

	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {
		removeAll();
	}
}
