package com.example.connectdbattempt1;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class Survey extends AppCompatActivity {

    private RadioGroup[] radioGroups = new RadioGroup[4]; // Array to hold RadioGroups
    private Button submitButton;
    private com.example.connectdbattempt1.ResponsesDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        dbHelper = new ResponsesDBHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase(); // This will create or open the existing database

        // Survey database insert code retrieved from https://chat.openai.com/share/1d089765-3568-4cf1-8641-e87db81c1ead
        radioGroups[0] = findViewById(R.id.radio_group1);
        radioGroups[1] = findViewById(R.id.radio_group2);
        radioGroups[2] = findViewById(R.id.radio_group3);
        radioGroups[3] = findViewById(R.id.radio_group4);


        submitButton = findViewById(R.id.btnSubmitSurvey);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String answer1 = getSelectedRadioButtonText(radioGroups[0]);
                String answer2 = getSelectedRadioButtonText(radioGroups[1]);
                String answer3 = getSelectedRadioButtonText(radioGroups[2]);
                String answer4 = getSelectedRadioButtonText(radioGroups[3]);

                ContentValues values = new ContentValues();
                values.put("Answer1", answer1);
                values.put("Answer2", answer2);
                values.put("Answer3", answer3);
                values.put("Answer4", answer4);

                sqLiteDatabase.insert("SurveyResponses", null, values);

                Toast.makeText(Survey.this, "Survey submitted!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), TutorialYesNo.class);
                startActivity(intent);
            }
        });
    }


    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedId);
            return selectedRadioButton.getText().toString();
        }
        return "";
    }
}