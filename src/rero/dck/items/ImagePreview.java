package rero.dck.items;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import rero.dck.DItem;
import rero.dck.DParent;
import rero.gui.background.BackgroundDesktop;
import rero.gui.background.BackgroundPanel;
import rero.gui.background.BackgroundToolBar;

public class ImagePreview extends JPanel implements DItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ImageConfigPreview preview;
	protected JLabel evil;
	protected int height;
	protected int width;

	public ImagePreview(int _width, int _height) {
		width = _width;
		height = _height;

		setBorder(BorderFactory.createEmptyBorder(0, width, 0, width));

		setLayout(new GridLayout(1, 1));
		preview = new ImageConfigPreview();

		add(preview);
	}

	@Override
	public void setParent(DParent parent) {

	}

	@Override
	public void setEnabled(boolean b) {}

	@Override
	public void save() {

	}

	@Override
	public void refresh() {
		preview.center();
	}

	@Override
	public int getEstimatedWidth() {
		return 0;
	}

	@Override
	public void setAlignWidth(int width) {}

	public void setParentVariable(String parent) {

	}

	@Override
	public JComponent getComponent() {
		return this;
	}

	protected LinkedList listeners = new LinkedList();

	protected void fireEvent(String command) {
		ActionEvent ev = new ActionEvent(this, 0, command);

		Iterator i = listeners.iterator();
		while (i.hasNext()) {
			ActionListener temp = (ActionListener) i.next();
			temp.actionPerformed(ev);
		}
	}

	public void addActionListener(ActionListener l) {
		listeners.add(l);
	}

	protected class ImageConfigPreview extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected BackgroundPanel window;
		protected JPanel wparent;
		protected JDesktopPane desktop;
		protected BackgroundToolBar toolbar;

		public ImageConfigPreview() {
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createEtchedBorder());

			desktop = new JDesktopPane();

			BackgroundDesktop wallpaper = new BackgroundDesktop(desktop);
			wallpaper.setSize(Toolkit.getDefaultToolkit().getScreenSize());
			desktop.add(wallpaper, new Integer(Integer.MIN_VALUE));

			add(desktop, BorderLayout.CENTER);

			window = new BackgroundPanel();

			window.setLayout(new BorderLayout());

			JPanel bottom = new JPanel();
			bottom.setLayout(new BorderLayout());
			bottom.setPreferredSize(new Dimension(0, 30));
			bottom.setOpaque(false);

			toolbar = new BackgroundToolBar();
			toolbar.setPreferredSize(new Dimension(0, 15));
			toolbar.setFloatable(false);
			bottom.add(toolbar, BorderLayout.NORTH);

			window.add(bottom, BorderLayout.SOUTH);

			wparent = new JPanel();
			wparent.setLayout(new BorderLayout());
			wparent.setBorder(BorderFactory.createEtchedBorder());
			wparent.add(window, BorderLayout.CENTER);

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					desktop.add(wparent);
					center();
					validate();
				}
			});

			PreviewMouseListener listener = new PreviewMouseListener();

			window.addMouseListener(listener);
			toolbar.addMouseListener(listener);
			desktop.addMouseListener(listener);
		}

		public void center() {
			int width, height, x, y, dw, dh;

			dw = desktop.getBounds().width;
			dh = desktop.getBounds().height;

			width = dw - 40;
			height = dh - 40;

			x = 20;
			y = 20;

			wparent.setBounds(x, y, width, height);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(0, height);
		}

		@Override
		public Dimension getMinimumSize() {
			return getPreferredSize();
		}

		protected class PreviewMouseListener extends MouseAdapter {
			@Override
			public void mousePressed(MouseEvent ev) {
				if (ev.getSource() == window) {
					fireEvent("window");
				} else if (ev.getSource() == toolbar) {
					fireEvent("statusbar");
				} else if (ev.getSource() == desktop) {
					fireEvent("desktop");
				}
			}
		}
	}
}
