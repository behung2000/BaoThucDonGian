package com.example.b23;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.b23.adapter.AlarmListAdapter;
import com.example.b23.entitys.Alarms;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView timeAlarm;
    Button addAlarm;
    Button deleteAll;

    TimePickerDialog timePickerDialog;
    Calendar calendar;

    int hour=-1;
    int min=-1;

    ListView listViewAlarms;
    ArrayList<Alarms> listAlarms;
    Alarms alarms;
    AlarmListAdapter alarmListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeAlarm = (TextView) findViewById(R.id.idTextViewTimeAlarm);
        addAlarm = (Button) findViewById(R.id.idButtonAddAlarm);
        deleteAll = (Button) findViewById(R.id.idButtonDeleteAll);

        setListAlarms();

        timeAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int hourcalendar = calendar.get(Calendar.HOUR_OF_DAY);
                int mincalendar = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.e("Time Picker Dialog","Choose a time to Alarm");

                        hour = hourOfDay;
                        min = minute;

                        String shour = String.valueOf(hour);
                        String smin = String.valueOf(min);

                        if(hour<10) shour = "0" + shour;
                        if(min<10) smin = "0" + smin;

                        timeAlarm.setText(shour+" : "+smin);
                    }
                },hourcalendar,mincalendar,true);
                timePickerDialog.show();

            }
        });

        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stimeAlarm = timeAlarm.getText().toString();
                if(stimeAlarm.equals("-- : --")==false && hour!=-1 && min!=-1 && checkAddAlarm()==true)
                {
                    Log.e("if add Alarm","success");
                    insertRecords();
                    setListAlarms();
                }
                else
                {
                    Log.e("if add Alarm","Add Fail or alarm is available");
                    Toast toast = Toast.makeText(MainActivity.this,"Add Fail or alarm is available", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i< listAlarms.size(); i++)
                {
                    listAlarms.get(i).cancelAlarm(MainActivity.this);
                    listAlarms.get(i).delete();
                }
                listAlarms = new ArrayList<Alarms>();
                setListAlarms();
            }
        });
    }

    public void insertRecords(){
        Alarms alarms = new Alarms(MainActivity.this,hour,min);
        alarms.save();
        Log.e("insert Records","success");

        timeAlarm.setText("-- : --");
        Toast toast = Toast.makeText(MainActivity.this,"add alarm success!!!",Toast.LENGTH_SHORT);
        toast.show();
    }

    public void setListAlarms() {
        listViewAlarms = (ListView) findViewById(R.id.idlisttime);
        List<Alarms> list = Alarms.listAll(Alarms.class);
        alarms = new Alarms();
        listAlarms = new ArrayList<Alarms>();

        for(int i=0; i<list.size(); i++)
        {
            alarms = list.get(i);
            alarms.schedule(MainActivity.this);
            listAlarms.add(alarms);
        }
        alarmListAdapter = new AlarmListAdapter(MainActivity.this, listAlarms, listViewAlarms);
        listViewAlarms.setAdapter(alarmListAdapter);
    }

    public boolean checkAddAlarm()
    {
        int id = hour*100+min;
        if(listAlarms.size()>0)
        {
            for(int i=0; i<listAlarms.size(); i++)
            {
                if(id == listAlarms.get(i).getIdAlarm()) return false;
            }
        }
        return true;
    }

}