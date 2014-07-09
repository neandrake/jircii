package rero.gui.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import rero.bridges.menu.MenuBridge;
import rero.client.Capabilities;
import rero.config.ClientState;
import rero.gui.IRCAwareComponent;
import rero.gui.IRCSession;
import rero.gui.background.BackgroundPanel;
import rero.gui.input.InputField;
import rero.gui.toolkit.OrientedToolBar;
import rero.util.ClientUtils;
import text.WrappedDisplay;

public class StatusWindow extends BackgroundPanel implements IRCAwareComponent, Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String STATUS_NAME = "%STATUS%"; // a constant representing the name of the status window as far as
															// jIRCii goes...

	protected WrappedDisplay display;
	protected InputField input;
	protected WindowStatusBar statusbar;

	protected JToggleButton button; // for the switchbar homez.
	protected ImageIcon icon; // jEAH bABY

	protected ClientWindow frame;
	protected String query = "";

	protected Capabilities capabilities;

	protected Color defaultForegroundColor;

	protected MenuBridge menuManager;

	public void cleanup() {
		if (display != null) {
			display.clear();
		}

		if (input != null) {
			input.cleanup();
		}

		removeAll();
	}

	public void touch() {
		statusbar.rehash();
		statusbar.repaint();
	}

	protected void maybeShowPopup(MouseEvent ev, String desc) {
		if (ev.isPopupTrigger()) {
			JPopupMenu menu = getPopupMenu(desc, ClientUtils.getEventHashMap(getName(), getName()));

			if (menu != null) {
				menu.show(ev.getComponent(), ev.getX(), ev.getY());
				ev.consume();
			}
		}
	}

	@Override
	public void installCapabilities(Capabilities c) {
		capabilities = c;
		statusbar.installCapabilities(c);

		menuManager = (MenuBridge) c.getDataStructure("menuBridge");

		input.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent ev) {
				maybeShowPopup(ev, "input");
			}

			@Override
			public void mouseReleased(MouseEvent ev) {
				maybeShowPopup(ev, "input");
			}

			@Override
			public void mouseClicked(MouseEvent ev) {
				maybeShowPopup(ev, "input");
			}
		});

		MouseAdapter normal = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent ev) {
				maybeShowPopup(ev, getWindowType());
			}

			@Override
			public void mouseReleased(MouseEvent ev) {
				maybeShowPopup(ev, getWindowType());
			}

			@Override
			public void mouseClicked(MouseEvent ev) {
				maybeShowPopup(ev, getWindowType());
			}
		};

		display.addMouseListener(normal);
	}

	public JPopupMenu getPopupMenu(String name, HashMap event) {
		return menuManager.getPopupMenu(name, event);
	}

	public InputField getInput() {
		return input;
	}

	public WrappedDisplay getDisplay() {
		return display;
	}

	public WindowStatusBar getStatusBar() {
		return statusbar;
	}

	public void flag() {
		if (!getWindow().isSelected() && SwitchBarOptions.isHilightOn()) {
			getButton().setForeground(SwitchBarOptions.getHighlightColor());
		}
	}

	public void unflag() {
		if (SwitchBarOptions.isHilightOn()) {
			getButton().setForeground(defaultForegroundColor);
			getButton().repaint();
		}
	}

	public void init(ClientWindow _frame) {
		frame = _frame;
		frame.addWindowListener(new ClientWindowStuff());

		setLayout(new BorderLayout());

		display = new WrappedDisplay();
		input = new InputField();
		statusbar = new WindowStatusBar(this);

		add(display, BorderLayout.CENTER);

		JPanel space = new JPanel();
		space.setLayout(new BorderLayout());

		space.add(statusbar, BorderLayout.NORTH);
		space.add(input, BorderLayout.SOUTH);

		space.setOpaque(false);

		add(space, BorderLayout.SOUTH);

		frame.setContentPane(this);

		setTitle(getName());
		frame.setIcon(getImageIcon());
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String q) {
		query = q;
		statusbar.stateChanged(null); // I know that the statusbar doesn't do anything with the 'state' variable
	}

	public void setTitle(String title) {
		if (title != null && title.equals("%STATUS%")) {
			title = "Status";
		}

		frame.setTitle(title);
	}

	public ClientWindow getWindow() {
		return frame;
	}

	public String getTitle() {
		return frame.getTitle();
	}

	@Override
	public void setName(String name) {
		getButton().setText(getName());
		getButton().repaint();
		setTitle(getName());
		touch();
	}

	@Override
	public String getName() {
		return "%STATUS%";
	}

	public ImageIcon getImageIcon() {
		if (icon == null) {
			icon = new ImageIcon(ClientState.getClientState().getResource("status.gif"));
		}

		return icon;
	}

	public JToggleButton getButton() {
		if (button == null) {
			if (getName().equals("%STATUS%")) {
				button = new JToggleButton("Status", getImageIcon());
			} else {
				button = new JToggleButton(getName(), getImageIcon());
			}

			button.setHorizontalAlignment(SwingConstants.LEFT);
			button.setMargin(new Insets(0, 0, 0, 5));
			button.setFocusPainted(false);

			button.setPreferredSize(new Dimension(OrientedToolBar.BUTTON_FIXED_WIDTH, button.getPreferredSize().height));

			button.setSelected(false);

			defaultForegroundColor = button.getForeground();

			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent ev) {
					// Shift+Click closes windows
					int onmask = InputEvent.SHIFT_DOWN_MASK | InputEvent.BUTTON1_DOWN_MASK;
					if ((ev.getModifiersEx() & onmask) == onmask) {
						String text = ((JToggleButton) ev.getSource()).getText();
						IRCSession session = capabilities.getGlobalCapabilities().getSessionManager().getActiveSession();
						session.getWindow(text).getWindow().closeWindow();
					} else {
						maybeShowPopup(ev, "switchbar");
					}
				}

				@Override
				public void mouseReleased(MouseEvent ev) {
					maybeShowPopup(ev, "switchbar");
				}

				@Override
				public void mouseClicked(MouseEvent ev) {
					maybeShowPopup(ev, "switchbar");
				}
			});
		}

		return button;
	}

	protected class ClientWindowStuff implements ClientWindowListener {
		@Override
		public void onActive(ClientWindowEvent ev) {
			unflag();
		}

		@Override
		public void onOpen(ClientWindowEvent ev) {}

		@Override
		public void onInactive(ClientWindowEvent ev) {}

		@Override
		public void onMinimize(ClientWindowEvent ev) {}

		@Override
		public void onClose(ClientWindowEvent ev) {
			cleanup();
		}
	}

	public String getWindowType() {
		return "Status";
	}

	@Override
	public int compareTo(Object o) {
		StatusWindow temp = (StatusWindow) o;

		if (compareWindowType() == temp.compareWindowType()) {
			return this.getName().toUpperCase().compareTo(((StatusWindow) o).getName().toUpperCase());
		}

		return compareWindowType() - temp.compareWindowType();
	}

	public int compareWindowType() {
		return 1;
	}

	public boolean isLegalWindow() {
		return true;
	}

	/*
	 * protected void finalize() { System.out.println("Finalizing " + getWindowType() + ", " + getName()); }
	 */
}
