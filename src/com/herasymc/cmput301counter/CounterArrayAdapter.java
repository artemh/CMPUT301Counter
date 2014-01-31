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
 * CounterArrayAdapter.java
 * 
 */

package com.herasymc.cmput301counter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CounterArrayAdapter extends ArrayAdapter<Counter> {

	private final Context context;
	private final ArrayList<Counter> objects;
	
	public CounterArrayAdapter(Context context, CounterList list) {
		super(context, R.layout.layout_list, list.getList());
		this.context = context;
		this.objects = list.getList();
	}
	
	/* Allows for displaying of Counter objects with our custom list view. */
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.layout_list, parent, false);
		TextView textViewName = (TextView) view.findViewById(R.id.counterName);
		textViewName.setText(objects.get(position).getName());
		TextView textViewDate = (TextView) view.findViewById(R.id.counterDate);
		textViewDate.setText(objects.get(position).getCreationDate().toLocaleString());
		TextView textViewCount = (TextView) view.findViewById(R.id.counterCount);
		textViewCount.setText(objects.get(position).getTotalCount() + " >");
		return view;
	}

}
