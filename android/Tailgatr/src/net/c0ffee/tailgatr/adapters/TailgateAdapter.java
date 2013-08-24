package net.c0ffee.tailgatr.adapters;

import java.util.List;

import net.c0ffee.tailgatr.R;
import net.c0ffee.tailgatr.data.Tailgate;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TailgateAdapter extends ArrayAdapter<Tailgate> {

	public TailgateAdapter(Context context, List<Tailgate> objects) {
		super(context, R.layout.tailgate_list_item, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Tailgate tailgate = this.getItem(position);
		
		LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View listItemView = convertView;
        if (null == convertView) { 
            listItemView = inflater.inflate(R.layout.tailgate_list_item, parent, false);
        }
        
        TextView text1 = (TextView) listItemView.findViewById(R.id.text1);
        TextView text2 = (TextView) listItemView.findViewById(R.id.text2);
		
		return parent;
		
	}
}
