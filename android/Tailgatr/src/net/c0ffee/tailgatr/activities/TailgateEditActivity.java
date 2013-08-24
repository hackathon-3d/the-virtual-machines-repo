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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
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
        
        mInfoFragment = null;
        mInvitesFragment = null;
        mFoodFragment = null;
        
        mInfoTab = ab.newTab().setText(R.string.info).setTabListener(this);
        mInvitesTab = ab.newTab().setText(R.string.invites).setTabListener(this);
        mItemsTab = ab.newTab().setText(R.string.food).setTabListener(this);
        
        ab.addTab(mInfoTab);
        ab.addTab(mInvitesTab);
        ab.addTab(mItemsTab);
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.equals(mInfoTab)) {
			if (mInfoFragment == null) {
				mInfoFragment = (TailgateInfoFragment) Fragment.instantiate(this, TailgateInfoFragment.class.getName());
				ft.add(android.R.id.content, mInfoFragment);
			} else {
				ft.show(mInfoFragment);
			}
		} else if (tab.equals(mInvitesTab)) {
			if (mInvitesFragment == null) {
				mInvitesFragment = (TailgateInvitesFragment) Fragment.instantiate(this, TailgateInvitesFragment.class.getName());
				ft.add(android.R.id.content, mInvitesFragment);
			} else {
				ft.show(mInvitesFragment);
			}
		} else if (tab.equals(mItemsTab)) {
			if (mFoodFragment == null) {
				mFoodFragment = (TailgateFoodFragment) Fragment.instantiate(this, TailgateFoodFragment.class.getName());
				ft.add(android.R.id.content, mFoodFragment);
			} else {
				ft.show(mFoodFragment);
			}
		}
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (tab.equals(mInfoTab)) {
			ft.hide(mInfoFragment);
		} else if (tab.equals(mInvitesTab)) {
			ft.hide(mInvitesFragment);
		} else if (tab.equals(mItemsTab)) {
			ft.hide(mFoodFragment);
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
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_item_save:
	        	Toast.makeText(this, mInfoFragment.getTitle(), Toast.LENGTH_LONG).show();
	            return true;
	        case R.id.menu_item_cancel:
	        	finish();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
