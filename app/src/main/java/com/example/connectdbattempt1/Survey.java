package com.example.connectdbattempt1;


import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class Survey extends AppCompatActivity {

    int answerCount = 0;
    Button btnSubmitSurvey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        LinearLayout surveyLayout = findViewById(R.id.survey_layout);
        btnSubmitSurvey = findViewById(R.id.btnSubmitSurvey);
        btnSubmitSurvey.setEnabled(false);

        // Question 1
        TextView question1 = new TextView(this);
        question1.setText("How often do you experience difficulty hearing in noisy environments?");
        surveyLayout.addView(question1);

        RadioGroup answerGroup1 = new RadioGroup(this);
        String[] choices1 = {"Rarely", "Occasionally", "Frequently", "Almost always"};
        for (String choice : choices1) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(choice);
            answerGroup1.addView(radioButton);
        }
        surveyLayout.addView(answerGroup1);

        // Question 2
        TextView question2 = new TextView(this);
        question2.setText("How often do you find yourself asking others to repeat themselves during conversations?");
        surveyLayout.addView(question2);


        RadioGroup answerGroup2 = new RadioGroup(this);
        String[] choices2 = {"Rarely", "Occasionally", "Frequently", "Almost always"};
        for (String choice : choices2) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(choice);
            answerGroup2.addView(radioButton);
        }
        surveyLayout.addView(answerGroup2);

        // Question 3
        TextView question3 = new TextView(this);
        question3.setText("How often does your hearing difficulty impact your daily activities or quality of life?");
        surveyLayout.addView(question3);


        RadioGroup answerGroup3 = new RadioGroup(this);
        String[] choices3 = {"Rarely", "Occasionally", "Frequently", "Almost always"};
        for (String choice : choices3) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(choice);
            answerGroup3.addView(radioButton);
        }
        surveyLayout.addView(answerGroup3);

        // Question 4
        TextView question4 = new TextView(this);
        question4.setText("How often does your hearing difficulty impact your daily activities or quality of life?");
        surveyLayout.addView(question4);


        RadioGroup answerGroup4 = new RadioGroup(this);
        String[] choices4 = {"Rarely", "Occasionally", "Frequently", "Almost always"};
        for (String choice : choices4) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(choice);
            answerGroup4.addView(radioButton);
        }
        surveyLayout.addView(answerGroup4);

        TextView question5 = new TextView(this);
        question5.setText("Please indicate how often you experience hearing difficulties in the following situations:");
        surveyLayout.addView(question5);

        TextView question56 = new TextView(this);
        question56.setText("Watching television or movies");
        surveyLayout.addView(question56);

        RadioGroup answerGroup56 = new RadioGroup(this);
        String[] choices56 = {"Rarely", "Occasionally", "Frequently", "Almost always"};
        for (String choice : choices56) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(choice);
            answerGroup56.addView(radioButton);
        }
        surveyLayout.addView(answerGroup56);

        TextView question57 = new TextView(this);
        question57.setText("Participating in group conversations");
        surveyLayout.addView(question57);

        RadioGroup answerGroup57 = new RadioGroup(this);
        String[] choices57 = {"Rarely", "Occasionally", "Frequently", "Almost always"};
        for (String choice : choices57) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(choice);
            answerGroup57.addView(radioButton);
        }
        surveyLayout.addView(answerGroup57);

        TextView question58 = new TextView(this);
        question58.setText("Talking on the phone");
        surveyLayout.addView(question58);

        RadioGroup answerGroup58 = new RadioGroup(this);
        String[] choices58 = {"Rarely", "Occasionally", "Frequently", "Almost always"};
        for (String choice : choices58) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(choice);
            answerGroup58.addView(radioButton);
        }
        surveyLayout.addView(answerGroup58);

        TextView question59 = new TextView(this);
        question59.setText("Interacting in crowded or noisy environments");
        surveyLayout.addView(question59);

        RadioGroup answerGroup59 = new RadioGroup(this);
        String[] choices59 = {"Rarely", "Occasionally", "Frequently", "Almost always"};
        for (String choice : choices59) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(choice);
            answerGroup59.addView(radioButton);
        }
        surveyLayout.addView(answerGroup59);

        TextView question510 = new TextView(this);
        question510.setText("Listening to music or audio");
        surveyLayout.addView(question510);

        RadioGroup answerGroup510 = new RadioGroup(this);
        String[] choices510 = {"Rarely", "Occasionally", "Frequently", "Almost always"};
        for (String choice : choices510) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(choice);
            answerGroup510.addView(radioButton);
        }
        surveyLayout.addView(answerGroup510);

        if (answerCount == 10) {
            btnSubmitSurvey.setEnabled(true);
        }
    }
}

