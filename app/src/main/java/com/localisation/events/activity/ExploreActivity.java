package com.localisation.events.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.localisation.events.R;
import com.localisation.events.adapter.MenuAdapter;
import com.localisation.events.menu.SlideMenu;
import com.localisation.events.model.OnTaskCompleted;
import com.localisation.events.model.User;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.Date;

public class ExploreActivity extends FragmentActivity implements OnTaskCompleted {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private DrawerLayout menuLayout; //Layout Principal
    private ListView menuElementsList; //Menu
    private ActionBarDrawerToggle menuToggle; //Gère l'ouverture et la fermeture du menu

    private CharSequence menuTitle = "Menu";
    private CharSequence activityTitle = "Explorer";

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        user = getIntent().getParcelableExtra("user");
        AsyncRefreshDB refresh = new AsyncRefreshDB();
        refresh.LinkTask(this);
        refresh.execute();
        setUpMapIfNeeded();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExploreActivity.this);
                for (int i = 0; i < ProfileActivity.eventList.size(); i++) {
                    if (ProfileActivity.eventList.get(i).getName().equals( marker.getTitle())) {
                        builder.setMessage("Description: " + ProfileActivity.eventList.get(i).getDescription() + "\n"
                        + "Lieu: " + ProfileActivity.eventList.get(i).getPlace_name() + "\n"
                        + "Addresse: " + ProfileActivity.eventList.get(i).getAddress() ).setTitle("Description étendu");
                        if(user.getFutureEvents().contains(ProfileActivity.eventList.get(i))) {
                            final int id = i;
                            builder.setPositiveButton("Se désinscrire", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    user.getFutureEvents().remove(ProfileActivity.eventList.get(id));

                                    dialog.dismiss();
                                }

                            });
                        }
                        else {
                            final int id = i;
                            builder.setPositiveButton("S'inscrire", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    user.getFutureEvents().add(ProfileActivity.eventList.get(id));

                                    dialog.dismiss();
                                }

                            });
                        }
                        builder.setNegativeButton("Sortir", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing but close the dialog

                                dialog.dismiss();
                            }

                        });


                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
                return false;
            }
        });



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
        mMap.clear();
        for(int i = 0; i<ProfileActivity.eventList.size(); i++)
        {
            for(int j=0; j<user.getInterest().size(); j++)
            {
                if(user.getInterest().get(j).getId() == ProfileActivity.eventList.get(i).getTheme().getId() ) {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(ProfileActivity.eventList.get(i).getCoord().getLatitude(), ProfileActivity.eventList.get(i).getCoord().getLongitude())).title(ProfileActivity.eventList.get(i).getName()));
                }
            }
        }
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
        //getSupportActionBar().setTitle(activityTitle);
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

    private class AsyncRefreshDB extends AsyncTask<Void, Integer, Void>
    {

        private OnTaskCompleted listener;

        public void LinkTask(OnTaskCompleted listener) {
            this.listener = listener;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... arg0) {
            try {

                MainActivity.SyncDB();


            }
            catch(Exception e) {
                e.printStackTrace();


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            listener.onTaskCompleted(true,"Done");
        }


    }

    public void onLocationChanged(Location location) {
        AsyncRefreshDB refresh = new AsyncRefreshDB();
        refresh.LinkTask(this);
        refresh.execute();




    }

    public void onTaskCompleted(boolean success, String message)
    {
        if(success)
        {
            setUpMap();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Rafraichissant DB terminee", Toast.LENGTH_LONG).show();
        }
    }
}
