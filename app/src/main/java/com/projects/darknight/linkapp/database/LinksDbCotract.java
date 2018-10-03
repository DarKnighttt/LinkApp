package com.projects.darknight.linkapp.database;

import android.provider.BaseColumns;

public class LinksDbCotract {

    private LinksDbCotract(){}

    public static class LinkTable implements BaseColumns{
        public static final String TABLE_NAME = "links";
        public static final String COLUMN_LINK = "link";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_OPEN_TIME = "open_time";
    }
}
