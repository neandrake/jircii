package rero.bridges.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import jircii.app.Application;
import rero.client.user.UserHandler;
import sleep.runtime.ScriptInstance;

public class SimpleItem extends JMenuItem implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ScriptInstance owner;
	protected String code;

	public SimpleItem(ScriptInstance _owner, String _label, String _code) {
		if (_label.indexOf('&') > -1) {
			setText(_label.substring(0, _label.indexOf('&')) + _label.substring(_label.indexOf('&') + 1, _label.length()));
			setMnemonic(_label.charAt(_label.indexOf('&') + 1));
		} else {
			setText(_label);
		}

		owner = _owner;
		code = _code;

		if (code.charAt(0) != '/') {
			code = '/' + code;
		}

		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		((UserHandler) Application.getInstance().getWindow().getSessionManager().getActiveSession().getCapabilities().getDataStructure("commands")).processCommand(code);
	}
}
