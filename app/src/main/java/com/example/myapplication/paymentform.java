package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class paymentform extends AppCompatActivity {
    TextView pay,bookinid,amount;
    Button ok;
    private ProgressDialog pdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentform);

        bookinid=(TextView)findViewById(R.id.bookingid);
        amount=(TextView)findViewById(R.id.amount);
        ok=(Button)findViewById(R.id.okbtn);
        pay=(Button)findViewById(R.id.paybtn);
        pdialog=new ProgressDialog(this);

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


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdialog.setMessage("Please wait...");
                pdialog.show();

                Toast.makeText(paymentform.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(paymentform.this, home.class);
                startActivity(intent);
            }
        });

    }

    }


