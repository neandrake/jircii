package rero.dialogs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;

import rero.config.ClientState;
import rero.dck.DMain;
import rero.dck.DParent;
import rero.dck.DTab;
import rero.dck.items.ImageInput;
import rero.dck.items.ImagePreview;
import rero.dck.items.NormalInput;
import rero.dck.items.SelectInput;
import rero.dck.items.TabbedInput;

public class ImageDialog extends DMain implements DParent, ActionListener {
	protected String current = "desktop";
	protected TabbedInput tabs;
	protected ImagePreview preview;
	protected NormalInput label;

	protected void setupLabel() {
		label.setText("<html><b><u>" + current.substring(0, 1).toUpperCase() + current.substring(1, current.length()) + "</u></b>: background properties</html>");
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		tabs.save();
		current = ev.getActionCommand();

		setupLabel();

		tabs.refresh();
		preview.refresh();
		preview.repaint();
	}

	@Override
	public String getTitle() {
		return "Backgrounds";
	}

	@Override
	public void notifyParent(String variable) {
		ClientState.getClientState().fireChange(current);
		preview.repaint();

		if (variable.equals(current + ".bgtype")) {
			tabs.refresh();
		}
	}

	@Override
	public String getVariable(String variable) {
		return current + "." + variable;
	}

	@Override
	public String getDescription() {
		return "Client Background Images";
	}

	@Override
	public void setupDialog() {
		addBlankSpace();
		preview = (ImagePreview) addOther(new ImagePreview(35, 115));

		label = addLabelNormal("Editing Statusbar Options", 5);
		setupLabel();

		tabs = addTabbedInput();
		tabs.addTab(new BackgroundSetup());
		tabs.addTab(new EditColor());
		tabs.addTab(new TransformImage());
		tabs.addTab(new EditTint());

		tabs.setParent(this);
		preview.addActionListener(this);
	}

	protected class EditTint extends DTab {
		@Override
		public String getTitle() {
			return "Tint";
		}

		@Override
		public boolean isEnabled() {
			int type = ClientState.getClientState().getInteger(current + ".bgtype", 0);
			return type == 2 || type == 3;
		}

		@Override
		public String getDescription() {
			return "Adjust transparency settings";
		}

		@Override
		public void setupDialog() {
			addFloatInput("tint", 0f, "Alpha Tint: ");
		}
	}

	protected class EditColor extends DTab {
		@Override
		public String getTitle() {
			return "Setup";
		}

		@Override
		public boolean isEnabled() {
			return ClientState.getClientState().getInteger(current + ".bgtype", 0) != 0;
		}

		@Override
		public String getDescription() {
			return "Select color and image for background";
		}

		@Override
		public void setupDialog() {
			addOther(new ImageInput("image", "", "Background Image: ", 'I'));
			addColorInput("color", Color.black, "Select Background Color", 'c');
		}
	}

	protected class TransformImage extends DTab {
		@Override
		public String getTitle() {
			return "Transform";
		}

		@Override
		public boolean isEnabled() {
			return ClientState.getClientState().getInteger(current + ".bgtype", 0) == 3;
		}

		@Override
		public String getDescription() {
			return "Options for selected background image";
		}

		@Override
		public void setupDialog() {
			addSelectInput("bgstyle", 0, new String[] { "Tile", "Center", "Fill", "Stretch" }, "Transform Image: ", 'T', 25);
			addCheckboxInput("relative", false, "Align image with desktop", 'A');
		}
	}

	protected class BackgroundSetup extends DTab {
		private DefaultComboBoxModel desktop = new DefaultComboBoxModel(new String[] { "Default", "Solid Color", "           ", "Image" });
		private DefaultComboBoxModel statusbar = new DefaultComboBoxModel(new String[] { "Default", "Solid Color", "Transparent", "Image" });
		private DefaultComboBoxModel window = new DefaultComboBoxModel(new String[] { "       ", "Solid Color", "           ", "Image" });

		private SelectInput selector;

		@Override
		public void refresh() {
			if (current.equals("statusbar")) {
				selector.setModel(statusbar);
			}

			if (current.equals("desktop")) {
				selector.setModel(desktop);
			}

			if (current.equals("window")) {
				selector.setModel(window);
			}

			super.refresh();
		}

		@Override
		public String getTitle() {
			return "Type";
		}

		@Override
		public String getDescription() {
			return "Choose background options for the component";
		}

		@Override
		public void setupDialog() {
			selector = addSelectInput("bgtype", 0, new String[] { "Default", "Solid Color", "Transparent", "Image" }, "Type of background:  ", 'T', 0);
		}
	}
}
