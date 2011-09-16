package ricm.mca2005.fivechess;

public class Chessman {
	int point[][];
	boolean tableplayer1[][][];
	boolean tableplayer2[][][];

	public Chessman() {
		point = new int[19][19];
		tableplayer1 = new boolean[1020][19][19];
		tableplayer2 = new boolean[1020][19][19];
		init();
	}

	void init() {
		int i, j, k, count = 0;
		for (i = 0; i < 19; i++)
			for (j = 0; j < 19; j++)
				point[i][j] = 2; // set broad as empty
		for (i = 0; i < 19; i++)
			for (j = 0; j < 15; j++) {
				for (k = 0; k < 5; k++) {
					tableplayer1[count][i][j + k] = true;
					tableplayer2[count][i][j + k] = true;
				}
				count++;
			}
		for (i = 0; i < 15; i++)
			for (j = 0; j < 19; j++) {
				for (k = 0; k < 5; k++) {
					tableplayer1[count][i + k][j] = true;
					tableplayer2[count][i + k][j] = true;
				}
				count++;
			}
		for (i = 0; i < 15; i++)
			for (j = 0; j < 15; j++) {
				for (k = 0; k < 5; k++) {
					tableplayer1[count][i + k][j + k] = true;
					tableplayer2[count][i + k][j + k] = true;
				}
				count++;
			}
		for (i = 0; i < 15; i++)
			for (j = 18; j >= 4; j--) {
				for (k = 0; k < 5; k++) {
					tableplayer1[count][i + k][j - k] = true;
					tableplayer2[count][i + k][j - k] = true;
				}
				count++;
			}
	}

	void modify(int x, int y, int state) {
		point[x][y] = state;
	}

}
