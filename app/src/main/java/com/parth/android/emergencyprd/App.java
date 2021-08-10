package com.parth.android.emergencyprd;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL_ID1 = "channel1";
    public static final String CHANNEL_ID2 = "channel2";
    public static final String CHANNEL_ID3 = "channel3";


    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }


    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID1,
                    "name_age_blood",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager1 =getSystemService(NotificationManager.class);
            manager1.createNotificationChannel(channel1);

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_ID2,
                    "emergencyPH",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager2 =getSystemService(NotificationManager.class);
            manager2.createNotificationChannel(channel2);

            NotificationChannel channel3 = new NotificationChannel(
                    CHANNEL_ID3,
                    "extras",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager3 =getSystemService(NotificationManager.class);
            manager3.createNotificationChannel(channel3);



        }
    }

}
