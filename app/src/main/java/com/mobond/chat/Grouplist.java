package com.mobond.chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobond.chat.adapter.Grouplistadapter;

import java.util.ArrayList;

public class Grouplist extends AppCompatActivity {

    private DatabaseReference mDatabase;
    Button sign_out_button;
    EditText name,message;
    ArrayList<Profile> userdata;
    ArrayList<Groupdata> groups;
    SharedPreferences userdetails;
    ListView grouplist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouplist);
        Toast.makeText(this, "clikeddd", Toast.LENGTH_SHORT).show();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        grouplist = (ListView) findViewById(R.id.grouplist);
        userdetails = getSharedPreferences("userdetails", Context.MODE_PRIVATE);

        groups = new ArrayList<Groupdata>();

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

        Log.d("grp","calling.....");
        grouplist.setAdapter(new Grouplistadapter(Grouplist.this,groups));
        Log.d("grp","calledddd.....");

    }

}
