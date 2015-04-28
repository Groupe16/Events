package com.localisation.events.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.localisation.events.model.Coord;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zalila on 2015-04-26.
 */
public class SQLiteCoord extends SQLiteOpenHelper {

    Context context;

    public SQLiteCoord(Context context) {
        super(context, SQLiteUser.DATABASE_NAME, null, SQLiteUser.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_COORD_TABLE = "CREATE TABLE coord ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "longitude TEXT, "+
                "latitude TEXT, "+
                "altitude TEXT )";

        db.execSQL(CREATE_COORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS coord");
        this.onCreate(db);
    }

    private static final String TABLE_COORD = "coord";

    private static final String KEY_ID = "id";
    private static final String KEY_LONG = "longitude";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_ALT = "altitude";

    private static final String[] COLUMNS = {KEY_ID,KEY_LONG,KEY_LAT,KEY_ALT};

    public void addCoord(Coord coord, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, coord.getId());
        values.put(KEY_LONG, coord.getLongitude());
        values.put(KEY_LAT, coord.getLatitude());
        values.put(KEY_ALT, coord.getAltitude());

        db.insert(TABLE_COORD, null, values);

        db.close();
    }

    public Coord getCoord(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_COORD, COLUMNS, " id = ?", new String[] { String.valueOf(id) },
                        null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Coord coord = new Coord();
        coord.setId(Integer.parseInt(cursor.getString(0)));
        coord.setLongitude(Double.parseDouble(cursor.getString(1)));
        coord.setLatitude(Double.parseDouble(cursor.getString(2)));
        coord.setAltitude(Double.parseDouble(cursor.getString(3)));

        return coord;
    }

    public List<Coord> getAllCoords() {
        List<Coord> coords = new LinkedList<>();

        String query = "SELECT * FROM " + TABLE_COORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Coord coord = null;
        if (cursor.moveToFirst()) {
            do {
                coord = new Coord();
                coord.setId(Integer.parseInt(cursor.getString(0)));
                coord.setLongitude(Double.parseDouble(cursor.getString(1)));
                coord.setLatitude(Double.parseDouble(cursor.getString(2)));
                coord.setAltitude(Double.parseDouble(cursor.getString(3)));

                coords.add(coord);
            } while (cursor.moveToNext());
        }
        return coords;
    }

    public int updateCoord(Coord coord, int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LONG, coord.getLongitude());
        values.put(KEY_LAT, coord.getLatitude());
        values.put(KEY_ALT, coord.getAltitude());
        values.put(KEY_ID, coord.getId());

        int i = db.update(TABLE_COORD, values, KEY_ID+" = ?",
                new String[] { String.valueOf(id) });
        db.close();
        return i;
    }

    public void deleteCoord(Coord coord) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_COORD, KEY_ID+" = ?",
                new String[] { String.valueOf(coord.getId()) });

        db.close();
    }

    public int coordCount(){
        int number = 0;
        String query = "SELECT count(*) FROM " + TABLE_COORD;

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
