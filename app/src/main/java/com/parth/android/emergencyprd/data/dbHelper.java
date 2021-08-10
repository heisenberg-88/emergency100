package com.parth.android.emergencyprd.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.parth.android.emergencyprd.data.dbContract.dbentry;

public class dbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "emergency.db";

    private static final int DATABASE_VERSION = 1;

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_TABLE = "CREATE TABLE " + dbentry.TABLE_NAME+" ("
                + dbentry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +dbentry.COLUMN_USER_NAME + " TEXT NOT NULL, "
                +dbentry.COLUMN_AGE + " TEXT NOT NULL, "
                +dbentry.COLUMN_BLOOD + " TEXT NOT NULL, "
                +dbentry.COLUMN_EPN1 +" TEXT NOT NULL, "
                +dbentry.COLUMN_EPN2 +" TEXT NOT NULL, "
                +dbentry.COLUMN_EXTRAS + " TEXT);";


        db.execSQL(SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // change if there is change in database version
    }
}
