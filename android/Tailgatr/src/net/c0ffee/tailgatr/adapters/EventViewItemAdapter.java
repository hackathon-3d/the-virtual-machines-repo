package net.c0ffee.tailgatr.adapters;

import java.util.List;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.data.EventItem;
import net.c0ffee.tailgatr.data.User;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;


public class EventViewItemAdapter extends ArrayAdapter<EventItem> {
	private User currentUser;
	
	public EventViewItemAdapter(Context context, List<EventItem> objects, User currentUser) {
		super(context, R.layout.event_view_item, objects);
		this.currentUser = currentUser;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		final EventItem item = this.getItem(position);
		
		LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View listItemView = convertView;
        if (null == convertView) { 
            listItemView = inflater.inflate(R.layout.event_view_item, parent, false);
        }
        
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        TextView description = (TextView) listItemView.findViewById(R.id.description);
        TextView provider = (TextView) listItemView.findViewById(R.id.provider_name);
        CheckBox checkbox = (CheckBox) listItemView.findViewById(R.id.checkbox);
		
        title.setText(item.getTitle());
        description.setText(item.getDescription());
        if (item.getProvider() == null) {
        	checkbox.setVisibility(View.VISIBLE);
        	provider.setVisibility(View.INVISIBLE);
        	checkbox.setChecked(false);
        }
        else if (item.getProvider().get_id() == currentUser.get_id()) {
        	checkbox.setVisibility(View.VISIBLE);
        	provider.setVisibility(View.INVISIBLE);
        	checkbox.setChecked(true);
        } else {
        	checkbox.setVisibility(View.INVISIBLE);
        	provider.setVisibility(View.VISIBLE);
        	provider.setText(item.getProvider().getNickname());
        }

        checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
            	Log.d("SALKDFJA", item.getTitle() + isChecked + position);
                if ( isChecked )
                {
                    
                }
            }
        });
        
		return listItemView;
	}
}