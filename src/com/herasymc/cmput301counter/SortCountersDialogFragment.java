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
		bundle.putInt("sort", sortID);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	public interface SortCountersDialogListener {
		void onFinishSortDialog(int sortType);
	}

	private int getSort() {
		return getArguments().getInt("sort", 0);
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
