package rero.dck.items;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import rero.dck.DContainer;
import rero.dck.DItem;
import rero.dck.DParent;
import rero.dck.DTab;

public class TabbedInput extends JPanel implements DItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected LinkedList items = new LinkedList();
	protected JTabbedPane tabs;

	public TabbedInput() {
		setLayout(new BorderLayout());

		tabs = new JTabbedPane();
		add(tabs, BorderLayout.CENTER);
	}

	public void addTab(DTab item) {
		tabs.addTab(item.getTitle(), null, item.getDialog(), item.getDescription());
		items.add(item);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(0, (int) super.getPreferredSize().getHeight());
	}

	@Override
	public void setEnabled(boolean b) {
		Iterator i = items.iterator();
		while (i.hasNext()) {
			((DContainer) i.next()).setEnabled(b);
		}

		tabs.setEnabled(b);
	}

	@Override
	public void save() {
		Iterator i = items.iterator();
		while (i.hasNext()) {
			((DContainer) i.next()).save();
		}
	}

	@Override
	public int getEstimatedWidth() {
		return 0;
	}

	@Override
	public void setAlignWidth(int width) {}

	@Override
	public void setParent(DParent parent) {
		Iterator i = items.iterator();
		while (i.hasNext()) {
			((DContainer) i.next()).setParent(parent);
		}
	}

	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	public void refresh() {
		int count = 0;
		Iterator i = items.iterator();
		while (i.hasNext()) {
			DTab item = (DTab) i.next();
			item.refresh();

			tabs.setEnabledAt(count, item.isEnabled());
			item.setEnabled(item.isEnabled());

			count++;
		}
	}
}
