package com.parth.android.emergencyprd;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.telephony.SmsManager;
import android.text.Html;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class NotificationReciever extends BroadcastReceiver {
    FusedLocationProviderClient fusedLocationProviderClient;


    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(context);
        String number = intent.getStringExtra("numberstring");
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete( Task<Location> task) {
                Location location = task.getResult();
                if(location!=null){
                    try {
                        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                        String message = Html.fromHtml(addresses.get(0).getLatitude()+"")+ " ,"
                                + Html.fromHtml(addresses.get(0).getLongitude()+"");

                        SmsManager.getDefault().sendTextMessage(number, null, message, null,null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

    }




}
