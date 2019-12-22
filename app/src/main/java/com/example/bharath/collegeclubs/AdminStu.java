package com.example.bharath.collegeclubs;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminStu extends ListActivity {

    ArrayList<String> result=new ArrayList<String>();
    SQLiteDatabase db;
    String content,arole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arole=getIntent().getStringExtra("arole");
        openAndQueryDatabase();
        displayResultList();

    }

    private void openAndQueryDatabase() {
        DBconnector dbcon=new DBconnector(this.getApplicationContext());
        db=dbcon.getWritableDatabase();
        Cursor c=db.rawQuery(" select * from student where club='"+arole+"' ",null);
        if(c!=null){
            if(c.moveToFirst()){
                do{
                    String name=c.getString(c.getColumnIndex("name"));
                    String email=c.getString(c.getColumnIndex("email"));
                    String phn=c.getString(c.getColumnIndex("phn"));
                    String sid=c.getString(c.getColumnIndex("sid"));
                    String branch=c.getString(c.getColumnIndex("branch"));
                    String club=c.getString(c.getColumnIndex("club"));

                    content="Name: "+name+"\nEmail ID: "+email+"\nPhone: "+phn+"\nID No.: "+sid+"\nBranch: "+branch;
                    result.add(content);

                }while(c.moveToNext());
            }
        }
    }

    private void displayResultList() {
        TextView tv=new TextView(this);
        tv.setText("Student Details");
        getListView().addHeaderView(tv);
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,result));
        final ListView lv=getListView();
        lv.setTextFilterEnabled(true);
    }
}
