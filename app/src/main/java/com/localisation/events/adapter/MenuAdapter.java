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
            "Recherche", //  barre de recherche
            "Profil", // ajouter l'avatar
            "Notifications",
            "Explorer",
            "Créer un événement",
            "Evénements",
            "Evénements passés",
            "Réglages"
    };

    private Class[] actions = {
            ProfileActivity.class,
            ProfileActivity.class,
            NotificationActivity.class,
            ExploreActivity.class,
            CreateEventActivity.class,
            EventsActivity.class,
            EventsActivity.class,
            SettingsActivity.class
    };

    public Class getAction(int position){
        return actions[position];
    }
}
