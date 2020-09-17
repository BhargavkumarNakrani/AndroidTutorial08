package com.rku.tutorial08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Currency;

public class UserDetailsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView detail;
    String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        detail=findViewById(R.id.userdetails);
        databaseHelper=new DatabaseHelper(this);

        String userdetails="";
        Intent intent=getIntent();
        Username=intent.getStringExtra("Username");

        Cursor cursor=databaseHelper.getSingleUser(Username);
        cursor.moveToFirst();

        userdetails+="Firstname : "+cursor.getString(1);
        userdetails+="\nLastname : "+cursor.getString(2);
        userdetails+="\nUsername : "+cursor.getString(3);
        userdetails+="\nPassword : "+cursor.getString(4);
        userdetails+="\nBranch : "+cursor.getString(5);
        userdetails+="\nGender : "+cursor.getString(6);
        userdetails+="\nCity : " +cursor.getString(7);
        userdetails+="\nStatus : "+cursor.getString(8);

        detail.setText(userdetails);
    }
}