package com.example.bharath.collegeclubs;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView reg;
    EditText name,pwd;
    ImageButton login;
    Spinner role;
    String urole;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reg= (TextView) findViewById(R.id.reg);
        name= (EditText) findViewById(R.id.name);
        pwd= (EditText) findViewById(R.id.pwd);
        login= (ImageButton) findViewById(R.id.login);
        role= (Spinner) findViewById(R.id.role);

        role.setOnItemSelectedListener(this);

        List<String> role_list=new ArrayList<String>();
        role_list.add("Admin");
        role_list.add("Faculty");
        role_list.add("Student");

        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,role_list);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adp);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Reg1.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") || pwd.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Enter all fields",Toast.LENGTH_LONG).show();
                }else if(urole.equalsIgnoreCase("Admin")){
                    if(name.getText().toString().equalsIgnoreCase("Admin") && pwd.getText().toString().equalsIgnoreCase("Admin")){
                        Intent intent=new Intent(getApplicationContext(),AdminHome.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                    }
                }else if(urole.equalsIgnoreCase("Faculty")){
                    SQLiteDatabase db=openOrCreateDatabase("clubs", SQLiteDatabase.CREATE_IF_NECESSARY, null);
                    Cursor c=db.rawQuery("select * from faculty where fid='"+name.getText().toString()+"' and pwd='"+pwd.getText().toString()+"'",null);
                    c.moveToFirst();
                    if(c!=null){
                        if(c.getCount()>0){
                            Intent intent=new Intent(getApplicationContext(),FacHome.class);
                            intent.putExtra("fid",name.getText().toString());
                            intent.putExtra("fclub",c.getString(c.getColumnIndex("club")));
                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                    }
                }else if(urole.equalsIgnoreCase("Student")){
                    SQLiteDatabase db=openOrCreateDatabase("clubs", SQLiteDatabase.CREATE_IF_NECESSARY, null);
                    Cursor c=db.rawQuery("select * from student where sid='"+name.getText().toString()+"' and pwd='"+pwd.getText().toString()+"'",null);
                    c.moveToFirst();
                    if(c!=null){
                        if(c.getCount()>0){
                            Intent intent=new Intent(getApplicationContext(),StuHome.class);
                            intent.putExtra("sid",name.getText().toString());
                            intent.putExtra("sclub",c.getString(c.getColumnIndex("club")));
                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Invalid Credentials",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        urole=role.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
