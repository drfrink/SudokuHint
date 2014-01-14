package com.nom.sudokuhint;

import java.util.ArrayList;

public class Sudoku {
	private int matrix[][] = new int[][] {
			{ 0, 0, 0, 0, 0, 0, 0, 6, 0 }, // evil level just to start
			{ 8, 0, 2, 0, 0, 3, 0, 0, 0 }, { 6, 0, 0, 8, 9, 0, 0, 3, 0 },
			{ 0, 1, 0, 9, 0, 0, 7, 0, 4 }, { 0, 0, 0, 0, 8, 0, 0, 0, 0 },
			{ 7, 0, 5, 0, 0, 2, 0, 9, 0 }, { 0, 2, 0, 0, 3, 8, 0, 0, 5 },
			{ 0, 0, 0, 4, 0, 0, 1, 0, 2 }, { 0, 4, 0, 0, 0, 0, 0, 0, 0 } };
	private boolean solveable = false;
	private String start[][] = new String[9][9];
	private String end[][] = new String[9][9];
	private String attempt[][] = new String[9][9];
	private String editList[] = new String[81];

	public Sudoku() {
		start = stringify(matrix);
		attempt = stringify(matrix);
		solveable = solveMatrix();
		stringify();
		//This is Sudoku Constructor, Starts all variables and confirms the sudoku is solvable.
	}

	private String[][] stringify(int[][] cells) {
		String data[][] = new String[9][9];
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (cells[i][j] == 0) {
					data[i][j] = " ";
				} else {
					data[i][j] = String.valueOf(cells[i][j]);
				}
			}
		}
		
		return data;
	}

	public void stringify() {
		int c = 0;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				editList[c] = attempt[i][j];
				c++;
			}
		}
	}
	
	public String[][] attemptGetter() {
		return attempt;
	}
	
	public String[] editListGetter() {
		return editList;
	}
	
	public void restart() {
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				attempt[i][j] = start[i][j];
				stringify();
				intify();
			}
		}
	}
	
	public void solutionMatrix() {
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				attempt[i][j] = end[i][j];
				stringify();
				intify();
			}
		}
	}

	private boolean solveMatrix() {
		if (solve(0, 0)) {
			end = stringify(matrix);
			return true;
		} else {
			return false;
		}
	}

	public String checkMatrix() {
		int count = 0;
		int lefttogo = 0;
		String temp = "";
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if ((attempt[i][j].compareTo(end[i][j]))== 1 || (attempt[i][j].compareTo(" ")) == 1) {
					count++;
				} else if ((attempt[i][j].compareTo(" ")==0)) {
					lefttogo++;
				}
			}
		}
		if (count != 0) {
			temp = "So far you have " + String.valueOf(count) + " mistakes";
		} else if (lefttogo != 0){
			temp = "Nice, no mistakes so far";
		}else if (count == 0 && lefttogo ==0){
			temp = "You got the solution, Congrats!";
		}
		return temp;
	}
	
	public int[] checkWhere() {
		ArrayList<Integer> datalist = new ArrayList<Integer>();
		int temp = 0;
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if ((attempt[i][j].compareTo(end[i][j])) != 0) {
					temp = (i+1) * (j+1);
					datalist.add(temp);
				} 
			}
		}
		
		int data[] = new int[datalist.size()];
		
		for(int i=0;i<datalist.size();i++) {
			data[i] = datalist.get(i);
		}
		
		return data;
	}
	
	public String attemptMatrix() {
		intify();
		solveable = solveMatrix();
		String result = new String();
		
		if (solveable) {
			result = "Alright, lets get started";
			start = stringify(matrix);
			attempt = stringify(matrix);
			solveable = solveMatrix();
			stringify();
		}else {
			result = "Sorry, this isn't right";
		}
		
		return result;
	}

	private void intify() {
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(attempt[i][j] != " "){
					matrix[i][j] = Integer.parseInt(attempt[i][j]);
				} else {
					matrix[i][j] = 0;
				}
			}
		}
		
		
	}
	
	public String hintMatrix(int x) {
		int i = (x / 9);
		int j = (x % 9);
		attempt[i][j] = end[i][j];
		return attempt[i][j];
	}

	public int[] hintMatrix() {
		int data[] = checkWhere();
		int y = data.length;
		int z = (int) (Math.random() * y);
		int x = data[z];
		int i = x/9;
		int j = x%9;
		
		attempt[i][j] = end[i][j];
		stringify();
		intify();
		
		int temp[] = {data[z]};
		
		return temp;
	}

	public void setMatrix(int x, String data) {
		int i = (x / 9);
		int j = (x % 9);
		attempt[i][j] = data;
		intify();
	}
	
	public void setMatrix(String[] data) {
		int c = 0;
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				attempt[i][j] = data[c];
				c++;
			}
		}
		
	}

	private boolean solve(int i, int j) {
		if (i == 9) {
			i = 0;
			j++;
			if (j == 9) {
				return true;
			}
		}

		if (matrix[i][j] != 0) {
			return solve(i + 1, j);
		}

		for (int val = 0; val < 9; val++) {
			if (legal(i, j, val)) {
				matrix[i][j] = (val + 1);
				if (solve(i + 1, j)) {
					return true;
				}
			}
		}

		matrix[i][j] = 0;
		return false;
	}

	private boolean legal(int i, int j, int val) {
		for (int k = 0; k < 9; k++) {
			if ((val + 1) == matrix[k][j]) {
				return false;
			}
		}

		for (int k = 0; k < 9; k++) {
			if ((val + 1) == matrix[i][k]) {
				return false;
			}
		}

		int boxRowOffset = (i / 3) * 3;
		int boxColOffset = (j / 3) * 3;
		for (int k = 0; k < 3; k++) {
			for (int m = 0; m < 3; m++) {
				if ((val + 1) == matrix[boxRowOffset + k][boxColOffset + m]) {
					return false;
				}
			}
		}

		return true;
	}
}
