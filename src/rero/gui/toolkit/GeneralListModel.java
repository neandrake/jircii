package rero.gui.toolkit;

import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import contrib.javapro.SortTableModel;

/** A model for the sortable list window thingy... */
public abstract class GeneralListModel extends AbstractTableModel implements SortTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isSortable(int col) {
		return true;
	}

	@Override
	public abstract void sortColumn(int col, boolean ascending);

	@Override
	public abstract int getRowCount();

	@Override
	public abstract int getColumnCount();

	@Override
	public abstract String getColumnName(int col);

	public abstract int getColumnWidth(int col);

	public abstract HashMap getEventHashMap(int row);

	/** make sure this method always returns an AttributedString */
	@Override
	public abstract Object getValueAt(int row, int col);
}
