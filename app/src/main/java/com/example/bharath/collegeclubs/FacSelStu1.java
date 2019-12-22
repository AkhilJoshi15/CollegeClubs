package com.example.bharath.collegeclubs;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.content.DialogInterface;
import java.util.ArrayList;
import android.content.Intent;
import android.widget.Toast;


public class FacSelStu1 extends ListActivity {

    String fid,fclub,sid,uid;
    int i=0;
    ArrayList<String> result=new ArrayList<String>();
    private SQLiteDatabase newDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fid=getIntent().getStringExtra("fid");
        fclub=getIntent().getStringExtra("fclub");

        openAndQueryDatabase();
        displayResultList();

    }

    private void displayResultList() {

        TextView tView=new TextView(this);
        tView.setText("Approve Students");
        getListView().addHeaderView(tView);
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,result));
        final ListView lv=getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

                uid=(String)(lv.getItemAtPosition(position));

                AlertDialog.Builder dialog = new AlertDialog.Builder(FacSelStu1.this);
                dialog.setCancelable(false);
                dialog.setTitle(getResources().getString(R.string.app_name));
                dialog.setMessage("Select student as nominee?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        selectStudent(uid);

                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });

    }

    private void selectStudent(String sid) {

                        DBconnector dbHelper1 = new DBconnector(FacSelStu1.this);
                        SQLiteDatabase newDB1 = dbHelper1.getWritableDatabase();
                        Cursor c = newDB1.rawQuery(" select count(*) as cnt from student where club='" + fclub + "' and adminacc='yes' ", null);
                        if (c != null) {
                            if (c.moveToFirst()) {
                                String cnt = c.getString(c.getColumnIndex("cnt"));
                                if (Integer.parseInt(cnt) < 5) {

                                    SQLiteDatabase newDB2=openOrCreateDatabase("clubs", SQLiteDatabase.CREATE_IF_NECESSARY, null);

                                    ContentValues cv=new ContentValues();
                                    String status="yes";
                                    cv.put("adminacc", status);

                                    String[] args=new String[]{sid};
                                    newDB2.update("student", cv, "sid=?", args);

                                    Intent anotherActivityIntent = new Intent(getApplicationContext(), FacSelStu1.class);
                                    Toast.makeText(getApplicationContext(), "Student selected as nominee", Toast.LENGTH_LONG).show();
                                    anotherActivityIntent.putExtra("fclub", fclub);
                                    anotherActivityIntent.putExtra("fid", fid);
                                    startActivity(anotherActivityIntent);
                                } else {
                                    Toast.makeText(FacSelStu1.this, "Maximum limit has reached.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

    }

    private void openAndQueryDatabase() {


        DBconnector dbHelper=new DBconnector(this.getApplicationContext());
        newDB=dbHelper.getWritableDatabase();
        Cursor c=newDB.rawQuery(" select * from student where club='"+fclub+"' and adminacc='no' ",null);
        if(c!=null){
            if(c.moveToFirst()){
                do{
                    sid=c.getString(c.getColumnIndex("sid"));
                    result.add(sid);
                }while (c.moveToNext());
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent anotherActivityIntent = new Intent(getApplicationContext(), FacHome.class);
        anotherActivityIntent.putExtra("fclub", fclub);
        anotherActivityIntent.putExtra("fid", fid);
        startActivity(anotherActivityIntent);
    }
}
