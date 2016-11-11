package com.mobond.chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobond.chat.adapter.Chatadapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Chatscreen extends AppCompatActivity {

    private DatabaseReference mDatabase;
    ArrayList<Profile> userdata;
    ArrayList<Groupdata> groups;
    SharedPreferences userdetails;
    ListView grouplist;
    ImageView sendbtn;
    EditText message;
    Chatadapter ca;
    String groupid, groupname;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatscreen);
        Toast.makeText(this, "clikeddd", Toast.LENGTH_SHORT).show();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        grouplist = (ListView) findViewById(R.id.chatlist);
        sendbtn = (ImageView) findViewById(R.id.send);
        message = (EditText) findViewById(R.id.message);

        userdetails = getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        groupid = intent.getStringExtra("gkey");
        groupname = intent.getStringExtra("gname");


        userdata = new ArrayList<Profile>();
        loadConversationList();

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Chatscreen.this, "Msg click", Toast.LENGTH_SHORT).show();
                sendmessage();
            }
        });
        ca = new Chatadapter(Chatscreen.this,userdata);
        grouplist.setAdapter(ca);
        Timer timer = new Timer ();
        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run () {
                Log.e("dblog","time call");
                loadConversationList();
            }
        };
        timer.schedule (hourlyTask, 0, 1000);   // 1000*10*60 every 10 minut

    }

    public void sendmessage(){
        Log.d("dblog", "called");
        final String msg = message.getText().toString();
        message.setText("");
        final String status = "live";
        final String userId = userdetails.getString("uid","0");
        Date d = new Date();
        final String currenttime=d.getHours()+":"+d.getMinutes();
        final String dbid=userId+"-"+System.currentTimeMillis();
        Log.d("dblog",d.getDate()+"-"+d.getMonth()+"-"+d.getYear()+" "+d.getHours()+":"+d.getMinutes());

//        mDatabase.child("messages").child(dbid).child("message").setValue(msg);
//        mDatabase.child("messages").child(dbid).child("sid").setValue(userId);
//        mDatabase.child("messages").child(dbid).child("gid").setValue("central");
//        mDatabase.child("messages").child(dbid).child("time").setValue(currenttime);
//        mDatabase.child("messages").child(dbid).child("status").setValue(status);

        DatabaseReference usersRef = mDatabase.child(groupid);

        usersRef.push().setValue(new Profile(msg,userId,groupid,currenttime,status));
        userdata.add(new Profile(msg,userId,groupid,currenttime,status));
        ca.notifyDataSetChanged();
        grouplist.setSelection(userdata.size() - 1);
//        mDatabase.child("messages").child(userId).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get user value
//                        Log.d("dblog", "datachange");
//                        mDatabase.child("messages").child(dbid).child("message").setValue(msg);
//                        mDatabase.child("messages").child(dbid).child("sid").setValue(userId);
//                        mDatabase.child("messages").child(dbid).child("gid").setValue("central");
//                        mDatabase.child("messages").child(dbid).child("time").setValue(currenttime);
//                        mDatabase.child("messages").child(dbid).child("status").setValue(status);
//
//                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                            //Profile conversation = ds.getValue(Profile.class);
//                            Log.d("dblog","Fetch...  "+ds.getValue());
//                        }
//                        loadConversationList();
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.d("dblog", "getUser:onCancelled", databaseError.toException());
//                    }
//                });
    }

    private void loadConversationList() {

        FirebaseDatabase.getInstance().getReference(groupid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null) {
                    userdata.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Profile conversation = ds.getValue(Profile.class);

                        Log.d("dblog",""+userdata.size());
                        Log.d("dblogconversation",""+ds);
                        Log.d("dblog",ds.child("message").getValue().toString());

                        Profile g=new Profile();
                        if(ds.child("message").getValue().toString()!=null && ds.child("time").getValue().toString()!=null) {
                            String message = ds.child("message").getValue().toString();
                            String time = ds.child("time").getValue().toString();
                            String sid = ds.child("sid").getValue().toString();

                            if (message != null) {
                                g.setMessage(message);
                                g.setTime(time);
                                g.setSid(sid);
                            }

                            Log.e("rmsg",ds.child("message").getValue().toString());

                            userdata.add(g);

                        }
                        /*g.setSid("sid");
                        g.setGid(ds.child("gid").getValue().toString());
                        g.setTime(ds.child("time").getValue().toString());
                        g.setStatus(ds.child("status").getValue().toString());*/


                    }
                    ca.notifyDataSetChanged();
                    grouplist.setSelection(userdata.size() - 1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
