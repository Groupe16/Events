package com.localisation.events.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.localisation.events.model.Theme;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Zalila on 2015-04-26.
 */
public class SQLiteThemeUser extends SQLiteOpenHelper {

    Context context;

    public SQLiteThemeUser(Context context) {
        super(context, SQLiteUser.DATABASE_NAME, null, SQLiteUser.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_THEME_USER_TABLE = "CREATE TABLE theme_user ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "theme_id INTEGER , " +
                "user_id INTEGER, "+
                "FOREIGN KEY(user_id) REFERENCES user(id) ON UPDATE CASCADE, "+
                "FOREIGN KEY(theme_id) REFERENCES theme(id) ON UPDATE CASCADE "+
                " )";

        db.execSQL(CREATE_THEME_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS theme_user");
        this.onCreate(db);
    }

    private static final String TABLE_THEME_USER = "theme_user";

    private static final String KEY_ID = "id";
    private static final String KEY_THEME = "theme_id";
    private static final String KEY_USER = "user_id";

    private static final String[] COLUMNS = {KEY_ID,KEY_THEME,KEY_USER};

    public void add(int user, int theme){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_THEME, theme);
        values.put(KEY_USER, user);

        db.insert(TABLE_THEME_USER, null, values);

        db.close();
    }


    public List<Theme> getAllThemes(int user) {
        List<Theme> themes = new LinkedList<>();

        String query = "SELECT * FROM " + TABLE_THEME_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_THEME_USER, COLUMNS, " user_id = ?", new String[]{String.valueOf(user)},
                        null, null, null, null);

        Theme theme = null;
        SQLiteTheme sqLiteTheme = new SQLiteTheme(context);
        if (cursor.moveToFirst()) {
            do {
                theme = sqLiteTheme.getTheme(Integer.parseInt(cursor.getString(1)));
                themes.add(theme);
            } while (cursor.moveToNext());
        }
        return themes;
    }


    public void deleteTheme(Theme theme) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_THEME_USER, KEY_ID+" = ?",
                new String[] { String.valueOf(theme.getId()) });

        db.close();
    }

    public int themeCount(){
        int number = 0;
        String query = "SELECT count(*) FROM " + TABLE_THEME_USER;

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
