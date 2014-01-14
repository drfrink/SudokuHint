package com.nom.sudokuhint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class sudokuSet extends Activity implements OnClickListener{
	
	TableLayout table;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
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
		table = (TableLayout) findViewById(R.id.newSudokuPuzzle);
		Intent intent = getIntent();
		String data[] = intent.getStringArrayExtra("data");
		int c = 0;
		for (int row = 0; row < 9; row++) {
			TableRow currentRow = new TableRow(this);
			for (int column = 0; column < 9; column++) {
				EditText curEdit = new EditText(this);
				curEdit.setId(c);
				curEdit.setBackgroundResource(R.drawable.back);
				curEdit.setText(data[c]);
				curEdit.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
				curEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
				curEdit.setOnClickListener(this);
				curEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
				currentRow.addView(curEdit);
				c++;
			}
			currentRow.setId(row);
			table.addView(currentRow);
		}
		/*
		//TableLayout actions = (TableLayout) findViewById(R.id.newSudokuBar);
		TableRow currentRow = new TableRow(this);
		Button setButton = new Button(this);
		c++;
		setButton.setId(c);
		setButton.setText(R.string.button_set);
		TableRow.LayoutParams params_set = (TableRow.LayoutParams) setButton.getLayoutParams();
		params_set.span=4;
		setButton.setLayoutParams(params_set);
		Button clearButton = new Button(this);
		c++;
		clearButton.setId(c);
		clearButton.setText(R.string.button_clear);
		clearButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				table = (TableLayout) findViewById(R.id.newSudokuPuzzle);
				for(int x=0;x<81;x++) {
					int i = x/9;
					int j = x%9;
					TableRow tr = (TableRow) table.getChildAt(i);
					EditText text = (EditText) tr.getChildAt(j);
					text.setText(" ");
				}
			}
		});
		setButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent output = new Intent();
				String data[] = new String[81];
				table = (TableLayout) findViewById(R.id.newSudokuPuzzle);
				for(int x=0;x<81;x++) {
					int i = x/9;
					int j = x%9;
					TableRow tr = (TableRow) table.getChildAt(i);
					EditText text = (EditText) tr.getChildAt(j);
					data[x] = text.getText().toString();
				}
				output.putExtra("data", data);
				setResult(Activity.RESULT_OK, output);
			}
		});
		currentRow.addView(setButton);
		currentRow.addView(clearButton);
		currentRow.setId(9);
		//actions.addView(currentRow);
		table.setStretchAllColumns(true);/*
		*/
	}
	@Override
	public void onClick(View v) {
		//TableRow tr = (TableRow) v.getParent();
		EditText text = (EditText) v;
		text.requestFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(text, InputMethodManager.SHOW_IMPLICIT);
		text.setText(imm.toString());
	}
	
	public void setButton(View v) {
		Intent output = new Intent();
		String data[] = new String[81];
		//table = (TableLayout) findViewById(R.id.newSudokuPuzzle);
		for(int x=0;x<81;x++) {
			int i = x/9;
			int j = x%9;
			TableRow tr = (TableRow) table.getChildAt(i);
			EditText text = (EditText) tr.getChildAt(j);
			data[x] = text.getText().toString();
		}
		output.putExtra("newdata", data);
		setResult(Activity.RESULT_OK, output);
		finish();
	}
	
	public void clearButton(View v) {
		//table = (TableLayout) findViewById(R.id.newSudokuPuzzle);
		for(int x=0;x<81;x++) {
			int i = x/9;
			int j = x%9;
			TableRow tr = (TableRow) table.getChildAt(i);
			EditText text = (EditText) tr.getChildAt(j);
			text.setText(" ");
		}
	}
}


/*
for(int j=0;j<81;j++) {
Toast.makeText(getApplicationContext(), data[j],
		Toast.LENGTH_SHORT).show();
}
*/