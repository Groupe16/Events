package com.localisation.events.activity;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.localisation.events.R;
import com.localisation.events.adapter.InterestAdapter;
import com.localisation.events.menu.SlideMenu;
import com.localisation.events.model.Event;
import com.localisation.events.model.User;

//pour afficher un événement
public class EventActivity extends ActionBarActivity {

    private DrawerLayout menuLayout; //Layout Principal
    private ListView menuElementsList; //Menu
    private ActionBarDrawerToggle menuToggle; //Gère l'ouverture et la fermeture du menu

    private CharSequence menuTitle = "Menu";
    private CharSequence activityTitle = "Evénement";

    private Event event;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

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

        event = getIntent().getParcelableExtra("event");
        activityTitle = event.getName();
        fillFields(event);

    }

    private void fillFields(Event event) {
        TextView name = (TextView) findViewById(R.id.name_text);
        name.setText(event.getName());
        TextView description = (TextView) findViewById(R.id.description_text);
        description.setText(event.getDescription());
        TextView visibility = (TextView) findViewById(R.id.visibility_text);
        visibility.setText((event.isVisibility() ? "Publique" : "Privé"));
        TextView startDate = (TextView) findViewById(R.id.start_date_text);
        startDate.setText(String.valueOf(event.getStartDate()));
        TextView endDate = (TextView) findViewById(R.id.end_date_text);
        endDate.setText(String.valueOf(event.getEndDate()));
        TextView theme = (TextView) findViewById(R.id.theme_text);
        theme.setText(event.getTheme().getGroup() + "/" + event.getTheme().getName());
        TextView place = (TextView) findViewById(R.id.place_text);
        place.setText(event.getPlace_name());
        TextView address = (TextView) findViewById(R.id.address_text);
        address.setText(event.getAddress());
        TextView longitude = (TextView) findViewById(R.id.longitudeTextView);
        longitude.setText(String.valueOf(event.getCoord().getLongitude()));
        TextView latitude = (TextView) findViewById(R.id.latitudeTextView);
        latitude.setText(String.valueOf(event.getCoord().getLatitude()));
        TextView altitude = (TextView) findViewById(R.id.altitudeTextView);
        altitude.setText(String.valueOf(event.getCoord().getAltitude()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
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
