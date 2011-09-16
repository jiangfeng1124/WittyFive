package ricm.mca2005.fivechess;

public class Computer {
	int row, line;
	Chessline judge;
	int power;
	int player1lines[][];
	int player2lines[][];

	public Computer(Chessline judge) {
		this.judge = judge;
		player1lines = new int[19][19];
		player2lines = new int[19][19];
		power = 3;
	}

	private void count() {
		int i, j, k;
		for (i = 0; i < 19; i++)
			for (j = 0; j < 19; j++) {
				player1lines[i][j] = 0;
				player2lines[i][j] = 0;
				if (judge.chessman.point[i][j] == 2) {
					for (k = 0; k < 1020; k++) {
						if (judge.chessman.tableplayer1[k][i][j]) {

							switch (judge.chessline[k][0]) {
							case 1:
								player1lines[i][j] += 30 * power;
								break;
							case 2:
								player1lines[i][j] += 120 * power;
								break;
							case 3:
								player1lines[i][j] += 400 * power;
								break;
							case 4:
								player1lines[i][j] += 1000 * power;
								break;
							}
							switch (judge.chessline[k][1]) {
							case 1:
								player2lines[i][j] += 90;
								break;
							case 2:
								player2lines[i][j] += 360;
								break;
							case 3:
								// player2lines[i][j]+=900;
								player2lines[i][j] += 1500;
								break;
							case 4:
								// player2lines[i][j]+=2160;
								player2lines[i][j] += 5000;
								break;
							}
						}
					}
				}
			}
	}

	public void location() {
		int lines1max = 0, lines2max = 0;
		int l1max_i = 0, l1max_j = 0;
		int l2max_i = 0, l2max_j = 0;
		count();
		for (int i = 0; i < 19; i++)
			for (int j = 0; j < 19; j++) {
				if (judge.chessman.point[i][j] == 2) {
					if (player1lines[i][j] > lines1max) {
						lines1max = player1lines[i][j];
						l1max_i = i;
						l1max_j = j;
					}
					if (player2lines[i][j] > lines2max) {
						lines2max = player2lines[i][j];
						l2max_i = i;
						l2max_j = j;
					}
				}
			}
		if (lines2max > lines1max) {
			row = l2max_i;
			line = l2max_j;
		} else {
			row = l1max_i;
			line = l1max_j;
		}
	}
}