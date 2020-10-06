package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {
private Button profileb,bookingb,updateb,cancelb,mapb,feedbackb,contactb,viewb,logoutb;
private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        profileb=(Button)findViewById(R.id.profileb);
        bookingb=(Button)findViewById(R.id.bookingb);
        updateb=(Button)findViewById(R.id.updateb);
        cancelb=(Button)findViewById(R.id.cancelb);
        mapb=(Button)findViewById(R.id.mapb);
        feedbackb=(Button)findViewById(R.id.feedbackb);
        contactb=(Button)findViewById(R.id.contactb);
        viewb=(Button)findViewById(R.id.viewb);
        logoutb=(Button)findViewById(R.id.logoutb);
        mAuth=FirebaseAuth.getInstance();

        profileb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home.this,"Profile",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this,profile.class);
                startActivity(intent);
            }
        });
        bookingb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home.this,"New Booking",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this,booking.class);
                startActivity(intent);
            }
        });
        updateb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home.this,"Booking Update",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this,update.class);
                startActivity(intent);
            }
        });
        cancelb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home.this,"Booking Cancel",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this,cancel.class);
                startActivity(intent);
            }
        });
        mapb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home.this,"Map",Toast.LENGTH_SHORT).show();
            }
        });
        feedbackb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home.this,"Feedback",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this,feedbackform.class);
                startActivity(intent);
            }
        });
        contactb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home.this,"Contact Us",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this,contactform.class);
                startActivity(intent);
            }
        });
        viewb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home.this,"View Booking",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this,viewbooking.class);
                startActivity(intent);

            }
        });
        logoutb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signOut();
                finish();
                Toast.makeText(home.this,"Logout Successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this,login.class);
                startActivity(intent);

            }
        });

    }
}
