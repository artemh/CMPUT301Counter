package com.herasymc.cmput301counter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CounterList {
	
	private static final String FILENAME = "list.dat";
	private ArrayList<Counter> list;
	private Context context;
	private CounterListIO io;
	private Comparator<Counter> sortType;
	private static CounterList instance = null;
	
	private CounterList(Context c) {
		context = c;
		io = CounterListIO.getInstance();
		list = io.load(FILENAME, context);
		SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(context);
		setSort(s.getInt("sortType", 2));
	}
	
	public static CounterList getInstance(Context c) {
		if (instance == null) {
			instance = new CounterList(c);
		}
		return instance;
	}
	
	public ArrayList<Counter> getList() {
		return list;
	}
	
	public int getSort() {
		if (sortType == Counter.Comparators.NAME) {
			return 0;
		} else if (sortType == Counter.Comparators.DATE) {
			return 1;
		} else {
			return 2;
		}
	}
	
	public void setSort(int id) {
		if (id == 0) {
			sortType = Counter.Comparators.NAME;
		} else if (id == 1) {
			sortType = Counter.Comparators.DATE;
		} else {
			sortType = Counter.Comparators.COUNT;
		}
		SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(context);
	    SharedPreferences.Editor e = s.edit();
	    e.putInt("sortType", id);
	    e.commit();
	    sort();
	    save();
	}
	
	public void add(String name) {
		list.add(new Counter(name));
		sort();
		save();
	}
	
	public void remove(int index) {
		list.remove(index);
		save();
	}
	
	public Counter get(int index) {
		return list.get(index);
	}
	
	public void rename(int index, String name) {
		list.get(index).setName(name);
		save();
	}
	
	public void increment(int index) {
		list.get(index).addCount();
		save();
	}
	
	public void reset(int index) {
		list.get(index).resetCounts();
		save();
	}
	
	public void save() {
		io.save(FILENAME, context, list);
	}
	
	public void sort(){
		Collections.sort(list, sortType);
	}

}
