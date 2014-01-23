package com.herasymc.cmput301counter;

import android.app.DialogFragment;
import android.view.KeyEvent;
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
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
