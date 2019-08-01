package com.example.quizapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.quizapplication.Class.Items;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardsActivity extends AppCompatActivity {

    List<Items> scoreList = new ArrayList<>();
    ListView listOfScoreBoards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_boards);

        listOfScoreBoards = (ListView) findViewById(R.id.listOfScoreBoards);



    }
}
