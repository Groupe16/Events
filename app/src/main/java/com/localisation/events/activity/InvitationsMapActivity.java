package com.localisation.events.activity;

import android.content.res.Configuration;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.localisation.events.R;
import com.localisation.events.menu.SlideMenu;
import com.localisation.events.model.User;

public class InvitationsMapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private DrawerLayout menuLayout; //Layout Principal
    private ListView menuElementsList; //Menu
    private ActionBarDrawerToggle menuToggle; //Gère l'ouverture et la fermeture du menu

    private CharSequence menuTitle = "Menu";
    private CharSequence activityTitle = "Invitations";

    private User user;
    public static User userS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations_map);
        setUpMapIfNeeded();

        //user = getIntent().getParcelableExtra("user");

        if (getIntent().getStringExtra("activity").equals("main"))
            user = MainActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("registration"))
            user = RegistrationActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("explore"))
            user = ExploreActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("create"))
            user = CreateEventActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("events"))
            user = EventsActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("invitations"))
            user = InvitationsActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("interest"))
            user = ExploreActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("profile"))
            user = ProfileActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("maps"))
            user = InvitationsMapActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("invitation"))
            user = InvitationActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("event"))
            user = EventActivity.userS;

        SlideMenu slideMenu = new SlideMenu(this, user);
        menuLayout = slideMenu.getMenuLayout();
        menuElementsList = slideMenu.getMenuElementsList();
        menuToggle = slideMenu.getMenuToggle();
        menuTitle = slideMenu.getMenuTitle();
        activityTitle = slideMenu.getActivityTitle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(android.os.Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
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
       // getSupportActionBar().setTitle(activityTitle);
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

    public static void onLocationChanged(Location location) {





    }
}
