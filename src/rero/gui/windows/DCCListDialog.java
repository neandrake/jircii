package rero.gui.windows;

import java.util.HashMap;

import rero.client.DataStructures;
import rero.client.output.OutputCapabilities;
import rero.dcc.DataDCC;
import rero.dcc.GenericDCC;
import rero.dcc.ProtocolDCC;
import rero.gui.toolkit.GeneralListModel;
import rero.ircfw.interfaces.ChatListener;
import rero.ircfw.interfaces.FrameworkConstants;
import rero.util.TimerListener;
import text.AttributedString;
// sorted JTable code...

public class DCCListDialog extends GeneralListDialog implements TimerListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DCCListDialog() {
		super("DCC Sessions", "dcc", new DCCListModel());
	}

	@Override
	public void timerExecute() {
		table.repaint();
	}

	private class DCCListener implements ChatListener, FrameworkConstants {
		@Override
		public boolean isChatEvent(String event, HashMap eventData) {
			return (event.indexOf("CHAT_") > -1 || event.indexOf("SEND_") > -1 || event.indexOf("RECEIVE_") > -1);
		}

		@Override
		public int fireChatEvent(HashMap data) {
			model.fireTableDataChanged();
			return EVENT_DONE;
		}
	}

	@Override
	public void init() {
		((DCCListModel) model).installData((DataDCC) capabilities.getDataStructure(DataStructures.DataDCC), capabilities.getOutputCapabilities());
		model.fireTableDataChanged();

		capabilities.getTimer().addTimer(this, 1000);
		capabilities.addChatListener(new DCCListener());
	}

	private static class DCCListModel extends GeneralListModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected DataDCC data;
		protected OutputCapabilities output;

		// private HashMap event = new HashMap();

		@Override
		public HashMap getEventHashMap(int row) {
			HashMap event = new HashMap();

			GenericDCC temp = (GenericDCC) getConnections().get(row);
			event.put("$this", temp.getImplementation().toString());

			return event;
		}

		public void installData(DataDCC d, OutputCapabilities o) {
			data = d;
			output = o;
		}

		@Override
		public void sortColumn(int col, boolean ascending) {
			fireTableDataChanged();
		}

		private java.util.List getConnections() {
			return data.getConnections(-1, ProtocolDCC.STATE_OPEN);
		}

		@Override
		public int getRowCount() {
			if (data == null) {
				return 0;
			}

			return getConnections().size();
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public int getColumnWidth(int col) {
			if (col == 0) {
				return 75;
			}

			if (col == 1) {
				return 100;
			}

			return 400;
		}

		@Override
		public String getColumnName(int col) {
			switch (col) {
			case 0:
				return "Type";

			case 1:
				return "Nickname";

			case 2:
				return "Information";

			case 3:
				return "File";
			}

			return "Unknown";
		}

		@Override
		public Object getValueAt(int row, int col) {
			if (row >= getRowCount()) {
				return null;
			}

			HashMap temp = getEventHashMap(row);
			String text = "";

			switch (col) {
			case 0:
				text = output.parseSet(temp, "DCC_LIST_TYPE");
				break;

			case 1:
				text = output.parseSet(temp, "DCC_LIST_NICK");
				break;

			case 2:
				text = output.parseSet(temp, "DCC_LIST_INFORMATION");
				break;

			case 3:
				text = output.parseSet(temp, "DCC_LIST_FILE");
				break;
			}

			AttributedString tempa = AttributedString.CreateAttributedString(text);
			tempa.assignWidths();

			return tempa;
		}

		@Override
		public boolean isSortable(int col) {
			return false;
		}
	}

	@Override
	public String getWindowType() {
		return "DCCStats";
	}
}
