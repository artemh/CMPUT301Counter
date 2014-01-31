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
 * CounterSummaryActivity.java
 * 
 * This Activity is responsible for displaying the summary
 * of counters to the user, either for one or for all counters,
 * organized by minute/hour/day/week/month.
 * 
 */

package com.herasymc.cmput301counter;

import java.util.ArrayList;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CounterSummaryActivity extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private static CounterList list;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_summary);

		list = CounterList.getInstance(getApplicationContext());
		id = (int) getIntent().getLongExtra("id", -1); // if id is -1, summarize all counters
		
		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		// Show the Up button in the action bar.
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Set up the dropdown list navigation in the action bar.
		actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_summary_minute),
								getString(R.string.title_summary_hour),
								getString(R.string.title_summary_day),
								getString(R.string.title_summary_week),
								getString(R.string.title_summary_month) }), this);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counter_summary, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = new CounterSummaryFragment();
		Bundle args = new Bundle();
		args.putInt(CounterSummaryFragment.ARG_SECTION_NUMBER, position);
		args.putInt(CounterSummaryFragment.ARG_ID_NUMBER, this.id);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
		return true;
	}

	/**
	 * This fragment shows the actual summary.
	 */
	public static class CounterSummaryFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		public static final String ARG_ID_NUMBER = "id_number";

		public CounterSummaryFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_counter_summary, container, false);
			ListView view = (ListView) rootView
					.findViewById(R.id.section_label);
			ArrayAdapter<String> adapter;
			
			if (getArguments().getInt(ARG_ID_NUMBER) == -1) {
				/* build a list of stats of all counters */
				ArrayList<String> strings = new ArrayList<String>();
				for (int i = 0; i < list.size(); ++i) {
					strings.add(list.get(i).getName() + ":");
					strings.addAll(list.getHistory(i, getArguments().getInt(ARG_SECTION_NUMBER)));
				}
				adapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_list_item_1, strings);
			} else {
				/* show stats for just one counter */
				adapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_list_item_1, 
						list.getHistory(getArguments().getInt(ARG_ID_NUMBER), getArguments().getInt(ARG_SECTION_NUMBER)));
			}
			view.setAdapter(adapter);
			return rootView;
		}
	}

}
