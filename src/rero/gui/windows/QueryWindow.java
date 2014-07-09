package rero.gui.windows;

import javax.swing.ImageIcon;

import rero.client.Capabilities;
import rero.config.ClientState;

public class QueryWindow extends StatusWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String user; // user object.

	public QueryWindow(String _user) {
		user = _user;
	}

	@Override
	public ImageIcon getImageIcon() {
		if (icon == null) {
			icon = new ImageIcon(ClientState.getClientState().getResource("query.gif"));
		}

		return icon;
	}

	@Override
	public void installCapabilities(Capabilities c) {
		super.installCapabilities(c);
	}

	@Override
	public String getWindowType() {
		if (user.charAt(0) == '=') {
			return "chat";
		}

		return "query";
	}

	@Override
	public String getQuery() {
		return user;
	}

	@Override
	public void setName(String name) {
		user = name;
		super.setName(name);
	}

	@Override
	public String getName() {
		return user;
	}

	@Override
	public int compareWindowType() {
		return 3;
	}
}
