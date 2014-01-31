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
 * SortCountersDialogFragment.java
 * 
 * Contains the code for the dialog to sort counters (used in the CounterList Activity)
 * 
 * Implemented with help from the Android Developer Guides for Dialogs
 * https://developer.android.com/guide/topics/ui/dialogs.html
 * and from the CodePath DialogFragment tutorial
 * https://github.com/thecodepath/android_guides/wiki/Using-DialogFragment
 * 
 */

package com.herasymc.cmput301counter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class SortCountersDialogFragment extends DialogFragment {
	
	public SortCountersDialogFragment() {
		//
	}
	
	public static SortCountersDialogFragment newInstance(int sortID) {
		SortCountersDialogFragment fragment = new SortCountersDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("sortType", sortID);
		fragment.setArguments(bundle);
		return fragment;
	}

	private int getSort() {
		return getArguments().getInt("sortType", 0);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle SavedInstanceState) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle(R.string.dialog_sort_title);
		alertDialogBuilder.setSingleChoiceItems(R.array.sort_types, getSort(), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				SortCountersDialogListener listener = (SortCountersDialogListener) getActivity();
				listener.onFinishSortDialog(which);
				dialog.dismiss();
			}
		});
		return alertDialogBuilder.create();
	}
	
}
