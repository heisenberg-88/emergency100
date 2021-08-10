package com.parth.android.emergencyprd;


import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parth.android.emergencyprd.data.dbContract.dbentry;

//implements LoaderManager.LoaderCallbacks<Cursor>
public class Editor_activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_DB_LOADER=0;
    private Uri currenturi ;
    private EditText nameEdit;
    private EditText ageEdit;
    private EditText bloodEdit;
    private EditText epn1Edit;
    private EditText epn2Edit;
    private EditText extrasEdit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        currenturi = intent.getData();

        nameEdit=(EditText)findViewById(R.id.edit_name);
        ageEdit=(EditText)findViewById(R.id.edit_age);
        bloodEdit=(EditText)findViewById(R.id.edit_blood);
        epn1Edit=(EditText)findViewById(R.id.edit_epn1);
        epn2Edit=(EditText)findViewById(R.id.edit_epn2);
        extrasEdit=(EditText)findViewById(R.id.edit_extra);

        if(currenturi==null){
            setTitle("Add Your Info");
        }else{
            setTitle("Edit Info");
            getLoaderManager().initLoader(EXISTING_DB_LOADER,null,this);
        }


    }





    private void savedata(){
        /// get al string values
        String name = nameEdit.getText().toString().trim();
        String age =ageEdit.getText().toString().trim();
        String blood =bloodEdit.getText().toString().trim();
        String epn1=epn1Edit.getText().toString().trim();
        String epn2=epn2Edit.getText().toString().trim();
        String extras=extrasEdit.getText().toString().trim();

        if(currenturi==null && TextUtils.isEmpty(name) && TextUtils.isEmpty(age) && TextUtils.isEmpty(blood) && TextUtils.isEmpty(epn1) &&
                TextUtils.isEmpty(epn2) ){
            return;
        }

        ContentValues values = new ContentValues();
        values.put(dbentry.COLUMN_USER_NAME,name);
        values.put(dbentry.COLUMN_AGE,age);
        values.put(dbentry.COLUMN_BLOOD,blood);
        values.put(dbentry.COLUMN_EPN1,epn1);
        values.put(dbentry.COLUMN_EPN2,epn2);
        values.put(dbentry.COLUMN_EXTRAS,extras);

        if(currenturi==null){
            // This is a NEW database, so insert new data into the provider,
            // returning the content URI for the new data.
            Uri newUri = getContentResolver().insert(dbentry.CONTENT_URI, values);

            if(newUri==null){
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, "ERROR inserting the data",
                        Toast.LENGTH_SHORT).show();
            }else{
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, "Insertion successfull",
                        Toast.LENGTH_SHORT).show();
            }

        }else{
            // Otherwise this is an EXISTING data, so update the data with content URI: currenturi
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because currenturi will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(currenturi, values, null, null);

            if(rowsAffected==0){
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, "ERROR while updating the data",
                        Toast.LENGTH_SHORT).show();
            }else{
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, "Updated the data successfully",
                        Toast.LENGTH_SHORT).show();
            }

        }

    }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                savedata();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }












    @Override
    public Loader<Cursor> onCreateLoader(int i,  Bundle bundle) {
        // Since the editor shows all pet attributes, define a projection that contains
        // all columns from the pet table
        String[] projection = {
                dbentry._ID,
                dbentry.COLUMN_USER_NAME,
                dbentry.COLUMN_AGE ,
                dbentry.COLUMN_BLOOD,
                dbentry.COLUMN_EPN1,
                dbentry.COLUMN_EPN2,
                dbentry.COLUMN_EXTRAS};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                currenturi,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if(cursor.moveToFirst()){
            int nameindex=cursor.getColumnIndex(dbentry.COLUMN_USER_NAME);
            int ageindex=cursor.getColumnIndex(dbentry.COLUMN_AGE);
            int bloodindex=cursor.getColumnIndex(dbentry.COLUMN_BLOOD);
            int epn1index=cursor.getColumnIndex(dbentry.COLUMN_EPN1);
            int epn2index=cursor.getColumnIndex(dbentry.COLUMN_EPN2);
            int extrasindex=cursor.getColumnIndex(dbentry.COLUMN_EXTRAS);


            String name=cursor.getString(nameindex);
            String age=cursor.getString(ageindex);
            String blood=cursor.getString(bloodindex);
            String epn1=cursor.getString(epn1index);
            String epn2=cursor.getString(epn2index);
            String extras=cursor.getString(extrasindex);

            nameEdit.setText(name);
            ageEdit.setText(age);
            bloodEdit.setText(blood);
            epn1Edit.setText(epn1);
            epn2Edit.setText(epn2);
            extrasEdit.setText(extras);


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        nameEdit.setText("");
        ageEdit.setText("");
        bloodEdit.setText("");
        epn1Edit.setText("");
        epn2Edit.setText("");
        extrasEdit.setText("");
    }
}
