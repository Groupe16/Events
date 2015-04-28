package com.localisation.events.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.localisation.events.activity.CreateEventActivity;
import com.localisation.events.activity.EventsActivity;
import com.localisation.events.activity.ExploreActivity;
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

    private Vector<Theme> getAll(){
        if(ProfileActivity.interestList != null && ProfileActivity.interestList.size() > 0)
        {
            return ProfileActivity.interestList;
        }
        else {
            Vector<Theme> themes = new Vector<>();
            Theme theme = new Theme(0, "Concert", "Musique");
            themes.add(theme);
            theme = new Theme(1, "Jazz", "Musique");
            themes.add(theme);
            theme = new Theme(2, "Rock", "Musique");
            themes.add(theme);
            theme = new Theme(3, "Classique", "Musique");
            themes.add(theme);
            theme = new Theme(4, "Musqiues Electroniques", "Musique");
            themes.add(theme);
            theme = new Theme(5, "Exposition", "Art");
            themes.add(theme);
            theme = new Theme(6, "Musée", "Art");
            themes.add(theme);
            theme = new Theme(7, "Escapade", "Sorties");
            themes.add(theme);
            theme = new Theme(8, "Bar", "Sorties");
            themes.add(theme);
            theme = new Theme(9, "Restaurant", "Sorties");
            themes.add(theme);
            theme = new Theme(10, "Cinéma", "Sorties");
            themes.add(theme);

            theme = new Theme(11, "Autre", "");
            themes.add(theme);
            return themes;
        }
    }
}

