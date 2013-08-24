package net.c0ffee.tailgatr.adapters;

import java.util.List;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.data.EventItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


public class EventViewItemAdapter extends ArrayAdapter<EventItem> {
	public EventViewItemAdapter(Context context, List<EventItem> objects) {
		super(context, R.layout.event_view_item, objects);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		EventItem item = this.getItem(position);
		
		LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View listItemView = convertView;
        if (null == convertView) { 
            listItemView = inflater.inflate(R.layout.event_view_item, parent, false);
        }
        
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        TextView description = (TextView) listItemView.findViewById(R.id.description);
        CheckBox checkbox = (CheckBox) listItemView.findViewById(R.id.checkbox);
		
        title.setText(item.getTitle());
        description.setText(item.getDescription());
        if (item.getProvider() == null) {
        	checkbox.setVisibility(View.VISIBLE);
        	checkbox.setChecked(false);
        }
        else if (item.getProvider().getNickname() == "Doug") {
        	checkbox.setVisibility(View.VISIBLE);
        	checkbox.setChecked(true);
        } else {
        	checkbox.setVisibility(View.INVISIBLE);
        }
        
		return listItemView;
	}
}