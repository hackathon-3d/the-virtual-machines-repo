package net.c0ffee.tailgatr.activities;


import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.data.TailgateDatabase;
import net.c0ffee.tailgatr.data.TailgateProvider;
import net.c0ffee.tailgatr.fragments.FoodListFragment;
import net.c0ffee.tailgatr.fragments.TailgateInfoFragment;
import net.c0ffee.tailgatr.interfaces.TailgateFragmentCommunicator;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class TailgateActivity extends Activity implements ActionBar.TabListener, TailgateFragmentCommunicator {

	private Uri mUri;
	
	private Tab mInfoTab;
	private Tab mFoodTab;
	
	private TailgateInfoFragment mInfoFragment;
	private FoodListFragment mFoodFragment;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
        ab.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );
        
        mInfoFragment = null;
        mFoodFragment = null;
        
        mInfoTab = ab.newTab().setText(R.string.info).setTabListener(this);
        mFoodTab = ab.newTab().setText(R.string.food).setTabListener(this);
        
        //ab.addTab(mInfoTab);
        //ab.addTab(mFoodTab);
        
        mUri = null;
	    Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	        mUri = extras.getParcelable(TailgateProvider.CONTENT_TAILGATE_TYPE);
	    }
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.edit_tailgate_menu, menu);
	    
	    if (getIntent().getExtras() == null) {
	    	menu.findItem(R.id.menu_item_delete).setVisible(false);
	    }
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.menu_item_save:
	        	mUri = mInfoFragment.save();
	        	if (mUri != null) {
	        		Log.d("Groppe", "Saving list");
	        		mFoodFragment.save(mUri);
	        		Toast.makeText(getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();
	        		finish();
	        	}
	            return true;
	        case R.id.menu_item_delete:
	        	getContentResolver().delete(TailgateProvider.CONTENT_TAILGATE_URI, TailgateDatabase.COLUMN_TAILGATE_ID + " = " + mUri.getLastPathSegment(), null);
	        	Toast.makeText(getApplicationContext(), "Tailgate deleted", Toast.LENGTH_SHORT).show();
	        	finish();
	        	return true;
	        case R.id.menu_item_cancel:
	        	Toast.makeText(getApplicationContext(), "Changes Not Saved", Toast.LENGTH_SHORT).show();
	        	finish();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		if (tab.equals(mInfoTab)) {
			if (mInfoFragment == null) {
				mInfoFragment = (TailgateInfoFragment) Fragment.instantiate(this, TailgateInfoFragment.class.getName());
				ft.add(android.R.id.content, mInfoFragment);
			} else {
				ft.show(mInfoFragment);
			}
		} else if (tab.equals(mFoodTab)) {
			if (mFoodFragment == null) {
				mFoodFragment = (FoodListFragment) Fragment.instantiate(this, FoodListFragment.class.getName());
				ft.add(android.R.id.content, mFoodFragment);
			} else {
				ft.show(mFoodFragment);
			}
		}
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if (tab.equals(mInfoTab)) {
			ft.hide(mInfoFragment);
		} else if (tab.equals(mFoodTab)) {
			ft.hide(mFoodFragment);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	public Uri getTailgateUri() {
		return mUri;
	}

}
