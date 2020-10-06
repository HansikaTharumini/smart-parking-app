package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginform extends AppCompatActivity {
Button login,forget;
EditText username,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginform);

        login=(Button)findViewById(R.id.loginbutton);
        forget=(Button)findViewById(R.id.forget);
        username=(EditText)findViewById(R.id.useredit) ;
        password=(EditText)findViewById(R.id.passwordedit) ;
        mAuth=FirebaseAuth.getInstance();

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

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(loginform.this,"Forget Password",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(loginform.this,resetpassword.class);
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String usernamev=username.getText().toString();
                final String passwordv=password.getText().toString();

                if(usernamev.isEmpty())
                {
                    showMessage("Email is required");
                }
                else if(passwordv.isEmpty())
                {
                    showMessage("Password is required");
                }
                else
                {
                    signin(usernamev,passwordv);
                }

            }
            });

    }

    private void signin(String usernamev, String passwordv)
    {
        mAuth.signInWithEmailAndPassword(usernamev,passwordv).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(loginform.this, "Home", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginform.this, home.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    showMessage(task.getException().getMessage());
                }
            }
        });
    }

    private void showMessage(String text)
    {
      Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();

        if(user !=null)
        {
            Toast.makeText(loginform.this, "Home", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(loginform.this, home.class);
            startActivity(intent);
            finish();
        }
    }

}
