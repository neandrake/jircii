package rero.dialogs;

import java.awt.FlowLayout;

import jircii.app.Application;
import rero.dck.DGroup;
import rero.dck.DMain;
import rero.dck.items.CheckboxInput;

public class AttentionDialog extends DMain {
	@Override
	public String getTitle() {
		return "Notifications";
	}

	@Override
	public String getDescription() {
		return "Activity Notification Options";
	}

	// Draw the option dialog
	// This will draw a different options list for each of the supported operating systems
	@Override
	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();

		if (Application.getInstance().getNativeSystem().isMac()) {
			// Draw Mac OS X attention options
			DGroup temp2 = addDialogGroup(new DGroup("Mac OS X", 10) {
				@Override
				public void setupDialog() {
					addLabelNormal("Choose one or more events on which to bounce the jIRCii dock icon:", FlowLayout.CENTER);
					CheckboxInput cbBounceDockIconMsg = addCheckboxInput("option.attention.osx.bouncedock.msg", true, "Private Message", 'P', FlowLayout.LEFT);
					CheckboxInput cbBounceDockIconNotice = addCheckboxInput("option.attention.osx.bouncedock.notice", true, "Notice", 'N', FlowLayout.LEFT);
					CheckboxInput cbBounceDockIconChannelChat = addCheckboxInput("option.attention.osx.bouncedock.channelchat", false, "Channel Chat", 'C', FlowLayout.LEFT);
					CheckboxInput cbBounceDockIconActions = addCheckboxInput("option.attention.osx.bouncedock.actions", false, "Disconnected, Killed, or Kicked from Channel", 'D', FlowLayout.LEFT);
					addBlankSpace();
					CheckboxInput cbBounceDockIconRepeat = addCheckboxInput("option.attention.osx.bouncedock.repeat", true, "Repeatedly Bounce Until Opened", 'R', FlowLayout.LEFT);
					addBlankSpace();
				}
			});
		} else if (Application.getInstance().getNativeSystem().isWindows()) {} else if (Application.getInstance().getNativeSystem().isLinux()) {}
	}
}
