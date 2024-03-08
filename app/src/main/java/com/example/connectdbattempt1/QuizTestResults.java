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
    private SQLiteDatabase sqLiteDatabase;

    FloatingActionButton btnHome;
    TextView txtFeedback1;
    TextView txtResults;
    Button btnMore;

    int correct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_test_results);

        dbHelper = new ResponsesDBHelper(this);


        readDataFromTable();

        insertQuizResult(correct);

        btnHome = findViewById(R.id.btnHome);
        txtFeedback1 = findViewById(R.id.txtFeedback1);
        txtResults = findViewById(R.id.txtResult);
        btnMore = findViewById(R.id.btnMore);
        btnMore.setText("Back to Assessments");

        if (correct == 6) {
            txtFeedback1.setText("Your results suggest that you do not struggle with basic speech recognition.");
        } else if (correct == 0) {
            txtFeedback1.setText("Your results suggest that you struggle with basic speech recognition.");
        } else if (correct > 3 && correct <= 6) {
            txtFeedback1.setText("Your results suggest that it is unlikely that you struggle with basic speech recognition.");
        } else if (correct > 0 && correct <= 3) {
            txtFeedback1.setText("Your results suggest that you may struggle with basic speech recognition.");
        }

        String correctString = String.valueOf(correct);

        txtResults.setText(correctString + "/6");

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
                Intent intent = new Intent(getApplicationContext(), Assessments.class);
                startActivity(intent);
            }
        });

    }

    // Learned from previous code that used information from ChatGPT
    private void insertQuizResult(int correct) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("result", correct);

        long newRowId = sqLiteDatabase.insert("quiz", null, values);

        if (newRowId != -1) {
            Log.d("DATABASE", "Quiz result inserted successfully");
        } else {
            Log.d("DATABASE", "Failed to insert quiz result");
        }
    }

    // Adapted from ChatGPT https://chat.openai.com/share/4d10053b-bc5c-4238-82f9-b6e91814ff0c
    private void readDataFromTable() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query the database
        Cursor cursor = db.query(
                "answers",
                new String[]{"answer"},
                null,
                null,
                null,
                null,
                null
        );

        // Iterate over the cursor
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // Get the response from the cursor
                String response = cursor.getString(cursor.getColumnIndexOrThrow("answer"));


                if (response.equals("Team")) {
                    correct++;
                }

                if (response.equals("Chat")) {
                    correct++;
                }

                if (response.equals("Gaze")) {
                    correct++;
                }

                if (response.equals("Deep")) {
                    correct++;
                }

                if (response.equals("Thin")) {
                    correct++;
                }

                if (response.equals("Vine")) {
                    correct++;
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