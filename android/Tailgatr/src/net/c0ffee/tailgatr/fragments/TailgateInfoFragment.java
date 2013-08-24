package net.c0ffee.tailgatr.fragments;

import net.c0ffee.tailgatr.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class TailgateInfoFragment extends Fragment {

	private EditText mTitle;
	private EditText mDescription;
	
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tailgate_info_edit, null);
		mTitle = (EditText) view.findViewById(R.id.tailgate_title_field);
		mDescription = (EditText) view.findViewById(R.id.tailgate_description_field);
		return view;
	}
	
	public String getTitle() {
		return mTitle.getText().toString();
	}
	
	public String getDescription() {
		return mDescription.getText().toString();
	}
}
