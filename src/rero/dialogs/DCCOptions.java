package rero.dialogs;

import java.awt.FlowLayout;

import rero.client.dcc.LocalInfo;
import rero.config.ClientDefaults;
import rero.dck.DGroup;
import rero.dck.DItem;
import rero.dck.DMain;

public class DCCOptions extends DMain {
	@Override
	public String getTitle() {
		return "DCC Options";
	}

	@Override
	public String getDescription() {
		return "DCC Options";
	}

	@Override
	public void setupDialog() {
		addBlankSpace();

		addSelectInput("dcc.onsend", 0, new String[] { "Ask", "Auto Accept", "Ignore" }, "On Send Request:  ", 'S', 55);
		addSelectInput("dcc.exists", 0, new String[] { "Ask", "Overwrite", "Resume", "Ignore" }, "       If file exists: ", 'I', 55);

		addBlankSpace();
		addDirectoryInput("dcc.saveto", ClientDefaults.dcc_saveto, "Download directory: ", 'd', 55);
		addCheckboxInput("dcc.fillspaces", ClientDefaults.dcc_fillspaces, "Fill spaces in filename when sending file", 'F', FlowLayout.LEFT);

		addBlankSpace();
		addSelectInput("dcc.onchat", 0, new String[] { "Ask", "Auto Accept", "Ignore" }, "On Chat Request:  ", 'C', 55);

		addBlankSpace();

		DItem itema = addOptionInput("dcc.localinfo", ClientDefaults.dcc_localinfo, new String[] { LocalInfo.RESOLVE_FROM_SERVER, LocalInfo.RESOLVE_AUTOMATIC }, "DCC IP Address: ", 'I', 55);
		itema.getComponent().setToolTipText("<html>Select how your local host info is determined.<br>If neither of the automatic options work an<br>IP address can be manually input into this<br>textfield.</html>");

		DGroup temp = addDialogGroup(new DGroup("DCC Ports", 50) {
			@Override
			public void setupDialog() {
				addStringInput("dcc.low", ClientDefaults.dcc_low + "", "  First:  ", 'f', 60);
				addStringInput("dcc.high", ClientDefaults.dcc_high + "", "  Last:   ", 'l', 60);
			}
		});
	}
}
