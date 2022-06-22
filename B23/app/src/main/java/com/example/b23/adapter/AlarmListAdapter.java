package com.example.b23.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b23.R;
import com.example.b23.entitys.Alarms;

import java.util.ArrayList;

public class AlarmListAdapter extends BaseAdapter {

    private Activity activity;
    ArrayList<Alarms> listAlarms;
    ListView listView;

    public AlarmListAdapter(Activity activity, ArrayList<Alarms> listAlarms, ListView listView)
    {
        this.activity = activity;
        this.listAlarms = listAlarms;
        this.listView = listView;
    }

    public static class ViewHolder {
        TextView viewid;
        TextView viewhour;
        TextView viewmin;
        Button delete;
    }

    @Override
    public int getCount() {
        if(listAlarms.size()<=0) return 0;
        else return listAlarms.size();
    }

    @Override
    public Object getItem(int position) {
        return listAlarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = activity.getLayoutInflater();

        ViewHolder vh;

        if(convertView == null)
        {
            vh = new ViewHolder();
            row = inflater.inflate(R.layout.row_item, null, true);
            vh.viewid = (TextView) row.findViewById(R.id.idtime);
            vh.viewhour = (TextView) row.findViewById(R.id.idtimehour);
            vh.viewmin = (TextView) row.findViewById(R.id.idtimemin);
            vh.delete = (Button) row.findViewById(R.id.iddelete);
            row.setTag(vh);
        }
        else  vh = (ViewHolder) convertView.getTag();

        vh.viewid.setText(""+position);
        vh.viewhour.setText(""+listAlarms.get(position).getHour());
        vh.viewmin.setText(""+listAlarms.get(position).getMin());

        vh.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitrilistview = Integer.parseInt(vh.viewid.getText().toString());
                Toast.makeText(activity,""+vitrilistview,Toast.LENGTH_SHORT).show();

                Alarms alarms = listAlarms.get(vitrilistview);

                //cancel alarm
                alarms.cancelAlarm(activity);

                //delete database
                alarms.delete();

                //delete list view
                listAlarms.remove(vitrilistview);
                AlarmListAdapter alarmListAdapter = new AlarmListAdapter(activity, listAlarms, listView);
                listView.setAdapter(alarmListAdapter);
                alarmListAdapter.notifyDataSetChanged();
            }
        });
        return row;
    }
}
