package com.herasymc.cmput301counter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

import com.herasymc.cmput301counter.AddCounterDialogFragment.AddCounterDialogListener;
import com.herasymc.cmput301counter.SortCountersDialogFragment.SortCountersDialogListener;

public class CounterListActivity extends Activity implements AddCounterDialogListener, SortCountersDialogListener {
	
	private int sortID = 0;
	private static final String FILENAME = "list.dat";
	private ArrayList<CounterModel> list;
	private CounterListIO io;
	private CounterModelArrayAdapter adapter;
	private Comparator<CounterModel> sortType = CounterModel.Comparators.NAME;
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
				intent.putExtra("io", io);
				intent.putExtra("list", list);
				intent.putExtra("id", id);
				startActivity(intent);
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		io = new CounterListIO();
		list = io.load(FILENAME, getApplicationContext());
		adapter = new CounterModelArrayAdapter(this, list);
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
	        	// Implement summary
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onFinishAddDialog(String inputText) {
		list.add(new CounterModel(inputText));
		Collections.sort(list, sortType);
		adapter.notifyDataSetChanged();
		io.save(FILENAME, getApplicationContext(), list);
	}
	
	@Override
	public void onFinishSortDialog(int type) {
		if (type == 1) {
			sortType = CounterModel.Comparators.DATE;
			sortID = type;
		} else if (type == 2) {
			sortType = CounterModel.Comparators.COUNT;
			sortID = type;
		} else {
			sortType = CounterModel.Comparators.NAME;
			sortID = type;
		}
		Collections.sort(list, sortType);
		adapter.notifyDataSetChanged();
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
