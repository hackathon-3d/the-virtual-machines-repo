package net.c0ffee.tailgatr.activities;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.fragments.MasterFoodFragment;
import net.c0ffee.tailgatr.fragments.MasterTailgateFragment;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity implements ActionBar.TabListener {

	private Tab mTailgatesTab;
	private Tab mFoodTab;
	
	private MasterFoodFragment mFoodFragment;
	private MasterTailgateFragment mTailgateFragment;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar ab = getActionBar();
        ab.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );
        
        mFoodFragment = null;
        mTailgateFragment = null;
        
        mTailgatesTab = ab.newTab().setText(R.string.tailgates).setTabListener(this);
        mFoodTab = ab.newTab().setText(R.string.food).setTabListener(this);
        
        ab.addTab(mTailgatesTab);
        ab.addTab(mFoodTab);
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.equals(mFoodTab)) {
			if (mFoodFragment == null) {
				mFoodFragment = (MasterFoodFragment) Fragment.instantiate(this, MasterFoodFragment.class.getName());
				ft.add(android.R.id.content, mFoodFragment);
			} else {
				ft.show(mFoodFragment);
			}
		} else if (tab.equals(mTailgatesTab)) {
			if (mTailgateFragment == null) {
				mTailgateFragment = (MasterTailgateFragment) Fragment.instantiate(this, MasterTailgateFragment.class.getName());
				ft.add(android.R.id.content, mTailgateFragment);
			} else {
				ft.show(mTailgateFragment);
			}
		}
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		if (tab.equals(mFoodTab)) {
			ft.hide(mFoodFragment);
		} else if (tab.equals(mTailgatesTab)) {
			ft.hide(mTailgateFragment);
		}
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	/*
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_menu, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_item_about:
	        	//Toast.makeText(this, mInfoFragment.getTitle(), Toast.LENGTH_LONG).show();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	*/
}
