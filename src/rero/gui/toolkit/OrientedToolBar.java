package rero.gui.toolkit;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JToolBar;

import rero.config.ClientDefaults;
import rero.config.ClientState;
import rero.config.ClientStateListener;

public class OrientedToolBar extends JToolBar implements ClientStateListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int BUTTON_FIXED_WIDTH = 95;

	private FlowLayout2 fixed = null;
	private GridLayout2 fill = null;

	public OrientedToolBar() {
		setFloatable(false);
		propertyChanged(null, null);

		ClientState.getClientState().addClientStateListener("switchbar.fixed", this);
		// ClientState.getClientState().addClientStateListener("switchbar.position", this);
	}

	@Override
	public void propertyChanged(String var, String var2) {
		int orientation = ClientState.getClientState().getInteger("switchbar.position", 0);

		if (orientation == 2 || orientation == 3) {
			// setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setLayout(new GridLayout3());
			validate();
		} else if (ClientState.getClientState().isOption("switchbar.fixed", ClientDefaults.switchbar_fixed)) {
			if (fixed == null || fill == null) {
				fixed = new FlowLayout2(FlowLayout.LEFT, 0, 0);
				fill = new GridLayout2();
			}

			setLayout(fixed); // not perfect but whatever...
			validate();
		} else {
			setLayout(new GridLayout());
			validate();
		}
	}

	/**
	 * not really applicable anymore, jIRCii doesn't let you dock the switchbar to the left or right anymore, if that feature
	 * is added then this method will need to be updated
	 */
	@Override
	public void setOrientation(int o) {
		propertyChanged(null, null);
	}

	private class FlowLayout2 extends FlowLayout {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public FlowLayout2(int dir, int vgap, int hgap) {
			super(dir, vgap, hgap);
		}

		@Override
		public void layoutContainer(Container c) {

			if ((c.getComponentCount() * (BUTTON_FIXED_WIDTH + 2)) < c.getWidth()) {
				super.layoutContainer(c);
			} else {
				c.setLayout(fill);
				c.validate();
			}
		}
	}

	private class GridLayout2 extends GridLayout {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void layoutContainer(Container c) {
			if ((c.getComponentCount() * (BUTTON_FIXED_WIDTH + 2)) < c.getWidth()) {
				c.setLayout(fixed);
				c.validate();
			} else {
				super.layoutContainer(c);
			}
		}
	}

	private class GridLayout3 extends GridLayout {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public int getColumns() {
			return 1;
		}

		@Override
		public void layoutContainer(Container c) {
			if (c.getComponentCount() > 0) {
				int buttonheight = getComponent(0).getPreferredSize().height + getVgap();

				if ((buttonheight * c.getComponentCount()) > c.getHeight()) {
					setRows(c.getComponentCount());
					setColumns(1);
				} else {
					setRows(c.getHeight() / buttonheight);
					setColumns(1);
				}
			}

			super.layoutContainer(c);
		}
	}
}
