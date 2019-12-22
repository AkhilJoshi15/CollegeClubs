package com.example.bharath.collegeclubs;

import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBconnector extends SQLiteOpenHelper {

	public DBconnector(Context context) {
		super(context, "clubs", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE IF NOT EXISTS student(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT,pwd TEXT,phn TEXT,sid text,branch text,club text,vote text,adminacc text,votecnt text)";
		db.execSQL(sql);
		String sql3 = "CREATE TABLE IF NOT EXISTS faculty(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT,pwd TEXT,phn TEXT,fid text,branch text,club text)";
		db.execSQL(sql3);
		String sql1 = "CREATE TABLE IF NOT EXISTS admin(id INTEGER PRIMARY KEY AUTOINCREMENT, role TEXT, pwd TEXT,vote text)";
		db.execSQL(sql1);
		String sql2 = "CREATE TABLE IF NOT EXISTS event(id INTEGER PRIMARY KEY AUTOINCREMENT, ename TEXT, edes TEXT,edate TEXT,eimg TEXT,club text)";
		db.execSQL(sql2);
		String sql4 = "CREATE TABLE IF NOT EXISTS vote(id INTEGER PRIMARY KEY AUTOINCREMENT, sname TEXT, sclub TEXT,vcnt INTEGER)";
		db.execSQL(sql4);
        String sql5 = "CREATE TABLE IF NOT EXISTS feedback(id INTEGER PRIMARY KEY AUTOINCREMENT, about TEXT, description TEXT,sid text,sclub text)";
        db.execSQL(sql5);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE student IF EXITS");
		db.execSQL("DROP TABLE faculty IF EXITS");
		db.execSQL("DROP TABLE admin IF EXITS");
		db.execSQL("DROP TABLE event IF EXITS");
		db.execSQL("DROP TABLE vote IF EXITS");
		db.execSQL("DROP TABLE feedback IF EXITS");
		onCreate(db);
	}

    public void admin_role(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into admin (role,pwd,vote) values ('Cricket','cricket','no')");
        db.execSQL("insert into admin (role,pwd,vote) values ('Football','football','no')");
        db.execSQL("insert into admin (role,pwd,vote) values ('Music','music','no')");
        db.execSQL("insert into admin (role,pwd,vote) values ('Debate','debate','no')");
        db.execSQL("insert into admin (role,pwd,vote) values ('Rangoli','rangoli','no')");
    }

	public void insert_faculty(HashMap<String, String> map){
		SQLiteDatabase sb = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("name",  map.get("name"));
		cv.put("email",map.get("email"));
		cv.put("pwd",map.get("pwd"));
		cv.put("phn", map.get("phn"));
		cv.put("fid", map.get("fid"));
		cv.put("branch", map.get("branch"));
		cv.put("club", map.get("club"));
		sb.insert("faculty", null, cv);
	}

    public void insert_vote(HashMap<String, String> map){
        SQLiteDatabase sb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("sname",  map.get("sname"));
        cv.put("sclub",map.get("sclub"));
        cv.put("vcnt",0);
        sb.insert("vote", null, cv);
    }

    public void insert_student(HashMap<String, String> map){
        SQLiteDatabase sb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",  map.get("name"));
        cv.put("email",map.get("email"));
        cv.put("pwd",map.get("pwd"));
        cv.put("phn", map.get("phn"));
        cv.put("sid", map.get("sid"));
        cv.put("branch", map.get("branch"));
        cv.put("club", map.get("club"));
        cv.put("vote", "no");
        cv.put("adminacc", "no");
        cv.put("votecnt", "0");
        sb.insert("student", null, cv);
    }

    public void insert_event(HashMap<String, String> map){
        SQLiteDatabase sb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ename",  map.get("ename"));
        cv.put("edes",map.get("edes"));
        cv.put("edate",map.get("edate"));
        cv.put("eimg", map.get("eimg"));
        cv.put("club", map.get("club"));
        sb.insert("event", null, cv);
    }

    public void insert_feedback(HashMap<String, String> map){
        SQLiteDatabase sb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("about",  map.get("about"));
        cv.put("description",map.get("description"));
        cv.put("sid",map.get("sid"));
        cv.put("sclub", map.get("sclub"));
        sb.insert("feedback", null, cv);
    }


}
