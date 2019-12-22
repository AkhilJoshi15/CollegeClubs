package com.example.bharath.collegeclubs;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class StuEvents extends ListActivity {

    String sid,sclub,content;
    ArrayList<String> result=new ArrayList<String>();
    SQLiteDatabase db;
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sid=getIntent().getStringExtra("sid");
        sclub=getIntent().getStringExtra("sclub");
        openAndQueryDatabase();
        displayResultList();

    }

    private void openAndQueryDatabase() {
        DBconnector dbcon=new DBconnector(this.getApplicationContext());
        db=dbcon.getWritableDatabase();
        Cursor c=db.rawQuery(" select * from event where club='"+sclub+"' ",null);
        if(c!=null){
            if(c.moveToFirst()){
                do{
                    String id=c.getString(c.getColumnIndex("id"));

                    result.add(id);

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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),StuEventDetails.class);
                String eid=(String)(lv.getItemAtPosition(position));
                intent.putExtra("eid",eid);
                i++;
                startActivity(intent);
            }
        });

    }


}
