package com.example.connectdbattempt1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class userData extends AppCompatActivity {

    private ResponsesDBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    Button btnHome;
    ListView lstResults;
    ListView lstDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);


        dbHelper = new ResponsesDBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        btnHome = findViewById(R.id.btnHome);
        lstResults = findViewById(R.id.lstResults);
        lstDate = findViewById(R.id.lstDate);

        // Adapted and implemented code from this video: https://www.youtube.com/watch?v=dm-jan0YORg
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        String query = "SELECT result FROM results";

        //Adapted this code from ChatGPT
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery(query, null);

            ArrayList<String> resultsList = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {

                    String result = cursor.getString(cursor.getColumnIndexOrThrow("result"));

                    resultsList.add(result);
                    // Adapted from Android Studio website
                    lstResults.setDivider(null);

                } while (cursor.moveToNext());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultsList);

            lstResults.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        String query2 = "SELECT date FROM results";

        //Adapted this code from ChatGPT
        Cursor cursor2 = null;
        try {
            cursor2 = sqLiteDatabase.rawQuery(query2, null);

            ArrayList<String> dateList = new ArrayList<>();

            if (cursor2.moveToFirst()) {
                do {

                    String dates = cursor2.getString(cursor2.getColumnIndexOrThrow("date"));

                    dateList.add(dates);
                    // Adapted from Android Studio website
                    lstDate.setDivider(null);

                } while (cursor2.moveToNext());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dateList);

            lstDate.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor2 != null) {
                cursor2.close();
            }
        }

    }
}





















// https://chat.openai.com/share/24823636-4276-419c-912f-7f192c4e5710
// https://www.youtube.com/watch?v=dm-jan0YORg
// https://chat.openai.com/share/754ce738-be72-44a9-94ff-625674e3d743
// https://developer.android.com/reference/android/widget/ListView#setDivider(android.graphics.drawable.Drawable)