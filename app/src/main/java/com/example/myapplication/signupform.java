package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signupform extends AppCompatActivity {
    Button sign;
    EditText nic, name, email, contact, password, confirm;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupform);

        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        pdialog=new ProgressDialog(this);
        sign = (Button) findViewById(R.id.signbutton);
        nic = (EditText) findViewById(R.id.nictxt);
        name = (EditText) findViewById(R.id.nametxt);
        email = (EditText) findViewById(R.id.emailtxt);
        contact = (EditText) findViewById(R.id.contacttxt);
        password = (EditText) findViewById(R.id.passwordtxt);
        confirm = (EditText) findViewById(R.id.confirmtxt);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .8));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.signbutton:
                        registerUser();
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart () {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

        }
    }


    private void registerUser () {
        final String nicv = nic.getText().toString().trim();
        final String namev = name.getText().toString().trim();
        final String emailv = email.getText().toString().trim();
        final String passwordv = password.getText().toString().trim();
        String confirmv = confirm.getText().toString().trim();
        final String contactv = contact.getText().toString().trim();


        if (nicv.isEmpty()) {
            nic.setError("NIC Number is required");
            nic.requestFocus();
            return;
        } else if (namev.isEmpty()) {
            name.setError("Full Name is required");
            name.requestFocus();
            return;
        } else if (contactv.isEmpty()) {
            contact.setError("Contact number is required");
            contact.requestFocus();
            return;
        } else if (contactv.length() != 10) {
            contact.setError("Enter a valid contact number");
            contact.requestFocus();
            return;
        } else if (emailv.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailv).matches()) {
            email.setError("Enter a valid email ");
            email.requestFocus();
            return;
        } else if (passwordv.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        } else if (!passwordv.equals(confirmv)) {
            confirm.setError("Your password do not match with your confirm password");
            confirm.requestFocus();
            return;
        }
        pdialog.setMessage("Please wait...");
        pdialog.show();
        mAuth.createUserWithEmailAndPassword(emailv, passwordv)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(
                                    nicv, namev, contactv, emailv
                            );


                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(signupform.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(signupform.this, login.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(signupform.this, "Signup Unsuccessfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        else {
                            Toast.makeText(signupform.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}