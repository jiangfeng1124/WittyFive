package ricm.mca2005.fivechess;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class Chessboard extends JPanel {

	Image boardpic, whitepoint, blackpoint;
	Chess chess;

	// Image boardpic2,whitepoint2,blackpoint2;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(boardpic, 0, 0, this);
		// g.drawImage(boardpic2, 0, 0, this);

		g.setColor(Color.BLACK);
		g.drawRect(15, 15, 414, 414);
		for (int i = 1; i < 19; i++)
			g.drawLine(15 + 23 * i, 15, 15 + 23 * i, 429);
		for (int i = 1; i < 19; i++)
			g.drawLine(15, 15 + 23 * i, 429, 15 + 23 * i);

		if (chess.chessman != null) {
			for (int i = 0; i < 19; i++)
				for (int j = 0; j < 19; j++) {
					if (chess.chessman.point[i][j] == 0) {
						g.setColor(Color.black);
						g.fillOval(j * 23 + 5, i * 23 + 5, 21, 21);
						g.drawImage(blackpoint, j * 23 + 5, i * 23 + 5, this);
						// g.drawImage(whitepoint2, j*23+5, i*23+5, this);
					}
					if (chess.chessman.point[i][j] == 1) {
						g.setColor(Color.white);
						g.fillOval(j * 23 + 5, i * 23 + 5, 22, 22);
						g.drawImage(whitepoint, j * 23 + 5, i * 23 + 5, this);
						// g.drawImage(blackpoint2, j*23+5, i*23+5, this);
					}

				}

		}

	}

	public void draw() {
		repaint();
	}

	/**
	 * Create the panel
	 */
	Chessboard(Chess che) {
		super();
		chess = che;
		setLayout(null);
		setBackground(Color.ORANGE);
		// boardpic = Toolkit.getDefaultToolkit().getImage("images/board.jpg");
		// blackpoint =
		// Toolkit.getDefaultToolkit().getImage("images/black.jpg");
		// whitepoint =
		// Toolkit.getDefaultToolkit().getImage("images/white.jpg");
		boardpic = Toolkit.getDefaultToolkit().getImage(
				ClassLoader.getSystemResource("images/board.jpg"));
		blackpoint = Toolkit.getDefaultToolkit().getImage(
				ClassLoader.getSystemResource("images/black.jpg"));
		whitepoint = Toolkit.getDefaultToolkit().getImage(
				ClassLoader.getSystemResource("images/white.jpg"));
		//
	}

}
