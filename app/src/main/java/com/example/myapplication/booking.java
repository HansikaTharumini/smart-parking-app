package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static kotlin.text.Typography.amp;

public class booking extends AppCompatActivity {
    Button booking, view, ok,available;
    private Button oneb, twob, threeb, fourb, fiveb, sixb, sevenb, eightb, nineb, tenb, elevenb, twelveb;
    private ImageView barcode;
    private EditText vehicaltype, vehicalnumber, date, fromtime, totime, expire, cvv, cardnum;
    private TextView bookingid, amounttxt;
    private DatabaseReference ref;
    private DatabaseReference refslot;
    private DatabaseReference refp;

    int slot, slotnum = 0;
    long bid = 0;
    float amount = 0,total = 0;
    String type, number, datevar, from, to, expirev, imageadd, cardnums, cvvs,id;
    int cardnumv = 0, cvvv = 0, bookv;

    Uri FilePathUri;
    StorageReference storageReference;
    int Image_Request_Code = 7;
    String Storage_Path="Barcodes";
    String ImageUploadId;

    private static final String TAG = "booking";
    private DatePickerDialog.OnDateSetListener mDatesetlistener;
    private DatePickerDialog.OnDateSetListener eDatesetlistener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        booking = (Button) findViewById(R.id.bookingbtn);
        ok = (Button) findViewById(R.id.okbtn);
        view = (Button) findViewById(R.id.viewbtn);
        available = (Button) findViewById(R.id.availablbtn);
        bookingid = (TextView) findViewById(R.id.bookingid);
        vehicaltype = (EditText) findViewById(R.id.vtype);
        vehicalnumber = (EditText) findViewById(R.id.vnumber);
        fromtime = (EditText) findViewById(R.id.fromtime);
        totime = (EditText) findViewById(R.id.totime);
        date = (EditText) findViewById(R.id.datetxt);
        expire = (EditText) findViewById(R.id.expirebox);
        cvv = (EditText) findViewById(R.id.cvvbox);
        cardnum = (EditText) findViewById(R.id.cardnumbox);
        amounttxt = (TextView) findViewById(R.id.amountbox);
        oneb = (Button) findViewById(R.id.oneb);
        twob = (Button) findViewById(R.id.twob);
        threeb = (Button) findViewById(R.id.threeb);
        fourb = (Button) findViewById(R.id.fourb);
        fiveb = (Button) findViewById(R.id.fiveb);
        sixb = (Button) findViewById(R.id.sixb);
        sevenb = (Button) findViewById(R.id.sevenb);
        eightb = (Button) findViewById(R.id.eightb);
        nineb = (Button) findViewById(R.id.nineb);
        tenb = (Button) findViewById(R.id.tenb);
        elevenb = (Button) findViewById(R.id.elevenb);
        twelveb = (Button) findViewById(R.id.twelveb);
        barcode = (ImageView) findViewById(R.id.barcode);
        ref = FirebaseDatabase.getInstance().getReference().child("Book");
        refp = FirebaseDatabase.getInstance().getReference().child("Payment");

        storageReference = FirebaseStorage.getInstance().getReference();


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    bid = (dataSnapshot.getChildrenCount());

                String booki = (String.valueOf(bid + 1));
                bookingid.setText(booki);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(booking.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDatesetlistener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDatesetlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthv, int day) {
                Log.d(TAG, "onDateSet: mm/dd/yy " + day + "/" + monthv + "/" + year);
                int month = monthv + 1;
                String datev = day + "/" + month + "/" + year;
                date.setText(datev);
            }
        };
        expire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(booking.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        eDatesetlistener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        eDatesetlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthv, int day) {
                Log.d(TAG, "onDateSet: mm/dd/yy " + day + "/" + monthv + "/" + year);
                int month = monthv + 1;
                String exdate = day + "/" + month + "/" + year;
                expire.setText(exdate);
            }
        };
        totime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(booking.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        totime.setText(hour + ":" + minute);
                    }
                }, hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        fromtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minute = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(booking.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        fromtime.setText(hour + ":" + minute);
                    }
                }, hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Book book = new Book(type, number, datevar, from, to, slotnum, ImageUploadId);
                ref.child(String.valueOf(bid + 1)).setValue(book);

                cvvv = Integer.parseInt(cvvs);
                cardnumv = Integer.parseInt(cardnums);
                Payment payment = new Payment(bookv, total, expirev, cardnumv, cvvv);
                refp.child(String.valueOf(bid + 1)).setValue(payment);

                vehicaltype.getText().clear();
                vehicalnumber.getText().clear();
                date.getText().clear();
                fromtime.getText().clear();
                totime.getText().clear();
                slot = 0;
                Toast.makeText(booking.this, "Booking successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expirev = expire.getText().toString().trim();
                cvvs = cvv.getText().toString();
                cardnums = cardnum.getText().toString();
                bookv = Integer.parseInt(bookingid.getText().toString());
                slotnum = slot;
                total = getamount();

                if (slotnum == 0) {
                    Toast.makeText(getApplicationContext(), "please select a slot", Toast.LENGTH_LONG).show();
                } else if (cardnums.isEmpty()) {
                    cardnum.setError("Card Number is required");
                    cardnum.requestFocus();
                    return;
                } else if (expirev.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "please select a expire date", Toast.LENGTH_LONG).show();
                } else if (cvvs.isEmpty()) {
                    cvv.setError("CVV Number is required");
                    cvv.requestFocus();
                    return;
                } else if (expirev.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "please select a expire date", Toast.LENGTH_LONG).show();
                } else {
                    ok.setVisibility(View.VISIBLE);
                    getcode();
                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UploadImageFileToFirebaseStorage();
                ok.setVisibility(View.INVISIBLE);
                booking.setVisibility(View.VISIBLE);

            }
        });
        available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = vehicaltype.getText().toString().trim();
                number = vehicalnumber.getText().toString().trim();
                datevar = date.getText().toString().trim();
                from = fromtime.getText().toString().trim();
                to = totime.getText().toString().trim();
                total = getamount();
                String now = new String(String.valueOf(System.currentTimeMillis()));


                if (type.isEmpty()) {
                    vehicaltype.setError("Vehical Type is required");
                    vehicaltype.requestFocus();
                    return;
                } else if (total == 0) {
                    vehicaltype.setError("please enter valid vehical type");
                    vehicaltype.requestFocus();
                    return;
                } else if (number.isEmpty()) {
                    vehicalnumber.setError("Vehical Number is required");
                    vehicalnumber.requestFocus();
                    return;
                } else if (datevar.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "please select a date", Toast.LENGTH_LONG).show();
                } else if (from.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "please select a start time", Toast.LENGTH_LONG).show();
                } else if (to.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "please select a end time", Toast.LENGTH_LONG).show();
                } else {

                    //slot1
                    final int slot1 = 1;
                    ref.orderByChild("slot").equalTo(slot1).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    id = child.getKey();
                                    refslot = FirebaseDatabase.getInstance().getReference().child("Book").child(id);
                                    refslot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String sdate = dataSnapshot.child("date").getValue().toString();
                                            if (sdate.equals(datevar)) {
                                                oneb.setBackgroundColor(Color.parseColor("#AAFF0000"));
                                                oneb.setClickable(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });//slot2
                    final int slot2 = 2;
                    ref.orderByChild("slot").equalTo(slot2).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    id = child.getKey();
                                    refslot = FirebaseDatabase.getInstance().getReference().child("Book").child(id);
                                    refslot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String sdate = dataSnapshot.child("date").getValue().toString();
                                            if (sdate.equals(datevar)) {
                                                twob.setBackgroundColor(Color.parseColor("#AAFF0000"));
                                                twob.setClickable(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    //slot3
                    final int slot3 = 3;
                    ref.orderByChild("slot").equalTo(slot3).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    id = child.getKey();
                                    refslot = FirebaseDatabase.getInstance().getReference().child("Book").child(id);
                                    refslot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String sdate = dataSnapshot.child("date").getValue().toString();
                                            if (sdate.equals(datevar)) {
                                                threeb.setBackgroundColor(Color.parseColor("#AAFF0000"));
                                                threeb.setClickable(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    //slot4
                    final int slot4 = 4;
                    ref.orderByChild("slot").equalTo(slot4).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    id = child.getKey();
                                    refslot = FirebaseDatabase.getInstance().getReference().child("Book").child(id);
                                    refslot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String sdate = dataSnapshot.child("date").getValue().toString();
                                            if (sdate.equals(datevar)) {
                                                fourb.setBackgroundColor(Color.parseColor("#AAFF0000"));
                                                fourb.setClickable(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    //slot5
                    final int slot5 = 5;
                    ref.orderByChild("slot").equalTo(slot5).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    id = child.getKey();
                                    refslot = FirebaseDatabase.getInstance().getReference().child("Book").child(id);
                                    refslot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String sdate = dataSnapshot.child("date").getValue().toString();
                                            if (sdate.equals(datevar)) {
                                                fiveb.setBackgroundColor(Color.parseColor("#AAFF0000"));
                                                fiveb.setClickable(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });//slot6
                    final int slot6 = 6;
                    ref.orderByChild("slot").equalTo(slot6).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    id = child.getKey();
                                    refslot = FirebaseDatabase.getInstance().getReference().child("Book").child(id);
                                    refslot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String sdate = dataSnapshot.child("date").getValue().toString();
                                            if (sdate.equals(datevar)) {
                                                sixb.setBackgroundColor(Color.parseColor("#AAFF0000"));
                                                sixb.setClickable(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    //slot7
                    final int slot7 = 7;
                    ref.orderByChild("slot").equalTo(slot7).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    id = child.getKey();
                                    refslot = FirebaseDatabase.getInstance().getReference().child("Book").child(id);
                                    refslot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String sdate = dataSnapshot.child("date").getValue().toString();
                                            if (sdate.equals(datevar)) {
                                                sevenb.setBackgroundColor(Color.parseColor("#AAFF0000"));
                                                sevenb.setClickable(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    //slot8
                    final int slot8 = 8;
                    ref.orderByChild("slot").equalTo(slot8).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    id = child.getKey();
                                    refslot = FirebaseDatabase.getInstance().getReference().child("Book").child(id);
                                    refslot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String sdate = dataSnapshot.child("date").getValue().toString();
                                            if (sdate.equals(datevar)) {
                                                eightb.setBackgroundColor(Color.parseColor("#AAFF0000"));
                                                eightb.setClickable(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    //slot9
                    final int slot9 = 9;
                    ref.orderByChild("slot").equalTo(slot9).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    id = child.getKey();
                                    refslot = FirebaseDatabase.getInstance().getReference().child("Book").child(id);
                                    refslot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String sdate = dataSnapshot.child("date").getValue().toString();
                                            if (sdate.equals(datevar)) {
                                                nineb.setBackgroundColor(Color.parseColor("#AAFF0000"));
                                                nineb.setClickable(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });//slot10
                    final int slot10 = 10;
                    ref.orderByChild("slot").equalTo(slot10).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    id = child.getKey();
                                    refslot = FirebaseDatabase.getInstance().getReference().child("Book").child(id);
                                    refslot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String sdate = dataSnapshot.child("date").getValue().toString();
                                            if (sdate.equals(datevar)) {
                                                tenb.setBackgroundColor(Color.parseColor("#AAFF0000"));
                                                tenb.setClickable(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    //slot11
                    final int slot11 = 11;
                    ref.orderByChild("slot").equalTo(slot11).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    id = child.getKey();
                                    refslot = FirebaseDatabase.getInstance().getReference().child("Book").child(id);
                                    refslot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String sdate = dataSnapshot.child("date").getValue().toString();
                                            if (sdate.equals(datevar)) {
                                                elevenb.setBackgroundColor(Color.parseColor("#AAFF0000"));
                                                elevenb.setClickable(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    //slot12
                    final int slot12 = 12;
                    ref.orderByChild("slot").equalTo(slot12).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    id = child.getKey();
                                    refslot = FirebaseDatabase.getInstance().getReference().child("Book").child(id);
                                    refslot.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            String sdate = dataSnapshot.child("date").getValue().toString();
                                            if (sdate.equals(datevar)) {
                                                twelveb.setBackgroundColor(Color.parseColor("#AAFF0000"));
                                                twelveb.setClickable(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                    //slots
                    oneb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slot = 1;
                            oneb.setBackgroundColor(Color.parseColor("#8800ff00"));
                            Toast.makeText(booking.this, "Select slot Number 1", Toast.LENGTH_SHORT).show();

                            twob.setClickable(false);
                            eightb.setClickable(false);
                            threeb.setClickable(false);
                            nineb.setClickable(false);
                            fourb.setClickable(false);
                            tenb.setClickable(false);
                            fiveb.setClickable(false);
                            elevenb.setClickable(false);
                            sixb.setClickable(false);
                            twelveb.setClickable(false);
                            sevenb.setClickable(false);
                        }
                    });
                    twob.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slot = 2;
                            twob.setBackgroundColor(Color.parseColor("#8800ff00"));
                            Toast.makeText(booking.this, "Select slot Number 2", Toast.LENGTH_SHORT).show();

                            oneb.setClickable(false);
                            eightb.setClickable(false);
                            threeb.setClickable(false);
                            nineb.setClickable(false);
                            fourb.setClickable(false);
                            tenb.setClickable(false);
                            fiveb.setClickable(false);
                            elevenb.setClickable(false);
                            sixb.setClickable(false);
                            twelveb.setClickable(false);
                            sevenb.setClickable(false);
                        }
                    });
                    threeb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slot = 3;
                            threeb.setBackgroundColor(Color.parseColor("#8800ff00"));
                            Toast.makeText(booking.this, "Select slot Number 3", Toast.LENGTH_SHORT).show();

                            twob.setClickable(false);
                            eightb.setClickable(false);
                            oneb.setClickable(false);
                            nineb.setClickable(false);
                            fourb.setClickable(false);
                            tenb.setClickable(false);
                            fiveb.setClickable(false);
                            elevenb.setClickable(false);
                            sixb.setClickable(false);
                            twelveb.setClickable(false);
                            sevenb.setClickable(false);
                        }
                    });
                    fourb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slot = 4;
                            fourb.setBackgroundColor(Color.parseColor("#8800ff00"));
                            Toast.makeText(booking.this, "Select slot Number 4", Toast.LENGTH_SHORT).show();

                            twob.setClickable(false);
                            eightb.setClickable(false);
                            threeb.setClickable(false);
                            nineb.setClickable(false);
                            oneb.setClickable(false);
                            tenb.setClickable(false);
                            fiveb.setClickable(false);
                            elevenb.setClickable(false);
                            sixb.setClickable(false);
                            twelveb.setClickable(false);
                            sevenb.setClickable(false);
                        }
                    });
                    fiveb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slot = 5;
                            fiveb.setBackgroundColor(Color.parseColor("#8800ff00"));
                            Toast.makeText(booking.this, "Select slot Number 5", Toast.LENGTH_SHORT).show();

                            twob.setClickable(false);
                            eightb.setClickable(false);
                            threeb.setClickable(false);
                            nineb.setClickable(false);
                            fourb.setClickable(false);
                            tenb.setClickable(false);
                            oneb.setClickable(false);
                            elevenb.setClickable(false);
                            sixb.setClickable(false);
                            twelveb.setClickable(false);
                            sevenb.setClickable(false);
                        }
                    });
                    sixb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slot = 6;
                            sixb.setBackgroundColor(Color.parseColor("#8800ff00"));
                            Toast.makeText(booking.this, "Select slot Number 6", Toast.LENGTH_SHORT).show();

                            twob.setClickable(false);
                            eightb.setClickable(false);
                            threeb.setClickable(false);
                            nineb.setClickable(false);
                            fourb.setClickable(false);
                            tenb.setClickable(false);
                            fiveb.setClickable(false);
                            elevenb.setClickable(false);
                            oneb.setClickable(false);
                            twelveb.setClickable(false);
                            sevenb.setClickable(false);
                        }
                    });
                    sevenb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slot = 7;
                            sevenb.setBackgroundColor(Color.parseColor("#8800ff00"));
                            Toast.makeText(booking.this, "Select slot Number 7", Toast.LENGTH_SHORT).show();

                            twob.setClickable(false);
                            eightb.setClickable(false);
                            threeb.setClickable(false);
                            nineb.setClickable(false);
                            fourb.setClickable(false);
                            tenb.setClickable(false);
                            fiveb.setClickable(false);
                            elevenb.setClickable(false);
                            sixb.setClickable(false);
                            twelveb.setClickable(false);
                            oneb.setClickable(false);
                        }
                    });
                    eightb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slot = 8;
                            eightb.setBackgroundColor(Color.parseColor("#8800ff00"));
                            Toast.makeText(booking.this, "Select slot Number 8", Toast.LENGTH_SHORT).show();

                            twob.setClickable(false);
                            oneb.setClickable(false);
                            threeb.setClickable(false);
                            nineb.setClickable(false);
                            fourb.setClickable(false);
                            tenb.setClickable(false);
                            fiveb.setClickable(false);
                            elevenb.setClickable(false);
                            sixb.setClickable(false);
                            twelveb.setClickable(false);
                            sevenb.setClickable(false);
                        }
                    });
                    nineb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slot = 9;
                            nineb.setBackgroundColor(Color.parseColor("#8800ff00"));
                            Toast.makeText(booking.this, "Select slot Number 9", Toast.LENGTH_SHORT).show();

                            twob.setClickable(false);
                            eightb.setClickable(false);
                            threeb.setClickable(false);
                            oneb.setClickable(false);
                            fourb.setClickable(false);
                            tenb.setClickable(false);
                            fiveb.setClickable(false);
                            elevenb.setClickable(false);
                            sixb.setClickable(false);
                            twelveb.setClickable(false);
                            sevenb.setClickable(false);
                        }
                    });
                    tenb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slot = 10;
                            tenb.setBackgroundColor(Color.parseColor("#8800ff00"));
                            Toast.makeText(booking.this, "Select slot Number 10", Toast.LENGTH_SHORT).show();

                            twob.setClickable(false);
                            eightb.setClickable(false);
                            threeb.setClickable(false);
                            nineb.setClickable(false);
                            fourb.setClickable(false);
                            oneb.setClickable(false);
                            fiveb.setClickable(false);
                            elevenb.setClickable(false);
                            sixb.setClickable(false);
                            twelveb.setClickable(false);
                            sevenb.setClickable(false);
                        }
                    });
                    elevenb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slot = 11;
                            elevenb.setBackgroundColor(Color.parseColor("#8800ff00"));
                            Toast.makeText(booking.this, "Select slot Number 11", Toast.LENGTH_SHORT).show();

                            twob.setClickable(false);
                            eightb.setClickable(false);
                            threeb.setClickable(false);
                            nineb.setClickable(false);
                            fourb.setClickable(false);
                            tenb.setClickable(false);
                            fiveb.setClickable(false);
                            oneb.setClickable(false);
                            sixb.setClickable(false);
                            twelveb.setClickable(false);
                            sevenb.setClickable(false);
                        }
                    });
                    twelveb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slot = 12;
                            twelveb.setBackgroundColor(Color.parseColor("#8800ff00"));
                            Toast.makeText(booking.this, "Select slot Number 12", Toast.LENGTH_SHORT).show();

                            twob.setClickable(false);
                            eightb.setClickable(false);
                            threeb.setClickable(false);
                            nineb.setClickable(false);
                            fourb.setClickable(false);
                            tenb.setClickable(false);
                            fiveb.setClickable(false);
                            elevenb.setClickable(false);
                            sixb.setClickable(false);
                            oneb.setClickable(false);
                            sevenb.setClickable(false);
                        }
                    });
                }
            }

        });
    }

    private float getamount() {
       switch (type)
       {
           case "bike":amount=30;break;
           case "threewheel":amount=30;break;
           case "car":amount=40;break;
           case "van":amount=50;break;
           case "bus":amount=60;break;
           case "lorry":amount=60;break;
           default:amount=0;break;
       }
        return amount;
    }

    //barcodde genarate
    private void getcode() {
        try{
            barcode();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
    public void barcode() throws WriterException
    {
        BitMatrix bitMatrix=multiFormatWriter.encode(bookingid.getText().toString(), BarcodeFormat.CODE_128,400,170,null);
        BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
        Bitmap bitmap=barcodeEncoder.createBitmap(bitMatrix);
        barcode.setImageBitmap(bitmap);
    }
    //image store
    private void UploadImageFileToFirebaseStorage() {
        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            // Getting image upload ID.
                            ImageUploadId = ref.push().getKey();
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Showing exception erro message.
                            Toast.makeText(booking.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else {

            Toast.makeText(booking.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                barcode.setImageBitmap(bitmap);

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

}