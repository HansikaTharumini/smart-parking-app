package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

public class cancel extends AppCompatActivity {
    TextView type,typebox,number,numberbox,date,datebox,fromtime,timefrom,totime,timeto,amount,amountbox,slot,slotbox;
    EditText id;
    Button search,cancel;
    String vehicaltype,vehicalnumber,startTime,endTime,bdate;
    float amountv;
    int bid;
    DatabaseReference ref;
    DatabaseReference refp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

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
        amount=(TextView)findViewById(R.id.amount);
        amountbox=(TextView)findViewById(R.id.amountbox);
        slot=(TextView)findViewById(R.id.slot);
        slotbox=(TextView)findViewById(R.id.slotbox);
        search=(Button)findViewById(R.id.searchbtn);
        cancel=(Button)findViewById(R.id.cancelbtn);
        id=(EditText)findViewById(R.id.bookingidbox);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = id.getText().toString();
                if (value.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "ID cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    bid = Integer.parseInt(id.getText().toString());
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
                                cancel.setVisibility(View.VISIBLE);
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
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 cancelbook(bid);
            }
        });
    }

    private void cancelbook(int bid) {
        DatabaseReference refb=FirebaseDatabase.getInstance().getReference("Book").child(String.valueOf(bid));
        DatabaseReference refp=FirebaseDatabase.getInstance().getReference("Payment").child(String.valueOf(bid));

        refb.removeValue();
        refp.removeValue();

        Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
        finish();
    }
}
