package com.example.quizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button btnSynonyms, btnAntonyms, btnEnglishGrammar, btnSpelling;
    private Questions questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        questions = new Questions();

        btnSynonyms=(Button) findViewById(R.id.btnSynonyms);
        btnAntonyms=(Button) findViewById(R.id.btnAntonyms);
        btnEnglishGrammar=(Button) findViewById(R.id.btnEnglishGrammar);
        btnSpelling=(Button) findViewById(R.id.btnSpelling);

        btnSynonyms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, QuestionAndAnswer.class);
                startActivity(intent);
                questionsCounter(0, 0);
            }
        });

        btnAntonyms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, QuestionAndAnswer.class);
                startActivity(intent);
                questionsCounter(1,3);
            }
        });
        btnEnglishGrammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, QuestionAndAnswer.class);
                startActivity(intent);
                questionsCounter(2,6);
            }
        });
        btnSpelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, QuestionAndAnswer.class);
                startActivity(intent);
                questionsCounter(3,7);
            }
        });


    }
    private void questionsCounter(int count, int count2)
    {
        questions.categoryCount = count;
        questions.questCounter = count2;
        questions.questionC = count2;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Home.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
