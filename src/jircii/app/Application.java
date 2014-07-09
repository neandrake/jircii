package jircii.app;

import java.net.URI;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import rero.config.ClientDefaults;
import rero.config.ClientState;
import rero.ident.IdentDaemon;
import rero.test.ProxySettings;
import rero.test.QuickConnect;

public class Application {
	private static Application instance = null;

	private static synchronized void setInstance(Application instance) {
		Application.instance = instance;
	}

	public static synchronized Application getInstance() {
		return instance;
	}

	private NativeSystem nativeSystem;
	private SystemCapabilities systemCapabilities;
	private ApplicationWindow window;

	public Application() {
		nativeSystem = NativeSystem.getNativeSystem();
		systemCapabilities = new SystemCapabilities(this);
		nativeSystem.registerCapabilities(systemCapabilities);
	}

	private void createAndShowWindow() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				window = new ApplicationWindow();
				window.setVisible(true);
			}
		});
	}

	public ApplicationWindow getWindow() {
		return window;
	}
	
	public SystemCapabilities getCapabilities() {
		return systemCapabilities;
	}
	
	public NativeSystem getNativeSystem() {
		return nativeSystem;
	}

	private void parseArgs(String[] args) {
		int ARGNO = 0;

		if ((ARGNO + 1) < args.length && args[ARGNO].equals("-settings")) {
			ClientState.setBaseDirectory(args[ARGNO + 1]);
			ARGNO += 2;
		}

		try {
			if ((ARGNO + 1) < args.length && args[ARGNO].equals("-lnf")) {
				UIManager.setLookAndFeel(args[ARGNO + 1]);
				ARGNO += 2;
			} else if (ClientState.getClientState().isOption("ui.native", ClientDefaults.ui_native)) {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} else {
				UIManager.LookAndFeelInfo[] feels = UIManager.getInstalledLookAndFeels();
				for (int x = 0; x < feels.length; x++) {
					if (feels[x].getName().equals("Nimbus")) {
						UIManager.setLookAndFeel(feels[x].getClassName());
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Could not load specified look and feel, using system default");
			e.printStackTrace();
		}

		ProxySettings.initialize();

		IdentDaemon.initialize();

		if (ARGNO < args.length && (args[ARGNO].indexOf("irc://") > -1 || args[ARGNO].indexOf("ircs://") > -1)) {
			try {
				new QuickConnect(new URI(args[ARGNO]));
			} catch (Exception urlex) {
				urlex.printStackTrace();
			}
			ARGNO++;
		}
	}

	public static void main(String args[]) {
		setInstance(new Application());

		getInstance().parseArgs(args);
		getInstance().createAndShowWindow();
	}
}
