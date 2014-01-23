package com.herasymc.cmput301counter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CounterModelArrayAdapter extends ArrayAdapter<CounterModel> {

	private final Context context;
	private final ArrayList<CounterModel> objects;
	
	public CounterModelArrayAdapter(Context context, ArrayList<CounterModel> objects) {
		super(context, R.layout.counterlayout, objects);
		this.context = context;
		this.objects = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.counterlayout, parent, false);
		TextView textViewName = (TextView) view.findViewById(R.id.counterName);
		textViewName.setText(objects.get(position).getName());
		TextView textViewDate = (TextView) view.findViewById(R.id.counterDate);
		textViewDate.setText(objects.get(position).getCreationDate().toString());
		TextView textViewCount = (TextView) view.findViewById(R.id.counterCount);
		textViewCount.setText(objects.get(position).getTotalCount() + " >");
		return view;
	}

}
