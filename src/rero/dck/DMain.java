package rero.dck;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class DMain extends DContainer {
	@Override
	public JComponent setupLayout(JComponent component) {
		component.setLayout(new BorderLayout());
		component.add(new JPanel(), BorderLayout.CENTER);
		component.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());

		component.add(container, BorderLayout.NORTH);

		return container;
	}

	public abstract String getDescription();
}
