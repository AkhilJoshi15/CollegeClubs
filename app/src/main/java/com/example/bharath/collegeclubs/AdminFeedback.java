package com.example.bharath.collegeclubs;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminFeedback extends ListActivity {

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
        Cursor c=db.rawQuery(" select * from feedback where sclub='"+arole+"' ",null);
        if(c!=null){
            if(c.moveToFirst()){
                do{
                    String about=c.getString(c.getColumnIndex("about"));
                    String description=c.getString(c.getColumnIndex("description"));
                    String sid=c.getString(c.getColumnIndex("sid"));

                    content="About: "+about+"\nDescription: "+description+"\nStudent ID: "+sid;
                    result.add(content);

                }while(c.moveToNext());
            }
        }
    }

    private void displayResultList() {
        TextView tv=new TextView(this);
        tv.setText("Feedback Details");
        getListView().addHeaderView(tv);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result));
        final ListView lv=getListView();
        lv.setTextFilterEnabled(true);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), AdminView.class);
        intent.putExtra("arole", arole);
        startActivity(intent);
    }
}
