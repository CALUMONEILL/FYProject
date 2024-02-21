package com.example.connectdbattempt1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Results extends AppCompatActivity {

    Button btnHome;
    ListView ratingsListView;

    private com.example.connectdbattempt1.HearingTestDatabaseHelper HearingTestDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ratingsListView = findViewById(R.id.ratingsListView);
        HearingTestDatabaseHelper = new HearingTestDatabaseHelper(this);

        btnHome = findViewById(R.id.btnHomeScreen);
        // Retrieved from ChatGPT
        // Adapted and implemented code from this video
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });


        SQLiteDatabase db = HearingTestDatabaseHelper.getReadableDatabase();



        String query = "SELECT rating FROM ratings";

        //Adapted this code from ChatGPT
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);

            ArrayList<String> ratingsList = new ArrayList<>();

            if (cursor.moveToFirst()) {
                do {

                    String rating = cursor.getString(cursor.getColumnIndexOrThrow("rating"));

                    // Logging for debugging purposes (not ChatGPT)
                    Log.d("RatingLog", "Rating: " + rating);

                    ratingsList.add(rating + "/5");
                    // Adapted from Android Studio website
                    ratingsListView.setDivider(null);

                } while (cursor.moveToNext());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ratingsList);

            ratingsListView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                //cursor.close();
            }
        }
    }
}




// https://chat.openai.com/share/24823636-4276-419c-912f-7f192c4e5710
// https://www.youtube.com/watch?v=dm-jan0YORg
// https://chat.openai.com/share/754ce738-be72-44a9-94ff-625674e3d743
// https://developer.android.com/reference/android/widget/ListView#setDivider(android.graphics.drawable.Drawable)