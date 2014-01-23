package com.herasymc.cmput301counter;

import android.app.DialogFragment;

public class ResetCounterDialogFragment extends DialogFragment {

	public ResetCounterDialogFragment() {
		// Empty constructor
	}
	
	public static ResetCounterDialogFragment newInstance() {
		ResetCounterDialogFragment fragment = new ResetCounterDialogFragment();
		return fragment;
	}
	
}
