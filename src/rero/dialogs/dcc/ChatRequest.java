package rero.dialogs.dcc;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rero.dcc.Chat;
import rero.dcc.ConnectDCC;
import rero.dialogs.toolkit.ADialog;
import rero.dialogs.toolkit.APanel;
import rero.dialogs.toolkit.LabelGroup;
import rero.dialogs.toolkit.PlainLabel;

public class ChatRequest extends APanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static boolean showDialog(Component component, ConnectDCC connection) {
		ChatRequest request = new ChatRequest();
		request.setupDialog(connection);

		ADialog dialog = new ADialog(component, "DCC Chat Request", request, null);
		dialog.pack();
		return (dialog.showDialog(component) != null);
	}

	@Override
	public void setupDialog(Object value) {
		JPanel space = new JPanel();
		space.setPreferredSize(new Dimension(0, 15));

		JPanel space2 = new JPanel();
		space2.setPreferredSize(new Dimension(0, 15));

		LabelGroup labels = new LabelGroup();
		JLabel user, host, blank;

		user = new JLabel("User: ");
		host = new JLabel("Host: ");
		blank = new JLabel("");

		labels.addLabel(user);
		labels.addLabel(blank);
		labels.sync(); // lines the labels up

		ConnectDCC info1 = (ConnectDCC) value;
		Chat info2 = (Chat) info1.getImplementation();

		PlainLabel iuser, ihost;

		iuser = new PlainLabel(info2.getNickname());
		ihost = new PlainLabel(info1.getHost() + ":" + info1.getPort());

		addComponent(new PlainLabel("A user is requesting a direct chat"));

		addComponent(space2);
		addComponent(mergeComponents(user, iuser));
		addComponent(mergeComponents(host, ihost));
		addComponent(space);
	}

	@Override
	public Object getValue(Object defvalue) {
		return "";
	}
}
