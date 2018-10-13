package com.example.calden.mobiledatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by calden on 30/8/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String db_name="user.db";
    public static final String tb_name="details";
    public static final String col1_name="id";
    public static final String col2_name="pass";

    public DatabaseHelper(Context context) {
        super(context, db_name, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table details(id text primary key,pass text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists details");
        onCreate(sqLiteDatabase);
    }

    public boolean insert(String user,String pass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",user);
        contentValues.put("pass",pass);
        long result= db.insert("details",null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("Select id from details",null);
        return res;
    }

}
