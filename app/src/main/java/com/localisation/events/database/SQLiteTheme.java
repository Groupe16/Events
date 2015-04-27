package com.localisation.events.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.localisation.events.model.Theme;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zalila on 2015-04-26.
 */
public class SQLiteTheme extends SQLiteOpenHelper {

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "events_db";

    Context context;

    public SQLiteTheme(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_THEME_TABLE = "CREATE TABLE theme ( " +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, "+
                "group TEXT )";

        db.execSQL(CREATE_THEME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS theme");
        this.onCreate(db);
    }

    private static final String TABLE_THEME = "theme";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_GROUP = "group";

    private static final String[] COLUMNS = {KEY_ID,KEY_NAME,KEY_GROUP};

    public void addTheme(Theme theme, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, String.valueOf(theme.getId()));
        values.put(KEY_NAME, theme.getName());
        values.put(KEY_GROUP, theme.getGroup());

        db.insert(TABLE_THEME, null, values);

        db.close();
    }

    public Theme getTheme(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_THEME, COLUMNS, " id = ?", new String[] { String.valueOf(id) },
                        null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Theme theme = new Theme();
        theme.setId(Integer.parseInt(cursor.getString(0)));
        theme.setName(cursor.getString(1));
        theme.setGroup(cursor.getString(2));

        return theme;
    }

    public List<Theme> getAllThemes() {
        List<Theme> themes = new LinkedList<>();

        String query = "SELECT * FROM " + TABLE_THEME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Theme theme = null;
        if (cursor.moveToFirst()) {
            do {
                theme = new Theme();
                theme.setId(Integer.parseInt(cursor.getString(0)));
                theme.setName(cursor.getString(1));
                theme.setGroup(cursor.getString(2));

                themes.add(theme);
            } while (cursor.moveToNext());
        }
        return themes;
    }

    public int updateTheme(Theme theme, int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, theme.getName());
        values.put(KEY_GROUP, theme.getGroup());
        values.put(KEY_ID, id);

        int i = db.update(TABLE_THEME, values, KEY_ID+" = ?",
                new String[] { String.valueOf(theme.getId()) });
        db.close();
        return i;
    }

    public void deleteTheme(Theme theme) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_THEME, KEY_ID+" = ?",
                new String[] { String.valueOf(theme.getId()) });

        db.close();
    }

    public int themeCount(){
        int number = 0;
        String query = "SELECT count(*) FROM " + TABLE_THEME;

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
