package com.herasymc.cmput301counter;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CounterViewActivity extends Activity {

	private static final String FILENAME = "list.dat";
	private ArrayList<CounterModel> list;
	private CounterListIO io;
	private TextView textViewName;
	private TextView textViewCount;
	private Button button;
	private int id;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_view);
		// Show the Up button in the action bar.
		setupActionBar();
		io = (CounterListIO) getIntent().getSerializableExtra("io");
		list = (ArrayList<CounterModel>) getIntent().getSerializableExtra("list");
		id = (int) getIntent().getLongExtra("id", -1);
		textViewName = (TextView) findViewById(R.id.counter_view_name);
		textViewCount = (TextView) findViewById(R.id.counter_view_count);
		button = (Button) findViewById(R.id.button_count);
		textViewName.setText(list.get(id).getName());
		textViewCount.setText(Long.toString(list.get(id).getTotalCount()));
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				list.get(id).addCount();
				textViewCount.setText(Long.toString(list.get(id).getTotalCount()));
				io.save(FILENAME, getApplicationContext(), list);
			}
		});
 	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counter_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
