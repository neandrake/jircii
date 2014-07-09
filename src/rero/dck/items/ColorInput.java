package rero.dck.items;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;

import rero.config.ClientState;
import rero.dck.SuperInput;

public class ColorInput extends SuperInput implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected SolidIcon colorIcon;
	protected JButton button;
	protected Color initial;

	public ColorInput(String _variable, Color _initial, String text, char mnemonic) {
		setLayout(new FlowLayout(FlowLayout.CENTER));

		initial = _initial;
		variable = _variable;

		colorIcon = new SolidIcon(initial, 18, 18);
		button = new JButton(text, colorIcon);
		button.setMnemonic(mnemonic);

		button.addActionListener(this);
		add(button);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		Color temp = JColorChooser.showDialog(button, "Select color...", colorIcon.getColor());
		if (temp != null) {
			colorIcon.setColor(temp);
			button.repaint();
			notifyParent();
		}
	}

	@Override
	public void save() {
		ClientState.getClientState().setColor(getVariable(), colorIcon.getColor());
	}

	@Override
	public void refresh() {
		colorIcon.setColor(ClientState.getClientState().getColor(getVariable(), initial));
		button.repaint();
	}

	@Override
	public int getEstimatedWidth() {
		return 0;
	}

	@Override
	public void setAlignWidth(int width) {}

	@Override
	public JComponent getComponent() {
		return this;
	}

	protected static class SolidIcon implements Icon {
		private int width, height;
		private Color color;

		public SolidIcon(Color c, int w, int h) {
			width = w;
			height = h;
			color = c;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color c) {
			color = c;
		}

		@Override
		public int getIconWidth() {
			return width;
		}

		@Override
		public int getIconHeight() {
			return height;
		}

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			g.setColor(color);
			g.fillRect(x, y, width - 1, height - 1);
		}
	}
}
