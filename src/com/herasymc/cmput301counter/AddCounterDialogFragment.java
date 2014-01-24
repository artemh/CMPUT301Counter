/*
 * implemented with help from the Android Developer Guides for Dialogs
 * https://developer.android.com/guide/topics/ui/dialogs.html
 * and from the CodePath DialogFragment tutorial
 * https://github.com/thecodepath/android_guides/wiki/Using-DialogFragment
 */

package com.herasymc.cmput301counter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class AddCounterDialogFragment extends DialogFragment implements OnEditorActionListener {
	
	EditText input;
	
	public AddCounterDialogFragment() {
		// Empty constructor
	}
	
	public static AddCounterDialogFragment newInstance() {
		AddCounterDialogFragment fragment = new AddCounterDialogFragment();
		return fragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle SavedInstanceState) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle(R.string.dialog_add_title);
		
		input = new EditText(getActivity());
		input.setHint(R.string.dialog_add_hint);
		alertDialogBuilder.setView(input);
		
		alertDialogBuilder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				AddCounterDialogListener listener = (AddCounterDialogListener) getActivity();
				listener.onFinishAddDialog(input.getText().toString());
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

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (EditorInfo.IME_ACTION_DONE == actionId) {
			AddCounterDialogListener listener = (AddCounterDialogListener) getActivity();
			listener.onFinishAddDialog(input.getText().toString());
			this.dismiss();
			return true;
		}
		return false;
	}

}
