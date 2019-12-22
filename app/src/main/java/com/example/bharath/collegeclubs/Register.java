package com.example.bharath.collegeclubs;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;
import android.content.ContentValues;
import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Register extends AppCompatActivity {

    ImageButton reg;
    EditText name,phn,pwd,email,sid;
    Spinner branch,club;
    String ubranch,uclub;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg= (ImageButton) findViewById(R.id.reg);
        branch= (Spinner) findViewById(R.id.branch);
        club= (Spinner) findViewById(R.id.club);
        name=(EditText)findViewById(R.id.name);
        phn=(EditText)findViewById(R.id.phn);
        pwd=(EditText)findViewById(R.id.pwd);
        email=(EditText)findViewById(R.id.email);
        sid=(EditText)findViewById(R.id.sid);


        List<String> bname=new ArrayList<String>();
        bname.add("CSE");
        bname.add("ECE");
        bname.add("EEE");
        bname.add("CE");
        bname.add("ME");
        bname.add("IT");

        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bname);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branch.setAdapter(adp);

        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ubranch = branch.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<String> rlist=new ArrayList<String>();
        rlist.add("Cricket");
        rlist.add("Football");
        rlist.add("Music");
        rlist.add("Debate");
        rlist.add("Rangoli");

        ArrayAdapter<String> adp2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,rlist);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        club.setAdapter(adp2);

        club.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                uclub = club.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") || phn.getText().toString().equals("") || pwd.getText().toString().equals("") || email.getText().toString().equals("")
                        || sid.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Fill all fields",Toast.LENGTH_LONG).show();
                }else{

                    DBconnector db=new DBconnector(getApplicationContext());
                    HashMap<String,String> map=new HashMap<String,String >();
                    map.put("name",name.getText().toString());
                    map.put("email",email.getText().toString());
                    map.put("pwd",pwd.getText().toString());
                    map.put("phn",phn.getText().toString());
                    map.put("fid", sid.getText().toString());
                    map.put("branch", ubranch);
                    map.put("club", uclub);

                    db.insert_faculty(map);

                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    Toast.makeText(getApplicationContext(),"Registration Success\nBranch:"+ubranch+"\nClub:"+uclub,Toast.LENGTH_LONG).show();
                    startActivity(intent);

                }
            }
        });


    }

}
