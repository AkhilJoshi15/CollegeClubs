package com.example.bharath.collegeclubs;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class StuEventDetails extends AppCompatActivity {

    TextView ename,edes,edate;
    ImageView eimg;
    String eid;
    SQLiteDatabase newDB;
    String sename,sedes,sedate,seimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_event_details);

        eid=getIntent().getStringExtra("eid");

        ename= (TextView) findViewById(R.id.ename);
        edes= (TextView) findViewById(R.id.edes);
        edate= (TextView) findViewById(R.id.edate);
        eimg= (ImageView) findViewById(R.id.eimg);

        openAndQueryDatabase();
        showDetails();

    }

    private void showDetails() {

        ename.setText(sename);
        edes.setText(sedes);
        edate.setText(sedate);
        eimg.setImageURI(Uri.parse(seimg));

    }

    private void openAndQueryDatabase() {

        DBconnector dbHelper=new DBconnector(this.getApplicationContext());
        newDB=dbHelper.getWritableDatabase();
        Cursor c=newDB.rawQuery(" select * from event where id='"+eid+"' ",null);
        if(c!=null){

            if(c.moveToFirst()){

                do{

                    sename=c.getString(c.getColumnIndex("ename"));
                    sedes=c.getString(c.getColumnIndex("edes"));
                    sedate=c.getString(c.getColumnIndex("edate"));
                    seimg=c.getString(c.getColumnIndex("eimg"));

                }while(c.moveToNext());

            }

        }

    }


}
