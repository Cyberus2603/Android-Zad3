package com.cyberus2603.androidzad3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Trace;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button mon_bt, tue_bt, wed_bt, thu_bt, fri_bt, edit_bt;
    TextView[] sub_hours_tv;
    TextView[] sub_text_tv;
    EditText[] sub_edit_text_et;

    DatabaseReference firebase_db = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mon_sub1 = firebase_db.child("mon_sub1");
    DatabaseReference mon_sub2 = firebase_db.child("mon_sub2");
    DatabaseReference mon_sub3 = firebase_db.child("mon_sub3");
    DatabaseReference mon_sub4 = firebase_db.child("mon_sub4");
    DatabaseReference mon_sub5 = firebase_db.child("mon_sub5");
    DatabaseReference mon_sub6 = firebase_db.child("mon_sub6");
    DatabaseReference mon_sub7 = firebase_db.child("mon_sub7");

    DatabaseReference tue_sub1 = firebase_db.child("tue_sub1");
    DatabaseReference tue_sub2 = firebase_db.child("tue_sub2");
    DatabaseReference tue_sub3 = firebase_db.child("tue_sub3");
    DatabaseReference tue_sub4 = firebase_db.child("tue_sub4");
    DatabaseReference tue_sub5 = firebase_db.child("tue_sub5");
    DatabaseReference tue_sub6 = firebase_db.child("tue_sub6");
    DatabaseReference tue_sub7 = firebase_db.child("tue_sub7");

    DatabaseReference wed_sub1 = firebase_db.child("wed_sub1");
    DatabaseReference wed_sub2 = firebase_db.child("wed_sub2");
    DatabaseReference wed_sub3 = firebase_db.child("wed_sub3");
    DatabaseReference wed_sub4 = firebase_db.child("wed_sub4");
    DatabaseReference wed_sub5 = firebase_db.child("wed_sub5");
    DatabaseReference wed_sub6 = firebase_db.child("wed_sub6");
    DatabaseReference wed_sub7 = firebase_db.child("wed_sub7");

    DatabaseReference thu_sub1 = firebase_db.child("thu_sub1");
    DatabaseReference thu_sub2 = firebase_db.child("thu_sub2");
    DatabaseReference thu_sub3 = firebase_db.child("thu_sub3");
    DatabaseReference thu_sub4 = firebase_db.child("thu_sub4");
    DatabaseReference thu_sub5 = firebase_db.child("thu_sub5");
    DatabaseReference thu_sub6 = firebase_db.child("thu_sub6");
    DatabaseReference thu_sub7 = firebase_db.child("thu_sub7");

    DatabaseReference fri_sub1 = firebase_db.child("fri_sub1");
    DatabaseReference fri_sub2 = firebase_db.child("fri_sub2");
    DatabaseReference fri_sub3 = firebase_db.child("fri_sub3");
    DatabaseReference fri_sub4 = firebase_db.child("fri_sub4");
    DatabaseReference fri_sub5 = firebase_db.child("fri_sub5");
    DatabaseReference fri_sub6 = firebase_db.child("fri_sub6");
    DatabaseReference fri_sub7 = firebase_db.child("fri_sub7");

    String[] hours = {" 8.00 : 9.30   "," 9.45 : 11.15 ","11.45 : 13.15","13.30 : 15.00","15.15 : 16.45","17.00 : 18.30","18.45 : 20.15"};
    String[] mon_subjects, tue_subjects, wed_subjects, thu_subjects, fri_subjects;
    String[] subjects = {"Subject 1", "Subject 2", "Subject 3", "Subject 4","Subject 5", "Subject 6", "Subject 7"};
    public enum edited_day {mon,tue,wed,thu,fri};
    edited_day day;
    boolean accept_changes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mon_bt = findViewById(R.id.mon_button);
        tue_bt = findViewById(R.id.tue_button);
        wed_bt = findViewById(R.id.wed_button);
        thu_bt = findViewById(R.id.thu_button);
        fri_bt = findViewById(R.id.fri_button);
        edit_bt = findViewById(R.id.edit_button);

        day = edited_day.mon;
        accept_changes = false;

        mon_subjects = new String[hours.length];
        tue_subjects = new String[hours.length];
        wed_subjects = new String[hours.length];
        thu_subjects = new String[hours.length];
        fri_subjects = new String[hours.length];

        sub_hours_tv = new TextView[hours.length];
        sub_text_tv = new TextView[hours.length];
        sub_edit_text_et = new EditText[hours.length];

        sub_hours_tv[0] = findViewById(R.id.sub1_hours);
        sub_hours_tv[1] = findViewById(R.id.sub2_hours);
        sub_hours_tv[2] = findViewById(R.id.sub3_hours);
        sub_hours_tv[3] = findViewById(R.id.sub4_hours);
        sub_hours_tv[4] = findViewById(R.id.sub5_hours);
        sub_hours_tv[5] = findViewById(R.id.sub6_hours);
        sub_hours_tv[6] = findViewById(R.id.sub7_hours);

        sub_text_tv[0] = findViewById(R.id.sub1_name);
        sub_text_tv[1] = findViewById(R.id.sub2_name);
        sub_text_tv[2] = findViewById(R.id.sub3_name);
        sub_text_tv[3] = findViewById(R.id.sub4_name);
        sub_text_tv[4] = findViewById(R.id.sub5_name);
        sub_text_tv[5] = findViewById(R.id.sub6_name);
        sub_text_tv[6] = findViewById(R.id.sub7_name);

        sub_edit_text_et[0] = findViewById(R.id.sub1_edit);
        sub_edit_text_et[1] = findViewById(R.id.sub2_edit);
        sub_edit_text_et[2] = findViewById(R.id.sub3_edit);
        sub_edit_text_et[3] = findViewById(R.id.sub4_edit);
        sub_edit_text_et[4] = findViewById(R.id.sub5_edit);
        sub_edit_text_et[5] = findViewById(R.id.sub6_edit);
        sub_edit_text_et[6] = findViewById(R.id.sub7_edit);

        for (int i = 0; i < hours.length; i++){
            sub_edit_text_et[i].setEnabled(false);
            sub_edit_text_et[i].setVisibility(View.INVISIBLE);
            sub_hours_tv[i].setText(hours[i]);
            sub_text_tv[i].setText(subjects[i]);
            sub_edit_text_et[i].setText(subjects[i]);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        mon_sub1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mon_subjects[0] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mon_sub2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mon_subjects[1] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mon_sub3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mon_subjects[2] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mon_sub4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mon_subjects[3] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mon_sub5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mon_subjects[4] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mon_sub6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mon_subjects[5] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mon_sub7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mon_subjects[6] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tue_sub1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tue_subjects[0] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tue_sub2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tue_subjects[1] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tue_sub3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tue_subjects[2] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tue_sub4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tue_subjects[3] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tue_sub5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tue_subjects[4] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tue_sub6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tue_subjects[5] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tue_sub7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tue_subjects[6] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        wed_sub1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wed_subjects[0] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        wed_sub2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wed_subjects[1] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        wed_sub3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wed_subjects[2] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        wed_sub4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wed_subjects[3] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        wed_sub5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wed_subjects[4] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        wed_sub6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wed_subjects[5] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        wed_sub7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wed_subjects[6] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        thu_sub1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thu_subjects[0] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        thu_sub2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thu_subjects[1] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        thu_sub3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thu_subjects[2] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        thu_sub4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thu_subjects[3] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        thu_sub5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thu_subjects[4] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        thu_sub6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thu_subjects[5] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        thu_sub7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                thu_subjects[6] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fri_sub1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fri_subjects[0] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fri_sub2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fri_subjects[1] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fri_sub3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fri_subjects[2] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fri_sub4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fri_subjects[3] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fri_sub5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fri_subjects[4] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fri_sub6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fri_subjects[5] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fri_sub7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fri_subjects[6] = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mon_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < hours.length; i++) {
                    sub_text_tv[i].setText(mon_subjects[i]);
                    sub_edit_text_et[i].setText(mon_subjects[i]);
                }
               day = edited_day.mon;
            }
        });

        tue_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < hours.length; i++) {
                    sub_text_tv[i].setText(tue_subjects[i]);
                    sub_edit_text_et[i].setText(tue_subjects[i]);
                }
                day = edited_day.tue;
            }
        });

        wed_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < hours.length; i++) {
                    sub_text_tv[i].setText(wed_subjects[i]);
                    sub_edit_text_et[i].setText(wed_subjects[i]);
                }
                day = edited_day.wed;
            }
        });

        thu_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < hours.length; i++) {
                    sub_text_tv[i].setText(thu_subjects[i]);
                    sub_edit_text_et[i].setText(thu_subjects[i]);
                }
                day = edited_day.thu;
            }
        });

        fri_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < hours.length; i++) {
                    sub_text_tv[i].setText(fri_subjects[i]);
                    sub_edit_text_et[i].setText(fri_subjects[i]);
                }
                day = edited_day.fri;
            }
        });

        edit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accept_changes) {
                    accept_changes = false;
                    String edit = "Edit";
                    edit_bt.setText(edit);
                    for (int i = 0; i < hours.length; i++) {
                        sub_text_tv[i].setVisibility(View.VISIBLE);
                        sub_text_tv[i].setEnabled(true);
                        sub_edit_text_et[i].setVisibility(View.INVISIBLE);
                        sub_edit_text_et[i].setEnabled(false);
                    }
                    switch (day) {
                        case mon: {
                            mon_sub1.setValue(sub_edit_text_et[0].getText().toString());
                            mon_sub2.setValue(sub_edit_text_et[1].getText().toString());
                            mon_sub3.setValue(sub_edit_text_et[2].getText().toString());
                            mon_sub4.setValue(sub_edit_text_et[3].getText().toString());
                            mon_sub5.setValue(sub_edit_text_et[4].getText().toString());
                            mon_sub6.setValue(sub_edit_text_et[5].getText().toString());
                            mon_sub7.setValue(sub_edit_text_et[6].getText().toString());
                            break;
                        }
                        case tue: {
                            tue_sub1.setValue(sub_edit_text_et[0].getText().toString());
                            tue_sub2.setValue(sub_edit_text_et[1].getText().toString());
                            tue_sub3.setValue(sub_edit_text_et[2].getText().toString());
                            tue_sub4.setValue(sub_edit_text_et[3].getText().toString());
                            tue_sub5.setValue(sub_edit_text_et[4].getText().toString());
                            tue_sub6.setValue(sub_edit_text_et[5].getText().toString());
                            tue_sub7.setValue(sub_edit_text_et[6].getText().toString());
                            break;
                        }
                        case wed: {
                            wed_sub1.setValue(sub_edit_text_et[0].getText().toString());
                            wed_sub2.setValue(sub_edit_text_et[1].getText().toString());
                            wed_sub3.setValue(sub_edit_text_et[2].getText().toString());
                            wed_sub4.setValue(sub_edit_text_et[3].getText().toString());
                            wed_sub5.setValue(sub_edit_text_et[4].getText().toString());
                            wed_sub6.setValue(sub_edit_text_et[5].getText().toString());
                            wed_sub7.setValue(sub_edit_text_et[6].getText().toString());
                            break;
                        }
                        case thu: {
                            thu_sub1.setValue(sub_edit_text_et[0].getText().toString());
                            thu_sub2.setValue(sub_edit_text_et[1].getText().toString());
                            thu_sub3.setValue(sub_edit_text_et[2].getText().toString());
                            thu_sub4.setValue(sub_edit_text_et[3].getText().toString());
                            thu_sub5.setValue(sub_edit_text_et[4].getText().toString());
                            thu_sub6.setValue(sub_edit_text_et[5].getText().toString());
                            thu_sub7.setValue(sub_edit_text_et[6].getText().toString());
                            break;
                        }
                        case fri: {
                            fri_sub1.setValue(sub_edit_text_et[0].getText().toString());
                            fri_sub2.setValue(sub_edit_text_et[1].getText().toString());
                            fri_sub3.setValue(sub_edit_text_et[2].getText().toString());
                            fri_sub4.setValue(sub_edit_text_et[3].getText().toString());
                            fri_sub5.setValue(sub_edit_text_et[4].getText().toString());
                            fri_sub6.setValue(sub_edit_text_et[5].getText().toString());
                            fri_sub7.setValue(sub_edit_text_et[6].getText().toString());
                            break;
                        }
                        default:
                            break;
                    }

                } else {
                    accept_changes = true;
                    String accept = "Accept";
                    edit_bt.setText(accept);
                    for (int i = 0; i < hours.length; i++) {
                        sub_text_tv[i].setVisibility(View.INVISIBLE);
                        sub_text_tv[i].setEnabled(false);
                        sub_edit_text_et[i].setVisibility(View.VISIBLE);
                        sub_edit_text_et[i].setEnabled(true);
                    }
                }
            }
        });
    }
}