package com.herasymc.cmput301counter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.herasymc.cmput301counter.AddCounterDialogFragment.AddCounterDialogListener;

public class CounterListActivity extends Activity implements AddCounterDialogListener {
	
	private ArrayList<CounterModel> countersList;
	private ListView view;
	private int sort;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_list);
		countersList = new ArrayList<CounterModel>();
		view = (ListView) findViewById(R.id.countersList);
		sort = 0;
		ArrayAdapter<CounterModel> adapter = new ArrayAdapter<CounterModel>(
				this,
				android.R.layout.simple_list_item_1,
				countersList );
		view.setAdapter(adapter);
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
	        	//
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
		countersList.add(new CounterModel(inputText));
		sortList();
	}
	
	private void showAddDialog() {
		FragmentManager manager = getFragmentManager();
		AddCounterDialogFragment dialog = AddCounterDialogFragment.newInstance();
		dialog.show(manager, "fragment_add");
	}
	
	private void sortList() {
		Collections.sort(countersList, new Comparator<CounterModel>() {
			@Override
			public int compare(CounterModel lhs, CounterModel rhs) {
				int val = 0;
				switch(sort) {
					case 0: // sort by name
						val =  lhs.getName().compareTo(rhs.getName());
					case 1: // sort by date added
						val =  lhs.getCreationDate().compareTo(rhs.getCreationDate());
					case 2: // sort by count
						val =  Double.compare(lhs.getTotalCount(), rhs.getTotalCount());
				}
				return val;
			}
		});
	}
}
