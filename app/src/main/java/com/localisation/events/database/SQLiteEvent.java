package com.localisation.events.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.localisation.events.model.Event;
import com.localisation.events.model.User;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Zalila on 2015-04-26.
 */
public class SQLiteEvent extends SQLiteOpenHelper {

    Context context;

    public SQLiteEvent(Context context) {
        super(context, SQLiteUser.DATABASE_NAME, null, SQLiteUser.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_EVENT_TABLE = "CREATE TABLE event ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "description TEXT, " +
                "visibility INTEGER, " +
                "place_name TEXT, " +
                "address TEXT, " +
                "start_date TEXT, " +
                "end_date TEXT, " +
                "start_time TEXT, " +
                "end_time TEXT, " +
                "organizer_id INTEGER, " +
                "coord_id INTEGER, " +
                "event_id TEXT, " +
                "FOREIGN KEY(organizer_id) REFERENCES user(id) ON UPDATE CASCADE, "+
                "FOREIGN KEY(coord_id) REFERENCES coord(id) ON UPDATE CASCADE, "+
                "FOREIGN KEY(event_id) REFERENCES event(id) ON UPDATE CASCADE "+
                ")";

        db.execSQL(CREATE_EVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS event");
        this.onCreate(db);
    }

    private static final String TABLE_EVENT = "event";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_V = "visibility";
    private static final String KEY_PLACE = "place_name";
    private static final String KEY_ADR = "address";
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_END_DATE = "end_date";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_ORGANIZER_ID = "organizer_id";
    private static final String KEY_COORD_ID = "coord_id";
    private static final String KEY_THEME_ID = "event_id";

    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_DESC, KEY_V, KEY_PLACE, KEY_ADR,
             KEY_START_DATE, KEY_END_DATE, KEY_START_TIME, KEY_END_TIME, KEY_ORGANIZER_ID,
             KEY_COORD_ID, KEY_THEME_ID};

    public void addEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, String.valueOf(event.getId()));
        values.put(KEY_NAME, event.getName());
        values.put(KEY_DESC, event.getDescription());
        values.put(KEY_V, String.valueOf(event.isVisibility()));
        values.put(KEY_PLACE, event.getPlace_name());
        values.put(KEY_ADR, event.getAddress());
        values.put(KEY_START_DATE, String.valueOf(event.getStartDate()));
        values.put(KEY_END_DATE, String.valueOf(event.getEndDate()));
        values.put(KEY_START_TIME, String.valueOf(event.getStartDate().getTime()));
        values.put(KEY_END_TIME, String.valueOf(event.getEndDate().getTime()));
        values.put(KEY_ORGANIZER_ID, event.getOrganizers().get(0).getId());
        values.put(KEY_COORD_ID, event.getCoord().getId());
        values.put(KEY_THEME_ID, event.getTheme().getId());

        db.insert(TABLE_EVENT, null, values);

        db.close();
    }

    public Event getEvent(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_EVENT, COLUMNS, " id = ?", new String[] { String.valueOf(id) },
                        null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Event event = new Event();
        event.setId(Integer.parseInt(cursor.getString(0)));
        event.setName(cursor.getString(1));
        event.setDescription(cursor.getString(2));
        event.setVisibility(Boolean.valueOf(cursor.getString(3)));
        event.setPlace_name(cursor.getString(4));
        event.setAddress(cursor.getString(5));
        event.setStartDate(Date.valueOf(cursor.getString(6)));
        event.setEndDate(Date.valueOf(cursor.getString(7)));
        SQLiteUser user = new SQLiteUser(context);
        event.getOrganizers().add(user.getUser(Integer.parseInt(cursor.getString(10))));
        SQLiteCoord coord = new SQLiteCoord(context);
        event.setCoord(coord.getCoord(Integer.parseInt(cursor.getString(11))));
        SQLiteTheme theme = new SQLiteTheme(context);
        event.setTheme(theme.getTheme(Integer.parseInt(cursor.getString(12))));

        return event;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new LinkedList<>();

        String query = "SELECT * FROM " + TABLE_EVENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Event event = null;
        if (cursor.moveToFirst()) {
            do {
                event = new Event();
                event.setId(Integer.parseInt(cursor.getString(0)));
                event.setName(cursor.getString(1));
                event.setDescription(cursor.getString(2));
                event.setVisibility(Boolean.valueOf(cursor.getString(3)));
                event.setPlace_name(cursor.getString(4));
                event.setAddress(cursor.getString(5));
                event.setStartDate(Date.valueOf(cursor.getString(6)));
                event.setEndDate(Date.valueOf(cursor.getString(7)));
                SQLiteUser user = new SQLiteUser(context);
                event.getOrganizers().add(user.getUser(Integer.parseInt(cursor.getString(10))));
                SQLiteCoord coord = new SQLiteCoord(context);
                event.setCoord(coord.getCoord(Integer.parseInt(cursor.getString(11))));
                SQLiteTheme theme = new SQLiteTheme(context);
                event.setTheme(theme.getTheme(Integer.parseInt(cursor.getString(12))));

                events.add(event);
            } while (cursor.moveToNext());
        }
        return events;
    }

    public int updateEvent(Event event, int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, String.valueOf(event.getId()));
        values.put(KEY_NAME, event.getName());
        values.put(KEY_DESC, event.getDescription());
        values.put(KEY_V, String.valueOf(event.isVisibility()));
        values.put(KEY_PLACE, event.getPlace_name());
        values.put(KEY_ADR, event.getAddress());
        values.put(KEY_START_DATE, String.valueOf(event.getStartDate()));
        values.put(KEY_END_DATE, String.valueOf(event.getEndDate()));
        values.put(KEY_START_TIME, String.valueOf(event.getStartDate().getTime()));
        values.put(KEY_END_TIME, String.valueOf(event.getEndDate().getTime()));
        values.put(KEY_ORGANIZER_ID, event.getOrganizers().get(0).getId());
        values.put(KEY_COORD_ID, event.getCoord().getId());
        values.put(KEY_THEME_ID, event.getTheme().getId());

        int i = db.update(TABLE_EVENT, values, KEY_ID+" = ?",
                new String[] { String.valueOf(event.getId()) });
        db.close();
        return i;
    }

    public void deleteEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_EVENT, KEY_ID+" = ?",
                new String[] { String.valueOf(event.getId()) });

        db.close();
    }

    public int eventCount(){
        int number = 0;
        String query = "SELECT count(*) FROM " + TABLE_EVENT;

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
