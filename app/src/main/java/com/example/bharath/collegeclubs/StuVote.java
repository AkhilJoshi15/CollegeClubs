package com.example.bharath.collegeclubs;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StuVote extends AppCompatActivity {

    String sid,sclub,svname,vcnt;
    Spinner vname;
    Button vote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_vote);

        sid=getIntent().getStringExtra("sid");
        sclub=getIntent().getStringExtra("sclub");

        vname= (Spinner) findViewById(R.id.vname);
        List<String> list=new ArrayList<String>();
        DBconnector dbcon=new DBconnector(getApplicationContext());
        SQLiteDatabase sqldb=dbcon.getWritableDatabase();
        final Cursor cursor=sqldb.rawQuery("select * from student where club='"+sclub+"' and adminacc='yes' ",null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    list.add(cursor.getString(cursor.getColumnIndex("sid")));
                }while(cursor.moveToNext());
            }
        }
        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vname.setAdapter(adp);
        vname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                svname = vname.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vote= (Button) findViewById(R.id.vote);
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBconnector dbcon1=new DBconnector(getApplicationContext());
                SQLiteDatabase sqldb1=dbcon1.getWritableDatabase();
                Cursor cursor1=sqldb1.rawQuery(" select * from student where sid='"+svname+"' ",null);
                if(cursor1!=null){
                    if(cursor1.moveToFirst()){
                        do{
                            vcnt=cursor1.getString(cursor1.getColumnIndex("votecnt"));
                        }while(cursor1.moveToNext());
                    }
                }
                int vcnt1= Integer.parseInt(vcnt);
                vcnt1++;

                SQLiteDatabase newDB2=openOrCreateDatabase("clubs", SQLiteDatabase.CREATE_IF_NECESSARY, null);

                ContentValues cv=new ContentValues();
                ContentValues cv1=new ContentValues();
                String status="yes";
                cv.put("vote", status);
                cv1.put("votecnt", vcnt1);

                String[] args=new String[]{sid};
                newDB2.update("student", cv, "sid=?", args);

                String[] args1=new String[]{svname};
                newDB2.update("student", cv1, "sid=?", args1);

                Intent anotherActivityIntent = new Intent(getApplicationContext(), StuHome.class);
                Toast.makeText(getApplicationContext(), "Voted to "+svname, Toast.LENGTH_LONG).show();
                anotherActivityIntent.putExtra("sclub", sclub);
                anotherActivityIntent.putExtra("sid", sid);
                startActivity(anotherActivityIntent);
            }
        });

    }
}
