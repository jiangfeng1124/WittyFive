package ricm.mca2005.fivechess;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class ServerThread extends Thread {
	boolean connected = false;
	Chess chess;

	public ServerThread(Chess che) {
		chess = che;
	}

	public void run() {
		if (!connected) {
			try {
				chess.serversocket = new ServerSocket(chess.serverport);
				chess.frame.setTitle("Waiting for client...");
				chess.conn = chess.serversocket.accept();
				connected = true;
				chess.in = chess.conn.getInputStream();
				chess.out = chess.conn.getOutputStream();
				chess.wait = false;
				chess.NetPlay = true;
				chess.player1 = true;
				chess.player2 = false;
				chess.setThread();
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Network error!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}

}
