package com.localisation.events.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.localisation.events.R;
import com.localisation.events.menu.SlideMenu;
import com.localisation.events.model.OnTaskCompleted;
import com.localisation.events.model.User;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//pour créer un événement
public class CreateEventActivity extends ActionBarActivity implements OnTaskCompleted {
    private DrawerLayout menuLayout; //Layout Principal
    private ListView menuElementsList; //Menu
    private ActionBarDrawerToggle menuToggle; //Gère l'ouverture et la fermeture du menu

    private CharSequence menuTitle = "Menu";
    private CharSequence activityTitle = "Profil";
    private User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
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

        getLocation();
    }

    private void getLocation() {
        //TODO recupérer localisation actuelle
    }

    public void ValidateEvent(View view){
        EditText mName   = (EditText)findViewById(R.id.ev_name_field);
        EditText mPlaceName  = (EditText)findViewById(R.id.ev_place_name);
        EditText mAddress  = (EditText)findViewById(R.id.ev_address_field);
        EditText mDesc  = (EditText)findViewById(R.id.ev_desc_field);
        EditText m_DAA  = (EditText)findViewById(R.id.debut_an_field);
        EditText m_DMM  = (EditText)findViewById(R.id.debut_mois_field);
        EditText m_DJJ  = (EditText)findViewById(R.id.debut_jour_field);
        EditText m_DHH  = (EditText)findViewById(R.id.debut_heure_field);
        EditText m_Dmm  = (EditText)findViewById(R.id.debut_minute_field);
        EditText m_DSS  = (EditText)findViewById(R.id.debut_seconde_field);
        EditText m_EAA  = (EditText)findViewById(R.id.fin_an_field);
        EditText m_EMM  = (EditText)findViewById(R.id.fin_mois_field);
        EditText m_EJJ  = (EditText)findViewById(R.id.fin_jour_field);
        EditText m_EHH  = (EditText)findViewById(R.id.fin_heure_field);
        EditText m_Emm  = (EditText)findViewById(R.id.fin_minute_field);
        EditText m_ESS  = (EditText)findViewById(R.id.fin_seconde_field);

        java.util.Date startDate = null;
        java.util.Date endDate = null;
        Time startTime = null;
        Time endTime = null;
        try {
            int valSecD = Integer.parseInt(m_DSS.getText().toString());
            int valMinD = Integer.parseInt(m_Dmm.getText().toString());
            int valHeureD = Integer.parseInt(m_DHH.getText().toString());
            int valJourD = Integer.parseInt(m_DJJ.getText().toString());
            int valMoisD = Integer.parseInt(m_DMM.getText().toString());
            int valAnD = Integer.parseInt(m_DAA.getText().toString());
            int valSecE = Integer.parseInt(m_ESS.getText().toString());
            int valMinE = Integer.parseInt(m_Emm.getText().toString());
            int valHeureE = Integer.parseInt(m_EHH.getText().toString());
            int valJourE = Integer.parseInt(m_EJJ.getText().toString());
            int valMoisE = Integer.parseInt(m_EMM.getText().toString());
            int valAnE = Integer.parseInt(m_EAA.getText().toString());
            valAnD += 2000;
            valAnE += 2000;
            if(valJourD > 31 || valJourD < 1 || valJourE > 31 || valJourE < 1)
            {
                throw new IndexOutOfBoundsException("La valeur des jours est incorrect");
            }
            if(valMoisD > 12 || valMoisD < 1 || valMoisE > 12 || valMoisE < 1)
            {
                throw new IndexOutOfBoundsException("La valeur des mois est incorrect");
            }
            if(valHeureD > 23 || valHeureD < 0 || valHeureE > 23 || valHeureE < 0)
            {
                throw new IndexOutOfBoundsException("La valeur des heures est incorrect");
            }
            if(valMinD > 59 || valMinD < 0 || valMinE > 59 || valMinE < 0)
            {
                throw new IndexOutOfBoundsException("La valeur des minutes est incorrect");
            }
            if(valSecD > 59 || valSecD < 0 || valSecE > 59 || valSecE < 0)
            {
                throw new IndexOutOfBoundsException("La valeur des secondes est incorrect");
            }

            Integer valueDD = (valAnD * 10000 + valMoisD * 100 + valJourD);
            Integer valueDE = (valAnE * 10000 + valMoisE * 100 + valJourE);
            Integer valueTD = (valHeureD * 10000 + valMinE * 100 + valSecD);
            Integer valueTE = (valHeureE * 10000 + valMinE * 100 + valSecE);
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
            startDate =  originalFormat.parse(valueDD.toString());
            endDate =  originalFormat.parse(valueDE.toString());
            originalFormat = new SimpleDateFormat("HHmmSS");
            Date tempDate = originalFormat.parse(valueTD.toString());
            startTime = new Time(tempDate.getTime());
            tempDate = originalFormat.parse(valueTE.toString());
            endTime = new Time(tempDate.getTime());
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Veuillez entrez des valeurs numérique sensible pour la date" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
    }
        if (startDate != null && endDate != null) {
            if (mName.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour le nom de l'evénement.", Toast.LENGTH_LONG).show();
            } else if (mPlaceName.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour le nom du lieu.", Toast.LENGTH_LONG).show();
            } else if (mAddress.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour l'addresse.", Toast.LENGTH_LONG).show();
            } else if (mDesc.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour la description.", Toast.LENGTH_LONG).show();
            } else {
/*
                Button validate_button = (Button) findViewById(R.id.validateInscription);
                validate_button.setVisibility(View.INVISIBLE);
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.inscriptionProgress);
                progressBar.setVisibility(View.VISIBLE);*/
                Geocoder geoCoder = new Geocoder(this);
                double latitude = 0;
                double longitude = 0;
                try {

                    List<Address> matches = geoCoder.getFromLocationName(mAddress.getText().toString(),1);
                    Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
                    latitude = bestMatch.getLatitude();
                    longitude = bestMatch.getLongitude();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                if(latitude != 0 && longitude != 0) {
                    AsyncRegisterEventToDB dbConnection = new AsyncRegisterEventToDB();
                    dbConnection.InsertData(mName.getText().toString(), mDesc.getText().toString(), mPlaceName.getText().toString(), mAddress.getText().toString(), startDate, endDate, startTime, endTime, longitude, latitude, user.getId());
                    dbConnection.LinkTask(this);
                    dbConnection.execute();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "L'addresse n'a pu etre validée", Toast.LENGTH_LONG).show();
                }

            }
        }

    }

    public void onTaskCompleted(boolean success, String message)
    {
        if(success)
        {
            Intent intent = new Intent(CreateEventActivity.this, ProfileActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "La création de l'évènement a échoué. (" + message + ")", Toast.LENGTH_LONG).show();
        }
    }

    private class AsyncRegisterEventToDB extends AsyncTask<Void, Integer, Void>
    {
        private static final String url = "jdbc:mysql://android.safe.ca:3306/events";
        private static final String user = "Vincent";
        private static final String pass = "test";

        private String _nomEvenement = null;
        private String _descEvenement = null;
        private String _nomPlace = null;
        private String _Addresse = null;
        private double _longitude  = 0;
        private double _latitude = 0;
        private Date _dateDebut = null;
        private Date _dateFin = null;
        private Time _tempsDebut =null;
        private Time _tempsFin = null;
        private int _userID = 0;
        private String resultMessage = null;
        boolean success = false;


        private OnTaskCompleted listener;

        public void LinkTask(OnTaskCompleted listener){
            this.listener=listener;
        }

        public void InsertData(String nomEvenement, String descEvenement, String nomPlace, String Addresse, Date dateDebut, Date dateFin , Time tempsDebut, Time tempsFin, double longitude, double latitude, int userID) {
            _nomEvenement = nomEvenement;
            _descEvenement = descEvenement;
            _nomPlace = nomPlace;
            _Addresse = Addresse;
            _dateDebut = dateDebut;
            _dateFin = dateFin;
            _tempsDebut = tempsDebut;
            _tempsFin = tempsFin;
            _longitude = longitude;
            _latitude = latitude;
            _userID = userID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                if(MainActivity.conn == null || MainActivity.conn.isClosed() ) {
                    Class.forName("com.mysql.jdbc.Driver");
                    MainActivity.conn = DriverManager.getConnection(url, user, pass);
                }

                // create a Statement from the connection
                Statement statement = MainActivity.conn.createStatement();

                java.sql.Date sqlEndDate = new java.sql.Date(_dateFin.getTime());
                java.sql.Date sqlStartDate = new java.sql.Date(_dateDebut.getTime());
                Time sqlEndTime = new java.sql.Time(_tempsFin.getTime());
                Time sqlStartTime = new java.sql.Time(_tempsDebut.getTime());
                statement.executeUpdate("INSERT INTO Coord (latitude, longitude) VALUES ('" + _latitude + "', '" + _longitude + "');");
                ResultSet rs  = statement.executeQuery("SELECT coord_id FROM Coord WHERE latitude = '" + _latitude + "' AND longitude = '" + _longitude + "';");
                int coord_id = 0;
                while(rs.next()) {
                    coord_id = rs.getInt("coord_id");
                }
                statement.executeUpdate("INSERT INTO Event (name,description, visibility, place_name, coord_id, address, start_date, end_date, start_time, end_time, theme_id, organizer_id)" + "VALUES ('" +_nomPlace + "', '" + _descEvenement + "', '1', '" + _nomPlace + "', '" + coord_id + "', '" + _Addresse + "', '" + sqlStartDate + "', '" + sqlEndDate +  "', '" + sqlStartTime + "', '" + sqlEndTime + "', '2', '" + _userID + "')");
                resultMessage = "DB insertion success";

                MainActivity.SyncDB();
                success = true;

            }
            catch(Exception e) {
                e.printStackTrace();
                resultMessage = e.getMessage();
                success =  false;

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            listener.onTaskCompleted(success,resultMessage);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
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
//        boolean drawerOpen = menuLayout.isDrawerOpen(menuElementsList);
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
        //menuToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        //menuToggle.onConfigurationChanged(newConfig);
    }
}
