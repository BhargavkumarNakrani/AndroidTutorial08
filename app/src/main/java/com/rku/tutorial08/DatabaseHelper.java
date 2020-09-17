package com.rku.tutorial08;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE="alluser";
    public static final String TABLE="userdata";
    public static final String COL_1="id";
    public static final String COL_2="first_name";
    public static final String COL_3="last_name";
    public static final String COL_4="username";
    public static final String COL_5="password";
    public static final String COL_6="branch";
    public static final String COL_7="gender";
    public static final String COL_8="city";
    public static final String COL_9="status";

    public DatabaseHelper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table "+TABLE+"( "+COL_1+" integer primary key autoincrement, " +
                COL_2+" text," +
                COL_3+" text," +
                COL_4+" text unique,"+
                COL_5+" text ,"+
                COL_6+" text ,"+
                COL_7+" text ,"+
                COL_8+" text ,"+
                COL_9+" text "+
                ")";
        Log.i("sql",sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE);
        onCreate(db);
    }

    public boolean isUsernameTaken(String email_id) {
        String[] columns={COL_1};
        String selection= COL_4 +"=?";
        String[] selectArgs={email_id};
        Cursor cursor=getWritableDatabase().query(TABLE,columns,selection,selectArgs,null,null,null);
        return cursor.getCount()==1;
    }

    public boolean insertData(String first_name, String last_name, String email_id, String password, String branchStatus, String radio, String city, String check) {
        ContentValues values=new ContentValues();
        values.put(COL_2,first_name);
        values.put(COL_3,last_name);
        values.put(COL_4,email_id);
        values.put(COL_5,password);
        values.put(COL_6,branchStatus);
        values.put(COL_7,radio);
        values.put(COL_8,city);
        values.put(COL_9,check);

        long result=getWritableDatabase().insert(TABLE,null,values);
        return result!=-1;
    }

    public boolean validateData(String valusername, String valpassword) {
        String[] columns={COL_1};
        String selection= COL_4 + " = ?"+" AND " + COL_5 + " = ?";
        String[] selectArgs={valusername,valpassword};
        Cursor cursor= getReadableDatabase().query(TABLE,columns,selection,selectArgs,null,null,null);
        return cursor.getCount() == 1;
    }


    public Cursor getUsersList() {
        String[] columns={COL_4};
        return getWritableDatabase().query(TABLE,columns,null,null,null,null,null);
    }

    public Cursor getSingleUser(String username) {
        String selection=COL_4+"=?";
        String[] selectionArgs={username};
        return getWritableDatabase().query(TABLE,null,selection,selectionArgs,null,null,null);
    }
}
