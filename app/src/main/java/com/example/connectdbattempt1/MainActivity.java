package com.example.connectdbattempt1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.example.connectdbattempt1.R;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button btnAddName;
    private Button btnDisplayNames;
    private TextView textViewNames;

    public Button btnHearing;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHearing = (Button) findViewById(R.id.btnHearing);
        editTextName = findViewById(R.id.editTextName);
        btnAddName = findViewById(R.id.btnAddName);
        btnDisplayNames = findViewById(R.id.btnDisplayNames);
        textViewNames = findViewById(R.id.textViewNames);

        // Create or open the database
        database = openOrCreateDatabase("mydatabase", MODE_PRIVATE, null);

        // Create the table if not exists
        database.execSQL("CREATE TABLE IF NOT EXISTS mytable (name TEXT)");

        btnAddName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNameToDatabase();
            }
        });

        btnDisplayNames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNames();
            }
        });

        btnHearing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HearingTest.class);
                startActivity(intent);
            }
        });
    }

    private void addNameToDatabase() {
        String name = editTextName.getText().toString().trim();

        if (!name.isEmpty()) {
            // Insert the name into the database
            ContentValues values = new ContentValues();
            values.put("name", name);
            database.insert("mytable", null, values);

            // Clear the EditText
            editTextName.getText().clear();
        }
    }

    private void displayNames() {
        // Query all names from the database
        Cursor cursor = database.query("mytable", new String[]{"name"}, null, null, null, null, null);

        StringBuilder names = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                // Append each name to the StringBuilder
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                names.append(name).append("\n");
            } while (cursor.moveToNext());
        }


        textViewNames.setText(names.toString());

        cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Closes the database when the activity is destroyed. This stops me seeing the database once the application stops running.
        // Leaving in for reference in case I need it but probably not for now.
        if (database != null && database.isOpen()) {
            //database.close();
        }
    }
}

