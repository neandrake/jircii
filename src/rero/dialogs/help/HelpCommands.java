package rero.dialogs.help;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import rero.dialogs.HelpWindow;

public class HelpCommands extends HelperObject implements ListSelectionListener {
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}

		JList theList = (JList) e.getSource();
		String key = (String) (theList.getSelectedValue());

		if (theList.isSelectionEmpty() || key.equals(" ")) {
			updateText("");
		} else {
			updateText(help.getCommand(key));
		}
	}

	@Override
	public JComponent getNavigationComponent() {
		JList comOptions = new JList(HelpWindow.getCommandData().getData());
		comOptions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		comOptions.addListSelectionListener(this);

		return comOptions;
	}
}
