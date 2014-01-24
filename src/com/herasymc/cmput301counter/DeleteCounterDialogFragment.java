package com.herasymc.cmput301counter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

public class DeleteCounterDialogFragment extends DialogFragment {

	public DeleteCounterDialogFragment() {
		// Empty constructor
	}
	
	public static DeleteCounterDialogFragment newInstance() {
		DeleteCounterDialogFragment fragment = new DeleteCounterDialogFragment();
		return fragment;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		alertDialogBuilder.setTitle(R.string.dialog_delete_title);
		TextView text = new TextView(getActivity());
		text.setText(R.string.dialog_delete_body);
		text.setPadding(30, 30, 30, 30);
		text.setTextSize(18);
		alertDialogBuilder.setView(text);
		
		alertDialogBuilder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DeleteCounterDialogListener listener = (DeleteCounterDialogListener) getActivity();
				listener.onFinishDeleteDialog();
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
