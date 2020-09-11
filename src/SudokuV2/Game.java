package SudokuV2;

/*
 * Một Game đại diện cho 1 sudoku 9x9
 */
public class Game {

	public int[][] solution;

	static int s = 9;// size of board game

	public Game(int[][] solution) {
		super();
		this.solution = new int[s][s];
		this.solution = solution;
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				this.solution[i][j] = solution[i][j];
			}
		}

	}

	public Game() {

	}

	public Game setSolution(int[][] solution) {
		for (int i = 0; i < s; i++) {
			for (int j = 0; j < s; j++) {
				this.solution[i][j] = solution[i][j];
			}
		}
		Game game = new Game(solution);
		return game;
	}

	// kiểm tra các ô 3x3 trong sudoku có hợp lệ hay không
	public static boolean boxCheck(Game game) {
		int[][] sMatrix = game.solution;
		for (int i = 0; i < s; i += 3) {
			for (int j = 0; j < s; j += 3) {
				boolean[] gridMatrix = new boolean[s];
				for (int k = i; k < 3 + i; k++) {
					for (int l = j; l < 3 + j; l++) {
						int currentNumber = sMatrix[k][l];
						if (currentNumber < 1 || currentNumber > 9) {
							return false;
						}
						gridMatrix[currentNumber - 1] = true;
					}
				}
				for (boolean booleanValue : gridMatrix) {
					if (!booleanValue) {
						return false;
					}
				}
			}
		}
		return true;
	}

	// kiểm tra các dòng và các cột trong sudoku có hợp lệ không
	public static boolean rowColumnCheck(Game game) {
		int[][] sMatrix = game.solution;
		for (int i = 0; i < s; i++) {
			boolean[] rowArray = new boolean[s];
			boolean[] columnArray = new boolean[s];
			for (int j = 0; j < s; j++) {
				int currentNumberRow = sMatrix[i][j];
				int currentNumberColumn = sMatrix[j][i];
				if ((currentNumberRow < 1 || currentNumberRow > s)
						&& (currentNumberColumn < 1 || currentNumberColumn > s)) {
					return false;
				}
				rowArray[currentNumberRow - 1] = true;
				columnArray[currentNumberColumn - 1] = true;

			}
			for (boolean booleanValue : rowArray) {
				if (!booleanValue) {
					return false;
				}
			}
			for (boolean booleanValue : columnArray) {
				if (!booleanValue) {
					return false;
				}
			}
		}
		return true;
	}

	// tính độ xung đột của các ô 3x3 trong sudoku
	public int evalBox(Game game) {
		int[][] sMatrix = game.solution;
		int eval = 0;
		for (int i = 0; i < sMatrix.length; i += 3) {
			for (int j = 0; j < sMatrix.length; j += 3) {
				boolean[] gridMatrix = new boolean[s];
				for (int k = i; k < 3 + i; k++) {
					for (int l = j; l < 3 + j; l++) {
						int currentNumber = sMatrix[k][l];
						if (currentNumber < 1 || currentNumber > s) {
							eval++;
						}
						gridMatrix[currentNumber - 1] = true;
					}
				}
				for (boolean booleanValue : gridMatrix) {
					if (!booleanValue) {
						eval++;
					}
				}
			}
		}
		return eval;
	}

	// tính độ xung đột trrrn các cột của sudoku
	public int evalColumn(Game game) {
		int[][] sMatrix = game.solution;
		int eval = 0;
		for (int i = 0; i < s; i++) {
			boolean[] rowArray = new boolean[s];
			boolean[] columnArray = new boolean[s];
			for (int j = 0; j < s; j++) {
				int currentNumberRow = sMatrix[i][j];
				int currentNumberColumn = sMatrix[j][i];
				if ((currentNumberRow < 1 || currentNumberRow > s)
						&& (currentNumberColumn < 1 || currentNumberColumn > s)) {
					eval++;
				}
				rowArray[currentNumberRow - 1] = true;
				columnArray[currentNumberColumn - 1] = true;

			}
			for (boolean booleanValue : rowArray) {
				if (!booleanValue) {
					eval++;
				}
			}
			for (boolean booleanValue : columnArray) {
				if (!booleanValue) {
					eval++;
				}
			}
		}
		return eval;
	}

	// xem thử có phải là sudoku hợp lệ hay không
	public boolean isGoal(Game game) {
		return rowColumnCheck(game) && boxCheck(game);
	}

	// tính các xung đột của 1 sudoku
	public int eval(Game game) {
		return evalColumn(game) + evalBox(game);
	}

}
