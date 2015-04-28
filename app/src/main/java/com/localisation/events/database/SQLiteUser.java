package com.localisation.events.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.localisation.events.model.User;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zalila on 2015-04-26.
 */
public class SQLiteUser extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "events_db";

    Context context;

    public SQLiteUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USER_TABLE = "CREATE TABLE user ( " +
                "id INTEGER PRIMARY KEY, " +
                "first_name TEXT, "+
                "last_name TEXT, "+
                "city TEXT " +
                "bday TEXT " +
                "login TEXT " +
                "pwd TEXT " +
                "email TEXT " +
                "phone TEXT " +
                "device TEXT " +
                "last_cxn TEXT " +
                ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        this.onCreate(db);
    }

    private static final String TABLE_USER = "user";

    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_CITY = "city";
    private static final String KEY_B_DAY = "bday";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PWD = "pwd";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_DEVICE = "device";
    private static final String KEY_LAST_CXN = "last_cxn";

    private static final String[] COLUMNS = {KEY_ID,KEY_FIRST_NAME, KEY_LAST_NAME, KEY_CITY, KEY_B_DAY,
    KEY_LOGIN, KEY_PWD, KEY_EMAIL, KEY_PHONE, KEY_DEVICE, KEY_LAST_CXN};

    public void addUser(User user, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, String.valueOf(user.getId()));
        values.put(KEY_FIRST_NAME, user.getFirstName());
        values.put(KEY_LAST_NAME, user.getLastName());
        values.put(KEY_CITY, user.getCity());
        values.put(KEY_B_DAY, String.valueOf(user.getbDate()));
        values.put(KEY_LOGIN, user.getLogin());
        values.put(KEY_PWD, user.getPassword());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_DEVICE, user.getDevice());
        values.put(KEY_LAST_CXN, String.valueOf(user.getLastConnection()));
        db.insert(TABLE_USER, null, values);

        //TODO save interet et event

        db.close();
    }

    public User getUser(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_USER, COLUMNS, " id = ?", new String[] { String.valueOf(id) },
                        null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User();
        user.setId(Integer.parseInt(cursor.getString(0)));
        user.setFirstName(cursor.getString(1));
        user.setLastName(cursor.getString(2));
        user.setCity(cursor.getString(3));
        user.setbDate(Date.valueOf(cursor.getString(4)));
        user.setLogin(cursor.getString(5));
        user.setPassword(cursor.getString(6));
        user.setEmail(cursor.getString(7));
        user.setPhone(cursor.getString(8));
        user.setDevice(cursor.getString(9));
        user.setLastConnection(Timestamp.valueOf(cursor.getString(10)));

        //TODO get interets et event

        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();

        String query = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setFirstName(cursor.getString(1));
                user.setLastName(cursor.getString(2));
                user.setCity(cursor.getString(3));
                user.setbDate(Date.valueOf(cursor.getString(4)));
                user.setLogin(cursor.getString(5));
                user.setPassword(cursor.getString(6));
                user.setEmail(cursor.getString(7));
                user.setPhone(cursor.getString(8));
                user.setDevice(cursor.getString(9));
                user.setLastConnection(Timestamp.valueOf(cursor.getString(10)));

                users.add(user);
            } while (cursor.moveToNext());
        }
        return users;
    }

    public int updateUser(User user, int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, String.valueOf(user.getId()));
        values.put(KEY_FIRST_NAME, user.getFirstName());
        values.put(KEY_LAST_NAME, user.getLastName());
        values.put(KEY_CITY, user.getCity());
        values.put(KEY_B_DAY, String.valueOf(user.getbDate()));
        values.put(KEY_LOGIN, user.getLogin());
        values.put(KEY_PWD, user.getPassword());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_DEVICE, user.getDevice());
        values.put(KEY_LAST_CXN, String.valueOf(user.getLastConnection()));

        int i = db.update(TABLE_USER, values, KEY_ID+" = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
        return i;
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, KEY_ID+" = ?",
                new String[] { String.valueOf(user.getId()) });

        db.close();
    }

    public int userCount(){
        int number = 0;
        String query = "SELECT count(*) FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                number = Integer.parseInt(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        db.close();
        return number;
    }
}
