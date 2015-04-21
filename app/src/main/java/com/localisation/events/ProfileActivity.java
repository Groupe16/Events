package com.localisation.events;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//pour afficher le profil de l'utilisateur
public class ProfileActivity extends ActionBarActivity {

    private DrawerLayout menuLayout; //Layout Principal
    private ListView menuElementsList; //Menu
    private ActionBarDrawerToggle menuToggle; //Gère l'ouverture et la fermeture du menu

    private CharSequence menuTitle = "Menu";
    private CharSequence activityTitle = "DroiDev : Slide Menu Tuto";

    ArrayList<String> TZIDs = new ArrayList<String>();
    public final String DATE_FORMAT = "yyyy-MM-dd' 'hh:mm' 'Z";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //recupèration du layout et la liste
        menuLayout = (DrawerLayout) findViewById(R.id.menu_layout);
        menuElementsList = (ListView) findViewById(R.id.menu_elements);

        //direction grauche -> droite  //même valeur que celle spécifiée en XML dans la ListView (android:layout_gravity="start")
        menuLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);


        // Get TimeZone List
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                this, R.layout.element_menu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        String[] TZ = TimeZone.getAvailableIDs();

        for (int i = 0; i < TZ.length; i++) {
            if (!(TZIDs.contains(TimeZone.getTimeZone(TZ[i]).getDisplayName()))) {
                TZIDs.add(TimeZone.getTimeZone(TZ[i]).getDisplayName());
            }
        }

        for (int i = 0; i < TZIDs.size(); i++) {
            adapter.add(TZIDs.get(i));
        }

        //adapter pour ajouter les éléments
        menuElementsList.setAdapter(adapter);

        //activation du bouton home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_drawer);

        //création de MenuToggle qui permet d'ouvrir et fermer le menu
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        menuToggle = new ActionBarDrawerToggle(this, /* host Activity */
                menuLayout, /* DrawerLayout object */
                //R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(activityTitle);
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(menuTitle);
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };
        menuLayout.setDrawerListener(menuToggle);


        //clic des éléments du menu
        menuElementsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
                //... Faites ce que vous désirez suite au clic sur l’élément ayant comme index "position"...
            }
        });
// If Application just started select Current TimeZone
        if (savedInstanceState == null) {
            Calendar calendar = Calendar.getInstance();
            String timezone = calendar.getTimeZone().getID();
            convert(timezone);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // masquer et réafficher les éléments du menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Cache l'élément du menu optionnel à l'ouverture et le fait réapparaitre à la fermeture
        boolean drawerOpen = menuLayout.isDrawerOpen(menuElementsList);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (menuToggle.onOptionsItemSelected(item)) {
            return true;
        }
        /*switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_profile:
                return true;
            case R.id.action_notifications:
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            default:*/
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, R.string.search, Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        convert(TZIDs.get(position));
        // update selected item and title, then close the drawer
        menuElementsList.setItemChecked(position, true);
        setTitle(TZIDs.get(position));
        menuLayout.closeDrawer(menuElementsList);
    }

    private void convert(String timezone) {
        TextView outputDateTime = (TextView) findViewById(R.id.time_zone_current_time);
        String out = ChangeTimeZone(timezone);
        outputDateTime.setText(out);

    };

    private Date getDateFromString(String inputDateTime) {
        DateFormat formater = new SimpleDateFormat(DATE_FORMAT);
        Date inputDate = null;
        try {
            inputDate = formater.parse(inputDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return inputDate;
    }

    private String ChangeTimeZone( String timezone) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String currentDateandTime = sdf.format(new Date());
        Date inputeDateTime = getDateFromString(currentDateandTime);

        DateFormat formater = new SimpleDateFormat(DATE_FORMAT);
        formater.setTimeZone(TimeZone.getTimeZone(timezone));
        String newDateTime = formater.format(inputeDateTime);
        return newDateTime;
    }

    @Override
    public void setTitle(CharSequence title) {
        activityTitle = title;
        getActionBar().setTitle(activityTitle);
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
