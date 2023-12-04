package com.example.admin_app;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.core.Tag;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("TAG", "Refreshed ztoken "+token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        if (message.getData().size()>0) {
            String title = message.getData().get("title");
            String message1 = message.getData().get("message");
            String desc = message.getData().get("description");

            Intent intent =new Intent(getApplicationContext(),notification.class);
            intent.putExtra("title",title);
            intent.putExtra("message", message1);
            intent.putExtra("description",desc);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }
    }

}
