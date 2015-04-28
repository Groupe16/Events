package com.localisation.events.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.localisation.events.activity.CreateEventActivity;
import com.localisation.events.activity.EventsActivity;
import com.localisation.events.activity.ExploreActivity;
import com.localisation.events.activity.InterestActivity;
import com.localisation.events.activity.InvitationsActivity;
import com.localisation.events.activity.ProfileActivity;

/**
 * Created by Zalila on 2015-04-21.
 */
public class MenuAdapter extends BaseAdapter {
    private Context mContext;

    public MenuAdapter(Context c) {
        mContext = c;
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

        textView.setText(mThumbIds[position]);
        return textView;
    }

    private String[] mThumbIds = {
            "Profil", // ajouter l'avatar
            "Explorer",
            "Créer un événement",
            "Evénements",
            "Invitations",
            "Interets"
    };

    private Class[] actions = {
            ProfileActivity.class,
            ExploreActivity.class,
            CreateEventActivity.class,
            EventsActivity.class,
            InvitationsActivity.class,
            InterestActivity.class
    };

    public static String[] activities = {
            "profile",
            "explore",
            "create",
            "events",
            "invitations",
            "interest"
    };

    public Class getAction(int position){
        return actions[position];
    }
}
