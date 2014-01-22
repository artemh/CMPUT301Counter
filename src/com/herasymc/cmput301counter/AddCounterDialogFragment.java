/* 
 * written using help from the following codepath android tutorial:
 * https://github.com/thecodepath/android_guides/wiki/Using-DialogFragment
 */

package com.herasymc.cmput301counter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

public class AddCounterDialogFragment extends DialogFragment {

	public AddCounterDialogFragment() {
		// Empty constructor
	}
	
	public static AddCounterDialogFragment newInstance() {
		AddCounterDialogFragment fragment  = new AddCounterDialogFragment();
		return fragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle SavedInstanceState) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle(R.string.dialog_add_title);
		
		final EditText input = new EditText(getActivity());
		alertDialogBuilder.setView(input);
		
		alertDialogBuilder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
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
