package net.c0ffee.tailgatr.activities;

import java.util.ArrayList;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.adapters.EventViewItemAdapter;
import net.c0ffee.tailgatr.async.GetEventViewInfoTask;
import net.c0ffee.tailgatr.data.EventItem;
import net.c0ffee.tailgatr.data.User;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class EventViewActivity extends Activity  {

	// UI references
	
	
	public static class InfoFragment extends Fragment {
		private GetEventViewInfoTask task;
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
			// Inflate the layout for this fragment
			//return inflater.inflate(R.layout.example_fragment, container, false);
			
			ScrollView scroller = new ScrollView(getActivity());
			LinearLayout layout = new LinearLayout(getActivity());
			TextView title = new TextView(getActivity());
			title.setTextSize(30);
			title.setText("Loading...");
			TextView description = new TextView(getActivity());
			description.setText("The information is loading. Please wait.");
			//scroller.addView(title);
			layout.addView(title);
			layout.addView(description);
			layout.setOrientation(LinearLayout.VERTICAL);
			scroller.addView(layout);
			
			task = new GetEventViewInfoTask(title, description, 0);
			task.execute("token_here");
			return scroller;
			
			
		}
		
	}
	
	public static class ItemsFragment extends ListFragment {
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
	
			ArrayList<EventItem> items = new ArrayList<EventItem>();
			items.add(new EventItem(0, "item title!", "desc1", "food",
			new User(0, "dougnd@gmail.com", "doug", "")));
			items.add(new EventItem(0, "item title2!", "", "food",
					new User(0, "dougnd@gmail.com", "doug", "")));
			items.add(new EventItem(0, "item title3!", "dekjs s alksj asks s kldjalksj s skjs  sflkas s fksja sdkfjs askf a sd fsjdfalsk  fs dfjaksj falskdj as fksj sc3", "food",
					null));
			items.add(new EventItem(0, "item title4!", "desc4", "food",
					new User(50, "dougnd@gmail.com", "Doug", "")));
			
			
			EventViewItemAdapter a = new EventViewItemAdapter(getActivity(), items, new User(50, "dougnd@gmail.com", "Doug", "") );
			setListAdapter(a);
		}
		
	}
	
	public static class InviteesFragment extends ListFragment {
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
	
			ArrayList<User> items = new ArrayList<User>();
			items.add(new User(0, "dougnd@gmail.com", "doug", ""));
			items.add(new User(0, "dougnd@gmail.com", "doug2", ""));
			items.add(new User(0, "dougnd@gmail.com", "doug3", ""));
			items.add(new User(0, "dougnd@gmail.com", "doug4", ""));
			
			ArrayAdapter<User> a = new ArrayAdapter<User>(getActivity(), android.R.layout.simple_list_item_1, items );
			setListAdapter(a);
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
        
        // invitees tab
        tab = ab.newTab().setText(R.string.invitees_tab)
				.setTabListener(new MyTabListener(this, InviteesFragment.class.getName()));
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
