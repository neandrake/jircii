package rero.dialogs;

import java.awt.FlowLayout;

import rero.config.ClientDefaults;
import rero.dck.DGroup;
import rero.dck.DMain;
import rero.dck.items.CheckboxInput;

public class WindowsDialog extends DMain {
	@Override
	public String getTitle() {
		return "Window UI";
	}

	@Override
	public String getDescription() {
		return "Window User Interface Options";
	}

	@Override
	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();

		DGroup temp2 = addDialogGroup(new DGroup("Channel Users List", 30) {
			@Override
			public void setupDialog() {
				addSelectInput("listbox.position", 1, new String[] { "Left", "Right" }, "Position:  ", 'P', 25);
				addStringInput("listbox.width", ClientDefaults.listbox_width + "", "Width:  ", 'N', 100);
			}
		});

		addBlankSpace();
		DGroup temp3 = addDialogGroup(new DGroup() {
			@Override
			public void setupDialog() {
				addStringInput("ui.max_history", Integer.toString(ClientDefaults.max_history), "Max History:  ", 'M', 100);
			}
		});

		addBlankSpace();
		CheckboxInput boxed2 = addCheckboxInput("listbox.enabled", true, "Enable Channel Users Listbox", 'C', FlowLayout.CENTER);
		boxed2.addDependent(temp2);

		addBlankSpace();

		addCheckboxInput("ui.showsbar", true, "Show statusbar in windows", 'S', FlowLayout.CENTER);

		addBlankSpace();

		addColorInput("ui.editcolor", ClientDefaults.ui_editcolor, "Editbox text color", 'E');

		addBlankSpace();
	}
}
