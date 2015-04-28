package com.localisation.events.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.provider.ContactsContract;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.localisation.events.model.Event;
import com.localisation.events.model.Theme;
import com.localisation.events.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Vector;

import com.localisation.events.R;
import com.localisation.events.adapter.InterestAdapter;
import com.localisation.events.menu.SlideMenu;
import com.localisation.events.adapter.MenuAdapter;
import com.localisation.events.model.User;

//pour afficher le profil de l'utilisateur
public class ProfileActivity extends ActionBarActivity {

    public static Vector<Event> eventList = new Vector<Event>();
    public static Vector<Theme> interestList = new Vector<Theme>();
    public static Vector<User> userList = new Vector<User>();
    public static HashMap<Integer, List<Integer>> event_to_users = new HashMap<Integer, List<Integer>>() ;
    private DrawerLayout menuLayout; //Layout Principal
    private ListView menuElementsList; //Menu
    private ActionBarDrawerToggle menuToggle; //Gère l'ouverture et la fermeture du menu

    private CharSequence menuTitle = "Menu";
    private CharSequence activityTitle = "Profil";

    private User user;
    public static User userS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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

        userS = user;


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


        activityTitle = user.getFirstName() + " " + user.getLastName();
        fillProfile(user);


    }

    private void fillProfile(User user) {
        TextView email = (TextView) findViewById(R.id.email_text);
        email.setText(user.getEmail());
        TextView login = (TextView) findViewById(R.id.pseudo_text);
        login.setText(user.getLogin());
        TextView longitude = (TextView) findViewById(R.id.longitudeTextView);
        longitude.setText(String.valueOf(user.getCoord().getLongitude()));
        TextView latitude = (TextView) findViewById(R.id.latitudeTextView);
        latitude.setText(String.valueOf(user.getCoord().getLatitude()));
        TextView lastCxn = (TextView) findViewById(R.id.last_cxn_text);
        lastCxn.setText(String.valueOf(user.getLastConnection()));
        TextView firstName = (TextView) findViewById(R.id.first_name_text);
        firstName.setText(user.getFirstName());
        TextView lastName = (TextView) findViewById(R.id.last_name_text);
        lastName.setText(user.getLastName());
        TextView bDay = (TextView) findViewById(R.id.bDate_text);
        bDay.setText(String.valueOf(user.getbDate()));
        TextView city = (TextView) findViewById(R.id.city_text);
        city.setText(user.getCity());
        TextView phone = (TextView) findViewById(R.id.phone_text);
        phone.setText(user.getPhone());
        if (user.getInterest().size() > 0) {
            //InterestAdapter adapter = new InterestAdapter(this, user.getInterest());
            //ListView listInterest = (ListView) findViewById(R.id.interestListView);
            //listInterest.setAdapter(adapter);
        }else{
            //TextView msgView = (TextView) findViewById(R.id.msgTextView);
            //msgView.setText("aucun interet sauvegardé");
        }
    }

    public void updateClick(View view){
        Intent intent = new Intent(ProfileActivity.this, RegistrationActivity.class);
        //intent.putExtra("user", user);
        userS = user;
        intent.putExtra("activity", "profile");
        startActivity(intent);
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
