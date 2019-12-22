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

public class VoteCount extends ListActivity {

    String sclub;
    ArrayList<String> result=new ArrayList<String>();
    private SQLiteDatabase newDB;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sclub=getIntent().getStringExtra("sclub");

        openAndQueryDatabase();
        displayResultList();

    }

    private void displayResultList() {

        TextView tView=new TextView(this);
        tView.setText("Vote Count");
        getListView().addHeaderView(tView);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result));
        final ListView lv=getListView();
        lv.setTextFilterEnabled(true);


    }

    private void openAndQueryDatabase() {


        DBconnector dbHelper=new DBconnector(this.getApplicationContext());
        newDB=dbHelper.getWritableDatabase();
        Cursor c=newDB.rawQuery(" select * from student where club='"+sclub+"' and adminacc='yes' ",null);
        if(c!=null){

            if(c.moveToFirst()){

                do{

                    String sid=c.getString(c.getColumnIndex("sid"));
                    String votecnt=c.getString(c.getColumnIndex("votecnt"));
                    String content="Sid: "+sid+"\nCount: "+votecnt;
                    result.add(content);

                }while (c.moveToNext());

            }

        }

    }


}
