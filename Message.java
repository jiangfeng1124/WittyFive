package ricm.mca2005.fivechess;

import java.io.IOException;

import javax.swing.JOptionPane;

public class Message {

	Chess chess;
	public byte[] bytebuf;
	public int command; // 0 - start new game
	// 1 - clear board
	// 2 - put point
	// 10 - disconnect
	public int row, col;

	Message(Chess che) {
		chess = che;
		bytebuf = new byte[4];
	}

	void encode() {
		// bytebuf=new byte[4];
		bytebuf[0] = 55;
		bytebuf[1] = (byte) command;
		bytebuf[2] = (byte) row;
		bytebuf[3] = (byte) col;
	}

	void decode() {
		if ((bytebuf.length != 4) || (bytebuf[0] != 55)) {
			command = -1;
			row = -1;
			col = -1;
			return;
		}
		command = (int) bytebuf[1];
		row = (int) bytebuf[2];
		col = (int) bytebuf[3];
	}

	int read() {
		int length;
		// System.out.println("Now read message... command="+command);
		try {
			length = chess.in.read(bytebuf);
			decode();
			return length;
		} catch (Exception e) {
			if (chess.disconnecting == 0)
				JOptionPane.showMessageDialog(null, "Connect error", "Error",
						JOptionPane.ERROR_MESSAGE);
			chess.NetOK = false;
			chess.disconnect();
			return -1;
		}
	}

	void send() {
		encode();
		// System.out.println("Now send message...command="+command);
		try {
			chess.out.write(bytebuf);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connect error", "Error",
					JOptionPane.ERROR_MESSAGE);
			chess.NetOK = false;
			chess.disconnect();
		}
	}

}
