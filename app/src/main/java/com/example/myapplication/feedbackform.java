package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.SmileRating;

public class feedbackform extends AppCompatActivity {
    private Button send;
    EditText feedb;
    public String mood;
    private DatabaseReference ref;
    Feedback feedback;
    long fid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbackform);

        send=(Button)findViewById(R.id.sendbtn);
        feedb=(EditText)findViewById(R.id.feedbacktxt);
        SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);
        feedback=new Feedback();
        ref= FirebaseDatabase.getInstance().getReference().child("Feedback");

        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected( int smiley, boolean reselected) {

                switch (smiley) {
                    case SmileRating.BAD:
                        mood="Bad";
                        Toast.makeText(feedbackform.this,"BAD",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GOOD:
                        mood="Good";
                        Toast.makeText(feedbackform.this,"GOOD",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GREAT:
                        mood="Great";
                        Toast.makeText(feedbackform.this,"GREAT",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.OKAY:
                        mood="Okay";
                        Toast.makeText(feedbackform.this,"OKAY",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.TERRIBLE:
                        mood="Terrible";
                        Toast.makeText(feedbackform.this,"TERRIBLE",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    fid = (dataSnapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feed = feedb.getText().toString().trim();
                String face = mood;
                if (feed.isEmpty()) {
                    feedb.setError("Feedback  is required");
                    feedb.requestFocus();
                    return;
                } else {
                    feedback.setFeedback(feed);
                    feedback.setMood(face);
                    ref.child(String.valueOf(fid+1)).setValue(feedback);

                    Toast.makeText(feedbackform.this, "Sending successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
