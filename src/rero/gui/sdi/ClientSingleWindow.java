package rero.gui.sdi;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import rero.gui.windows.ClientWindow;
import rero.gui.windows.ClientWindowEvent;
import rero.gui.windows.ClientWindowListener;

public class ClientSingleWindow extends JPanel implements ClientWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected LinkedList clisteners = new LinkedList();
	protected ClientWindowEvent cevent;
	protected ClientPanel parent;

	public ClientSingleWindow(ClientPanel _parent) {
		cevent = new ClientWindowEvent(this);
		setLayout(new BorderLayout());

		parent = _parent;
	}

	@Override
	public void addWindowListener(ClientWindowListener l) {
		clisteners.add(l);
	}

	public void processActive() {
		Iterator i = clisteners.listIterator();
		while (i.hasNext()) {
			ClientWindowListener temp = (ClientWindowListener) i.next();
			temp.onActive(cevent);
		}
	}

	public void processInactive() {
		Iterator i = clisteners.listIterator();
		while (i.hasNext()) {
			ClientWindowListener temp = (ClientWindowListener) i.next();
			temp.onInactive(cevent);
		}
	}

	public void processClose() {
		Iterator i = clisteners.listIterator();
		while (i.hasNext()) {
			ClientWindowListener temp = (ClientWindowListener) i.next();
			temp.onClose(cevent);
		}
	}

	public void processOpen() {
		Iterator i = clisteners.listIterator();
		while (i.hasNext()) {
			ClientWindowListener temp = (ClientWindowListener) i.next();
			temp.onOpen(cevent);
		}
	}

	@Override
	public void closeWindow() {
		parent.killWindow(this);
	}

	@Override
	public boolean isMaximum() {
		return false;
	}

	@Override
	public boolean isIcon() {
		return false;
	}

	@Override
	public void setMaximum(boolean b) {}

	@Override
	public void setIcon(boolean b) {
		if (b) {
			parent.doDeactivate(parent.getWindowFor(this));
		} else {
			parent.doActivate(parent.getWindowFor(this));
		}
	}

	@Override
	public void setIcon(ImageIcon blah) {}

	@Override
	public void show() {}

	@Override
	public boolean isSelected() {
		return parent.getActiveWindow() != null && parent.getActiveWindow().getWindow() == this;
	}

	@Override
	public void activate() {
		parent.doActivate(parent.getWindowFor(this));
	}

	@Override
	public void setTitle(String title) {}

	@Override
	public String getTitle() {
		return "";
	}

	@Override
	public void setContentPane(Container c) {
		add(c, BorderLayout.CENTER);
	}
}
