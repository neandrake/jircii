package rero.gui;

import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jircii.app.Application;
import rero.bridges.menu.MenuBridge;
import rero.config.ClientDefaults;
import rero.config.ClientState;
import rero.config.ClientStateListener;
import rero.config.StringList;
import rero.dialogs.server.Server;
import rero.dialogs.server.ServerData;
import rero.gui.toolkit.MinimalTabUI;
import rero.gui.windows.ClientWindowEvent;
import rero.gui.windows.ClientWindowListener;
import rero.net.SocketEvent;
import rero.net.interfaces.SocketStatusListener;
import rero.test.QuickConnect;

public class SessionManager extends JTabbedPane implements ClientWindowListener, SocketStatusListener, ChangeListener, ClientStateListener {
	private static final long serialVersionUID = 1L;

	protected HashMap sessions = new HashMap();
	protected IRCSession active = null;
	protected JFrame frame = null;
	protected MenuBridge bridge = null;
	protected PopupManager popups = null;
	protected KeyBindings keyb = null;

	protected String lastscript = "";
	protected long lastref = 0;

	@Override
	public void propertyChanged(String property, String parameter) {
		bridge = (MenuBridge) getActiveSession().getCapabilities().getDataStructure("menuBridge");
		rero.util.ClientUtils.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (ClientState.getClientState().isOption("ui.showbar", ClientDefaults.ui_showbar)) {
					JMenuBar menu = new JMenuBar();
					bridge.installMenubar(menu);
					frame.setJMenuBar(menu);
				} else {
					frame.setJMenuBar(null);
				}
				frame.validate();
			}
		});
	}

	@Override
	public void stateChanged(ChangeEvent ev) {
		active = getSession(getSelectedComponent());

		if (getActiveSession() != null) {
			propertyChanged(null, null);
			// MenuBridge temp = (MenuBridge)getActiveSession().getCapabilities().getDataStructure("menuBridge");
			// temp.installMenubar(menu);
			GraphicalToolbar.stateChanged(); // CHEATING!!!!

			getActiveSession().getCapabilities().dispatchEvent(switchEventHashMap);
		}
	}

	protected HashMap switchEventHashMap = new HashMap();

	public SessionManager(JFrame _frame) {
		//
		// make the tabs scrollable, show them on the bottom.
		//
		// super(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
		super();

		switchEventHashMap.put("$event", "session");

		setTabPlacement(SwingConstants.BOTTOM);
		// setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT); - swing bug

		keyb = new KeyBindings(this);
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyb);

		addChangeListener(this);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// do this before doing anything else... dig?
				if (!ClientState.getClientState().isOption("ui.showtabs", ClientDefaults.ui_showtabs)) {
					setUI(new MinimalTabUI());
				}

				StringList temp = ClientState.getClientState().getStringList("auto.connect");

				if (QuickConnect.IsQuickConnect()) {
					addSession();
					getSession(getComponentAt(0)).executeCommand(QuickConnect.GetInformation().getConnectCommand());
				} else if (temp.getList().size() == 0) {
					addSession();
				} else {
					ServerData sdata = ServerData.getServerData();

					int y = 0;
					Iterator i = temp.getList().iterator();
					while (i.hasNext()) {
						Server stemp = sdata.getServerByName(i.next().toString());

						if (stemp != null) {
							addSession();
							getSession(getComponentAt(y)).executeCommand(stemp.getCommand());
							y++;
						}
					}
				}

				GraphicalToolbar.startup(); // the toolbar knows how to set itself up using the global stuff available
			}
		});

		frame = _frame;

		if (ClientState.getClientState().isOption("ui.showbar", ClientDefaults.ui_showbar)) {
			frame.setJMenuBar(new JMenuBar());
		}

		if (getMouseListeners().length > 0) {
			removeMouseListener(getMouseListeners()[0]);
		}

		popups = new PopupManager();
		addMouseListener(popups);

		ClientState.getClientState().addClientStateListener("loaded.scripts", this);
		ClientState.getClientState().addClientStateListener("ui.showbar", this);
	}

	/** returns the currently active irc session */
	public IRCSession getActiveSession() {
		return active;
	}

	/** returns the specified session */
	public IRCSession getSpecificSession(int index) {
		return getSession(getComponentAt(index));
	}

	/** all calls to add session should be done on the java event thread... period! Or else bad things will happen. */
	public void addSession() {
		IRCSession session = new IRCSession();

		active = session; // we'll assume that a new session is always "active"

		sessions.put(session.getStatusWindow().getWindow(), session); // store the session by its status window
		sessions.put(session.getCapabilities().getSocketConnection(), session); // store the session by its socket
		sessions.put(session.getCapabilities(), session); // store the session by its capabilities

		sessions.put(session.getDesktop(), session); // store the session by its component

		session.getStatusWindow().getWindow().addWindowListener(this);
		session.getCapabilities().getSocketConnection().addSocketStatusListener(this);

		setSelectedComponent(add("not connected", session.getDesktop()));

		session.getClient().post(); // tells the irc client code to do the default stuff once everything is initialized

		/*
		 * for (int x = 0; x < getComponents().length; x++) { System.out.println("Adding listener: " + getComponents()[x]);
		 * getComponents()[x].addMouseListener(popups); }
		 */

		propertyChanged(null, null);
		revalidate();

		if (ClientState.getClientState().getString("user.nick", null) == null && !QuickConnect.IsQuickConnect()) {
			Application.getInstance().getCapabilities().showPreferences(null);
			session.getStatusWindow().getInput().requestFocus(); // give status window focus after first time setup...
		}
	}

	public IRCSession getSession(Object key) {
		return (IRCSession) sessions.get(key);
	}

	public int getIndexFor(Object key) {
		IRCSession session = getSession(key);

		if (session == null) {
			return -1;
		}

		int n = indexOfComponent(session.getDesktop());

		if (n > getTabCount()) {
			return -1;
		}

		return n;
	}

	public void removeSession(IRCSession session) {
		Iterator i = sessions.values().iterator();
		while (i.hasNext()) {
			Object temp = i.next();
			if (temp == session) {
				i.remove();
			}
		}

		long freememory = Runtime.getRuntime().freeMemory();
		remove(session.getDesktop());

		keyb.removeSession(session);

		session.cleanup();

		revalidate();

		System.gc();

		freememory = Runtime.getRuntime().freeMemory() - freememory;
		System.out.println("Freed a total of " + rero.util.ClientUtils.formatBytes(freememory));

		if (getTabCount() <= 0) {
			addSession(); // bad things happen if we don't always have a session "open"
		}

		if (active == session) {
			stateChanged(null);
		}
	}

	@Override
	public void onClose(ClientWindowEvent ev) {
		IRCSession source = getSession(ev.getSource());

		removeSession(source); // remove the session first so the irc connection is told to clean up and not "auto reconnect"

		if (source.getCapabilities().isConnected()) {
			source.getCapabilities().sendln("QUIT :Hey! Where'd my controlling terminal go?");
			source.getCapabilities().getSocketConnection().disconnect();
		}
	}

	@Override
	public void onActive(ClientWindowEvent ev) {

	}

	@Override
	public void onInactive(ClientWindowEvent ev) {

	}

	@Override
	public void onMinimize(ClientWindowEvent ev) {

	}

	@Override
	public void onOpen(ClientWindowEvent ev) {

	}

	public void setTabTitle(Object key, String text) {
		setTitleAt(getIndexFor(key), text);
	}

	@Override
	public void socketStatusChanged(SocketEvent ev) {
		if (ev.data.isConnected) {
			if (getIndexFor(ev.socket) > -1) {
				setTitleAt(getIndexFor(ev.socket), ev.data.hostname);
			}
		} else {
			if (getIndexFor(ev.socket) > -1) {
				setTitleAt(getIndexFor(ev.socket), "disconnected");
			}
		}

		GraphicalToolbar.stateChanged(); // CHEATING!!!!
	}

	public IRCSession getSessionAt(Point location) {
		if (indexAtLocation((int) location.getX(), (int) location.getY()) < 0) {
			return null;
		}
		return getSession(getComponentAt(indexAtLocation((int) location.getX(), (int) location.getY())));
	}

	protected class PopupManager extends MouseAdapter {
		public void maybeShowPopup(MouseEvent ev) {
			if (ev.isPopupTrigger()) {
				IRCSession temp = getSessionAt(ev.getPoint());

				if (temp == null) {
					return;
				}

				bridge = (MenuBridge) temp.getCapabilities().getDataStructure("menuBridge");
				JPopupMenu menu = bridge.getPopupMenu("tab", null);

				if (menu == null) {
					return;
				}

				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				ev.consume();

				return;
			}

			return;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			maybeShowPopup(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			maybeShowPopup(e);

			if (e.getButton() == MouseEvent.BUTTON1) {
				int idx = indexAtLocation(e.getX(), e.getY());

				if (idx > -1) {
					setSelectedIndex(idx);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			maybeShowPopup(e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
	}
}
