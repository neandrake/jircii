package rero.dck;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.UIManager;

public class FileLink extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected LinkedList listeners;
	protected JComponent label;

	public FileLink() {
		addMouseListener(new TakeAction());
		listeners = new LinkedList();

		label = this;
	}

	protected String text = "";

	public void setText(String _text) {
		text = _text;
		setToolTipText(text);
		repaint();
	}

	public String getText() {
		return text;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(0, Toolkit.getDefaultToolkit().getFontMetrics(getFont()).getHeight());
	}

	@Override
	public void paint(Graphics g) {
		StringBuffer string = new StringBuffer();
		FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(g.getFont());

		int x;
		for (x = 0; x < text.length() && fm.stringWidth(text.substring(0, x)) < getWidth(); x++) {
			;
		}

		if (isEnabled()) {
			g.setColor(label.getForeground());
			g.drawLine(0, getHeight() - fm.getDescent() + 1, fm.stringWidth(text.substring(0, x)), getHeight() - fm.getDescent() + 1);
		} else {
			g.setColor(label.getForeground().brighter());
		}

		g.drawString(getText().substring(0, x), 0, getHeight() - fm.getDescent());
	}

	public void addActionListener(ActionListener l) {
		listeners.add(l);
	}

	public void fireEvent() {
		ActionEvent event = new ActionEvent(this, 0, "?");

		Iterator i = listeners.iterator();
		while (i.hasNext()) {
			((ActionListener) i.next()).actionPerformed(event);
		}
	}

	public class TakeAction extends MouseAdapter {
		protected Color original;

		@Override
		public void mouseClicked(MouseEvent ev) {
			if (isEnabled()) {
				fireEvent();
			}
		}

		@Override
		public void mousePressed(MouseEvent ev) {
			if (isEnabled()) {
				original = label.getForeground();
				label.setForeground(UIManager.getColor("TextArea.selectionBackground"));
				label.repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent ev) {
			if (isEnabled()) {
				label.setForeground(original);
				label.repaint();
			}
		}

		@Override
		public void mouseEntered(MouseEvent ev) {}

		@Override
		public void mouseExited(MouseEvent ev) {
			if (isEnabled()) {
				label.setForeground(original);
				label.repaint();
			}
		}
	}
}
