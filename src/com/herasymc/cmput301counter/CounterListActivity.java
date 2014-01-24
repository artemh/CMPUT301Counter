package com.herasymc.cmput301counter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CounterListActivity extends Activity implements AddCounterDialogListener, SortCountersDialogListener {
	
	private int sortID;
	private static final String FILENAME = "list.dat";
	private ArrayList<Counter> list;
	private CounterListIO io;
	private CounterArrayAdapter adapter;
	private Comparator<Counter> sortType = Counter.Comparators.COUNT;
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
				intent.putExtra("list", list);
				intent.putExtra("id", id);
				intent.putExtra("FILENAME", FILENAME);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		io = new CounterListIO();
		list = io.load(FILENAME, getApplicationContext());
		SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    sortID = s.getInt("sortID", 2);
		sort(sortID);
		adapter = new CounterArrayAdapter(this, list);
		view.setAdapter(adapter);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		io.save(FILENAME, getApplicationContext(), list);
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
				intent.putExtra("list", list);
				intent.putExtra("id", -1);
	        	startActivity(intent);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onFinishAddDialog(String inputText) {
		list.add(new Counter(inputText));
		sort(sortID);
		adapter.notifyDataSetChanged();
		io.save(FILENAME, getApplicationContext(), list);
	}
	
	@Override
	public void onFinishSortDialog(int type) {
		sort(type);
		SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    SharedPreferences.Editor e = s.edit();
	    e.putInt("sortID", sortID);
	    e.commit();
		adapter.notifyDataSetChanged();
		io.save(FILENAME, getApplicationContext(), list);
	}
	
	private void sort(int type) {
		if (type == 1) {
			sortType = Counter.Comparators.DATE;
			sortID = type;
		} else if (type == 2) {
			sortType = Counter.Comparators.COUNT;
			sortID = type;
		} else {
			sortType = Counter.Comparators.NAME;
			sortID = type;
		}
		Collections.sort(list, sortType);
	}
	
	private void showAddDialog() {
		FragmentManager manager = getFragmentManager();
		AddCounterDialogFragment dialog = AddCounterDialogFragment.newInstance();
		dialog.show(manager, "fragment_add");
	}
	
	private void showSortDialog() {
		FragmentManager manager = getFragmentManager();
		SortCountersDialogFragment dialog = SortCountersDialogFragment.newInstance(sortID);
		dialog.show(manager, "fragment_sort");
	}
}
