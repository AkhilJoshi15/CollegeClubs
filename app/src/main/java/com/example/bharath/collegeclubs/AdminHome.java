package com.example.bharath.collegeclubs;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminHome extends AppCompatActivity {

    EditText pwd;
    Spinner role;
    Button submit;
    String urole;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        pwd= (EditText) findViewById(R.id.pwd);
        submit= (Button) findViewById(R.id.submit);
        role= (Spinner) findViewById(R.id.role);

        List<String> rlist=new ArrayList<String>();
        rlist.add("Cricket");
        rlist.add("Football");
        rlist.add("Music");
        rlist.add("Debate");
        rlist.add("Rangoli");

        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,rlist);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adp);

        role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                urole = role.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBconnector dbc=new DBconnector(getApplicationContext());
                dbc.admin_role();
                db=openOrCreateDatabase("clubs",SQLiteDatabase.CREATE_IF_NECESSARY,null);
                Cursor c=db.rawQuery("select * from admin where role='"+urole+"' and pwd='"+pwd.getText().toString()+"' ",null);
                c.moveToFirst();
                if(c!=null){
                    if(c.getCount()>0){
                        Intent intent=new Intent(getApplicationContext(),AdminView.class);
                        intent.putExtra("arole",urole);
                        Toast.makeText(getApplicationContext(),"urole:"+urole,Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}
