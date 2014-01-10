package com.nom.sudokuhint;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import com.nom.sudokuhint.Sudoku;

public class MainActivity extends Activity implements OnClickListener {

	Sudoku puzzle = new Sudoku();
	TableLayout table;
	static final int returndata = 1;
	static final int setdata = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final HorizontalScrollView hScroll = (HorizontalScrollView) findViewById(R.id.scrollHorizontal);
	    final ScrollView vScroll = (ScrollView) findViewById(R.id.scrollVertical);
	    vScroll.setOnTouchListener(new View.OnTouchListener() { //inner scroll listener         
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            return false;
	        }
	    });
	    hScroll.setOnTouchListener(new View.OnTouchListener() { //outer scroll listener         
	        private float mx, my, curX, curY;
	        private boolean started = false;

	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            curX = event.getX();
	            curY = event.getY();
	            int dx = (int) (mx - curX);
	            int dy = (int) (my - curY);
	            switch (event.getAction()) {
	                case MotionEvent.ACTION_MOVE:
	                    if (started) {
	                        vScroll.scrollBy(0, dy);
	                        hScroll.scrollBy(dx, 0);
	                    } else {
	                        started = true;
	                    }
	                    mx = curX;
	                    my = curY;
	                    break;
	                case MotionEvent.ACTION_UP: 
	                    vScroll.scrollBy(0, dy);
	                    hScroll.scrollBy(dx, 0);
	                    started = false;
	                    break;
	            }
	            return true;
	        }
	    });
		table = (TableLayout) findViewById(R.id.sudokuPuzzle);
		String[][] data = puzzle.attemptGetter();
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
		//TableRow tr = (TableRow) v.getParent();
		Intent sudoku_edit = new Intent(this, sudokuEdit.class);
		int buttonid = v.getId();
		String but = Integer.toString(buttonid);
		puzzle.stringify();
		String[] editList = puzzle.editListGetter();
		sudoku_edit.putExtra("data", editList);
		sudoku_edit.putExtra("pos", but);
		this.startActivityForResult(sudoku_edit, returndata);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == returndata) {
			if (resultCode == RESULT_OK) {
				String newNum = data.getStringExtra("new");
				int pos = data.getExtras().getInt("pos");
				if (newNum == "Hint?") {
					newNum = puzzle.hintMatrix(pos);
				}
				Button btn2 = (Button)table.findViewById(pos);
				btn2.setText(newNum);
				puzzle.setMatrix(pos, newNum);
			} 
		} else if (requestCode == setdata) {
			if (resultCode == RESULT_OK) {
				String[] newNums = data.getStringArrayExtra("newdata");
				puzzle.setMatrix(newNums);
				puzzle.stringify();
				setBtn(puzzle.editListGetter());
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.menu_restart:
			puzzle.restart();
			setBtn(puzzle.editListGetter());
			break;
		case R.id.menu_set:
			Intent sudoku_set = new Intent(this,sudokuSet.class);
			sudoku_set.putExtra("data",puzzle.editListGetter());
			this.startActivityForResult(sudoku_set,setdata);
			break;
		case R.id.menu_solve:
			puzzle.solutionMatrix();
			setBtn(puzzle.editListGetter());
			break;
		case R.id.menu_hint:
			puzzle.hintMatrix();
			setBtn(puzzle.editListGetter());
			break;
		case R.id.menu_check:
			String check = puzzle.checkMatrix();
			Toast.makeText(getApplicationContext(), check,
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.menu_checkloc:
			int[] where = puzzle.checkWhere();
			btnHighlight(where);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	public void setBtn(String[] newdata) {
		for(int x=0;x<newdata.length;x++) {
			int i = x/9;
			int j = x%9;
			table = (TableLayout) findViewById(R.id.sudokuPuzzle);
			TableRow tr = (TableRow) table.getChildAt(i);
			Button btn = (Button) tr.getChildAt(j);
			btn.setText(newdata[x]);
		}
		
		puzzle.setMatrix(newdata);
	}
	
	public void btnHighlight(int[] cells) {
		//Highlight button positions for 10 seconds
	}

}
