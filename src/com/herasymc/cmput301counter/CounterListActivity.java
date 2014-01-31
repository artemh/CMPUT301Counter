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
 * CounterListActivity.java
 * 
 * This Activity is responsible for displaying the list
 * of counters to the user, and lets the user view a specific
 * counter, add a counter, or view statistics.
 * 
 */

package com.herasymc.cmput301counter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CounterListActivity extends Activity implements AddCounterDialogListener, SortCountersDialogListener {
	
	private static CounterList list;
	private CounterArrayAdapter adapter;
	private ListView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_list);
		
		view = (ListView) findViewById(R.id.countersList);
		view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(CounterListActivity.this, CounterViewActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		list = CounterList.getInstance(getApplicationContext());
		list.sort();
		adapter = new CounterArrayAdapter(this, list);
		view.setAdapter(adapter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		list.save();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counter_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_sort:
	        	showSortDialog();
	            return true;
	        case R.id.action_add:
	            showAddDialog();
	            return true;
	        case R.id.action_summary:
	        	Intent intent = new Intent(CounterListActivity.this, CounterSummaryActivity.class);
				intent.putExtra("id", -1);
	        	startActivity(intent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onFinishAddDialog(String inputText) {
		list.add(inputText);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onFinishSortDialog(int type) {
		list.setSort(type);
		adapter.notifyDataSetChanged();
	}
	
	private void showAddDialog() {
		FragmentManager manager = getFragmentManager();
		AddCounterDialogFragment dialog = AddCounterDialogFragment.newInstance();
		dialog.show(manager, "fragment_add");
	}
	
	private void showSortDialog() {
		FragmentManager manager = getFragmentManager();
		SortCountersDialogFragment dialog = SortCountersDialogFragment.newInstance(list.getSort());
		dialog.show(manager, "fragment_sort");
	}
}
