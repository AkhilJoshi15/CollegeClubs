package com.example.bharath.collegeclubs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

public class AdminView extends AppCompatActivity {

    TextView student,upload,events,logout,feedback;
    String arole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);

        arole=getIntent().getStringExtra("arole");

        logout= (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        feedback= (TextView) findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminFeedback.class);
                intent.putExtra("arole",arole);
                startActivity(intent);
            }
        });

        upload= (TextView) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AdminUpload.class);
                intent.putExtra("arole",arole);
                startActivity(intent);
            }
        });

        events= (TextView) findViewById(R.id.events);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AdminEvents.class);
                intent.putExtra("arole",arole);
                startActivity(intent);
            }
        });

        student= (TextView) findViewById(R.id.student);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AdminStu.class);
                intent.putExtra("arole",arole);
                startActivity(intent);
            }
        });

    }
}
