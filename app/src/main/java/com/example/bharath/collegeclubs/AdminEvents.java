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

public class AdminEvents extends ListActivity {

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
        Cursor c=db.rawQuery(" select * from event where club='"+arole+"' ",null);
        if(c!=null){
            if(c.moveToFirst()){
                do{
                    String name=c.getString(c.getColumnIndex("ename"));
                    String email=c.getString(c.getColumnIndex("edes"));
                    String phn=c.getString(c.getColumnIndex("edate"));

                    content="Event Name: "+name+"\nDescription: "+email+"\nDate: "+phn;
                    result.add(content);

                }while(c.moveToNext());
            }
        }
    }

    private void displayResultList() {
        TextView tv=new TextView(this);
        tv.setText("Event Details");
        getListView().addHeaderView(tv);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result));
        final ListView lv=getListView();
        lv.setTextFilterEnabled(true);
    }


}
