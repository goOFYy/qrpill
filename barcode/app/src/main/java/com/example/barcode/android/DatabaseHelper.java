package com.example.barcode.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 4/20/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "patients.db";
    public static final String TABLE_NAME = "med";
    public static final String ID = "id";
    public static final String bc = "bc";
    public static final String medname = "medname";
    public static final String hours = "hours";
    public static final String next = "next";



    public DatabaseHelper(Context context) {
        super(context,DB_NAME, null, 2);

       //SQLiteDatabase db = this.getWritableDatabase();
        //Log.d("med", context.getDatabasePath(this.DB_NAME).toString());

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, BC TEXT, MEDNAME TEXT, HOURS TEXT, NEXT TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean insertData ( String bc1, String medname1, String hours1, String next1){
        //String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(bc, bc1);
        contentValues.put(medname, medname1);
        contentValues.put(hours, hours1);
        contentValues.put(next, next1);
       long result= db.insert(TABLE_NAME,null, contentValues);
        if (result== -1){

            return false;}

        else
            return true;

    }
    public Cursor getdata (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * From med where id ="+id ,null);
        return null;
    }
    public Cursor gettime (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT next From med where id ="+id ,null);
        return res;
    }
    public Cursor getalldata (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * From med",null);

        return res;
    }
}
