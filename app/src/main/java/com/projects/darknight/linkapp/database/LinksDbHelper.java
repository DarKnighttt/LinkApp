package com.projects.darknight.linkapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class LinksDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "links.db";

    private static final String CREATE_LINKS =
            "CREATE TABLE " + LinksDbCotract.LinkTable.TABLE_NAME + "(" +
                    LinksDbCotract.LinkTable._ID + " INTEGER PRIMARY KEY," +
                    LinksDbCotract.LinkTable.COLUMN_LINK + " TEXT," +
                    LinksDbCotract.LinkTable.COLUMN_STATUS + " INTEGER," +
                    LinksDbCotract.LinkTable.COLUMN_OPEN_TIME + " REAL)";

    private static final String DELETE_LINKS =
            "DROP TABLE IF EXISTS " + LinksDbCotract.LinkTable.TABLE_NAME;

    public LinksDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LINKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_LINKS);
        onCreate(db);
    }
}
