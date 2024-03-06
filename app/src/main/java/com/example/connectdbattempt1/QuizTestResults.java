package com.example.connectdbattempt1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class QuizTestResults extends AppCompatActivity {

    private ResponsesDBHelper dbHelper;

    FloatingActionButton btnHome;
    TextView txtFeedback1;
    Button btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_test_results);

        dbHelper = new ResponsesDBHelper(this);

        readDataFromTable();



        btnHome = findViewById(R.id.btnHome);
        txtFeedback1 = findViewById(R.id.txtFeedback);
        btnMore = findViewById(R.id.btnMore);

        // Adapted and implemented code from this video: https://www.youtube.com/watch?v=dm-jan0YORg
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home3.class);
                startActivity(intent);
            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Stats.class);
                startActivity(intent);
            }
        });


    }

    /*private void populateStringMappings() {
        // Populate the map with unique strings corresponding to each entry
        stringMappings.put("Team", "UniqueString1");
        stringMappings.put("Chat", "UniqueString2");
        stringMappings.put("Gaze", "UniqueString3");
        stringMappings.put("Deep", "UniqueString4");
        stringMappings.put("Thin", "UniqueString5");
        stringMappings.put("Vine", "UniqueString6");
        // Add more mappings as needed
    }*/

    private void readDataFromTable() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query the database
        Cursor cursor = db.query(
                "answers",                          // The table to query
                new String[]{"answer"},          // The columns to return
                null,                                      // The columns for the WHERE clause
                null,                                      // The values for the WHERE clause
                null,                                      // don't group the rows
                null,                                      // don't filter by row groups
                null                                      // The sort order
        );

        // Iterate over the cursor
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Get the response from the cursor
                String response = cursor.getString(cursor.getColumnIndexOrThrow("answer"));

                // Compare the response with your target string
                if (response.equals("Team")) {
                    // Perform the action you want if the response matches the target string
                    // For example, show a toast message
                    Toast.makeText(this, "Response matches the target string!", Toast.LENGTH_SHORT).show();
                }
            }
            cursor.close();
        }

        // Close the database connection
        db.close();
    }
}



















// https://chat.openai.com/share/24823636-4276-419c-912f-7f192c4e5710
// https://www.youtube.com/watch?v=dm-jan0YORg
// https://chat.openai.com/share/754ce738-be72-44a9-94ff-625674e3d743
// https://developer.android.com/reference/android/widget/ListView#setDivider(android.graphics.drawable.Drawable)