package com.mobond.chat.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mobond.chat.Chatscreen;
import com.mobond.chat.Groupdata;
import com.mobond.chat.Grouplist;
import com.mobond.chat.R;

import java.util.ArrayList;


public class Grouplistadapter extends BaseAdapter{
    private static LayoutInflater inflater=null;
    private ArrayList<Groupdata> grouplist;
    private Activity context;
    public Grouplistadapter(Grouplist activity, ArrayList<Groupdata> groups) {
        grouplist=groups;
        context=activity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return grouplist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.grouplistitem, null);
        holder.tv=(TextView) rowView.findViewById(R.id.title);
        holder.tv.setText(grouplist.get(position).getGname());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent activityname =new Intent(context, Chatscreen.class);
                activityname.putExtra("gkey",grouplist.get(position).getGid());
                activityname.putExtra("gname",grouplist.get(position).getGname());
                context.startActivity(activityname);


                Toast.makeText(context, "You Clicked "+grouplist.get(position).getGname(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

    public class Holder
    {
        TextView tv;
    }
}