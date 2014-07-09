package rero.gui.windows;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.ImageIcon;

import rero.bridges.menu.MenuBridge;
import rero.client.Capabilities;
import rero.gui.input.InputField;
import text.WrappedDisplay;
import text.event.ClickEvent;
import text.event.ClickListener;

public abstract class EmptyWindow extends StatusWindow implements ClientWindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void touch() {}

	@Override
	public void installCapabilities(Capabilities c) {
		capabilities = c;
		menuManager = (MenuBridge) c.getDataStructure("menuBridge");

		init();
	}

	@Override
	public InputField getInput() {
		return null;
	}

	@Override
	public WrappedDisplay getDisplay() {
		return null;
	}

	@Override
	public WindowStatusBar getStatusBar() {
		return null;
	}

	@Override
	public void init(ClientWindow _frame) {
		frame = _frame;
		frame.addWindowListener(new ClientWindowStuff());
		frame.addWindowListener(this);

		frame.setContentPane(this);

		setTitle(getName());
		frame.setIcon(getImageIcon());
	}

	public abstract void init();

	@Override
	public String getQuery() {
		return "Nada";
	}

	@Override
	public void setQuery(String q) {}

	@Override
	public void setTitle(String title) {
		frame.setTitle(title);
	}

	@Override
	public ClientWindow getWindow() {
		return frame;
	}

	@Override
	public String getTitle() {
		return frame.getTitle();
	}

	@Override
	public abstract String getName();

	@Override
	public abstract ImageIcon getImageIcon();

	@Override
	public String getWindowType() {
		return "Other";
	}

	@Override
	public boolean isLegalWindow() {
		return false;
	}

	private boolean isOpen = true;

	public boolean isOpen() {
		return isOpen;
	}

	@Override
	public void onActive(ClientWindowEvent ev) {}

	@Override
	public void onOpen(ClientWindowEvent ev) {
		isOpen = true;
	}

	@Override
	public void onInactive(ClientWindowEvent ev) {}

	@Override
	public void onMinimize(ClientWindowEvent ev) {}

	@Override
	public void onClose(ClientWindowEvent ev) {
		isOpen = false;
	}

	protected LinkedList listeners = new LinkedList();

	public void addClickListener(ClickListener l) {
		listeners.add(l);
	}

	public void fireClickEvent(String text, MouseEvent mev) {
		ClickEvent ev = new ClickEvent(text, getName(), mev);

		ListIterator i = listeners.listIterator();
		while (i.hasNext() && !ev.isConsumed()) {
			ClickListener l = (ClickListener) i.next();
			l.wordClicked(ev);
		}
	}

	@Override
	public int compareWindowType() {
		return 4;
	}
}
