package com.example.robo_ace.rosace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class RosCustomListAdapter extends BaseAdapter {

    private ArrayList<TaskItem> listData;

    private LayoutInflater layoutInflater;

    public RosCustomListAdapter(Context context, ArrayList<TaskItem> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.ros_list_row_layout, null);
            holder = new ViewHolder();
            holder.titleView = (TextView) convertView.findViewById(R.id.title_ros_list_layout);
            holder.idView = (TextView) convertView.findViewById(R.id.id_ros_list_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleView.setText(listData.get(position).getTask_name());
        holder.idView.setText( listData.get(position).getTask_id());


        return convertView;
    }

    static class ViewHolder {
        TextView titleView;
        TextView idView;

    }
}

