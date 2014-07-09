package rero.dck.items;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import jircii.app.Application;
import rero.config.ClientState;
import rero.config.StringList;
import rero.dck.DCapabilities;
import rero.dck.DItem;
import rero.dck.DParent;
import rero.dialogs.server.Server;
import rero.dialogs.server.ServerData;
import rero.dialogs.server.ServerGroup;
import rero.dialogs.toolkit.ADialog;
import rero.dialogs.toolkit.APanel;
import rero.dialogs.toolkit.LabelGroup;

public class ServerList extends JPanel implements DItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int height;
	protected int width;

	protected JButton connect, edit;
	protected JComboBox network;

	protected JList list;

	protected ServerData data;
	protected StringList autoConnect;
	protected JComponent component;

	protected DCapabilities capabilities;

	public ServerList(ServerData _data, int _width, int _height, DCapabilities _capabilities) {
		data = _data;

		autoConnect = ClientState.getClientState().getStringList("auto.connect");

		capabilities = _capabilities;

		width = _width;
		height = _height;

		component = this;

		setBorder(BorderFactory.createEmptyBorder(0, width, 0, width));

		setLayout(new BorderLayout());

		connect = new JButton("Connect");
		connect.setMnemonic('C');

		edit = new JButton("Edit");
		edit.setMnemonic('E');

		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER));

		buttons.add(connect);
		buttons.add(edit);

		network = new JComboBox(new NetworkListModel());
		network.setPrototypeDisplayValue("Random Servers");

		buttons.add(network);

		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				ServerEditorDialog temp = new ServerEditorDialog(component);
				temp.getListbox().setSelectedIndex(list.getSelectedIndex());
				temp.getListbox().ensureIndexIsVisible(list.getSelectedIndex());
				temp.showDialog(component);
				data.update();
				((ServerListModel) (list.getModel())).fireChange();
				((NetworkListModel) (network.getModel())).fireChange();
			}
		});

		network.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				data.setActive((ServerGroup) network.getSelectedItem());
				((ServerListModel) (list.getModel())).fireChange();
			}
		});

		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				handleConnectAction();
			}
		});

		add(buttons, BorderLayout.SOUTH);

		ServerListModel lmodel = new ServerListModel();

		list = new JList(lmodel);
		list.setCellRenderer(lmodel);

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {
				if (ev.getClickCount() >= 2) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							handleConnectAction();
						}
					});
					ev.consume();
				}
			}
		});

		add(new JScrollPane(list, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

		setPreferredSize(new Dimension(0, height));
	}

	public void handleConnectAction() {
		capabilities.forceSave();

		if (Application.getInstance().getWindow().getSessionManager().getActiveSession().getCapabilities().isConnected()) {
			Application.getInstance().getWindow().getSessionManager().addSession();
		}

		if (list.getSelectedValue() != null) {
			Server connectToMe = (Server) list.getSelectedValue();
			Application.getInstance().getWindow().getSessionManager().getActiveSession().executeCommand(connectToMe.getCommand());
			capabilities.closeDialog();
		} else {
			JOptionPane.showMessageDialog(null, "Please select a server", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void setParent(DParent parent) {

	}

	@Override
	public void setEnabled(boolean b) {}

	@Override
	public void save() {
		if (network.getSelectedItem() != null) {
			ClientState.getClientState().setInteger("sdialog.selected", ((ServerGroup) network.getSelectedItem()).getNumber());
		}
		data.save();
	}

	@Override
	public void refresh() {
		try {
			network.setSelectedIndex(ClientState.getClientState().getInteger("sdialog.selected", 0));
			data.update();
			((ServerListModel) list.getModel()).fireChange();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public int getEstimatedWidth() {
		return 0;
	}

	@Override
	public void setAlignWidth(int width) {}

	public void setParentVariable(String parent) {

	}

	@Override
	public JComponent getComponent() {
		return this;
	}

	protected class ServerEditorDialog extends JDialog {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected JList alist;

		public String showDialog(Component comp) {
			setLocationRelativeTo(comp);
			setVisible(true);
			return "";
		}

		public JList getListbox() {
			return alist;
		}

		public ServerEditorDialog(JComponent comp) {
			super(JOptionPane.getFrameForComponent(comp), "Server Editor", true);

			getContentPane().setLayout(new BorderLayout());

			ServerListModel lmodel = new ServerListModel();

			alist = new JList(lmodel);
			alist.setCellRenderer(lmodel);

			JPanel lpanel = new JPanel();
			lpanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			lpanel.setLayout(new BorderLayout());
			lpanel.add(new JScrollPane(alist, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

			getContentPane().add(lpanel, BorderLayout.CENTER);

			JToolBar buttons = new JToolBar();
			buttons.setFloatable(false);
			buttons.setLayout(new FlowLayout());

			JButton add, edit, remove, sort, ok;

			add = new JButton("Add");
			add.setMnemonic('A');
			buttons.add(add);

			edit = new JButton("Edit");
			edit.setMnemonic('E');
			buttons.add(edit);

			remove = new JButton("Remove");
			remove.setMnemonic('R');
			buttons.add(remove);

			buttons.addSeparator();

			sort = new JButton("Sort");
			sort.setMnemonic('S');
			buttons.add(sort);

			buttons.addSeparator();

			ok = new JButton("Close");
			ok.setMnemonic('C');
			buttons.add(ok);

			edit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					EditServerInfo editor = new EditServerInfo();
					editor.setupDialog(alist.getSelectedValue());

					ADialog dialog = new ADialog(component, "Edit Server", editor, alist.getSelectedValue());
					dialog.setSize(new Dimension(330, 280));
					if (dialog.showDialog(component) != null) // the returned value is modified directly.
					{
						((ServerListModel) alist.getModel()).fireChange();
					}
				}
			});

			add.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					EditServerInfo editor = new EditServerInfo();
					editor.setupDialog(null);

					ADialog dialog = new ADialog(component, "New Server", editor, null);
					dialog.setSize(new Dimension(330, 280));

					Server temp = (Server) dialog.showDialog(component);
					if (temp != null) {
						data.addServer(temp);
						data.update();
						((ServerListModel) alist.getModel()).fireChange();
					}
				}
			});

			remove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					if (alist.getSelectedValue() != null) {
						data.removeServer((Server) alist.getSelectedValue());
						data.update();
						((ServerListModel) alist.getModel()).fireChange();
					}
				}
			});

			sort.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					data.sort();
					data.update();
					((ServerListModel) alist.getModel()).fireChange();
				}
			});

			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					setVisible(false);
				}
			});

			getContentPane().add(buttons, BorderLayout.SOUTH);

			setSize(400, 200);
		}

	}

	protected class ServerListModel extends AbstractListModel implements ListCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected JLabel cell = new JLabel();

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			Server svalue = (Server) value;

			if (index < 0 || index > getSize()) {
				return cell;
			}

			cell.setToolTipText(svalue.getHost() + ":" + svalue.getPorts());

			Color fore = UIManager.getColor("TextField.foreground");

			if (autoConnect.isValue(svalue.getHost())) {
				fore = Color.blue;
			}

			if (isSelected) {
				cell.setOpaque(true);
				cell.setBackground(UIManager.getColor("TextField.selectionBackground"));

				if (fore == Color.blue) {
					cell.setForeground(fore);
				} else {
					cell.setForeground(UIManager.getColor("TextField.selectionForeground"));
				}

				cell.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));

				if (svalue.getNetwork().equals("")) {
					cell.setText("Random: " + svalue.getHost() + ":" + svalue.getPorts());
				} else {
					if (svalue.isSecure()) {
						cell.setText(svalue.getNetwork() + ": " + svalue.getHost() + ":" + svalue.getPorts() + " (SSL)");
					} else {
						cell.setText(svalue.getNetwork() + ": " + svalue.getHost() + ":" + svalue.getPorts());
					}
				}
			} else {
				cell.setOpaque(false);
				cell.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
				cell.setForeground(fore);

				String description = svalue.getDescription();
				String network = svalue.getNetwork();

				if (description == null || description.equals("")) {
					description = svalue.getHost();
				}

				if (network == null || network.equals("")) {
					network = "Random";
				}

				cell.setText(network + ": " + description);
			}

			return cell;
		}

		public void fireChange() {
			fireContentsChanged(this, 0, -1);
		}

		@Override
		public Object getElementAt(int index) {
			return data.getServers().get(index);
		}

		@Override
		public int getSize() {
			return data.getServers().size();
		}
	}

	protected class NetworkListModel extends AbstractListModel implements ComboBoxModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected Object selected;

		@Override
		public Object getSelectedItem() {
			return selected;
		}

		@Override
		public void setSelectedItem(Object item) {
			selected = item;
		}

		public void fireChange() {
			fireContentsChanged(this, 0, -1);
		}

		@Override
		public Object getElementAt(int index) {
			return data.getGroups().get(index);
		}

		@Override
		public int getSize() {
			return data.getGroups().size();
		}
	}

	protected class EditServerInfo extends APanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected JTextField description = new JTextField();
		protected JTextField host = new JTextField();
		protected JTextField portRange = new JTextField();
		protected JTextField network = new JTextField();
		protected JCheckBox isSSL = new JCheckBox("Server requires SSL");
		protected JCheckBox isStartup = new JCheckBox("Connect to server on client startup");
		protected JPasswordField password = new JPasswordField();

		@Override
		public void setupDialog(Object value) {
			if (value == null) {
				setBorder(BorderFactory.createTitledBorder(" Create New Server "));
			} else {
				setBorder(BorderFactory.createTitledBorder(" Edit Server Information "));
			}

			JLabel description_l, host_l, portRange_l, network_l, password_l;

			LabelGroup labels = new LabelGroup();

			description_l = new JLabel(" Description: ");
			host_l = new JLabel(" Hostname: ");
			portRange_l = new JLabel(" Port(s): ");
			network_l = new JLabel(" Network: ");
			password_l = new JLabel(" Password: ");

			labels.addLabel(description_l);
			labels.addLabel(host_l);
			labels.addLabel(portRange_l);
			labels.addLabel(network_l);
			labels.addLabel(password_l);

			addComponent(mergeComponents(description_l, description, 20));
			addComponent(mergeComponents(host_l, host, 20));
			addComponent(mergeComponents(portRange_l, portRange, 100));
			addComponent(mergeComponents(network_l, network, 20));
			addComponent(isSSL);
			addComponent(mergeComponents(password_l, password, 20));
			addComponent(isStartup);

			labels.sync();

			if (value != null) {
				Server temp = (Server) value;
				description.setText(temp.getDescription());
				host.setText(temp.getHost());
				portRange.setText(temp.getPorts());
				network.setText(temp.getNetwork());
				isSSL.setSelected(temp.isSecure());

				isStartup.setSelected(autoConnect.isValue(temp.getHost()));

				password.setText(temp.getPassword());
			}
		}

		@Override
		public Object getValue(Object value) {
			Server server;

			if (value != null) {
				server = (Server) value;
			} else {
				server = new Server();
			}

			if (isStartup.isSelected() && !autoConnect.isValue(host.getText())) {
				// System.out.println("Adding: " + host.getText());

				autoConnect.add(host.getText());
				autoConnect.save();
			} else if (!isStartup.isSelected() && autoConnect.isValue(host.getText())) {
				// System.out.println("Removing: " + host.getText());

				autoConnect.remove(host.getText());
				autoConnect.save();
			}

			server.setValues(description.getText(), host.getText(), portRange.getText(), network.getText(), isSSL.isSelected(), password.getText());
			return server;
		}
	}
}
