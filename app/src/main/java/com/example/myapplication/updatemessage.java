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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class updatemessage extends AppCompatActivity {
   private EditText newpassword;
   private Button change;
   private FirebaseUser firebaseUser;
   private FirebaseAuth firebaseAuth;
   String passwordnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatemessage);
        change=(Button)findViewById(R.id.changebtn);
        newpassword=(EditText)findViewById(R.id.newpassword);

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

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordnew = newpassword.getText().toString();

                if (passwordnew.isEmpty()) {
                    newpassword.setError("New password is required");
                    newpassword.requestFocus();
                    return;
                } else {
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    firebaseUser.updatePassword(passwordnew).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(updatemessage.this, "Password changed", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(updatemessage.this, "Password update failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }
}
