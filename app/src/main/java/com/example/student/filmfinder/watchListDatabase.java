package com.example.student.filmfinder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;

import java.util.ArrayList;

public class watchListDatabase extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "watchList.db";
    public static final String TABLE_WATCHLIST = "watchList";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FILMNAME = "filmName";

    public watchListDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE " + TABLE_WATCHLIST + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FILMNAME + " TEXT " + ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int _old, int _new)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WATCHLIST);
        onCreate(db);
    }

    // add new row to the database
    public void addFilm(String title)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FILMNAME, title);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_WATCHLIST, null, values);
        db.close();
    }

    // printing the database as a string
    public String databaseToString()
    {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_WATCHLIST + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast())
        {
            if (c.getString(c.getColumnIndex("filmName")) != null)
            {
                dbString += c.getString(c.getColumnIndex("filmName"));
                dbString += "\n";
            }
            c.moveToNext();
        }

        db.close();
        return dbString;
    }

    // printing the database as an arraylist
    public ArrayList<String> databasetoArrayList()
    {
        ArrayList<String> databaseArrayList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_WATCHLIST + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast())
        {
            if (c.getString(c.getColumnIndex("filmName")) != null)
            {
                databaseArrayList.add(c.getString(c.getColumnIndex("filmName")));
            }
            c.moveToNext();
        }

        db.close();
        return databaseArrayList;
    }
}
