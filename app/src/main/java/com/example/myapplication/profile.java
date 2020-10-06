package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class profile extends AppCompatActivity {
 private TextView pnic,pname,pcontact,pemail;
private Button pupdate,passwordc;
 DatabaseReference ref;
private  FirebaseDatabase firebaseDatabase;
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pnic=(TextView)findViewById(R.id.nic);
        pname=(TextView)findViewById(R.id.fullname);
        pcontact=(TextView)findViewById(R.id.contact);
        pemail=(TextView)findViewById(R.id.email);
        pupdate=(Button)findViewById(R.id.updatebtn);
        passwordc=(Button)findViewById(R.id.passwordupdatebtn);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

                ref= FirebaseDatabase.getInstance().getReference().child("User").child(firebaseAuth.getUid());
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String nicv=dataSnapshot.child("nic").getValue().toString();
                        String namev=dataSnapshot.child("name").getValue().toString();
                        String contactv=dataSnapshot.child("contact").getValue().toString();
                        String emailv=dataSnapshot.child("email").getValue().toString();

                       pnic.setText(nicv);
                       pname.setText(namev);
                       pcontact.setText(contactv);
                       pemail.setText(emailv);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        pupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(profile.this,"Cannot change Email Address",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(profile.this,changedetails.class);
                startActivity(intent);
            }
        });
        passwordc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(profile.this,"Change Password",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(profile.this,updatemessage.class);
                startActivity(intent);
            }
        });
            }

    }


