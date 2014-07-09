package rero.dialogs;

import java.awt.FlowLayout;

import rero.config.ClientDefaults;
import rero.dck.DGroup;
import rero.dck.DMain;
import rero.dck.items.CheckboxInput;

public class ProxyDialog extends DMain {
	@Override
	public String getTitle() {
		return "Proxy Setup";
	}

	@Override
	public String getDescription() {
		return "SOCKS Proxy Settings";
	}

	@Override
	public void setupDialog() {
		addBlankSpace();
		addBlankSpace();

		DGroup temp = addDialogGroup(new DGroup("SOCKS Proxy Settings", 30) {
			@Override
			public void setupDialog() {
				addStringInput("proxy.server", ClientDefaults.proxy_server, "  Hostname: ", 'h', 60);
				addStringInput("proxy.port", ClientDefaults.proxy_port, "  Port:     ", 'o', 120);
				addBlankSpace();
				addStringInput("proxy.userid", ClientDefaults.proxy_userid, "  Username: ", 'u', 60);
				addStringInput("proxy.password", ClientDefaults.proxy_password, "  Password: ", 'p', 60);
			}
		});

		CheckboxInput boxed = addCheckboxInput("proxy.enabled", false, "Use Proxy Server", 'e', FlowLayout.CENTER);
		boxed.addDependent(temp);
	}
}
