package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class login extends Activity {
    FloatingActionButton first,information,log,sign;
    TextView txtlog,txtsign,txtinfo,click;
    Animation open,close,clockwise,anticlockwise;
    boolean isopen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        first=(FloatingActionButton)findViewById(R.id.mainfa);
        information=(FloatingActionButton)findViewById(R.id.information);
        log=(FloatingActionButton)findViewById(R.id.logbutton);
        sign=(FloatingActionButton)findViewById(R.id.sign) ;
        txtlog=(TextView)findViewById(R.id.txtlogin);
        txtsign=(TextView)findViewById(R.id.txtsign);
        txtinfo=(TextView)findViewById(R.id.txtinfo);
        click=(TextView)findViewById(R.id.click);
        open= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        close= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        clockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.r_clock);
        anticlockwise= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.r_anticlock);
       first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isopen)
                {
                    information.startAnimation(close);
                    click.startAnimation(open);
                    log.startAnimation(close);
                    sign.startAnimation(close);
                    txtlog.startAnimation(close);
                    txtinfo.startAnimation(close);
                    txtsign.startAnimation(close);
                    first.startAnimation(anticlockwise);
                    information.setClickable(false);
                    log.setClickable(false);
                    sign.setClickable(false);
                    isopen=false;
                }
                else
                    {
                        information.startAnimation(open);
                        log.startAnimation(open);
                        sign.startAnimation(open);
                        txtlog.startAnimation(open);
                        txtinfo.startAnimation(open);
                        txtsign.startAnimation(open);
                        click.startAnimation(close);
                        first.startAnimation(clockwise);
                        information.setClickable(true);
                        log.setClickable(true);
                        sign.setClickable(true);
                        isopen=true;
                    }
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(login.this,"SignUp",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login.this,signupform.class);
                startActivity(intent);
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(login.this, "Login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login.this, loginform.class);
                startActivity(intent);
            }
        });
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(login.this, "Information", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login.this, infomform.class);
                startActivity(intent);
            }
        });

    }

}
