package rero.dialogs.toolkit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ADialog extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected APanel panel;
	protected Object returnValue;

	protected JButton ok, cancel;

	protected Object value;

	public Object showDialog(Component comp) {
		setLocationRelativeTo(comp);
		setVisible(true);
		return returnValue;
	}

	public ADialog(Component comp, String title, APanel _panel, Object _value) {
		super(JOptionPane.getFrameForComponent(comp), title, true);

		value = _value;

		panel = _panel;
		getContentPane().setLayout(new BorderLayout());

		JPanel temp = new JPanel();
		temp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		temp.setLayout(new BorderLayout());
		temp.add(panel, BorderLayout.CENTER);

		getContentPane().add(temp, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));

		ok = new JButton("Ok");
		ok.setMnemonic('O');
		ok.addActionListener(this);

		cancel = new JButton("Cancel");
		cancel.setMnemonic('C');
		cancel.addActionListener(this);

		buttons.add(ok);
		buttons.add(cancel);

		getContentPane().add(buttons, BorderLayout.SOUTH);

		panel.processParent(this);

		returnValue = null;
	}

	public void closeAndReturn() {
		returnValue = panel.getValue(value);
		setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == ok) {
			returnValue = panel.getValue(value);
		} else if (ev.getSource() == cancel) {
			returnValue = null;
		} else {
			returnValue = panel.getValue(ev.getActionCommand());
		}

		setVisible(false);
	}
}
