package jircii.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import rero.config.ClientState;
import rero.gui.SessionManager;

public class ApplicationWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private SessionManager sessionManager;

	public ApplicationWindow() {
		super("jIRCii");
		
		sessionManager = new SessionManager(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(sessionManager, BorderLayout.CENTER);
		setIconImage(ClientState.getClientState().getIcon("jirc.icon", "jicon.jpg").getImage());
		setBounds(ClientState.getClientState().getBounds("desktop.bounds", Toolkit.getDefaultToolkit().getScreenSize(), new Dimension(640, 480)));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent ev) {
				Application.getInstance().getCapabilities().quit(null);
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent ev) {
				if (ClientState.getClientState().isOption("desktop.relative", false) || ClientState.getClientState().isOption("window.relative", false) || ClientState.getClientState().isOption("statusbar.relative", false)) {
					validate();
					ClientState.getClientState().fireChange("desktop");
					ClientState.getClientState().fireChange("window");
					ClientState.getClientState().fireChange("statusbar");
					repaint();
				}
			}
		});
	}
	
	public SessionManager getSessionManager() {
		return sessionManager;
	}
}
