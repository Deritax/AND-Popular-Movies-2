package com.itarusoft.movies.Database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    public MovieContract() {

    }

    public static final String CONTENT_AUTHORITY = "com.itarusoft.movies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public final static String TABLE_NAME = "movies";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_RELEASE = "release";

        public static final String COLUMN_POSTER = "poster";

        public static final String COLUMN_VOTE = "vote";

        public static final String COLUMN_SYNOPSIS = "synopsis";

    }

}
