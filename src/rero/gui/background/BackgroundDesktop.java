package rero.gui.background;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

import rero.config.ClientState;
import rero.config.ClientStateListener;

public class BackgroundDesktop extends JPanel implements ClientStateListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected BackgroundProperties bgProperties;
	// protected JDesktopPane desktop;
	protected JComponent desktop;

	public BackgroundDesktop(JComponent pane) {
		desktop = pane;

		if (bgProperties == null) {
			bgProperties = new BackgroundProperties("desktop", BackgroundUtil.BG_DEFAULT);
		}

		ClientState.getClientState().addClientStateListener("desktop", this);
	}

	@Override
	public Dimension getSize() {
		return desktop.getSize();
	}

	@Override
	public void propertyChanged(String property, String parms) {
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		int x, y, width, height;

		x = g.getClipBounds().x;
		y = g.getClipBounds().y;
		width = g.getClipBounds().width;
		height = g.getClipBounds().height;

		switch (bgProperties.getType()) {
		case BackgroundUtil.BG_DEFAULT:
			break;
		case BackgroundUtil.BG_SOLID:
			g.setColor(bgProperties.getColor());
			g.fillRect(x, y, width, height);
			paintChildren(g);
			break;
		case BackgroundUtil.BG_IMAGE:
			BackgroundUtil.drawBackground(this, g, bgProperties);
			paintChildren(g);
			break;
		}
	}
}
