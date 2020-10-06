package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class viewbooking extends AppCompatActivity {
    TextView type,typebox,number,numberbox,date,datebox,fromtime,timefrom,totime,timeto,slot,slotbox;
    EditText id;
    Button search,ok;
    String vehicaltype,vehicalnumber,startTime,endTime,bdate;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbooking);

        type=(TextView)findViewById(R.id.type);
        typebox=(TextView)findViewById(R.id.typebox);
        number=(TextView)findViewById(R.id.number);
        numberbox=(TextView)findViewById(R.id.numberbox);
        date=(TextView)findViewById(R.id.date);
        datebox=(TextView)findViewById(R.id.datebox);
        totime=(TextView)findViewById(R.id.totime);
        timeto=(TextView)findViewById(R.id.timeto);
        fromtime=(TextView)findViewById(R.id.fromtime);
        timefrom=(TextView)findViewById(R.id.timefrom);
        slot=(TextView)findViewById(R.id.slot);
        slotbox=(TextView)findViewById(R.id.slotbox);
        search=(Button)findViewById(R.id.searchbtn);
        id=(EditText)findViewById(R.id.bookingidbox);
        ok=(Button)findViewById(R.id.okbtn);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value = id.getText().toString();
                if (value.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "ID cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    int bid = Integer.parseInt(id.getText().toString());
                    ref = FirebaseDatabase.getInstance().getReference().child("Book").child(String.valueOf(bid));

                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                vehicaltype = dataSnapshot.child("vehicaltype").getValue().toString();
                                vehicalnumber = dataSnapshot.child("vehicalnumber").getValue().toString();
                                bdate = dataSnapshot.child("date").getValue().toString();
                                startTime = dataSnapshot.child("startTime").getValue().toString();
                                endTime = dataSnapshot.child("endTime").getValue().toString();
                                slotbox.setText(dataSnapshot.child("slot").getValue().toString());
                                ok.setVisibility(View.VISIBLE);
                                typebox.setText(vehicaltype);
                                numberbox.setText(vehicalnumber);
                                datebox.setText(bdate);
                                fromtime.setText(startTime);
                                totime.setText(endTime);
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid Id", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewbooking.this, home.class);
                startActivity(intent);
            }
        });
    }
}
