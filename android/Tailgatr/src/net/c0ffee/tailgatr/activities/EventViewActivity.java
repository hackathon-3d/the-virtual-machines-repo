package net.c0ffee.tailgatr.activities;

import net.c0ffee.tailgatr.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.ActionBar;
import android.app.ActionBar.Tab;

public class EventViewActivity extends Activity  {

	// UI references
	
	public static class InfoFragment extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
			// Inflate the layout for this fragment
			//return inflater.inflate(R.layout.example_fragment, container, false);
			
			ScrollView scroller = new ScrollView(getActivity());
			TextView text = new TextView(getActivity());
			text.setText("This is a test!!!");
			scroller.addView(text);
			return scroller;
		}
		
	}
	
	public static class ItemsFragment extends ListFragment {
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
	
			SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, resource, from, to)
			this.setListAdapter(adapter)
		}
		
	}
	
	

	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the layout
		//setContentView(R.layout.activity_event_view);
		
		// Get references to the ui elements
	
		
		// Set the onClick handlers
		
		ActionBar ab = getActionBar();
        ab.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );
        
        // info tab
        Tab tab = ab.newTab().setText(R.string.info_tab)
        					.setTabListener(new MyTabListener(this, InfoFragment.class.getName()));
        ab.addTab(tab);
        
        // items tab
        tab = ab.newTab().setText(R.string.items_tab)
				.setTabListener(new MyTabListener(this, ItemsFragment.class.getName()));
        ab.addTab(tab);
        
        /*tab = ab.newTab().setText(R.string.itemFragment)
				.setTabListener(new MyTabListener(this, ItemListFragment.class.getName()));
        ab.addTab(_itemTab);*/

	}
	
	private class MyTabListener implements ActionBar.TabListener
	{
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mFragName;
 
		public MyTabListener( Activity activity, String fragName )
		{
			mActivity = activity;
			mFragName = fragName;
		}
 
		public void onTabReselected( Tab tab, FragmentTransaction ft )
		{
			// TODO Auto-generated method stub
		}
 
		public void onTabSelected( Tab tab, FragmentTransaction ft )
		{
			mFragment = Fragment.instantiate( mActivity, mFragName );
			ft.add( android.R.id.content, mFragment );
		}
 
		public void onTabUnselected( Tab tab, FragmentTransaction ft )
		{
			ft.remove( mFragment );
			mFragment = null;
		}
	}


}
