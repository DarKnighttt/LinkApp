package com.projects.darknight.linkapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LinkContentProvider extends ContentProvider {


    private static final int LINK = 100;
    private static final int LINK_ID = 101;

    public static final String AUTHORITY_NAME = "com.projects.darknight.linkapp.databasecom.projects.darknight.linkapp.database";

    public static final Uri LINK_URI = Uri.parse("content://" + AUTHORITY_NAME + "/" + LinksDbCotract.LinkTable.TABLE_NAME);
    public static final Uri LINK_URI_ID = Uri.parse("content://" + AUTHORITY_NAME + "/" + LinksDbCotract.LinkTable.TABLE_NAME + "/" + LINK_ID);

    private LinksDbHelper linksHelper;

    public static final UriMatcher linkUriMatcher = buildUriMathcer();

    private static UriMatcher buildUriMathcer() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AUTHORITY_NAME, LinksDbCotract.LinkTable.TABLE_NAME, LINK);
        matcher.addURI(AUTHORITY_NAME, LinksDbCotract.LinkTable.TABLE_NAME + "/*", LINK_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();

        linksHelper = new LinksDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;

        final SQLiteDatabase db = linksHelper.getReadableDatabase();
        switch (linkUriMatcher.match(uri)) {
            case LINK:
                cursor = db.query(LinksDbCotract.LinkTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri turi = null;
        if (linksHelper == null)
            linksHelper = new LinksDbHelper(getContext());
        final SQLiteDatabase db = linksHelper.getWritableDatabase();
        switch (linkUriMatcher.match(uri)) {
            case LINK:
                long _ID1 = db.insert(LinksDbCotract.LinkTable.TABLE_NAME, null, values);

                if (_ID1 > 0) {
                    turi = ContentUris.withAppendedId(LINK_URI,_ID1);
                    getContext().getContentResolver().notifyChange(turi,null);
                }
                break;
            default: throw new SQLException("Failed to insert row into " + uri);
        }
        return turi;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        final SQLiteDatabase db = linksHelper.getWritableDatabase();
        switch (linkUriMatcher.match(uri)) {
            case LINK:
                count = db.delete(LinksDbCotract.LinkTable.TABLE_NAME, selection, selectionArgs);
                break;
            case LINK_ID:
                count = db.delete(LinksDbCotract.LinkTable.TABLE_NAME, selection, selectionArgs);
                break;
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        final SQLiteDatabase db = linksHelper.getWritableDatabase();
        switch (linkUriMatcher.match(uri)) {
            case LINK:
                count = db.update(LinksDbCotract.LinkTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case LINK_ID:
                count = db.update(LinksDbCotract.LinkTable.TABLE_NAME, values, selection, selectionArgs);
                break;
        }
        return count;
    }
}