package com.example.connectdbattempt1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

        btnHome = findViewById(R.id.btnHome);
        // Retrieved from ChatGPT
        // Adapted and implemented code from this video
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
            }
        });

        // Assuming you have a ListView in your layout with the id "ratingsListView"
        //ListView ratingsListView = findViewById(R.id.ratingsListView);

        // Create or open the database
        //SQLiteDatabase db = openOrCreateDatabase("dbHearphones", MODE_PRIVATE, null);
        SQLiteDatabase db = HearingTestDatabaseHelper.getReadableDatabase();


        // Assuming your table has a column named "rating"
        String query = "SELECT rating FROM ratings";

        // Execute the query
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);

            // Create a list to store the ratings
            ArrayList<String> ratingsList = new ArrayList<>();

            // Iterate through the cursor and add ratings to the list
            if (cursor.moveToFirst()) {
                do {
                    // Assuming "rating" is the column index, adjust if needed
                    String rating = cursor.getString(cursor.getColumnIndexOrThrow("rating"));

                    Log.d("RatingLog", "Rating: " + rating);

                    ratingsList.add(rating + "/5");
                    ratingsListView.setDivider(null);
                    //ratingsListView.setLayoutParams(new ViewGroup.LayoutParams(
                    //        ViewGroup.LayoutParams.MATCH_PARENT,
                       //     51));
                } while (cursor.moveToNext());
            }

            // Create an ArrayAdapter to display the ratings in the ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ratingsList);

            // Set the adapter to the ListView
            ratingsListView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the cursor and database in the finally block
            if (cursor != null) {
                //cursor.close();
            }
           // db.close();
        }
    }
}




// https://chat.openai.com/share/24823636-4276-419c-912f-7f192c4e5710
// https://www.youtube.com/watch?v=dm-jan0YORg