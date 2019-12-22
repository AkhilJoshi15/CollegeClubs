package com.example.bharath.collegeclubs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FacHome extends AppCompatActivity {

    TextView selstu,logout,cnt;
    String fid,fclub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_home);

        fid=getIntent().getStringExtra("fid");
        fclub=getIntent().getStringExtra("fclub");

        logout= (TextView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        cnt= (TextView) findViewById(R.id.cnt);
        cnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VoteCount.class);
                intent.putExtra("fid",fid);
                intent.putExtra("fclub",fclub);
                startActivity(intent);
            }
        });

        selstu= (TextView) findViewById(R.id.selstu);
        selstu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FacSelStu1.class);
                intent.putExtra("fid", fid);
                intent.putExtra("fclub", fclub);
                startActivity(intent);
            }
        });

    }
}
