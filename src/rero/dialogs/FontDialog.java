package rero.dialogs;

import java.awt.FlowLayout;

import rero.config.ClientDefaults;
import rero.dck.DMain;

public class FontDialog extends DMain {
	@Override
	public String getTitle() {
		return "Font Setup";
	}

	@Override
	public String getDescription() {
		return "Font Settings";
	}

	@Override
	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();
		addBlankSpace();
		addBlankSpace();
		addBlankSpace();

		addLabelNormal("Client Font:", FlowLayout.LEFT);
		addFontInput("ui.font", ClientDefaults.ui_font);

		addBlankSpace();

		addCheckboxInput("ui.antialias", ClientDefaults.ui_antialias, "Enable text anti-aliasing", 'A', FlowLayout.CENTER);

		addBlankSpace();

		addCharsetInput("client.encoding", "Use Charset:", 'c', 75);

		addBlankSpace();
		addBlankSpace();
		addBlankSpace();
		addBlankSpace();
	}
}
