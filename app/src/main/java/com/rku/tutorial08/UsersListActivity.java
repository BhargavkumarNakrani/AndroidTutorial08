package com.rku.tutorial08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UsersListActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String username;
    TextView txtWelcome;
    AlertDialog.Builder builder;
    ArrayList<String>  userlist=new ArrayList<>();
    ListView listData;
    ArrayAdapter<String> adapter;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        databaseHelper =new DatabaseHelper(this);

        Cursor cursor=databaseHelper.getUsersList();
        if (cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                userlist.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }

        listData=findViewById(R.id.listData);
        adapter=new ArrayAdapter<String>(this,R.layout.list_item_template,userlist);
        listData.setAdapter(adapter);

        listData.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UsersListActivity.this,UserDetailsActivity.class);
                intent.putExtra("Username",((TextView)view).getText().toString());
                startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences("loginCheck", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation Dialog")
                .setCancelable(false)
                .setMessage("Do You Want To Logout?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.remove("isLogin");
                        editor.commit();
                        Intent intent = new Intent(UsersListActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Stay Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                AlertDialog dialog=builder.create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}