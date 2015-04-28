package com.localisation.events.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.localisation.events.*;
import com.localisation.events.model.OnTaskCompleted;
import com.localisation.events.model.User;

import java.sql.DriverManager;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//pour s'inscrire
public class RegistrationActivity extends ActionBarActivity implements OnTaskCompleted {

    private User user;
    public static User userS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //user = getIntent().getParcelableExtra("user");
        if (getIntent().getStringExtra("activity").equals("main"))
            user = MainActivity.userS;
        else
            user = ProfileActivity.userS;
        userS = user;

        if (user != new User()){
            //fillFields(user);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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

    public void registrateClick(View view) throws ParseException {
        EditText mPass   = (EditText)findViewById(R.id.password_text);
        EditText mConfirm  = (EditText)findViewById(R.id.confirm_pass_text);
        EditText mEmail = (EditText)findViewById(R.id.email_text);
        EditText mPseudo = (EditText)findViewById(R.id.pseudo_text);
        EditText mFirst = (EditText)findViewById(R.id.first_name_text);
        EditText mLast = (EditText)findViewById(R.id.last_name_text);
        EditText mJour = (EditText)findViewById(R.id.bd_JJ_text);
        EditText mMois = (EditText)findViewById(R.id.bd_MM_text);
        EditText mAn = (EditText)findViewById(R.id.bd_AA_text);
        EditText mVille = (EditText)findViewById(R.id.city_text);
        EditText mPhone = (EditText)findViewById(R.id.phone_text);
        java.util.Date date = null;
        try {
            int valJour = Integer.parseInt(mJour.getText().toString());
            int valMois = Integer.parseInt(mMois.getText().toString());
            int valAn = Integer.parseInt(mAn.getText().toString());
            if (valAn > 15) {
                valAn += 1900;
            } else {
                valAn += 2000;
            }
            if(valJour > 31 || valJour < 1)
            {
                throw new IndexOutOfBoundsException("La valeur des jours est incorrect");
            }
            if(valMois > 12 || valMois < 1)
            {
                throw new IndexOutOfBoundsException("La valeur des mois est incorrect");
            }
            if(valAn > 2015)
            {
                throw new IndexOutOfBoundsException("La valeur de l'année est incorrect");
            }
            Integer value = valAn * 10000 + valMois * 100 + valJour;
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
            date =  originalFormat.parse(value.toString());
            Log.v("", date.toString());
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Veuillez entrez des valeurs numérique sensible pour la date", Toast.LENGTH_LONG).show();
        }
        if(mPass.getText().toString().equals( mConfirm.getText().toString()))
        {
            if(mEmail.getText().toString().length() == 0)
            {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour le email.", Toast.LENGTH_LONG).show();
            }
            else if(mPseudo.getText().toString().length() == 0)
            {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour le pseudo.", Toast.LENGTH_LONG).show();
            }
            else if(mPass.getText().toString().length() == 0)
            {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour le mot de passe.", Toast.LENGTH_LONG).show();
            }
            else if(mFirst.getText().toString().length() == 0)
            {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour le prénom.", Toast.LENGTH_LONG).show();
            }
            else if(mLast.getText().toString().length() == 0)
            {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour le nom de famille.", Toast.LENGTH_LONG).show();
            }
            else if(mJour.getText().toString().length() == 0 )
            {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour le jour de naissance.", Toast.LENGTH_LONG).show();
            }
            else if(mMois.getText().toString().length() == 0 )
            {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour le mois de naissance", Toast.LENGTH_LONG).show();
            }
            else if(mAn.getText().toString().length() == 0 )
            {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour l'année de naissance", Toast.LENGTH_LONG).show();
            }
            else if(mVille.getText().toString().length() == 0 )
            {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour la ville", Toast.LENGTH_LONG).show();
            }
            else if(mPhone.getText().toString().length() == 0 )
            {
                Toast.makeText(getApplicationContext(), "Veuillez entrez une valeur pour le numéro de téléphone", Toast.LENGTH_LONG).show();
            }
            else {

                Button validate_button = (Button) findViewById(R.id.validateInscription);
                validate_button.setVisibility(View.INVISIBLE);
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.inscriptionProgress);
                progressBar.setVisibility(View.VISIBLE);
                AsyncRegisterToDB dbConnection = new AsyncRegisterToDB();
                dbConnection.InsertData(mEmail.getText().toString(), mPseudo.getText().toString(), mPass.getText().toString(), mFirst.getText().toString(), mLast.getText().toString(), date, mVille.getText().toString(), mPhone.getText().toString());
                dbConnection.LinkTask(this);
                dbConnection.execute();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Mot de passe et sa confirmation ne correspondent pas", Toast.LENGTH_LONG).show();
        }
    }

    public void onTaskCompleted(boolean success, String message)
    {
        if(success)
        {
            Intent intent = new Intent(RegistrationActivity.this, ProfileActivity.class);
            intent.putExtra("activity", "registration");
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "L'inscription a échoué. (" + message + ")", Toast.LENGTH_LONG).show();
            Button validate_button = (Button) findViewById(R.id.validateInscription);
            validate_button.setVisibility(View.VISIBLE);
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.inscriptionProgress);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private class AsyncRegisterToDB extends AsyncTask<Void, Integer, Void>
    {
        private static final String url = "jdbc:mysql://android.safe.ca:3306/events";
        private static final String user = "Vincent";
        private static final String pass = "test";

        private String _email = null;
        private String _pseudo = null;
        private String _password = null;
        private String _first_name = null;
        private String _last_name = null;
        private java.util.Date _birthday = null;
        private String _city_name = null;
        private String _telephone = null;
        private String resultMessage = null;
        boolean success = false;

        private OnTaskCompleted listener;

        public void LinkTask(OnTaskCompleted listener){
            this.listener=listener;
        }

        public void InsertData(String email, String pseudo, String password, String first_name, String last_name, java.util.Date birthday, String city_name, String telephone) {
            _email = email;
            _pseudo = pseudo;
            _password = password;
            _first_name = first_name;
            _last_name = last_name;
            _birthday = birthday;
            _city_name = city_name;
            _telephone = telephone;
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
                java.sql.Date sqlDate = new java.sql.Date(_birthday.getTime());
                statement.executeUpdate("INSERT INTO User (first_name,last_name, bDate, login, password, device, city, email, telephone" + ")" + "VALUES ('" +_first_name + "', '" + _last_name + "', '" + sqlDate + "', '" + _pseudo + "', '" + _password + "', 'Android', '" + _city_name + "', '" + _email + "', '" + _telephone + "')");
                resultMessage = "DB insertion success";
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


}