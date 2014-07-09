/* 
    jerk.irc.dcc.ChatClient [ created - 1/2/02 ]

    Author: Raphael Mudge (rsmudge@mtu.edu)
    
    Description: 
    handles the details of talking in a DCC connection,  nothing to do
    with actually establishing the connection.  Talks to a JerkEngine
    reference.

    Documentation: 
    n/a

    Changelog:
       
 */

package rero.dcc;

import java.io.BufferedReader;
import java.io.PrintStream;

import rero.config.ClientState;
import rero.config.ClientStateListener;
import rero.ircfw.interfaces.FrameworkConstants;

public class Chat extends ProtocolDCC implements ClientStateListener {
	protected BufferedReader input;
	protected PrintStream output;

	public Chat(String _nickname) {
		nickname = _nickname;
		ClientState.getClientState().addClientStateListener("client.encoding", this);
	}

	@Override
	public void propertyChanged(String property, String value) {
		try {
			if (input != null && socket.isConnected()) {
				ClientState.getClientState();
				input = new BufferedReader(ClientState.getProperInputStream(socket.getInputStream()));
				ClientState.getClientState();
				output = ClientState.getProperPrintStream(socket.getOutputStream());
			}
		} catch (Exception ex) {
			System.out.println("Unable to switch encodings...");
			ex.printStackTrace();
		}
	}

	/** returns the nickname of who we are having a *chat* with */
	@Override
	public String getNickname() {
		return nickname;
	}

	/** sends a message to the chat */
	public void sendln(String text) {
		output.println(text);
	}

	@Override
	public int getTypeOfDCC() {
		return DCC_CHAT;
	}

	@Override
	public void run() {
		if (socket == null || !socket.isConnected()) {
			return;
		}

		try {
			socket.setKeepAlive(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		fireEvent("CHAT_OPEN", null);

		String text;

		try {
			ClientState.getClientState();
			output = ClientState.getProperPrintStream(socket.getOutputStream());
			ClientState.getClientState();
			input = new BufferedReader(ClientState.getProperInputStream(socket.getInputStream()));

			while (socket.isConnected()) {
				text = input.readLine();
				if (text == null) {
					fireEvent("CHAT_CLOSE", "closed");
					return;
				}

				idleTime = System.currentTimeMillis();

				fireEvent("CHAT", text);
			}

			fireEvent("CHAT_CLOSE", "closed");
		} catch (Exception ex) {
			ex.printStackTrace();

			fireError(ex.getMessage());
		}
	}

	public void fireEvent(String event, String description) {
		eventData.clear();

		eventData.put(FrameworkConstants.$NICK$, getNickname());
		eventData.put(FrameworkConstants.$EVENT$, event);
		eventData.put("$this", this.toString());

		if (description != null) {
			eventData.put(FrameworkConstants.$DATA$, getNickname() + " " + description);
			eventData.put(FrameworkConstants.$PARMS$, description);
		}

		dispatcher.dispatchEvent(eventData);
	}

	@Override
	public void fireError(String description) {
		eventData.put(FrameworkConstants.$NICK$, getNickname());
		eventData.put(FrameworkConstants.$EVENT$, "CHAT_CLOSE");
		eventData.put(FrameworkConstants.$DATA$, getNickname() + " " + description);
		eventData.put(FrameworkConstants.$PARMS$, description);
		eventData.put("$this", this.toString());

		dispatcher.dispatchEvent(eventData);
	}
}
