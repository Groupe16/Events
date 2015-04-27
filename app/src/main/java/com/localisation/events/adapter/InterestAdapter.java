package com.localisation.events.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.localisation.events.activity.CreateEventActivity;
import com.localisation.events.activity.EventsActivity;
import com.localisation.events.activity.ExploreActivity;
import com.localisation.events.activity.NotificationActivity;
import com.localisation.events.activity.ProfileActivity;
import com.localisation.events.activity.SettingsActivity;
import com.localisation.events.model.Theme;

import java.util.Vector;

/**
 * Created by Zalila on 2015-04-26.
 */
public class InterestAdapter extends BaseAdapter {
    private Context mContext;

    public InterestAdapter(Context c, Vector<Theme> interests) {
        mContext = c;
        mThumbIds = (Theme[]) interests.toArray();
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(mContext);
            //textView.setLayoutParams(new GridView.LayoutParams(230, 230));
            //textView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            textView.setPadding(8, 8, 8, 8);
        } else {
            textView = (TextView) convertView;
        }

        textView.setText(mThumbIds[position].getName());
        return textView;
    }

    private Theme[] mThumbIds;
}

