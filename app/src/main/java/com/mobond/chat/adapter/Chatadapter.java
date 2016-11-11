package com.mobond.chat.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mobond.chat.Chatscreen;
import com.mobond.chat.Profile;
import com.mobond.chat.R;

import java.util.ArrayList;


public class Chatadapter extends BaseAdapter{
    private static LayoutInflater inflater=null;
    private ArrayList<Profile> message_arraylist;
    private Activity context;
    SharedPreferences userdetails;
    String uid;
    public Chatadapter(Chatscreen activity, ArrayList<Profile> groups) {
        message_arraylist =groups;
        context=activity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

         userdetails = activity.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        uid=userdetails.getString("uid","0");
    }

    public void refresh(ArrayList<Profile> groups){
        this.message_arraylist=groups;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return message_arraylist.size();
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

        Log.e("dblog","pos -- "+position);
        if(uid.equalsIgnoreCase(message_arraylist.get(position).getSid())) {
            rowView = inflater.inflate(R.layout.leftbubble, null);
        }else{
            rowView = inflater.inflate(R.layout.rightbubble, null);
        }
        holder.tv=(TextView) rowView.findViewById(R.id.title);
        holder.email=(TextView) rowView.findViewById(R.id.email);

        holder.tv.setText(message_arraylist.get(position).getMessage());
        holder.email.setText(message_arraylist.get(position).getTime());



        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked "+ message_arraylist.get(position).getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }

    public class Holder
    {
        TextView tv;
        TextView email;
    }
}