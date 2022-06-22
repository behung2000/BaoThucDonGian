package com.example.b23.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.example.b23.RingActivity;
import com.example.b23.service.AlarmService;

public class AlarmRecevier extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentalarm = new Intent(context, RingActivity.class);
        intentalarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentalarm);
    }
}
