package com.itarusoft.movies.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.itarusoft.movies.database.MovieContract.MovieEntry;

public class MoviesDbHelper extends SQLiteOpenHelper{

    public static final String LOG_TAG = MoviesDbHelper.class.getSimpleName();

    private static final String DB_NAME = "movies.db";

    private static final int DB_VERSION = 1;

    public MoviesDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_MOVIES_TABLE =  "CREATE TABLE " +
                MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieEntry.COLUMN_TITLE + " TEXT NOT NULL DEFAULT '-'," +
                MovieEntry.COLUMN_RELEASE + " TEXT NOT NULL DEFAULT '-'," +
                MovieEntry.COLUMN_POSTER + " TEXT NOT NULL DEFAULT '-'," +
                MovieEntry.COLUMN_VOTE + " REAL NOT NULL DEFAULT 0," +
                MovieEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL DEFAULT '-'" + ");";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
