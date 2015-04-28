package com.localisation.events.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.localisation.events.R;
import com.localisation.events.model.Invitation;
import com.localisation.events.model.Theme;
import com.localisation.events.model.User;

import java.util.Vector;

/**
 * Created by Zalila on 2015-04-26.
 */
public class InvitationAdapter extends BaseAdapter {
    private Context mContext;
    private Vector<Invitation> invitations = new Vector<>();
    User user;

    public InvitationAdapter(Context c, User user) {
        mContext = c;
        invitations.addAll(user.getInvitations());
        this.user = user;
    }

    @Override
    public int getCount() {
        return invitations.size();//mThumbIds.size();//length;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout;
        TextView textView;
        Button accept = new Button(mContext);
        Button delete = new Button(mContext);
        linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(8, 8, 8, 8);
        textView = new TextView(mContext);
        linearLayout.addView(textView);
        linearLayout.addView(accept);
        linearLayout.addView(delete);
        textView.setText(invitations.get(position).getSender().getFirstName()
                + " vous à envoyer " +invitations.get(position).getEvent().getName());

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.getOrganizedEvents().add(invitations.get(position).getEvent());
                user.getInvitations().removeElement(invitations.get(position));

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.getInvitations().removeElement(invitations.get(position));
            }
        });

        return linearLayout;
    }

    public Vector<String> mThumbIds = new Vector<>();

}

