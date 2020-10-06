package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class update extends AppCompatActivity {
    private static final String TAG="update";
    private DatePickerDialog.OnDateSetListener mDatesetlistener;
    TextView type,number,date,timefrom,timeto,slot,idtxt,slotbox;
    EditText id,typebox,numberbox,datebox,totime,fromtime;
    Button search,update;
    String vehicaltype,vehicalnumber,startTime,endTime,bdate,imageid,imgnum;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        idtxt=(TextView)findViewById(R.id.bookingidtext);
        id=(EditText)findViewById(R.id.bookingid);
        type=(TextView)findViewById(R.id.type);
        typebox=(EditText)findViewById(R.id.typebox);
        number=(TextView)findViewById(R.id.number);
        numberbox=(EditText)findViewById(R.id.numberbox);
        date=(TextView)findViewById(R.id.date);
        datebox=(EditText)findViewById(R.id.datebox);
        totime=(EditText)findViewById(R.id.totime);
        timeto=(TextView)findViewById(R.id.timeto);
        fromtime=(EditText)findViewById(R.id.fromtime);
        timefrom=(TextView)findViewById(R.id.timefrom);
        slotbox=(TextView) findViewById(R.id.slotbox);
        search=(Button)findViewById(R.id.searchbtn);
        update=(Button)findViewById(R.id.updatebtn);
        ref=FirebaseDatabase.getInstance().getReference().child("Book");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
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
                                Toast.makeText(getApplicationContext(), "Cannot change slot number", Toast.LENGTH_LONG).show();
                                vehicaltype = dataSnapshot.child("vehicaltype").getValue().toString();
                                vehicalnumber = dataSnapshot.child("vehicalnumber").getValue().toString();
                                bdate = dataSnapshot.child("date").getValue().toString();
                                startTime = dataSnapshot.child("startTime").getValue().toString();
                                endTime = dataSnapshot.child("endTime").getValue().toString();
                                slotbox.setText(dataSnapshot.child("slot").getValue().toString());
                                update.setVisibility(View.VISIBLE);
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
        datebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(update.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDatesetlistener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDatesetlistener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthv, int day) {
                Log.d(TAG,"onDateSet: mm/dd/yy "+day+"/"+monthv+"/"+year);
                int month=monthv+1;
                String date= day+"/"+month+"/"+year;
                datebox.setText(date);
            }
        };
        totime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal=Calendar.getInstance();
                int hour=cal.get(Calendar.HOUR_OF_DAY);
                int minute=cal.get(Calendar.MINUTE);
                TimePickerDialog dialog=new TimePickerDialog(update.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        totime.setText(hour+":"+minute);
                    }
                },hour,minute,true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                 dialog.show();

            }
        });
        fromtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal=Calendar.getInstance();
                int hour=cal.get(Calendar.HOUR_OF_DAY);
                int minute=cal.get(Calendar.MINUTE);
                TimePickerDialog dialog=new TimePickerDialog(update.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        fromtime.setText(hour+":"+minute);
                    }
                },hour,minute,true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String type= typebox.getText().toString().trim();
                final String number= numberbox.getText().toString().trim();
                final String date= datebox.getText().toString().trim();
                final String from=fromtime.getText().toString().trim();
                final String to=totime.getText().toString().trim();
                int slotnum=Integer.parseInt(slotbox.getText().toString());
                String imageadd = imgnum;

                if (type.isEmpty()) {
                    typebox.setError("Vehical Type is required");
                    typebox.requestFocus();
                    return;
                }
                else if (number.isEmpty()) {
                    numberbox.setError("Vehical Number is required");
                    numberbox.requestFocus();
                    return;
                }else if (date.isEmpty()) {
                    datebox.setError("Date is required");
                    datebox.requestFocus();
                    return;
                }
                else if (from.isEmpty()) {
                    fromtime.setError("Start Time is required");
                    fromtime.requestFocus();
                    return;
                }
                else if (to.isEmpty()) {
                    totime.setError("End Time is required");
                    totime.requestFocus();
                    return;
                }

                Book book = new Book(type, number, date, from, to, slotnum, imageadd);
                ref.setValue(book);
                Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        }
}
