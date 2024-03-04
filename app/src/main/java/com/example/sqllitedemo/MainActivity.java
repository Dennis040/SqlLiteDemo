package com.example.sqllitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sqllitedemo.model.User;

import  java.io.PrintWriter;
import  java.io.StringWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import  android.content.Context;
import  android.content.Intent;

public class MainActivity extends AppCompatActivity {
public  String DATABASE_NAME="demosqlite.db";
public  String DB_SUFFIX_PATH = "/database/";
ListView lvUser;
Button btnThem;
ArrayAdapter<User> arrayAdapterUser;
SQLiteDatabase database = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        proccessCopy();
       addControls();
       loadData();
    }

    private void loadData() {
        database = openOrCreateDatabase("demosqlite.db", MODE_PRIVATE,null);
        Cursor cursor = database.query("tbUser",null,null,null,null,null,null);
        //Cursor cursor = database.rawQuery("select * from tbUser",null);
        //while (cursor.moveToNext());
        {
            //int ma = Integer.parseInt(cursor.getString(0));
            //String ten = cursor.getString(1);
            //String phone = cursor.getString(2);
            //User u = new User(ma,ten,phone);
            //arrayAdapterUser.add(u);
        }
        cursor.close();
    }

    private void addControls() {
        btnThem = findViewById(R.id.btnThem);
        lvUser = findViewById(R.id.lvUser);
        arrayAdapterUser = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
        lvUser.setAdapter(arrayAdapterUser);
    }

    private  String getDatabasePath()
{
    return getApplicationInfo().dataDir+DB_SUFFIX_PATH+DATABASE_NAME;
}
    private void proccessCopy() {
        try{
                File file = getDatabasePath(DATABASE_NAME);
                if(!file.exists())
                {
                    copyDatabaseFromAsset();
                    Toast.makeText(this,"Copy Database Successful",Toast.LENGTH_LONG).show();
                }
        }
        catch (Exception ex){
            Toast.makeText(this,"Copy Database Fail",Toast.LENGTH_LONG).show();
        }
    }

    private void copyDatabaseFromAsset() {
        try {
            InputStream inputFile = getAssets().open(DATABASE_NAME);
            String outputFileName = getDatabasePath();
            File file = new File(getApplicationInfo().dataDir+DB_SUFFIX_PATH);
            if(!file.exists())
            {
                file.mkdir();
                OutputStream outputfile = new FileOutputStream(outputFileName);
                byte [] buffer= new byte[1024];
                int length;
                while ((length=inputFile.read(buffer))>0)
                {
                    outputfile.write(buffer,0,length);
                }
                outputfile.flush();
                outputfile.close();
                inputFile.close();
            }
        }
        catch (Exception ex){
            Log.e("Error",ex.toString());
        }
    }
    
}