package rero.gui.toolkit;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.TabbedPaneUI;

import rero.config.ClientDefaults;
import rero.config.ClientState;

public class MinimalTabUI extends TabbedPaneUI implements ChangeListener {
	public MinimalTabUI() {
		// don't do anything :)
	}

	private static class MinimalLayout extends CardLayout {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void addLayoutComponent(Component c, Object constraints) {
			super.addLayoutComponent(c.hashCode() + "", c);
		}

		@Override
		public void addLayoutComponent(String cons, Component c) {
			super.addLayoutComponent(c.hashCode() + "", c);
		}
	}

	public Dimension getMinimumSize() {
		return null;
	}

	public Dimension getMaximumSize() {
		return null;
	}

	public Dimension getPreferredSize() {
		return null;
	}

	// public boolean contains(JComponent c, int x, int y) { return true; }

	@Override
	public Rectangle getTabBounds(JTabbedPane pane, int index) {
		return new Rectangle(0, 0, 0, 0);
	}

	@Override
	public int getTabRunCount(JTabbedPane pane) {
		return 0;
	}

	@Override
	public int tabForCoordinate(JTabbedPane pane, int x, int y) {
		return 0;
	}

	@Override
	public void installUI(JComponent c) {
		JTabbedPane temp = (JTabbedPane) c;
		c.setLayout(new MinimalLayout());

		int size = ClientState.getClientState().getInteger("notabs.border", ClientDefaults.notabs_border);

		c.setBorder(BorderFactory.createEmptyBorder(size, size, size, size));
		temp.addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent ev) {
		JTabbedPane temp = (JTabbedPane) ev.getSource();

		if (temp.getSelectedComponent() != null) {
			((CardLayout) temp.getLayout()).show(temp, temp.getSelectedComponent().hashCode() + "");
		}
		temp.revalidate();
		temp.repaint();
	}
}
