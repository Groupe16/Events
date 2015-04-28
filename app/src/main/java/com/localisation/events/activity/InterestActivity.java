package com.localisation.events.activity;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.localisation.events.R;
import com.localisation.events.adapter.InterestAdapter;
import com.localisation.events.adapter.InterestButtonAdapter;
import com.localisation.events.menu.SlideMenu;
import com.localisation.events.model.Theme;
import com.localisation.events.model.User;

import java.util.Vector;

public class InterestActivity extends ActionBarActivity {

    private DrawerLayout menuLayout; //Layout Principal
    private ListView menuElementsList; //Menu
    private ActionBarDrawerToggle menuToggle; //Gère l'ouverture et la fermeture du menu

    private CharSequence menuTitle = "Menu";
    private CharSequence activityTitle = "Intérêts";

    private User user;

    public User getUser() {
        return user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        user = getIntent().getParcelableExtra("user");

        SlideMenu slideMenu = new SlideMenu(this, user);
        menuLayout = slideMenu.getMenuLayout();
        menuElementsList = slideMenu.getMenuElementsList();
        menuToggle = slideMenu.getMenuToggle();
        menuTitle = slideMenu.getMenuTitle();
        activityTitle = slideMenu.getActivityTitle();

        //activation du bouton home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_drawer);
        //createSlideMenu();

        user = getIntent().getParcelableExtra("user");
        final Vector<String> themesn = new Vector<>();
        Vector<Theme> themes = new Vector<>();
        Theme theme = new Theme(0,"Concert","Musique");
        themes.add(theme);
        theme = new Theme(7,"Escapade","Sorties");
        themes.add(theme);
        theme = new Theme(8,"Bar","Sorties");
        themes.add(theme);
        user.setInterest(themes);

        for (Theme t : user.getInterest())
            themesn.add(t.getName());

        final InterestButtonAdapter adapter = new InterestButtonAdapter(this, user.getInterest());
        final ListView listInterest = (ListView) findViewById(R.id.interestListView);
        listInterest.setAdapter(adapter);

        listInterest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayout = (LinearLayout) listInterest.getAdapter().getView(position, view, parent);
                ImageView imageView;
                if (themesn.contains(((TextView) linearLayout.getChildAt(0)).getText())){
                    Theme t = user.getInterest().get(themesn.indexOf(((TextView) linearLayout.getChildAt(0)).getText()));
                    user.getInterest().removeElement(t);
                    themesn.removeElement(t.getName());
                    imageView = ((ImageView) linearLayout.getChildAt(1));
                    imageView.setImageResource(R.drawable.vide);
                    //TODO retirer theme t de la base
                }else{
                    Theme t = adapter.getAll().get(position);
                    user.getInterest().add(t);
                    imageView = ((ImageView) linearLayout.getChildAt(1));
                    imageView.setImageResource(R.drawable.ok);
                    themesn.add(t.getName());
                    //TODO ajouter theme t de la base
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_interest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // masquer et réafficher les éléments du menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Cache l'élément du menu optionnel à l'ouverture et le fait réapparaitre à la fermeture
        boolean drawerOpen = menuLayout.isDrawerOpen(menuElementsList);
//        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        activityTitle = title;
        getSupportActionBar().setTitle(activityTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        menuToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        menuToggle.onConfigurationChanged(newConfig);
    }

}
