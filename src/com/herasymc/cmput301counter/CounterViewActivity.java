package com.herasymc.cmput301counter;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CounterViewActivity extends Activity implements EditCounterDialogListener, ResetCounterDialogListener {

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
		io = new CounterListIO();
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
			case R.id.action_edit:
				showEditDialog();
				return true;
			case R.id.action_reset:
				showResetDialog();
				return true;
			case R.id.action_delete:
				showDeleteDialog();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onFinishEditDialog(String inputText) {
		list.get(id).setName(inputText);
		io.save(FILENAME, getApplicationContext(), list);
		textViewName.setText(inputText);
	}
	
	@Override
	public void onFinishResetDialog() {
		list.get(id).resetCounts();
		io.save(FILENAME, getApplicationContext(), list);
		textViewCount.setText(list.get(id).getTotalCount());
	}
	
	private void showEditDialog() {
		FragmentManager manager = getFragmentManager();
		EditCounterDialogFragment dialog = EditCounterDialogFragment.newInstance();
		dialog.show(manager, "fragment_edit");
	}
	
	private void showResetDialog() {
		FragmentManager manager = getFragmentManager();
		ResetCounterDialogFragment dialog = ResetCounterDialogFragment.newInstance();
		dialog.show(manager, "fragment_reset");
	}
	private void showDeleteDialog() {
		FragmentManager manager = getFragmentManager();
		DeleteCounterDialogFragment dialog = DeleteCounterDialogFragment.newInstance();
		dialog.show(manager, "fragment_delete");
	}

}
