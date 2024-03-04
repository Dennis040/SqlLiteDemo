package com.example.sqllitedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sqllitedemo.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    public String DATABASE_NAME = "demosqlite.db";
    public String DB_SUFFIX_PATH = "/databases/";
    ListView lvUser;
    Button btnThem;
    ArrayAdapter<User> arrayAdapterUser;
    static SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processCopy();
        addControls();
        loadData();
    }

    private void loadData() {
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("select * from tbUser", null);

        while (cursor.moveToNext()) {
            String ma = cursor.getString(0);
            String ten = cursor.getString(1);
            String phone = cursor.getString(2);

            User user = new User(ma, ten, phone);
            arrayAdapterUser.add(user);
        }
        cursor.close();
    }

    private void addControls() {
        btnThem = findViewById(R.id.btnThem);
        lvUser = findViewById(R.id.lvUser);
        arrayAdapterUser = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvUser.setAdapter(arrayAdapterUser);
    }

    @NonNull
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_SUFFIX_PATH + DATABASE_NAME;
    }

    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                copyDatabaseFromAsset();
                Toast.makeText(this,
                        "Copying sucess from Assets folder",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void copyDatabaseFromAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir + DB_SUFFIX_PATH);
            if (!f.exists())
                f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("Error", e.toString());
        }
    }

}