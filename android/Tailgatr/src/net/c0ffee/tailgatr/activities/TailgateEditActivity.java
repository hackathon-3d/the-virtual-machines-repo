package net.c0ffee.tailgatr.activities;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.fragments.TailgateFoodFragment;
import net.c0ffee.tailgatr.fragments.TailgateInfoFragment;
import net.c0ffee.tailgatr.fragments.TailgateInvitesFragment;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
public class TailgateEditActivity extends Activity implements ActionBar.TabListener {

	private Tab mInfoTab;
	private Tab mInvitesTab;
	private Tab mItemsTab;
	
	private TailgateInfoFragment mInfoFragment;
	private TailgateInvitesFragment mInvitesFragment;
	private TailgateFoodFragment mFoodFragment;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar ab = getActionBar();
        ab.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );
        
        mInfoTab = ab.newTab().setText(R.string.info).setTabListener(this);
        mInvitesTab = ab.newTab().setText(R.string.invites).setTabListener(this);
        mItemsTab = ab.newTab().setText(R.string.food).setTabListener(this);
        
        ab.addTab(mInfoTab);
        ab.addTab(mInvitesTab);
        ab.addTab(mItemsTab);
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.equals(mInfoTab)) {
			mInfoFragment = (TailgateInfoFragment) Fragment.instantiate(this, TailgateInfoFragment.class.getName());
			ft.add(android.R.id.content, mInfoFragment);
		} else if (tab.equals(mInvitesTab)) {
			mInvitesFragment = (TailgateInvitesFragment) Fragment.instantiate(this, TailgateInvitesFragment.class.getName());
			ft.add(android.R.id.content, mInvitesFragment);
		} else if (tab.equals(mItemsTab)) {
			mFoodFragment = (TailgateFoodFragment) Fragment.instantiate(this, TailgateFoodFragment.class.getName());
			ft.add(android.R.id.content, mFoodFragment);
		}
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (tab.equals(mInfoTab)) {
			ft.remove(mInfoFragment);
			mInfoFragment = null;
		} else if (tab.equals(mInvitesTab)) {
			ft.remove(mInvitesFragment);
			mInvitesFragment = null;
		} else if (tab.equals(mItemsTab)) {
			ft.remove(mFoodFragment);
			mFoodFragment = null;
		}
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.tailgate_edit_menu, menu);
	    return true;
	}
	
}
