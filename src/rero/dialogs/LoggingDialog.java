package rero.dialogs;

import java.awt.FlowLayout;

import rero.config.ClientDefaults;
import rero.dck.DGroup;
import rero.dck.DItem;
import rero.dck.DMain;
import rero.dck.items.CheckboxInput;

public class LoggingDialog extends DMain {
	@Override
	public String getTitle() {
		return "Setup Logs";
	}

	@Override
	public String getDescription() {
		return "Message Logging Setup";
	}

	@Override
	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();

		DGroup temp = addDialogGroup(new DGroup("Logging Options", 15) {
			@Override
			public void setupDialog() {

				addBlankSpace();
				DItem tempc = addDirectoryInput("log.saveto", ClientDefaults.log_saveto, "Log Directory: ", 'D', 10);

				addBlankSpace();

				DItem tempa = addCheckboxInput("log.strip", ClientDefaults.log_strip, "Strip colors from text", 'S', FlowLayout.LEFT);
				DItem tempb = addCheckboxInput("log.timestamp", ClientDefaults.log_timestamp, "Timestamp logged messages", 'T', FlowLayout.LEFT);
				addBlankSpace();
			}
		});

		addBlankSpace();

		CheckboxInput boxed = addCheckboxInput("log.enabled", ClientDefaults.log_enabled, "Enable Logging", 'E', FlowLayout.CENTER);
		boxed.addDependent(temp);

		addBlankSpace();
	}
}
