package com.localisation.events.activity;

import android.content.res.Configuration;
import android.location.LocationListener;
import android.os.AsyncTask;
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
import com.localisation.events.model.OnTaskCompleted;
import com.localisation.events.model.Theme;
import com.localisation.events.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class InterestActivity extends ActionBarActivity {

    private DrawerLayout menuLayout; //Layout Principal
    private ListView menuElementsList; //Menu
    private ActionBarDrawerToggle menuToggle; //Gère l'ouverture et la fermeture du menu

    private CharSequence menuTitle = "Menu";
    private CharSequence activityTitle = "Intérêts";
    private User user;

    public static User userS;

    public User getUser() {
        return user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        //user = getIntent().getParcelableExtra("user");

        if (getIntent().getStringExtra("activity").equals("main"))
            user = ProfileActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("registration"))
            user = ProfileActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("explore"))
            user = ProfileActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("create"))
            user = ProfileActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("events"))
            user = ProfileActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("invitations"))
            user = ProfileActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("interest"))
            user = ProfileActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("profile"))
            user = ProfileActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("maps"))
            user = ProfileActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("invitation"))
            user = ProfileActivity.userS;
        else if (getIntent().getStringExtra("activity").equals("event"))
            user = ProfileActivity.userS;

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
        final Vector<String> themes = new Vector<>();

        for (Theme t : user.getInterest())
            themes.add(t.getName());

        final InterestButtonAdapter adapter = new InterestButtonAdapter(this, user.getInterest());
        final ListView listInterest = (ListView) findViewById(R.id.interestListView);
        listInterest.setAdapter(adapter);

        listInterest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout linearLayout = (LinearLayout) listInterest.getAdapter().getView(position, view, parent);
                ImageView imageView;
                if (themes.contains(((TextView) linearLayout.getChildAt(0)).getText())){
                    Theme t = user.getInterest().get(themes.indexOf(((TextView) linearLayout.getChildAt(0)).getText()));
                    user.getInterest().removeElement(t);
                    themes.removeElement(t.getName());
                    imageView = ((ImageView) linearLayout.getChildAt(1));
                    imageView.setImageResource(R.drawable.vide);
                    imageView.refreshDrawableState();

                    AsyncConnectToDB asyncConnect = new AsyncConnectToDB();
                    asyncConnect.InsertData(user.getId(), t.getId(), false);
                    asyncConnect.execute();
                }else{
                    Theme t = adapter.getAll().get(position);
                    user.getInterest().add(t);
                    imageView = ((ImageView) linearLayout.getChildAt(1));
                    imageView.setImageResource(R.drawable.ok);
                    imageView.refreshDrawableState();
                    themes.add(t.getName());

                    AsyncConnectToDB asyncConnect = new AsyncConnectToDB();
                    asyncConnect.InsertData(user.getId(), t.getId(), true);
                    asyncConnect.execute();
                }
            }
        });
    }


    private class AsyncConnectToDB extends AsyncTask<Void, Integer, Void> {
        private int _user_id = 0;
        private int _theme_id = 0;
        private boolean _add = false;
        private String resultMessage = null;
        boolean success = false;



        public void InsertData(int user_id, int theme_id, boolean add) {
            _user_id = user_id;
            _theme_id = theme_id;
            _add= add;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... arg0) {
            RefreshUserInterest();
            return null;
        }

        private void RefreshUserInterest() {
            String query = "";
            if(!_add)
                query = "DELETE FROM user_theme WHERE user_id ='" + _user_id + "' AND theme_id = '" + _theme_id + "';";
            else
                query = "Insert INTO user_theme VALUES('"+ _user_id + "', '" + _theme_id + "');";
            Statement st = null;
            try {
                st = MainActivity.conn.createStatement();
                st.execute(query);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

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
