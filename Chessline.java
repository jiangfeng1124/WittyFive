package ricm.mca2005.fivechess;

public class Chessline {
	Chessman chessman;
	int chessline[][];

	public Chessline(Chessman che) {
		chessman = che;
		chessline = new int[1020][2];
	}

	public void count() {
		int k, j, i;
		for (k = 0; k < 1020; k++) {
			chessline[k][0] = 0;
			chessline[k][1] = 0;
		}
		for (k = 0; k < 1020; k++)
			for (i = 0; i < 19; i++)
				for (j = 0; j < 19; j++) {
					if (chessman.tableplayer1[k][i][j] == true
							&& chessline[k][0] >= 0) {
						if (chessman.point[i][j] == 1)
							chessline[k][0] = -1;
						if (chessman.point[i][j] == 0)
							chessline[k][0]++;
					}
					if (chessman.tableplayer2[k][i][j] == true
							&& chessline[k][1] >= 0) {
						if (chessman.point[i][j] == 0)
							chessline[k][1] = -1;
						if (chessman.point[i][j] == 1)
							chessline[k][1]++;
					}
				}
	}

	public int judge() {
		for (int i = 0; i < 1020; i++) {
			if (chessline[i][0] == 5)
				return 0;
			if (chessline[i][1] == 5)
				return 1;
		}
		return -1;
	}
}
