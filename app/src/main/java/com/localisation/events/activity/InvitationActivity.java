package com.localisation.events.activity;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.localisation.events.R;
import com.localisation.events.adapter.InvitationAdapter;
import com.localisation.events.menu.SlideMenu;
import com.localisation.events.model.Theme;
import com.localisation.events.model.User;

import java.util.ArrayList;
import java.util.List;

//pour rédiger le message de l'invitation
public class InvitationActivity extends ActionBarActivity {

    private DrawerLayout menuLayout; //Layout Principal
    private ListView menuElementsList; //Menu
    private ActionBarDrawerToggle menuToggle; //Gère l'ouverture et la fermeture du menu

    private CharSequence menuTitle = "Menu";
    private CharSequence activityTitle = "Invitation à un événement";

    private User user;
    public static User userS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

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

        //activation du bouton home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_drawer);

        ListView list = (ListView) findViewById(R.id.participantslistView);
        List<String> invites = new ArrayList<>();
        //TODO liste des invités
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, invites);
        list.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_invitation, menu);
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
