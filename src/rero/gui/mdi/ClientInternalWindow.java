package rero.gui.mdi;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import rero.gui.windows.ClientWindow;
import rero.gui.windows.ClientWindowEvent;
import rero.gui.windows.ClientWindowListener;

public class ClientInternalWindow extends JInternalFrame implements ClientWindow, InternalFrameListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected LinkedList clisteners = new LinkedList();
	protected ClientWindowEvent cevent;
	protected boolean isOpen = false;

	public ClientInternalWindow() {
		super("", true, true, true, true);

		cevent = new ClientWindowEvent(this);
		addInternalFrameListener(this);
	}

	public boolean isOpen() {
		return isOpen;
	}

	@Override
	public void setIcon(ImageIcon i) {
		setFrameIcon(i);
	}

	@Override
	public void addWindowListener(ClientWindowListener l) {
		clisteners.add(l);
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		Iterator i = clisteners.listIterator();
		while (i.hasNext()) {
			ClientWindowListener temp = (ClientWindowListener) i.next();
			temp.onActive(cevent);
		}
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		Iterator i = clisteners.listIterator();
		while (i.hasNext()) {
			ClientWindowListener temp = (ClientWindowListener) i.next();
			temp.onInactive(cevent);
		}
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		Iterator i = clisteners.listIterator();
		while (i.hasNext()) {
			ClientWindowListener temp = (ClientWindowListener) i.next();
			temp.onClose(cevent);
		}
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		Iterator i = clisteners.listIterator();
		while (i.hasNext()) {
			ClientWindowListener temp = (ClientWindowListener) i.next();
			temp.onMinimize(cevent);
		}
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		Iterator i = clisteners.listIterator();
		while (i.hasNext()) {
			ClientWindowListener temp = (ClientWindowListener) i.next();
			temp.onOpen(cevent);
		}
	}

	@Override
	public void closeWindow() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					setClosed(true);
				} catch (Exception ex) {}
			}
		});
	}

	@Override
	public void setMaximum(boolean b) {
		try {
			super.setMaximum(b);
		} catch (Exception ex) {}
	}

	@Override
	public void setIcon(boolean b) {
		try {
			super.setIcon(b);
		} catch (Exception ex) {}
	}

	@Override
	public void setTitle(String aTitle) {
		title = aTitle;
		revalidate();
		repaint();
	}

	@Override
	public void show() {
		isOpen = true;
		super.show();
	}

	@Override
	public void activate() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					if (!isOpen) {
						show();
					}

					setSelected(true);
				} catch (Exception ex) {}
			}
		});
	}
}
