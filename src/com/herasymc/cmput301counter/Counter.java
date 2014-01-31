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
 * Counter.java
 * 
 * This class represents a counter.
 * 
 */

package com.herasymc.cmput301counter;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import android.annotation.SuppressLint;

public class Counter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int count;
	private int id;
	private static int idCounter = 0;	// unique ID for history
	private String name;
	private Date created;				// date created
	private Date reset;					// date reset
	
	/* Initialize the counter, sets created/reset dates to now */
	public Counter(String name) {
		super();
		this.name = name;
		if (name.isEmpty()) {
			/* don't make blank counters */ 
			this.name = "New Counter";
		}
		count = 0;
		created = new Date();
		reset = (Date) created.clone();
		id = idCounter++;
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
	
	public Date getResetDate() {
		return reset;
	}
	
	public int getID() {
		return id;
	}
	
	// Increments counter
	public void addCount() {
		count++;
	}
	
	// Get counter value
	public int getCount() {
		return count;
	}
	
	// check if counter has ever been reset
	public boolean hasBeenReset() {
		if (created.equals(reset)) {
			return false;
		} else {
			return true;
		}
	}
	
	//
	public void resetCounts() {
		count = 0;
		reset = new Date();
	}
	
	/* 
	 * comparators for sorting a list of Counters, useful for sorting the
	 * counters in a GUI
	 */
	public static class Comparators {
		 public static Comparator<Counter> NAME = new Comparator<Counter>() {
			 // sort by name
				@SuppressLint("DefaultLocale")
				@Override
				public int compare(Counter lhs, Counter rhs) {
					return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
				}
		 };
		 public static Comparator<Counter> DATE = new Comparator<Counter>() {
			 // sort by creation date (oldest to newest)
				@Override
				public int compare(Counter lhs, Counter rhs) {
					return lhs.getCreationDate().compareTo(rhs.getCreationDate());
				}
		 };
		 public static Comparator<Counter> COUNT = new Comparator<Counter>() {
			 // sort by total count (biggest to smallest)
				@Override
				public int compare(Counter lhs, Counter rhs) {
					return Double.compare(rhs.getCount(), lhs.getCount());
				}
		 };
	}
	
	
}
