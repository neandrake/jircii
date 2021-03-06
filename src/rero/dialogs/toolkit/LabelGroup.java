package rero.dialogs.toolkit;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JComponent;

public class LabelGroup {
	protected LinkedList labels = new LinkedList();

	public void addLabel(JComponent l) {
		labels.add(l);
	}

	public void sync() {
		int size = 0;

		Iterator i = labels.iterator();
		while (i.hasNext()) {
			JComponent c = (JComponent) i.next();
			if (c.getPreferredSize().getWidth() > size) {
				size = (int) c.getPreferredSize().getWidth();
			}
		}

		i = labels.iterator();

		while (i.hasNext()) {
			JComponent c = (JComponent) i.next();
			c.setPreferredSize(new Dimension(size, 0));
		}
	}
}
