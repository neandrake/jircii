package rero.dialogs.help;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import rero.dialogs.HelpWindow;

public abstract class HelperObject {
	public abstract JComponent getNavigationComponent();

	protected HelpWindow help;

	public JComponent getNavigation() {
		JPanel general = new JPanel();
		general.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		general.setLayout(new BorderLayout(5, 5));

		JScrollPane genScroller = new JScrollPane(getNavigationComponent());
		genScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		general.add(new JLabel("Contents:"), BorderLayout.NORTH);
		general.add(genScroller, BorderLayout.CENTER);

		return general;
	}

	public void setHelp(HelpWindow _help) {
		help = _help;
	}

	public void updateText(String newText) {
		help.updateText(newText);
	}
}
