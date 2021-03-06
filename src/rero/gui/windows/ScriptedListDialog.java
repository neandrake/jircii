package rero.gui.windows;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import rero.gui.toolkit.GeneralListModel;
import rero.script.ScriptCore;
import rero.util.ClientUtils;
import sleep.runtime.Scalar;
import text.AttributedString;
import text.TextSource;
// sorted JTable code...

public class ScriptedListDialog extends GeneralListDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScriptedListDialog(String title, String hook, Object data, LinkedList cols) {
		super(title, hook, new ScriptedListModel(cols, data));
	}

	public void refreshData() {
		model.fireTableDataChanged();
	}

	@Override
	public void init() {
		((ScriptedListModel) model).install(popupHook, capabilities.getScriptCore());
	}

	public static class ScriptedCompare implements Comparator {
		private int col;
		private boolean rev;

		public ScriptedCompare(int column, boolean reverse) {
			rev = reverse;
			col = column;
		}

		@Override
		public int compare(Object a, Object b) {
			if (rev) {
				Object c = b;
				b = a;
				a = c;
			}

			String[] sa = a.toString().toLowerCase().split("\t");
			String[] sb = b.toString().toLowerCase().split("\t");

			try {
				int na = Integer.parseInt(sa[col]);
				int nb = Integer.parseInt(sb[col]);

				return na - nb;
			} catch (Exception ex) {}

			return sa[col].compareTo(sb[col]);
		}
	}

	private static class ScriptedListModel extends GeneralListModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Scalar data;
		private LinkedList cols;
		private ScriptCore script;
		private String popupHook;

		public void install(String popup, ScriptCore s) {
			script = s;
			popupHook = popup;
		}

		public ScriptedListModel(LinkedList headers, Object scalar) {
			cols = headers;

			data = (Scalar) scalar;
		}

		@Override
		public void sortColumn(int col, boolean ascending) {
			/*
			 * String function = "&" + popupHook + "_sort";
			 * 
			 * Stack temp = new Stack(); temp.push(SleepUtils.getScalar((ascending ? 1 : 0)));
			 * temp.push(SleepUtils.getScalar(col));
			 * 
			 * script.callFunction(function, temp);
			 */

			data.getArray().sort(new ScriptedCompare(col, ascending));
			fireTableDataChanged();
		}

		@Override
		public HashMap getEventHashMap(int row) {
			return ClientUtils.getEventHashMap(row + "", "");
		}

		@Override
		public int getRowCount() {
			return data.getArray().size();
		}

		@Override
		public int getColumnCount() {
			return cols.size();
		}

		@Override
		public int getColumnWidth(int col) {
			return (int) (TextSource.fontMetrics.stringWidth(cols.get(col).toString()) * 1.5);
		}

		@Override
		public String getColumnName(int col) {
			return cols.get(col).toString();
		}

		@Override
		public Object getValueAt(int row, int col) {
			if (row < getRowCount() && col < getColumnCount()) {
				String temp = data.getArray().getAt(row).toString();
				String blah[] = temp.split("\t");

				if (col < blah.length) {
					AttributedString tempa = AttributedString.CreateAttributedString(blah[col]);
					tempa.assignWidths();

					return tempa;
				}
			}

			return null;
		}

		@Override
		public boolean isSortable(int col) {
			return true;
		}
	}
}
