package rero.dialogs.dcc;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import rero.dcc.ConnectDCC;
import rero.dcc.Receive;
import rero.dialogs.toolkit.ADialog;
import rero.dialogs.toolkit.APanel;
import rero.dialogs.toolkit.LabelGroup;
import rero.dialogs.toolkit.PlainLabel;
import rero.util.ClientUtils;

public class ResumeRequest extends APanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Receive receive; // the *receiving* end - *uNF*
	protected JComboBox options;

	public static int showDialog(Component component, ConnectDCC connect) {
		ResumeRequest request = new ResumeRequest();
		request.setupDialog(connect);

		ADialog dialog = new ADialog(component, "File Exists", request, null);
		dialog.pack();

		Integer temp = (Integer) dialog.showDialog(component);
		if (temp == null) {
			return -1;
		}

		return temp.intValue();
	}

	@Override
	public void setupDialog(Object value) {
		JPanel space = new JPanel();
		space.setPreferredSize(new Dimension(0, 15));

		JPanel space2 = new JPanel();
		space2.setPreferredSize(new Dimension(0, 15));

		LabelGroup labels = new LabelGroup();
		JLabel file, size, action;

		file = new JLabel("File: ");
		size = new JLabel("Size: ");
		action = new JLabel("Action: ");

		labels.addLabel(file);
		labels.addLabel(size);
		labels.addLabel(action);
		labels.sync(); // lines the labels up

		ConnectDCC info1 = (ConnectDCC) value;
		Receive info2 = (Receive) info1.getImplementation();

		PlainLabel ifile, isize;

		ifile = new PlainLabel(info2.getFile().getName());
		isize = new PlainLabel(ClientUtils.formatBytes((int) info2.getFile().length()) + " of " + ClientUtils.formatBytes(info2.getExpectedSize()));

		addComponent(new PlainLabel("File already exists..."));

		addComponent(space2);

		addComponent(mergeComponents(file, ifile));
		addComponent(mergeComponents(size, isize));

		addComponent(space);

		options = new JComboBox(new String[] { "Resume", "Rename", "Overwrite" });

		addComponent(mergeComponents(action, options));
	}

	@Override
	public Object getValue(Object defvalue) {
		return new Integer(options.getSelectedIndex());
	}
}
