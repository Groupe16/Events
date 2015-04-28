package com.localisation.events.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.localisation.events.R;
import com.localisation.events.activity.ProfileActivity;
import com.localisation.events.model.Event;
import com.localisation.events.model.Theme;
import com.localisation.events.model.User;

import java.util.Vector;

/**
 * Created by Zalila on 2015-04-26.
 */
public class EventsAdapter extends BaseAdapter {
    private Context mContext;
    private Vector<Event> events = new Vector<>();
    public static Vector<Event> Events = new Vector<>();

    public EventsAdapter(Context c, User user) {
        mContext = c;
        //events.addAll(user.getPastEvents());
        //events.addAll(user.getFutureEvents());
        events = ProfileActivity.eventList;
    }

    @Override
    public int getCount() {
        return events.size();
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
        TextView textView = new TextView(mContext);
        textView.setPadding(8, 8, 8, 8);
        textView.setText(events.get(position).getName() + " "
                + events.get(position).getStartDate());
        return textView;
    }

    public Vector<String> mThumbIds = new Vector<>();

    public Vector<Event> getEvents() {
        return events;
    }

    public void setEvents(Vector<Event> events) {
        this.events = events;
    }
}

