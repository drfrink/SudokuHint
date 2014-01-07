package com.nom.sudokuhint;

public class Sudoku {
	int matrix[][] = new int[][] {
			{ 0, 0, 0, 0, 0, 0, 0, 6, 0 }, // evil level just to start
			{ 8, 0, 2, 0, 0, 3, 0, 0, 0 }, { 6, 0, 0, 8, 9, 0, 0, 3, 0 },
			{ 0, 1, 0, 9, 0, 0, 7, 0, 4 }, { 0, 0, 0, 0, 8, 0, 0, 0, 0 },
			{ 7, 0, 5, 0, 0, 2, 0, 9, 0 }, { 0, 2, 0, 0, 3, 8, 0, 0, 5 },
			{ 0, 0, 0, 4, 0, 0, 1, 0, 2 }, { 0, 4, 0, 0, 0, 0, 0, 0, 0 } };
	String solveable;
	String start[][] = new String[9][9];
	String end[][] = new String[9][9];
	String attempt[][] = new String[9][9];
	String editList[] = new String[81];

	public Sudoku() {
		stringify(matrix, start);
		stringify(matrix, attempt);
		solveMatrix();
		stringify(attempt, editList);
	}

	static void stringify(int[][] cells, String[][] data) {

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (cells[i][j] == 0) {
					data[i][j] = " ";
				} else {
					data[i][j] = String.valueOf(cells[i][j]);
				}
			}
		}
	}

	public void stringify(String[][] Old, String[] data) {
		int c = 0;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				data[c] = Old[i][j];
				c++;
			}
		}
		
		
	}

	public boolean solveMatrix() {
		if (solve(0, 0, matrix)) {
			stringify(matrix, end);
			return true;
		} else {
			return false;
		}
	}

	public String checkMatrix() {
		int count = 0;
		String temp = "";
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (attempt[i][j] != end[i][j] && attempt[i][j] != "Hint?"
						&& attempt[i][j] != " ") {
					count++;
				}
			}
		}
		if (count != 0) {
			temp = "So far you have " + String.valueOf(count) + " mistakes";
		} else {
			temp = "Nice, no mistakes so far";
		}
		return temp;
	}

	public String hintMatrix(int x) {
		int i = (x / 9);
		int j = (x % 9);
		attempt[i][j] = end[i][j];
		return attempt[i][j];
	}

	public void hintMatrix() {

	}

	public void setMatrix(int i, int j, String data) {
		attempt[i][j] = data;
	}

	static boolean solve(int i, int j, int[][] cells) {
		if (i == 9) {
			i = 0;
			j++;
			if (j == 9) {
				return true;
			}
		}

		if (cells[i][j] != 0) {
			return solve(i + 1, j, cells);
		}

		for (int val = 0; val < 9; val++) {
			if (legal(i, j, val, cells)) {
				cells[i][j] = (val + 1);
				if (solve(i + 1, j, cells)) {
					return true;
				}
			}
		}

		cells[i][j] = 0;
		return false;
	}

	static boolean legal(int i, int j, int val, int[][] cells) {
		for (int k = 0; k < 9; k++) {
			if ((val + 1) == cells[k][j]) {
				return false;
			}
		}

		for (int k = 0; k < 9; k++) {
			if ((val + 1) == cells[i][k]) {
				return false;
			}
		}

		int boxRowOffset = (i / 3) * 3;
		int boxColOffset = (j / 3) * 3;
		for (int k = 0; k < 3; k++) {
			for (int m = 0; m < 3; m++) {
				if ((val + 1) == cells[boxRowOffset + k][boxColOffset + m]) {
					return false;
				}
			}
		}

		return true;
	}
}
