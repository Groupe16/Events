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
import com.localisation.events.model.Theme;

import java.util.Vector;

/**
 * Created by Zalila on 2015-04-26.
 */
public class InterestButtonAdapter extends BaseAdapter {
    private Context mContext;
    private Vector<Theme> themes;

    public InterestButtonAdapter(Context c, Vector<Theme> interests) {
        mContext = c;
        for (Theme t: interests)
            mThumbIds.add(t.getName());//(Theme[]) interests.toArray();
        themes = getAll();
    }

    @Override
    public int getCount() {
        return themes.size();//mThumbIds.size();//length;
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
        LinearLayout linearLayout;
        TextView textView;
        ImageView imageView;
        linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(8, 8, 8, 8);
        textView = new TextView(mContext);
        imageView = new ImageView(mContext);
        linearLayout.addView(textView);
        linearLayout.addView(imageView);
        textView.setText(themes.get(position).getName());
        if (mThumbIds.indexOf(themes.get(position).getName()) != -1) {
            imageView.setImageResource(R.drawable.ok);
        }else {
            imageView.setImageResource(R.drawable.vide);
        }
        imageView.getLayoutParams().height = 30;
        imageView.getLayoutParams().width = 30;
        imageView.requestLayout();
        return linearLayout;
    }

    public Vector<String> mThumbIds = new Vector<>();

    public Vector<Theme> getAll(){
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

