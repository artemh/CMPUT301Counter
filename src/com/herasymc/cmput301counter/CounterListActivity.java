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
import com.herasymc.cmput301counter.SortCountersDialogFragment.SortCountersDialogListener;

public class CounterListActivity extends Activity implements AddCounterDialogListener, SortCountersDialogListener {
	
	private ArrayList<CounterModel> list;
	private ArrayAdapter<CounterModel> adapter;
	private ListView view;
	private Comparator<CounterModel> sortType;
	private int sortID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_list);
		list = new ArrayList<CounterModel>();
		view = (ListView) findViewById(R.id.countersList);
		adapter = new ArrayAdapter<CounterModel>(
				this,
				android.R.layout.simple_list_item_1,
				list );
		view.setAdapter(adapter);
		sortType = CounterModel.Comparators.NAME;
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
	}
	
	@Override
	public void onFinishSortDialog(int type) {
		if (type == 1) {
			sortType = CounterModel.Comparators.DATE;
		} else if (type == 2) {
			sortType = CounterModel.Comparators.COUNT;
		} else {
			sortType = CounterModel.Comparators.NAME;
		}
		sortID = type;
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
