package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class changedetails extends AppCompatActivity {
    private EditText newnic,newname,newcontact;
    private TextView newemail;
    private Button save;
    private  FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changedetails);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x=0;
        params.y=-20;

        getWindow().setAttributes(params);

        newnic=(EditText)findViewById(R.id.nic) ;
        newname=(EditText)findViewById(R.id.name) ;
        newemail=(TextView) findViewById(R.id.email) ;
        newcontact=(EditText)findViewById(R.id.contact) ;
        save=(Button)findViewById(R.id.savebtn);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("User").child(firebaseAuth.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nicv=dataSnapshot.child("nic").getValue().toString();
                String namev=dataSnapshot.child("name").getValue().toString();
                String contactv=dataSnapshot.child("contact").getValue().toString();
                String emailv=dataSnapshot.child("email").getValue().toString();

                newnic.setText(nicv);
                newname.setText(namev);
                newcontact.setText(contactv);
                newemail.setText(emailv);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(changedetails.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nic=newnic.getText().toString();
                String name=newname.getText().toString();
                String contact=newcontact.getText().toString();
                String email=newemail.getText().toString();

                if (nic.isEmpty()) {
                    newnic.setError("NIC is required");
                    newnic.requestFocus();
                    return;
                } else if (name.isEmpty()) {
                    newname.setError("Full Name is required");
                    newname.requestFocus();
                    return;
                } else if (contact.isEmpty()) {
                    newcontact.setError("Contact number is required");
                    newcontact.requestFocus();
                    return;
                } else if (contact.length() != 10) {
                    newcontact.setError("Enter a valid contact number");
                    newcontact.requestFocus();
                    return;
                }else {

                    User user = new User(nic, name, contact, email);
                    ref.setValue(user);
                    finish();
                }
            }
        });
    }
}
