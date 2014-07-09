package rero.dialogs;

import java.awt.FlowLayout;

import rero.config.ClientDefaults;
import rero.dck.DGroup;
import rero.dck.DMain;
import rero.dck.items.CheckboxInput;

public class SwitchBarDialog extends DMain {
	@Override
	public String getTitle() {
		return "Switchbar";
	}

	@Override
	public String getDescription() {
		return "Switchbar Options";
	}

	@Override
	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();

		DGroup temp = addDialogGroup(new DGroup("Switchbar Options", 30) {
			@Override
			public void setupDialog() {
				addSelectInput("switchbar.position", 0, new String[] { "Top", "Bottom", "Left", "Right" }, "Position:  ", 'P', 25);
				// addSelectInput("switchbar.position", 0, new String[] { "Top", "Bottom" }, "Position:  ", 'P', 25);
				addColorInput("switchbar.color", ClientDefaults.switchbar_color, "Activity Color", 'A');
				addCheckboxInput("switchbar.fixed", ClientDefaults.switchbar_fixed, "Fixed width switchbar buttons", 'F', FlowLayout.LEFT);
				addCheckboxInput("switchbar.sort", ClientDefaults.switchbar_sort, "Sort buttons alphabetically", 'F', FlowLayout.LEFT);
			}
		});

		CheckboxInput boxed = addCheckboxInput("switchbar.enabled", true, "Enable Switchbar", 'S', FlowLayout.CENTER);
		boxed.addDependent(temp);
	}
}
