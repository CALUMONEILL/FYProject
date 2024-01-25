package com.example.connectdbattempt1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HearingTestResults extends AppCompatActivity {

    private com.example.connectdbattempt1.ResponsesDBHelper dbHelper;

    Button btnHome;
    TextView txtFeedback1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing_test_results);

        dbHelper = new ResponsesDBHelper(this);

        btnHome = findViewById(R.id.btnHome);
        txtFeedback1 = findViewById(R.id.txtFeedback1);

        // Retrieved from ChatGPT
        // Adapted and implemented code from this video
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        int yesCount = countOccurrences("Yes");
        int noCount = countOccurrences("No");

        if (yesCount == 6) {
            txtFeedback1.setText("Your results suggest that it is likely that you do not have any hearing issues.");
        } else if (yesCount == 0) {
            txtFeedback1.setText("Your results suggest that you may have some hearing issues.");
        } else if (yesCount > 3 && yesCount <= 6) {
            txtFeedback1.setText("Your results suggest that it is unlikely that you have any hearing issues.");
        } else if (yesCount > 0 && yesCount <= 3) {
            txtFeedback1.setText("Your results suggest that you may have some hearing issues.");
        }

        Toast.makeText(this, "Yes count: " + yesCount + ", No count: " + noCount, Toast.LENGTH_SHORT).show();
    }

    private int countOccurrences (String response){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {dbHelper.response};
        String selection = dbHelper.response + " = ?";
        String[] selectionArgs = {response};

        Cursor cursor = db.query(
                dbHelper.responses,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }
}



















// https://chat.openai.com/share/24823636-4276-419c-912f-7f192c4e5710
// https://www.youtube.com/watch?v=dm-jan0YORg
// https://chat.openai.com/share/754ce738-be72-44a9-94ff-625674e3d743
// https://developer.android.com/reference/android/widget/ListView#setDivider(android.graphics.drawable.Drawable)