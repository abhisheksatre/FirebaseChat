package com.mobond.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class Adddata extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Button sign_out_button;
    EditText name,message;
    ArrayList<Profile> userdata;
    ArrayList<Groupdata> groups;
    SharedPreferences userdetails;
    String dummytext;
    //changes
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);
        Toast.makeText(this, "clikeddd", Toast.LENGTH_SHORT).show();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sign_out_button = (Button) findViewById(R.id.adddata);
        name = (EditText) findViewById(R.id.name);
        message = (EditText) findViewById(R.id.message);

        userdetails = getSharedPreferences("userdetails", Context.MODE_PRIVATE);


        userdata = new ArrayList<Profile>();
        groups = new ArrayList<Groupdata>();
        sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("dblog", "click");
                submitpost();
            }
        });

        Log.d("dblog",userdetails.getString("email", "nono"));


    }


    public void submitpost(){
        Log.d("dblog", "called");
        final String title = name.getText().toString();
        final String body = message.getText().toString();
        final String status = "live";
        final String userId = "4wxD1ocB9jMoYG96K75IGRvy1t72";
        Date d = new Date();

        Log.d("dblog",d.getDate()+" "+d.getDate());

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Log.d("dblog", "datachange");
                        mDatabase.child("users").child(userId).child("message").setValue(body);
                        mDatabase.child("users").child(userId).child("sid").setValue(userId);
                        mDatabase.child("users").child(userId).child("gid").setValue("central");
                        mDatabase.child("users").child(userId).child("time").setValue("9119");
                        mDatabase.child("users").child(userId).child("status").setValue(status);

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //Profile conversation = ds.getValue(Profile.class);
                            Log.d("dblog","Fetch...  "+ds.getValue());
                        }
                        loadConversationList();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("dblog", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }



    private void loadConversationList() {

        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Profile conversation = ds.getValue(Profile.class);
                        userdata.add(conversation);
                        Log.d("dblog",""+userdata.size());
                        Log.d("dblogconversation",""+ds);

                    }
                }
                printit();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void printit(){
        for(int i=0; i<userdata.size(); i++){
            Log.d("parr","getMessage "+userdata.get(i).getMessage());
            Log.d("parr","getSid "+userdata.get(i).getSid());
            Log.d("parr","getGid "+userdata.get(i).getGid());
            Log.d("parr","getTime "+userdata.get(i).getTime());
            Log.d("parr","getStatus "+userdata.get(i).getStatus());
            Log.d("parr","_________");
        }

        loadGroups();
    }



    private void loadGroups() {
        Log.d("grp","loadGroups callled");
        FirebaseDatabase.getInstance().getReference("groups").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Groupdata g=new Groupdata();
                        g.setGname(ds.child("name").getValue().toString());
                        g.setGid(ds.getKey());
                        g.setTime(ds.child("ctime").getValue().toString());
                        groups.add(g);
                    }
                }
                Log.d("grp","name isss "+groups.size());
                printGroup();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("grp","Groupdata error "+databaseError.getMessage());
            }
        });

    }

    public void printGroup(){
        for(int i=0; i<groups.size(); i++){
            Log.d("grp","gid "+groups.get(i).getGid());
            Log.d("grp","name "+groups.get(i).getGname());
            Log.d("grp","getTime "+groups.get(i).getTime());
            Log.d("grp","_________");
        }
    }

}
