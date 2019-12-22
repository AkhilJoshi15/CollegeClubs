package com.example.bharath.collegeclubs;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StuHome extends AppCompatActivity {

    TextView events, vote, logout, cnt, feedback;
    String sid, sclub, votestatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_home);

        sid = getIntent().getStringExtra("sid");
        sclub = getIntent().getStringExtra("sclub");

        logout = (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        feedback = (TextView) findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StuFeedback.class);
                intent.putExtra("sid", sid);
                intent.putExtra("sclub", sclub);
                intent.putExtra("votestatus", votestatus);
                startActivity(intent);
            }
        });

        events = (TextView) findViewById(R.id.events);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StuEvents.class);
                intent.putExtra("sid", sid);
                intent.putExtra("sclub", sclub);
                intent.putExtra("votestatus", votestatus);
                startActivity(intent);
            }
        });

        vote = (TextView) findViewById(R.id.vote);

        DBconnector dbcon = new DBconnector(getApplicationContext());
        SQLiteDatabase sqldb = dbcon.getWritableDatabase();
        Cursor cursor = sqldb.rawQuery(" select * from student where sid='" + sid + "' ", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    votestatus = cursor.getString(cursor.getColumnIndex("vote"));
                } while (cursor.moveToNext());
            }
        }

        if (votestatus.equals("no")) {
            vote.setEnabled(true);
            vote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), StuVote.class);
                    intent.putExtra("sid", sid);
                    intent.putExtra("sclub", sclub);
                    startActivity(intent);
                }
            });
        } else if (votestatus.equals("yes")) {
            vote.setEnabled(false);
        }

    }
}
