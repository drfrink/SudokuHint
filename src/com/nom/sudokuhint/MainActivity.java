package com.nom.sudokuhint;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import com.nom.sudokuhint.Sudoku;

public class MainActivity extends Activity implements OnClickListener {

	Sudoku puzzle = new Sudoku();
	TableLayout table;
	static final int returndata = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		table = (TableLayout) findViewById(R.id.sudokuPuzzle);
		String[][] data = puzzle.attempt;
		int c = 0;
		for (int row = 0; row < 9; row++) {
			TableRow currentRow = new TableRow(this);
			for (int button = 0; button < 9; button++) {
				Button currentButton = new Button(this);
				currentButton.setId(c);
				currentButton.setText(data[row][button]);
				currentButton.setOnClickListener(this);
				currentRow.addView(currentButton);
				c++;
			}
			currentRow.setId(row);
			table.addView(currentRow);
		}
	}

	@Override
	public void onClick(View v) {
		TableRow tr = (TableRow) v.getParent();
		Intent sudoku_edit = new Intent(this, sudokuEdit.class);
		int buttonid = (int)tr.getId();
		puzzle.stringify(puzzle.attempt, puzzle.editList);
		sudoku_edit.putExtra("data", puzzle.editList);
		sudoku_edit.putExtra("pos", buttonid);
		this.startActivityForResult(sudoku_edit, returndata);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == returndata) {
			if (resultCode == RESULT_OK) {
				String newNum = data.getStringExtra("new");
				int pos = data.getIntExtra("pos", 99);
				if (newNum == "Hint?") {
					newNum = puzzle.hintMatrix(pos);
				}
				Button btn2 = (Button)table.findViewById(pos);
				btn2.setText(newNum);
				String check = puzzle.checkMatrix();
				Toast.makeText(getApplicationContext(), check,
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
