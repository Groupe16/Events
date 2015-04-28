package com.localisation.events.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.localisation.events.R;
import com.localisation.events.adapter.MenuAdapter;
import com.localisation.events.model.User;

/**
 * Created by Zalila on 2015-04-23.
 */
public class SlideMenu {

    private DrawerLayout menuLayout; //Layout Principal
    private ListView menuElementsList; //Menu
    private ActionBarDrawerToggle menuToggle; //Gère l'ouverture et la fermeture du menu

    private CharSequence menuTitle = "Menu";
    private CharSequence activityTitle = "";

    private User user;

    public SlideMenu(final Activity context, final User user) {
        this.user = user;
//recupèration du layout et la liste
        menuLayout = (DrawerLayout) context.findViewById(R.id.menu_layout);
        menuElementsList = (ListView) context.findViewById(R.id.menu_elements);

        //direction grauche -> droite  //même valeur que celle spécifiée en
        // XML dans la ListView (android:layout_gravity="start")
        menuLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        final MenuAdapter adapter = new MenuAdapter(context);

        //adapter pour ajouter les éléments
        menuElementsList.setAdapter(adapter);

        //activation du bouton home
        //context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //context.getSupportActionBar().setHomeButtonEnabled(true);
        //context.getSupportActionBar().setIcon(R.drawable.ic_drawer);

        //création de MenuToggle qui permet d'ouvrir et fermer le menu
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        menuToggle = new ActionBarDrawerToggle(context, /* host Activity */
                menuLayout, /* DrawerLayout object */
                //R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                //getSupportActionBar().setTitle(activityTitle);
                context.invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getSupportActionBar().setTitle(menuTitle);
                context.invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };
        menuLayout.setDrawerListener(menuToggle);
        //clic des éléments du menu
        menuElementsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {

                Intent intent = new Intent(context, adapter.getAction(position));
                //intent.putExtra("user", user);
                intent.putExtra("activity", MenuAdapter.activities[position]);
                context.startActivity(intent);

            }
        });
    }

    public DrawerLayout getMenuLayout() {
        return menuLayout;
    }

    public ListView getMenuElementsList() {
        return menuElementsList;
    }

    public ActionBarDrawerToggle getMenuToggle() {
        return menuToggle;
    }

    public CharSequence getMenuTitle() {
        return menuTitle;
    }

    public CharSequence getActivityTitle() {
        return activityTitle;
    }
}
