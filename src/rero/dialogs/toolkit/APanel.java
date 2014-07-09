package rero.dialogs.toolkit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class APanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected GridBagConstraints constraints;
	protected GridBagLayout layout;

	public abstract void setupDialog(Object value); /* to be done *immediaely* before displaying the component */

	public abstract Object getValue(Object value);

	public APanel() {
		constraints = new GridBagConstraints();
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(0, 0, 2, 0);

		layout = new GridBagLayout();
		setLayout(layout);
	}

	/** called whenever this panel is added to a dialog, so the parent dialog can be processed by this panel */
	public void processParent(ADialog parent) {

	}

	public void addComponent(JComponent blah) {
		layout.setConstraints(blah, constraints);
		add(blah);
	}

	public static JComponent mergeComponents(JLabel label, JComponent c) {
		JPanel temp = new JPanel();
		temp.setLayout(new BorderLayout());
		temp.add(label, BorderLayout.WEST);
		temp.add(c, BorderLayout.CENTER);

		return temp;
	}

	public static JComponent mergeComponents(JLabel label, JComponent c, int gapint) {
		JPanel temp = new JPanel();
		temp.setLayout(new BorderLayout());
		temp.add(label, BorderLayout.WEST);
		temp.add(c, BorderLayout.CENTER);

		JPanel gap = new JPanel();
		gap.setPreferredSize(new Dimension(gapint, 0));
		temp.add(gap, BorderLayout.EAST);

		return temp;
	}
}
