package com.example.bharath.collegeclubs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashMap;

public class StuFeedback extends AppCompatActivity {

    String sid, sclub, votestatus;
    EditText name, email;
    Button reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_feedback);

        sid = getIntent().getStringExtra("sid");
        sclub = getIntent().getStringExtra("sclub");
        votestatus = getIntent().getStringExtra("votestatus");

        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        reg= (Button) findViewById(R.id.reg);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") || email.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_LONG).show();
                }else{

                    DBconnector db=new DBconnector(getApplicationContext());
                    HashMap<String,String> map=new HashMap<String,String >();
                    map.put("about",name.getText().toString());
                    map.put("description",email.getText().toString());
                    map.put("sid", sid);
                    map.put("sclub", sclub);

                    db.insert_feedback(map);

                    Intent intent=new Intent(getApplicationContext(),StuHome.class);
                    Toast.makeText(getApplicationContext(),"Upload Success",Toast.LENGTH_LONG).show();
                    intent.putExtra("sid", sid);
                    intent.putExtra("sclub", sclub);
                    intent.putExtra("votestatus", votestatus);
                    startActivity(intent);

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), StuHome.class);
        intent.putExtra("sid", sid);
        intent.putExtra("sclub", sclub);
        intent.putExtra("votestatus", votestatus);
        startActivity(intent);
    }
}
