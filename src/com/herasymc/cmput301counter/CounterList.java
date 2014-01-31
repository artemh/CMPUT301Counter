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
 * CounterList.java
 * 
 * This class contains a list of counters, and allows
 * for some basic manipulation (adding, renaming, deleting,
 * reseting, incrementing and getting counters), and
 * 
 */

package com.herasymc.cmput301counter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.SparseArray;

public class CounterList {
	
	private static final String FILENAME = "list.dat";
	private static final String FILENAME2 = "history.dat";
	private ArrayList<Counter> list;
	private SparseArray<ArrayList<Date>> history;
	private Context context;
	private Comparator<Counter> sortType;
	private static CounterList instance = null;
	
	/* Loads the ArrayList and sort type from the device, if it exists. */
	private CounterList(Context c) {
		context = c;
		list = loadList(FILENAME, context);
		history = loadMap(FILENAME2, context);
		SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(context);
		setSort(s.getInt("sortType", 2));
	}
	
	/* Ensure only a single instance of a CounterList is created */
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
		// Saves the sort type
		SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(context);
	    SharedPreferences.Editor e = s.edit();
	    e.putInt("sortType", id);
	    e.commit();
	    sort();
	    saveList(FILENAME, context, list);
	}
	
	/* 
	 * getHistory is responsible for building the ArrayList of strings that gets
	 * displayed in the Counter history view.
	 * 
	 * It takes two arguments:
	 * index - the index of the Counter in its list to get the history of.
	 * type - type of history:
	 *        0 - counts per minute
	 *        1 - counts per hour
	 *        2 - counts per day
	 *        3 - counts per week
	 *        4 - counts per month
	 */
	
	@SuppressWarnings("deprecation")
	public ArrayList<String> getHistory(int index, int type) {
		int id = list.get(index).getID();
		String curString;
		SimpleDateFormat format;
		Calendar calBegin = Calendar.getInstance();
		ArrayList<String> counts = new ArrayList<String>();
		
		if (list.get(index).hasBeenReset()) {
			counts.add("NOTE: This counter was reset");
			counts.add("on " + list.get(index).getResetDate().toLocaleString());
		}
		
		if (list.get(index).getCount() == 0) {
			/* don't try to run on an empty history */
			return counts;
		}
		
		ArrayList<Date> dates = history.get(id);
		calBegin.setTime(history.get(id).get(0));
		Calendar calCurrent = (Calendar) calBegin.clone();
		format = new SimpleDateFormat("MMM dd yyyy HH:mm", Locale.getDefault());
		curString = format.format(calBegin.getTime());
		if (type == 1) {
			format = new SimpleDateFormat("MMM dd yyyy HH:00", Locale.getDefault());
			curString = format.format(calBegin.getTime());
		}
		else if (type == 2) {
			format = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());
			curString = format.format(calBegin.getTime());
		} else if (type == 3) {
			format = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());
			curString = "Week of " + format.format(calBegin.getTime());
		} else if (type == 4) {
			format = new SimpleDateFormat("MMM yyyy", Locale.getDefault());
			curString = "Month of " + format.format(calBegin.getTime());
		}
		
		for (int i = 0; i < history.get(id).size(); ) {
			/* loop through the history */
			int count = 0;
			/* 
			 * compare counter dates to the first counter in the current block,
			 * until the threshold is exceeded. then, we can format the output
			 * string for the current block and restart 
			 */
			if (type == 0) {
				while (calBegin.get(Calendar.MINUTE) == calCurrent.get(Calendar.MINUTE) && 
						calBegin.get(Calendar.HOUR) == calCurrent.get(Calendar.HOUR) && 
						calBegin.get(Calendar.DAY_OF_YEAR) == calCurrent.get(Calendar.DAY_OF_YEAR) && 
						calBegin.get(Calendar.YEAR) == calCurrent.get(Calendar.YEAR)) {
					count++;
					i++;
					if (i == history.get(id).size()) {
						break;
					}
					calCurrent.setTime(dates.get(i));
				}
			} else if (type == 1) {
				while (calBegin.get(Calendar.HOUR) == calCurrent.get(Calendar.HOUR) &&
						calBegin.get(Calendar.DAY_OF_YEAR) == calCurrent.get(Calendar.DAY_OF_YEAR) && 
						calBegin.get(Calendar.YEAR) == calCurrent.get(Calendar.YEAR)) {
					count++;
					i++;
					if (i == history.get(id).size()) {
						break;
					}
					calCurrent.setTime(dates.get(i));
				}
			} else if (type == 2) {
				while (calBegin.get(Calendar.DAY_OF_YEAR) == calCurrent.get(Calendar.DAY_OF_YEAR) && 
						calBegin.get(Calendar.YEAR) == calCurrent.get(Calendar.YEAR)) {
					count++;
					i++;
					if (i == history.get(id).size()) {
						break;
					}
					calCurrent.setTime(dates.get(i));
				}
			} else if (type == 3) {
				while (calBegin.get(Calendar.WEEK_OF_YEAR) == calCurrent.get(Calendar.WEEK_OF_YEAR) &&
						calBegin.get(Calendar.YEAR) == calCurrent.get(Calendar.YEAR)) {
					count++;
					i++;
					if (i == history.get(id).size()) {
						break;
					}
					calCurrent.setTime(dates.get(i));
				}
			} else {
				while (calBegin.get(Calendar.MONTH) == calCurrent.get(Calendar.MONTH) && 
						calBegin.get(Calendar.YEAR) == calCurrent.get(Calendar.YEAR)) {
					count++;
					i++;
					if (i == history.get(id).size()) {
						break;
					}
					calCurrent.setTime(dates.get(i));
				}
			}
			counts.add(curString + " - " + Long.toString(count));
			count = 0;
			curString = format.format(calCurrent.getTime());
			if (type == 3) {
				curString = "Week of " + format.format(calCurrent.getTime());
			} else if (type == 4) {
				curString = "Month of " + format.format(calCurrent.getTime());
			}
			calBegin = (Calendar) calCurrent.clone();
		}
		return counts;
	}
	
	/* 
	 * The functions below essentially wrap their equivalent Counter &
	 * ArrayList functions, and also make sure to sort/save the list
	 * automatically as required.
	 */
	
	public void add(String name) {
		list.add(new Counter(name));
		sort();
		save();
	}
	
	public void remove(int index) {
		list.remove(index);
		history.remove(list.get(index).getID());
		save();
	}
	
	public Counter get(int index) {
		return list.get(index);
	}
	
	public void rename(int index, String name) {
		list.get(index).setName(name);
		saveList(FILENAME, context, list);
	}
	
	public void increment(int index) {
		list.get(index).addCount();
		saveList(FILENAME, context, list);
		if (history.get(list.get(index).getID()) == null) {
			history.put(list.get(index).getID(), new ArrayList<Date>());
		}
		history.get(list.get(index).getID()).add(new Date());
		saveMap(FILENAME2, context, history);
	}
	
	public void reset(int index) {
		list.get(index).resetCounts();
		history.get(list.get(index).getID()).clear();
		save();
	}
	
	public int size() {
		return list.size();
	}
	
	public void sort() {
		Collections.sort(list, sortType);
	}
	
	/* Load/save functions are below */
	
	public void save() {
		saveList(FILENAME, context, list);
		saveMap(FILENAME2, context, history);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Counter> loadList(String file, Context context) {
		ArrayList<Counter> l = null;
		try {
			FileInputStream f = context.openFileInput(file);
			ObjectInputStream o = new ObjectInputStream(f);
			l = (ArrayList<Counter>) o.readObject();
			o.close();
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (l == null) {
			return new ArrayList<Counter>();
		} else {
			return l;
		}
	}
	
	@SuppressWarnings("unchecked")
	public SparseArray<ArrayList<Date>> loadMap(String file, Context context) {
		SparseArray<ArrayList<Date>> l = null;
		try {
			FileInputStream f = context.openFileInput(file);
			ObjectInputStream o = new ObjectInputStream(f);
			l = (SparseArray<ArrayList<Date>>) o.readObject();
			o.close();
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (l == null) {
			return new SparseArray<ArrayList<Date>>();
		} else {
			return l;
		}
	}
	
	public void saveList(String file, Context context, ArrayList<Counter> list) {
		try {
			FileOutputStream f = context.openFileOutput(file, Context.MODE_PRIVATE);
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(list);
			o.close();
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveMap(String file, Context context, SparseArray<ArrayList<Date>> list) {
		try {
			FileOutputStream f = context.openFileOutput(file, Context.MODE_PRIVATE);
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(list);
			o.close();
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
