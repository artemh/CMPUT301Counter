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

public class EditCounterDialogFragment extends DialogFragment implements OnEditorActionListener {

	EditText input;
	
	public EditCounterDialogFragment() {
		// Empty constructor
	}
	
	public static EditCounterDialogFragment newInstance() {
		EditCounterDialogFragment fragment = new EditCounterDialogFragment();
		return fragment;
	}
	
	public interface EditCounterDialogListener {
		void onFinishEditDialog(String inputText);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle(R.string.dialog_edit_title);
		
		input = new EditText(getActivity());
		input.setHint(R.string.dialog_add_hint);
		alertDialogBuilder.setView(input);
		
		alertDialogBuilder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditCounterDialogListener listener = (EditCounterDialogListener) getActivity();
				listener.onFinishEditDialog(input.getText().toString());
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
			EditCounterDialogListener listener = (EditCounterDialogListener) getActivity();
			listener.onFinishEditDialog(input.getText().toString());
			this.dismiss();
			return true;
		}
		return false;
	}

}
