package com.example.bharath.collegeclubs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

public class Reg1 extends AppCompatActivity {

    TextView fac,stu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg1);

        fac= (TextView) findViewById(R.id.fac);
        fac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

        stu= (TextView) findViewById(R.id.stu);
        stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),StuRegister.class);
                startActivity(intent);
            }
        });

    }
}
