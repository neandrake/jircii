package text;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class AttributedLabel extends JComponent implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected AttributedString left;

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(left.getAttributedText().getWidth(), TextSource.fontMetrics.getHeight() + 2);
	}

	public void setText(AttributedString string) {
		left = string;
	}

	public void setText(String text) {
		left = AttributedString.CreateAttributedString(text);
		left.assignWidths();
	}

	public AttributedLabel() {
		setText("");
		addMouseListener(this);
	}

	public AttributedLabel(String text) {
		setText(text);
		addMouseListener(this);
	}

	@Override
	public void paint(Graphics g) {
		TextSource.initGraphics(g);

		int width = super.getWidth();
		int height = super.getHeight();

		int baseline = height - 4; // gives us a 5 pixel buffer -- was 4
									// between the textbox and the textarea

		TextSource.drawText(g, left.getAttributedText(), TextSource.UNIVERSAL_TWEAK, baseline);
	}

	@Override
	public void mousePressed(MouseEvent ev) {}

	@Override
	public void mouseEntered(MouseEvent ev) {}

	@Override
	public void mouseExited(MouseEvent ev) {}

	@Override
	public void mouseReleased(MouseEvent ev) {}

	@Override
	public void mouseClicked(MouseEvent ev) {
		if (ev.isShiftDown()) {
			int width;
			AttributedText iter;

			width = TextSource.UNIVERSAL_TWEAK;
			iter = left.getAttributedText();

			while (iter != null && (iter.width + width) < ev.getX()) {
				width = width + iter.width;
				iter = iter.next;
			}

			if (iter != null) {
				int index;

				if (ev.isControlDown() && iter.backIndex > -1) {
					index = iter.backIndex;
				} else {
					index = iter.foreIndex;
				}

				ModifyColorMapDialog.showModifyColorMapDialog(this, index);
			}

			ev.consume();
			return;
		}
	}
}
