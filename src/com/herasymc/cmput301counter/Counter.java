package com.herasymc.cmput301counter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

import android.annotation.SuppressLint;

public class Counter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Calendar> counts;
	private Calendar created;
	private Calendar reset;
	
	public Counter(String name) {
		super();
		this.name = name;
		if (name.isEmpty()) {
			this.name = "New Counter";
		}
		counts = new ArrayList<Calendar>();
		created = Calendar.getInstance();
		reset = (Calendar) created.clone();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Calendar getCreationDate() {
		return created;
	}
	
	public void addCount() {
		counts.add(Calendar.getInstance());
	}
	
	public int getTotalCount() {
		return counts.size();
	}
	
	public boolean hasBeenReset() {
		if (created.equals(reset)) {
			return false;
		} else {
			return true;
		}
	}
	
	public HashMap<String, Integer> getCountSummary(int interval) {
		HashMap<String, Integer> counts = new HashMap<String, Integer>();
		SimpleDateFormat format;
		switch(interval) {
		case 0: // per hour
			format = new SimpleDateFormat("MMMM dd, yyyy - KK:00 aa", Locale.US);
			break;
		case 1: // per day
			format = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
			break;
		case 2: // per week
			format = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
			break;
		default: // per month
			format = new SimpleDateFormat("MMMM, yyyy", Locale.US);
			break;
		}
		counts.put(format.format(this.counts.get(0).getTime()), 1);
		for (int i = 1; i < this.counts.size(); ++i) {
			if (format.format(this.counts.get(i).getTime()).equals(format.format(this.counts.get(i-1).getTime()))) {
				int count = counts.get(format.format(this.counts.get(i).getTime())).intValue();
				counts.remove(format.format(this.counts.get(i).getTime()));
				counts.put(format.format(this.counts.get(i).getTime()), count + 1);
			} else {
				counts.put(format.format(this.counts.get(i).getTime()), 1);
			}
		}
		return counts;
	}
	
	public void resetCounts() {
		counts.clear();
		reset = Calendar.getInstance();
	}
	
	public static class Comparators {
		 public static Comparator<Counter> NAME = new Comparator<Counter>() {
				@SuppressLint("DefaultLocale")
				@Override
				public int compare(Counter lhs, Counter rhs) {
					return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
				}
		 };
		 public static Comparator<Counter> DATE = new Comparator<Counter>() {
				@Override
				public int compare(Counter lhs, Counter rhs) {
					return lhs.getCreationDate().compareTo(rhs.getCreationDate());
				}
		 };
		 public static Comparator<Counter> COUNT = new Comparator<Counter>() {
				@Override
				public int compare(Counter lhs, Counter rhs) {
					return Double.compare(rhs.getTotalCount(), lhs.getTotalCount());
				}
		 };
	}
	
}
