package com.herasymc.cmput301counter;

import java.util.ArrayList;
import java.util.Date;

public class CounterModel {
	
	private String name;
	private ArrayList<Date> counts;
	
	public CounterModel(String name) {
		super();
		this.name = name;
		counts = new ArrayList<Date>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
}