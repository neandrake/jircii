package rero.dck;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.border.Border;

public class SmallButton extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static String NORMAL = "<html><b>?</b></html>";
	protected static String ACTIVE = "<html><b>?</b></html>";

	protected JLabel label;

	protected LinkedList listeners;

	public SmallButton(Border border, String text) {
		super(NORMAL);

		setToolTipText(text);

		addMouseListener(new TakeAction());

		label = this;

		listeners = new LinkedList();

		setBorder(border);
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
			fireEvent();
		}

		@Override
		public void mousePressed(MouseEvent ev) {
			original = label.getForeground();
			label.setForeground(UIManager.getColor("TextArea.selectionBackground"));
		}

		@Override
		public void mouseReleased(MouseEvent ev) {
			label.setForeground(original);
		}

		@Override
		public void mouseEntered(MouseEvent ev) {
			label.setText(ACTIVE);
		}

		@Override
		public void mouseExited(MouseEvent ev) {
			label.setText(NORMAL);
		}
	}
}
