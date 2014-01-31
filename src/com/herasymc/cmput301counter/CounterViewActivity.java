/*
 * CMPUT 301 Winter 2014 Assignment 1 - Counter App for Android
 * 
 * Copyright 2014 Artem Herasymchuk
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * ---
 * 
 * CounterViewActivity.java
 * 
 * This Activity is responsible for displaying a specific counter, 
 * and allows the user to increment, edit and reset the counter
 * and view its statistics.
 * 
 */

package com.herasymc.cmput301counter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CounterViewActivity extends Activity implements DeleteCounterDialogListener, EditCounterDialogListener, ResetCounterDialogListener {

	private static CounterList list;
	private TextView textViewName;
	private TextView textViewCount;
	private Button button;
	private int id;
	private int count;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_view);
		// Show the Up button in the action bar.
		setupActionBar();
		list = CounterList.getInstance(getApplicationContext());
		id = (int) getIntent().getLongExtra("id", -1);
		textViewName = (TextView) findViewById(R.id.counter_view_name);
		textViewCount = (TextView) findViewById(R.id.counter_view_count);
		button = (Button) findViewById(R.id.button_count);
		textViewName.setText(list.get(id).getName());
		count = list.get(id).getTotalCount();
		textViewCount.setText(Long.toString(count));
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				list.increment(id);
				count++;
				textViewCount.setText(Long.toString(count));
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
			case R.id.action_summaryview:
				Intent intent = new Intent(CounterViewActivity.this, CounterSummaryActivity.class);
				intent.putExtra("id", (long) id);
	        	startActivity(intent);
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
		list.rename(id, inputText);
		textViewName.setText(inputText);
	}
	
	@Override
	public void onFinishResetDialog() {
		list.reset(id);
		textViewCount.setText(Long.toString(list.get(id).getTotalCount()));
	}
	
	@Override
	public void onFinishDeleteDialog() {
		list.remove(id);
		this.finish();
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
