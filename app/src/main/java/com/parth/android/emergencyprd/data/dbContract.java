package com.parth.android.emergencyprd.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class dbContract {
    private dbContract(){};



    public static final String CONTENT_AUTHORITY = "com.parth.android.emergencyprd";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_DB = "datab";

    public static final class dbentry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DB);

        public static Uri currrentID;


        public final static String TABLE_NAME = "datab";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_USER_NAME ="name";
        public final static String COLUMN_AGE = "age";
        public final static String COLUMN_BLOOD = "blood";
        public final static String COLUMN_EPN1 = "epn1";
        public final static String COLUMN_EPN2 = "epn2";
        public final static String COLUMN_EXTRAS = "extras";


    }

}
