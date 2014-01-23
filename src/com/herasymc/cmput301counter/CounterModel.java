package com.herasymc.cmput301counter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class CounterModel {
	
	private String name;
	private ArrayList<Date> counts;
	private Date created;
	
	public CounterModel(String name) {
		super();
		this.name = name;
		counts = new ArrayList<Date>();
		created = new Date();
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
	}
	
	@Override
	public String toString() {
		return getName() + " (" + getTotalCount() + ")";
	}
	
	public static class Comparators {
		 public static Comparator<CounterModel> NAME = new Comparator<CounterModel>() {
				@Override
				public int compare(CounterModel lhs, CounterModel rhs) {
					return lhs.getName().compareTo(rhs.getName());
				}
		 };
		 public static Comparator<CounterModel> DATE = new Comparator<CounterModel>() {
				@Override
				public int compare(CounterModel lhs, CounterModel rhs) {
					return lhs.getCreationDate().compareTo(rhs.getCreationDate());
				}
		 };
		 public static Comparator<CounterModel> COUNT = new Comparator<CounterModel>() {
				@Override
				public int compare(CounterModel lhs, CounterModel rhs) {
					return Double.compare(lhs.getTotalCount(), rhs.getTotalCount());
				}
		 };
	}
	
}
