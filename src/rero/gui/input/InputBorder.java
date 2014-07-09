package rero.gui.input;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

import text.AttributedString;
import text.AttributedText;
import text.TextSource;

public class InputBorder implements Border {
	protected AttributedString indent;
	protected String original;

	public InputBorder(String text) {
		indent = AttributedString.CreateAttributedString(text);
		indent.assignWidths();

		original = text;
	}

	public String getText() {
		return original;
	}

	public AttributedText getAttributes() {
		return indent.getAttributedText();
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(0, indent.getAttributedText().getWidth(), 0, 0);
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		TextSource.initGraphics(g);
		TextSource.drawText(g, indent.getAttributedText(), x, height + y - 4); // was -4
	}
}
