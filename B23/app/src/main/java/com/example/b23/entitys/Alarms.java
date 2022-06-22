package com.example.b23.entitys;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.b23.broadcastReceiver.AlarmRecevier;
import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Unique;

import java.util.Calendar;

public class Alarms extends SugarRecord {

    @Unique
    @Column(name = "idalarm")
    private int IdAlarm;
    @Column(name = "hour")
    private int hour;
    @Column(name = "min")
    private int min;

    public Alarms(){

    }

    public Alarms(Context context,int hour, int min)
    {
        this.IdAlarm = hour*100+min;
        this.hour = hour;
        this.min = min;
        schedule(context);
    }

    public int getIdAlarm() {
        return IdAlarm;
    }

    public void setIdAlarm(int idAlarm) {
        IdAlarm = idAlarm;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void schedule(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmRecevier.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, getIdAlarm(), intent,0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // if alarm time has already passed, increment day by 1
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        final long RUN_DAILY = 24 * 60 * 60 * 1000;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), RUN_DAILY, pendingIntent);

        Log.e("schedule Alarm","success");
    }

    public void cancelAlarm(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmRecevier.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, getIdAlarm(), intent,0);

        alarmManager.cancel(pendingIntent);

        Log.e("cancel Alarm","success");
    }
}
