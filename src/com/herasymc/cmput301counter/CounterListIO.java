package com.herasymc.cmput301counter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;

public class CounterListIO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public ArrayList<Counter> load(String file, Context context) {
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
	
	public void save(String file, Context context, ArrayList<Counter> list) {
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
