package com.localisation.events;

import android.content.Context;
import android.content.Intent;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.localisation.events.model.OnTaskCompleted;
import com.localisation.events.model.User;


//page de login
public class MainActivity extends ActionBarActivity implements OnTaskCompleted {

    public static Connection conn = null;

    private static final String url = "jdbc:mysql://android.safe.ca:3306/events";
    private static final String user = "Vincent";
    private static final String pass = "test";
    private static int client_id = 0;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private class AsyncConnectToDB extends AsyncTask<Void, Integer, Void>
    {
        private String _email = null;
        private String _password = null;
        private String resultMessage = null;
        boolean success = false;

        private OnTaskCompleted listener;

        public void LinkTask(OnTaskCompleted listener){
            this.listener=listener;
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
            listener.onTaskCompleted(success,resultMessage);
        }

        public void Connect() {
            try {
                if(MainActivity.conn == null ||MainActivity.conn.isClosed() ) {
                    Class.forName("com.mysql.jdbc.Driver");
                    MainActivity.conn = DriverManager.getConnection(url, user, pass);
                }

                String result = "Database connection success\n";
                Statement st = MainActivity.conn.createStatement();
                String query = "select * from User where email = '" + _email + "' AND password = '" + _password + "';";
                ResultSet rs = st.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();

                while(rs.next()) {
                    if(ProfileActivity.myself == null) {
                        ProfileActivity.myself = new User();
                    }
                    ProfileActivity.myself.setId(rs.getInt("user_id"));
                    client_id =  ProfileActivity.myself.getId();
                    ProfileActivity.myself.setFirstName(rs.getString("first_name"));
                    ProfileActivity.myself.setLastName(rs.getString("last_name"));
                    ProfileActivity.myself.setbDate(rs.getDate("bDate"));
                    ProfileActivity.myself.setLogin(rs.getString("login"));
                    ProfileActivity.myself.setPassword(rs.getString("password"));
                    ProfileActivity.myself.setDevice(rs.getString("device"));
                    ProfileActivity.myself.setCity(rs.getString("city"));
                    ProfileActivity.myself.setEmail(rs.getString("email"));
                }
                if(client_id == 0)
                {
                    throw new Exception("Invalid client ID");
                }

                query = "update User SET last_connection=now() WHERE user_id=' " + ProfileActivity.myself.getId() + "';";
                boolean statementSuccess = st.execute(query);

                success = true;
            }
            catch(Exception e) {
                e.printStackTrace();
                resultMessage = e.getMessage().toString();
                success =  false;

            }
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
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
            intent.putExtra("CLIENT_ID", client_id);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "L'application n'a actuellement pas de connection, et aucune session n'a pas été sauvegardé.", Toast.LENGTH_LONG).show();
        }

    }

    public void registrationClick(View view){
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);

        startActivity(intent);
    }

    public void onTaskCompleted(boolean success, String message)
    {
        if(success)
        {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("CLIENT_ID", client_id);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "La connexion à échoué (" + message + ")", Toast.LENGTH_LONG).show();
        }
    }
}
