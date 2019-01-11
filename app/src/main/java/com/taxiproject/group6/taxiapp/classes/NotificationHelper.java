package com.taxiproject.group6.taxiapp.classes;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.taxiproject.group6.taxiapp.R;
import com.taxiproject.group6.taxiapp.activities.MainActivity;
import com.taxiproject.group6.taxiapp.activities.MapsActivity;

public class NotificationHelper extends ContextWrapper {

    //List of variables
    private static final String PRIMARY_CHANNEL_ID = "PRIMARY Channel";
    private static final String PRIMARY_CHANNEL_NAME = "PRIMARY";
    private static final String SECONDARY_CHANNEL_ID = "SECONDARY Channel";
    private static final String SECONDARY_CHANNEL_NAME = "SECONDARY";
    private NotificationManager manager;

    //Constructor
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    /*
     * creates channels for notifications
     * Checks if android version is Oreo or later
     * creates 2 different channels. both with High importance
     */
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannels() {
        NotificationChannel orderChannel =
                new NotificationChannel(PRIMARY_CHANNEL_ID, PRIMARY_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH); //Channel details
        orderChannel.enableLights(true);
        orderChannel.enableVibration(true);
        orderChannel.setLightColor(Color.GREEN);
        orderChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(orderChannel);

        NotificationChannel arrivedChannel =
                new NotificationChannel(SECONDARY_CHANNEL_ID, SECONDARY_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH); //Channel details
        arrivedChannel.enableLights(true);
        arrivedChannel.enableVibration(true);
        arrivedChannel.setLightColor(Color.RED);
        arrivedChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(arrivedChannel);
    }

    /**
     * Creates a manager if one havent created yet
     * @return notification manager
     */
    public NotificationManager getManager() {
        if(this.manager == null)
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return this.manager;
    }

    public NotificationCompat.Builder primaryNotification(String title, String message){

        return new NotificationCompat.Builder(getApplicationContext(), PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)                                                   //add icon
                .setContentTitle(title)                                                                    //add users title
                .setContentText(message)                                                                   //add users message
                .setStyle(new NotificationCompat.BigTextStyle()                                            //makes notification expandable
                        .bigText("BBBLLLLAAAAAAAAAAAAAAAAAAAAAAAA")                                                        //adds text when expanded
                        .setBigContentTitle("Expanded content")                                            //title of expanded notification
                        .setSummaryText("Summery"))                                                        //adds summery
                .setPriority(NotificationCompat.PRIORITY_HIGH)                                             //set priority to high by default
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)                                          //set category message
                .setColor(Color.GREEN)                                                                     //set colour
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder secondaryNotification(String title, String message){
        Intent intent = new Intent(NotificationHelper.this, MapsActivity.class);               //intent to change to new activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);//make intent pendingIntent

        return new NotificationCompat.Builder(getApplicationContext(), SECONDARY_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)                                                   //add icon
                .setContentTitle(title)                                                                    //add users title
                .setContentText(message)                                                                   //add users message
                .setStyle(new NotificationCompat.BigTextStyle()                                            //makes notification expandable
                        .bigText("BLAAA BLAAAAA BLAAAAAAAAAAAAAAAA")//adds text when expanded
                        .setBigContentTitle("Expanded content")                                            //title of expanded notification
                        .setSummaryText("Summery"))                                                        //adds summery
                .setPriority(NotificationCompat.PRIORITY_MAX)                                             //set priority to high by default
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)                                          //set category message
                .setContentIntent(pendingIntent)                                                           //calls when notification pressed
                .setColor(Color.RED)                                                                     //set colour
                .addAction(R.mipmap.ic_launcher, "OPEN", pendingIntent)                               //creates call button and calls when pressed
                .setAutoCancel(true);
    }

}
