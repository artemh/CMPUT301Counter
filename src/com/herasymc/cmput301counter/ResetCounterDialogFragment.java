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
 * ResetCounterDialogFragment.java
 * 
 * Contains the code for the dialog to reset counters (used in the CounterView Activity)
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
import android.widget.TextView;

public class ResetCounterDialogFragment extends DialogFragment {

	public ResetCounterDialogFragment() {
		// Empty constructor
	}
	
	public static ResetCounterDialogFragment newInstance() {
		ResetCounterDialogFragment fragment = new ResetCounterDialogFragment();
		return fragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle(R.string.dialog_reset_title);
		TextView text = new TextView(getActivity());
		text.setText(R.string.dialog_reset_body);
		text.setPadding(30, 30, 30, 30);
		text.setTextSize(18);
		alertDialogBuilder.setView(text);
		
		alertDialogBuilder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ResetCounterDialogListener listener = (ResetCounterDialogListener) getActivity();
				listener.onFinishResetDialog();
				dialog.dismiss();
			}
		});
		alertDialogBuilder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		return alertDialogBuilder.create();
	}
}
