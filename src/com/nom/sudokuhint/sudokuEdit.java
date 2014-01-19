package com.nom.sudokuhint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class sudokuEdit extends Activity implements OnClickListener {
	TextView txt; 

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		txt = (TextView) findViewById(R.id.textView1);
		Intent intent = getIntent();
		String[] data = intent.getStringArrayExtra("data");
		final int pos = Integer.parseInt(intent.getStringExtra("pos"));
		txt.setText(data[pos]);
		
		Button one = (Button) findViewById(R.id.button_1);
		Button two = (Button) findViewById(R.id.button_2);
		Button three = (Button) findViewById(R.id.button_3);
		Button four = (Button) findViewById(R.id.button_4);
		Button five = (Button) findViewById(R.id.button_5);
		Button six = (Button) findViewById(R.id.button_6);
		Button seven = (Button) findViewById(R.id.button_7);
		Button eight = (Button) findViewById(R.id.button_8);
		Button nine = (Button) findViewById(R.id.button_9);
		Button blank = (Button) findViewById(R.id.button_0);
		Button hint = (Button) findViewById(R.id.button_hint);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		five.setOnClickListener(this);
		six.setOnClickListener(this);
		seven.setOnClickListener(this);
		eight.setOnClickListener(this);
		nine.setOnClickListener(this);
		blank.setOnClickListener(this);
		hint.setOnClickListener(this);
		Button doneButton = (Button) findViewById(R.id.button_done);
		doneButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent output = new Intent();
				String temp = txt.getText().toString();
				if(temp.compareTo("") == 0) {
					temp = " ";
				} 
				output.putExtra("new", temp);
				output.putExtra("pos", Integer.toString(pos));
				setResult(Activity.RESULT_OK, output);
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Button btn = (Button) findViewById(id);
		String temp = btn.getText().toString();
		txt.setText(temp);
	}

}
