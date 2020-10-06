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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetpassword extends AppCompatActivity {

    private EditText mailedit;
    private Button resetb;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        mailedit=(EditText)findViewById(R.id.passwordedit);
        resetb=(Button)findViewById(R.id.resetbutton);
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

        resetb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usermail=mailedit.getText().toString().trim();

                if(usermail.isEmpty())
                {
                    showMessage("Email is required");
                }
                else
                {
                    mAuth.sendPasswordResetEmail(usermail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(resetpassword.this,"Password Reset email sent",Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent(resetpassword.this, login.class);
                                startActivity(intent);

                            }
                            else
                            {
                                Toast.makeText(resetpassword.this,"Password Reset unsuccessfully",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }
}
