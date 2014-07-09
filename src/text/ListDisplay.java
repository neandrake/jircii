package text;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.event.MouseInputListener;

import rero.config.ClientState;
import rero.config.ClientStateListener;
import text.list.ListData;
import text.list.ListDisplayComponent;
import text.list.ListElement;
import text.list.ListSelectionSpace;

public class ListDisplay extends JComponent implements MouseWheelListener, MouseInputListener, ClientStateListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ListDisplayComponent display;
	protected JScrollBar scroller;

	protected ListData data;
	protected ListSelectionSpace select;

	public ListElement getSelectedElement() {
		return select.getSelectedElement();
	}

	public LinkedList getSelectedElements() {
		return select.getSelectedElements();
	}

	@Override
	public void propertyChanged(String a, String b) {
		data.dirty();
	} // happens when ui.font changes...

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// manipulate the scroll bar directly for this one..

		if (e.getWheelRotation() >= 0) // up
		{
			scroller.setValue(scroller.getValue() + (e.getScrollAmount()));
			repaint();
		} else // down
		{
			scroller.setValue(scroller.getValue() - (e.getScrollAmount()));
			repaint();
		}
	}

	public ListDisplay(ListData data) {
		scroller = new JScrollBar(Adjustable.VERTICAL, 0, 0, 0, 0);
		display = new ListDisplayComponent();

		select = new ListSelectionSpace(this, data);
		addMouseListener(select);
		addMouseMotionListener(select);

		this.data = data;

		display.installDataSource(data);
		scroller.setModel(data);

		addMouseWheelListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		data.addChangeListener(display);

		setLayout(new BorderLayout());
		add(scroller, BorderLayout.EAST);
		add(display, BorderLayout.CENTER);

		setOpaque(false);
		setDoubleBuffered(false);

		ClientState.getClientState().addClientStateListener("ui.font", this);
	}

	@Override
	public void mousePressed(MouseEvent ev) {}

	@Override
	public void mouseReleased(MouseEvent ev) {}

	@Override
	public void mouseClicked(MouseEvent ev) {
		if (ev.isShiftDown() && ev.isControlDown()) {
			ListElement temp = data.getElementAtLocation(ev.getY());
			if (temp != null) {
				AttributedText attribs = temp.getAttributedText().getAttributesAt(ev.getX());
				if (attribs != null) {
					ModifyColorMapDialog.showModifyColorMapDialog(this, attribs.foreIndex);
					repaint();
					ev.consume();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent ev) {
		// we have nothing to do for this event...
	}

	@Override
	public void mouseExited(MouseEvent ev) {
		// nothing to do for this event...
	}

	@Override
	public void mouseDragged(MouseEvent ev) {}

	@Override
	public void mouseMoved(MouseEvent ev) {
		// again nothing to do for this event...
	}

	/*
	 * protected void finalize() { System.out.println("Finalizing the List Display"); }
	 */
}
