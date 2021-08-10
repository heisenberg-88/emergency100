package com.parth.android.emergencyprd;

import android.Manifest;
import android.app.LoaderManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.parth.android.emergencyprd.data.dbContract.dbentry;

import static com.parth.android.emergencyprd.App.CHANNEL_ID1;
import static com.parth.android.emergencyprd.App.CHANNEL_ID2;
import static com.parth.android.emergencyprd.App.CHANNEL_ID3;



public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{
    private static final int DB_LOADER = 0;
    private TextView name;
    private TextView age;
    private TextView blood;
    private TextView epn1;
    private TextView epn2;
    private TextView extras;
    private Button locationbtn;


    private NotificationManagerCompat notificationmanager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        name = (TextView)findViewById(R.id.name_view) ;
        age=(TextView)findViewById(R.id.age_view);
        blood =(TextView)findViewById(R.id.blood_view);
        epn1 = (TextView)findViewById(R.id.epn1_view);
        epn2 = (TextView)findViewById(R.id.epn2_view);
        extras=(TextView)findViewById(R.id.extra_view);
        locationbtn=(Button)findViewById(R.id.locationbtn);

        getLoaderManager().initLoader(DB_LOADER, null, this);

        notificationmanager = NotificationManagerCompat.from(this);

        Toast toasty = Toast.makeText(this,"CONFIGURED SUCCESSFULLY",Toast.LENGTH_SHORT);

        locationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                        toasty.show();
                    }else{
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                    }
                }else{
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},45);
                }

            }
        });


    }




//    @SuppressLint("MissingPermission")
//    private void getLocation(String number) {
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete( Task<Location> task) {
//                Location location = task.getResult();
//                if(location!=null){
//                    try {
//                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
//                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//
//                        message = Html.fromHtml(addresses.get(0).getLatitude()+"")+ " ,"
//                        + Html.fromHtml(addresses.get(0).getLongitude()+"");
//
//                        SmsManager.getDefault().sendTextMessage(number, null, message, null,null);
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//            }
//        });
//    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add:
                if(name.getText().toString().trim()==""){
                    Intent intent = new Intent(MainActivity.this,Editor_activity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Use Update option to edit the data . Data is already there , please delete if you want to add",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_delete:
                getContentResolver().delete(dbentry.CONTENT_URI, null, null);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                return true;
            case R.id.action_update:
                Intent update = new Intent(MainActivity.this,Editor_activity.class);
                Uri updateuri = dbentry.currrentID;
                update.setData(updateuri);
                startActivity(update);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }











    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                dbentry._ID,
                dbentry.COLUMN_USER_NAME,
                dbentry.COLUMN_AGE ,
                dbentry.COLUMN_BLOOD,
                dbentry.COLUMN_EPN1,
                dbentry.COLUMN_EPN2,
                dbentry.COLUMN_EXTRAS};

        return new CursorLoader(this,   // Parent activity context
                dbentry.CONTENT_URI,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
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


            String named=cursor.getString(nameindex);
            String aged=cursor.getString(ageindex);
            String bloodd=cursor.getString(bloodindex);
            String epn1d=cursor.getString(epn1index);
            String epn2d=cursor.getString(epn2index);
            String extrasd=cursor.getString(extrasindex);


            name.setText(named);
            age.setText(aged);
            blood.setText(bloodd);
            epn1.setText(epn1d);
            epn2.setText(epn2d);
            extras.setText(extrasd);


            if(named.length()!=0){

                RemoteViews extras = new  RemoteViews(getPackageName(),
                        R.layout.notification_extend);
                extras.setTextViewText(R.id.extras_notif,extrasd);
                Notification extrasnotif = new NotificationCompat.Builder(this,CHANNEL_ID3)
                        .setSmallIcon(R.drawable.ic_add_pet)
                        .setContentTitle("Expand for more Info")
                        .setCustomBigContentView(extras)
                        .setOngoing(true)
                        .build();
                notificationmanager.notify(3,extrasnotif);





                RemoteViews phone = new  RemoteViews(getPackageName(),
                        R.layout.notification_phone);
                phone.setTextViewText(R.id.epn1_notif,epn1d);
                phone.setTextViewText(R.id.epn2_notif,epn2d);
                Intent broadcast = new Intent(this,NotificationReciever.class);
                broadcast.putExtra("numberstring",epn1d);
                PendingIntent pendin = PendingIntent.getBroadcast(this,0,broadcast,PendingIntent.FLAG_UPDATE_CURRENT);

                Intent broadcast2 = new Intent(this,NotificationReciever.class);
                broadcast2.putExtra("numberstring",epn2d);
                PendingIntent pendin2 = PendingIntent.getBroadcast(this,1,broadcast2,PendingIntent.FLAG_UPDATE_CURRENT);
                Notification phonenotif = new NotificationCompat.Builder(this,CHANNEL_ID2)
                        .setSmallIcon(R.drawable.ic_add_pet)
                        .setCustomContentView(phone)
                        .setOngoing(true)
                        .addAction(R.mipmap.ic_launcher,"send1",pendin)
                        .addAction(R.mipmap.ic_launcher,"send2",pendin2)
                        .build();
                notificationmanager.notify(2,phonenotif);





                RemoteViews name = new  RemoteViews(getPackageName(),
                        R.layout.notification_collapsed);
                name.setTextViewText(R.id.name_notif,named);
                name.setTextViewText(R.id.age_notif,aged);
                name.setTextViewText(R.id.blood_notif,bloodd);
                Notification namenotif = new NotificationCompat.Builder(this,CHANNEL_ID1)
                        .setSmallIcon(R.drawable.ic_add_pet)
                        .setCustomContentView(name)
                        .setOngoing(true)
                        .build();
                notificationmanager.notify(1,namenotif);




            }
            

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}