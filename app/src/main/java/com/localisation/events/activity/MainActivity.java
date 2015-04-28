package com.localisation.events.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.localisation.events.*;
import com.localisation.events.activity.ProfileActivity;
import com.localisation.events.model.Coord;
import com.localisation.events.model.Event;
import com.localisation.events.model.OnTaskCompleted;
import com.localisation.events.model.Theme;
import com.localisation.events.model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.localisation.events.model.OnTaskCompleted;
import com.localisation.events.model.User;


//page de login
public class MainActivity extends ActionBarActivity implements OnTaskCompleted {
    private User myself = null;
    public static Connection conn = null;
    public static boolean canGetLocation = false;
    private static final String url = "jdbc:mysql://android.safe.ca:3306/events";
    private static final String user = "Vincent";
    private static final String pass = "test";
    private static int client_id = 0;
    public static ConnectivityManager connectivityManager =null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //GPSLocation GPSLocator = new GPSLocation(this, null);
    }

    public static Boolean SyncDB() throws SQLException, ClassNotFoundException {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (MainActivity.conn == null || MainActivity.conn.isClosed()) {
                Class.forName("com.mysql.jdbc.Driver");
                MainActivity.conn = DriverManager.getConnection(url, user, pass);
            }
            ProfileActivity.interestList = new Vector<Theme>();
            String query = "select * from Theme;";
            Statement st = MainActivity.conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                ProfileActivity.interestList.add(new Theme(rs.getInt("theme_id"), rs.getString("name"), rs.getString("groupe")));
            }
            ProfileActivity.eventList = new Vector<Event>();
            query = "select * from Event, Coord where Event.coord_id = Coord.coord_id;";
            rs = st.executeQuery(query);
            rsmd = rs.getMetaData();


            while (rs.next()) {
                Coord tempCoord = new Coord(rs.getDouble("longitude"), rs.getDouble("latitude"), 0);
                Event tempEvent = new Event();
                tempEvent.setId(rs.getInt("event_id"));
                tempEvent.setName(rs.getString("name"));
                tempEvent.setDescription(rs.getString("description"));
                tempEvent.setVisibility(rs.getBoolean("visibility"));
                tempEvent.setStartDate(rs.getDate("start_date"));
                tempEvent.setEndDate(rs.getDate("end_date"));
                tempEvent.setTheme(ProfileActivity.interestList.get(rs.getInt("theme_id")-1));
                //tempEvent.setOrganizers();
                tempEvent.setPlace_name(rs.getString("place_name"));
                tempEvent.setAddress(rs.getString("address"));
                tempEvent.setCoord(tempCoord);
                ProfileActivity.eventList.add(tempEvent);

            }

        }

        return true;
    }


    private class AsyncConnectToDB extends AsyncTask<Void, Integer, Void> implements LocationListener {
        private String _email = null;
        private String _password = null;
        private String resultMessage = null;
        boolean success = false;

        private OnTaskCompleted listener;

        public void LinkTask(OnTaskCompleted listener) {
            this.listener = listener;
        }

        public void InsertData(String email, String password) {
            _email = email;
            _password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... arg0) {
            Connect();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            listener.onTaskCompleted(success, resultMessage);
        }





        public void Connect() {
            try {
                if (MainActivity.conn == null || MainActivity.conn.isClosed()) {
                    Class.forName("com.mysql.jdbc.Driver");
                    MainActivity.conn = DriverManager.getConnection(url, user, pass);
                }

                String result = "Database connection success\n";
                Statement st = MainActivity.conn.createStatement();
                String query = "select * from User where email = '" + _email + "' AND password = '" + _password + "';";
                ResultSet rs = st.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    if (myself == null) {
                        myself = new User();
                    }
                    myself.setId(rs.getInt("user_id"));
                    client_id = myself.getId();
                    myself.setFirstName(rs.getString("first_name"));
                    myself.setLastName(rs.getString("last_name"));
                    myself.setbDate(rs.getDate("bDate"));
                    myself.setLogin(rs.getString("login"));
                    myself.setPassword(rs.getString("password"));
                    myself.setDevice(rs.getString("device"));
                    myself.setCity(rs.getString("city"));
                    myself.setEmail(rs.getString("email"));
                    myself.setPhone(rs.getString("telephone"));
                }
                if (client_id == 0) {
                    throw new Exception("Invalid client ID");
                }
                query = "update User SET last_connection=now() WHERE user_id=' " + myself.getId() + "';";
                boolean statementSuccess = st.execute(query);

                double latitude = 0.0;
                double longitude = 0.0;
                Location location = null;
                LocationManager locationManager;
                boolean isGPSEnabled;
                boolean isNetworkEnabled;
                long MIN_TIME_BW_UPDATES = 10;
                long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
                Context mContext = getApplicationContext();
                try {
                    locationManager = (LocationManager) mContext
                            .getSystemService(LOCATION_SERVICE);

                    // getting GPS status
                    isGPSEnabled = locationManager
                            .isProviderEnabled(LocationManager.GPS_PROVIDER);

                    // getting network status
                    isNetworkEnabled = locationManager
                            .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                    if (!isGPSEnabled && !isNetworkEnabled) {
                        // no network provider is enabled
                    } else {
                        MainActivity.canGetLocation = true;

                        if (isNetworkEnabled) {
                            /*
                            locationManager.requestLocationUpdates(
                                    LocationManager.NETWORK_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);*/
                            Log.d("Network", "Network Enabled");
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                        // if GPS Enabled get lat/long using GPS Services
                        if (isGPSEnabled) {
                            if (location == null) {
                                /*
                                locationManager.requestLocationUpdates(
                                        LocationManager.GPS_PROVIDER,
                                        MIN_TIME_BW_UPDATES,
                                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                                        */
                                Log.d("GPS", "GPS Enabled");
                                if (locationManager != null) {
                                    location = locationManager
                                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                    if (location != null) {
                                        latitude = location.getLatitude();
                                        longitude = location.getLongitude();
                                    }
                                }
                            }
                        }
                    }

                    if (longitude != 0) {
                        query = "update User SET longitude=' " + longitude + "' WHERE user_id=' " + myself.getId() + "';";
                        statementSuccess = st.execute(query);
                    }
                    if (latitude != 0) {
                        query = "update User SET latitude=' " + latitude + "' WHERE user_id=' " + myself.getId() + "';";
                        statementSuccess = st.execute(query);
                    }
                    myself.setCoord(new Coord(longitude, latitude, 0.0));

                    query = "select last_connection from User where email = '" + _email + "' AND password = '" + _password + "';";
                    rs = st.executeQuery(query);
                    rsmd = rs.getMetaData();

                    while (rs.next()) {
                        myself.setLastConnection(rs.getTimestamp(1));
                    }

                    success = SyncDB();

                    query = "select theme_id from user_theme where user_id='" + myself.getId() + "';";
                    Vector<Theme> interest = new Vector<Theme>();
                    rs = st.executeQuery(query);
                    while (rs.next()) {
                        interest.add(ProfileActivity.interestList.get(rs.getInt("theme_id")));
                    }
                    myself.setInterest(interest);
                }
                catch(Exception e) {
                    e.printStackTrace();
                    resultMessage = e.getMessage().toString();
                    success = false;
                }
            }
            catch(Exception e) {
                e.printStackTrace();
                resultMessage = e.getMessage().toString();
                success =  false;

            }
        }

        public  void onLocationChanged(Location location) {
            InvitationsMapActivity.onLocationChanged(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //Nothing
        }

        @Override
        public void onProviderEnabled(String provider) {
            //Nothing
        }

        @Override
        public void onProviderDisabled(String provider) {
            //Nothing
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void testClick(View view){
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
        {
            EditText mPass = (EditText) findViewById(R.id.conn_Pass);
            EditText mEmail = (EditText) findViewById(R.id.conn_Email);
            AsyncConnectToDB asyncConnect = new AsyncConnectToDB();
            asyncConnect.LinkTask(this);
            asyncConnect.InsertData(mEmail.getText().toString(), mPass.getText().toString());
            asyncConnect.execute();
        }
        else if(client_id != 0)
        {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            //intent.putExtra("user", myself);
            userS = myself;
            intent.putExtra("activity", "main");
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "L'application n'a actuellement pas de connection, et aucune session n'a pas été sauvegardé.", Toast.LENGTH_LONG).show();
        }

    }

    public static User userS;

    public void registrationClick(View view){
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        //intent.putExtra("user", new User());
        userS = new User();
        intent.putExtra("activity", "main");
        startActivity(intent);
    }

    public void onTaskCompleted(boolean success, String message)
    {
        if(success)
        {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            //intent.putExtra("user", myself);
            userS = myself;
            intent.putExtra("activity", "main");
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "La connexion à échoué (" + message + ")", Toast.LENGTH_LONG).show();
        }
    }
}
