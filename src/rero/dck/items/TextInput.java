package rero.dck.items;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import rero.config.ClientState;
import rero.config.StringList;
import rero.dck.SuperInput;

public class TextInput extends SuperInput implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JEditorPane text;

	public TextInput(String _variable, int inset) {
		text = new JEditorPane();
		setLayout(new BorderLayout(2, 2));

		add(new JScrollPane(text), BorderLayout.CENTER);

		variable = _variable;

		setBorder(BorderFactory.createEmptyBorder(0, inset, 0, inset));
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		text.setText("");
	}

	@Override
	public void save() {
		String[] blah = text.getText().split("\n");

		StringList temp = new StringList(getVariable());
		temp.clear();

		for (int x = 0; x < blah.length; x++) {
			temp.add(blah[x]);
		}

		temp.save();
	}

	@Override
	public int getEstimatedWidth() {
		return -1;
	}

	@Override
	public void setAlignWidth(int width) {}

	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	public void refresh() {
		StringList string = ClientState.getClientState().getStringList(getVariable());
		StringBuffer data = new StringBuffer();

		Iterator i = string.getList().iterator();
		while (i.hasNext()) {
			data.append(i.next());
			data.append("\n");
		}

		text.setText(data.toString());
		text.setCaretPosition(0);
	}
}
