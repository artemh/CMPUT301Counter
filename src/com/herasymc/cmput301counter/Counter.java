package com.herasymc.cmput301counter;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Counter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Date> counts;
	private Date created;
	private boolean reset;
	
	public Counter(String name) {
		super();
		this.name = name;
		counts = new ArrayList<Date>();
		created = new Date();
		reset = false;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getCreationDate() {
		return created;
	}
	
	public void addCount() {
		counts.add(new Date());
	}
	
	public int getTotalCount() {
		return counts.size();
	}
	
	public boolean hasBeenReset() {
		return reset;
	}
	
	public int getCount(Date begin, Date end) {
		int count = 0;
		for (Date date : counts) {
			if (date.after(begin) && date.before(end)) {
				count++;
			}
		}
		return count;
	}
	
	public void resetCounts() {
		counts.clear();
		reset = true;
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
