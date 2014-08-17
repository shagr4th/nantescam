package com.ab.nantescam;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CamAdapter extends ArrayAdapter<WebCam> {

	private final Context context;
	private final List<WebCam> values;

	public CamAdapter(Context context, List<WebCam> objects) {
		super(context, R.layout.rowlayout, R.id.label, objects);
		this.context = context;
		this.values = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		WebCam text = values.get(position);
		textView.setText(text.getName());
		
		String id = text.getCode() + "|";
		
		SharedPreferences prefs = context.getSharedPreferences(Cams.KEY_PREFS, 0);
		
		String fav = prefs.getString(Cams.KEY_PREF_FAVS, "");

		if (fav.contains(id)) {
			imageView.setImageResource(R.drawable.favf);
		} else {
			imageView.setImageResource(R.drawable.fave);
		}

		return rowView;
	}
}
