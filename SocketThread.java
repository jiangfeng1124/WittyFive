package ricm.mca2005.fivechess;

import java.io.*;
import javax.swing.*;

public class SocketThread extends Thread {
	Chess chess;
	Chessman chessman;
	boolean isRun = true;
	boolean halt = false;
	// byte message[]=new byte[2];
	Message message;
	InputStream in;
	OutputStream out;

	public SocketThread(Chess che) {
		chess = che;
		in = chess.in;
		out = chess.out;
		isRun = true;
		chessman = chess.chessman;
		message = che.message;
	}

	public void run() {
		int length;
		while (isRun && chess.NetOK) {
			try {
				length = message.read();
				if (length == 4) {
					// message.decode();
					if (message.command == 0) {
						if (chess.player1)
							chess.wait = false;
						if (chess.player2)
							chess.wait = true;
						JOptionPane.showMessageDialog(null,
								"Competitor starts a new game", "New game",
								JOptionPane.INFORMATION_MESSAGE);
						chess.chessman.init();
						chess.board.draw();
					}
					if (message.command == 1) {
						JOptionPane.showMessageDialog(null,
								"Competitor clears the chess broad",
								"New game", JOptionPane.INFORMATION_MESSAGE);
						chess.chessman.init();
						chess.board.draw();

					}
					if (message.command == 2) {
						messageDeal(message.row, message.col);
					}
					if (message.command == 10) {
						JOptionPane.showMessageDialog(null,
								"Competitor disconnect", "Network",
								JOptionPane.INFORMATION_MESSAGE);
						StopRun();
						chess.disconnect();
						halt = true;
					}
				}
			} catch (Exception e) {
				System.out.println(e);
				chess.NetOK = false;
				StopRun();
				chess.frame.setTitle("FiveChess RICM-MCA2005");
			}

		}
	}

	public void StopRun() {
		isRun = false;
		chess.NetOK = false;
	}

	public void messageDeal(int row, int col) {
		chess.wait = true;
		if (chess.player1)
			chessman.point[row][col] = 1;
		if (chess.player2)
			chessman.point[row][col] = 0;
		chess.board.draw();
		chess.frame.setTitle("Your turn...");
		chess.whowin();
		chess.wait = false;

	}

}
