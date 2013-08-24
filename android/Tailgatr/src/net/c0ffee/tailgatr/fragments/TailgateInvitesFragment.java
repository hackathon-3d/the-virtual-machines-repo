package net.c0ffee.tailgatr.fragments;

import java.util.ArrayList;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.data.User;

import android.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TailgateInvitesFragment extends ListFragment {

	private ArrayList<User> mUsers;
	
	private ArrayAdapter<User> mAdapter;
	
	private View mHeaderView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View list_root = inflater.inflate(android.R.layout.list_content, null);
		
		mHeaderView = inflater.inflate(R.layout.tailgate_header, null);
		return list_root;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		TextView text = (TextView) mHeaderView.findViewById(R.id.header_text);
		text.setText(getActivity().getResources().getString(R.string.whos_coming));
		getListView().addHeaderView(mHeaderView);
		
		mUsers = new ArrayList<User>();
		mAdapter = new ArrayAdapter<User>(this.getActivity(), android.R.layout.simple_list_item_checked, android.R.id.text1, mUsers);
		setListAdapter(mAdapter);
	}
}
