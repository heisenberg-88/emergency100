package com.parth.android.emergencyprd.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.parth.android.emergencyprd.data.dbContract.dbentry;


public class dbProvider extends ContentProvider {

    public static final String LOG_TAG = dbProvider.class.getSimpleName();

    private dbHelper dbhelper;



    @Override
    public boolean onCreate() {
        dbhelper = new dbHelper(getContext());
        return true;
    }





    @Override
    public Cursor query( Uri uri,  String[] projection,String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        Cursor cursor;

        cursor = database.query(dbentry.TABLE_NAME, projection, selection, selectionArgs,
                null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }







    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String name =values.getAsString(dbentry.COLUMN_USER_NAME);
        if(name==null){
            throw new IllegalArgumentException("db requires a name");
        }

        String age=values.getAsString(dbentry.COLUMN_AGE);
        if(age==null || age=="0"){
            throw new IllegalArgumentException("db requires valid age");
        }

        String blood=values.getAsString(dbentry.COLUMN_BLOOD);
        if(blood==null){
            throw new IllegalArgumentException("db requires valid blood group");
        }

        String epn1 = values.getAsString(dbentry.COLUMN_EPN1);
        if(epn1==null ){
            throw new IllegalArgumentException("db requires valid phone number");
        }

        String epn2 = values.getAsString(dbentry.COLUMN_EPN2);
        if(epn2==null ){
            throw new IllegalArgumentException("db requires valid phone number");
        }


        SQLiteDatabase database = dbhelper.getWritableDatabase();
        long id = database.insert(dbentry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }


        getContext().getContentResolver().notifyChange(uri, null);
        Uri urii;

        urii= ContentUris.withAppendedId(uri, id);
        dbentry.currrentID=urii;
        return urii;
    }









    @Override
    public int delete(Uri uri, String selection,  String[] selectionArgs) {
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        int rowsDeleted = database.delete(dbentry.TABLE_NAME, selection, selectionArgs);
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;

    }









    @Override
    public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        selection = dbentry._ID + "=?";
        selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

        if(values.containsKey(dbentry.COLUMN_USER_NAME)){
            String name =values.getAsString(dbentry.COLUMN_USER_NAME);
            if(name==null){
                throw new IllegalArgumentException("db requires a name");
            }
        }
        if(values.containsKey(dbentry.COLUMN_AGE)){
            String age=values.getAsString(dbentry.COLUMN_AGE);
            if(age==null || age=="0"){
                throw new IllegalArgumentException("db requires valid age");
            }
        }
        if(values.containsKey(dbentry.COLUMN_BLOOD)){
            String blood=values.getAsString(dbentry.COLUMN_BLOOD);
            if(blood==null){
                throw new IllegalArgumentException("db requires valid blood group");
            }
        }
        if(values.containsKey(dbentry.COLUMN_EPN1)){
            String epn1 = values.getAsString(dbentry.COLUMN_EPN1);
            if(epn1==null ){
                throw new IllegalArgumentException("db requires valid phone number");
            }
        }
        if(values.containsKey(dbentry.COLUMN_EPN2)){
            String epn2 = values.getAsString(dbentry.COLUMN_EPN2);
            if(epn2==null ){
                throw new IllegalArgumentException("db requires valid phone number");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        int rowsUpdated = database.update(dbentry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;

    }






    @Override
    public String getType(Uri uri) {
        return null;
    }

}
