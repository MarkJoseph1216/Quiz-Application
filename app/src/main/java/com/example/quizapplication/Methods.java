package com.example.quizapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.content.Context.MODE_PRIVATE;

public class Methods {

    Context context;
    SQLiteDatabase myDB = null;
    private static final String PLAYER_ID = "id";

    public Methods(Context context){
        this.context = context;
    }

    public void intentMethod(final Class<? extends Activity> activityIntent){
        Intent intent = new Intent(context, activityIntent);
        context.startActivity(intent);
    }

    public void createDatabase(String databaseName){
        myDB = context.openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS scores(" + PLAYER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, score TEXT);");
    }

    public void insertDatabase(String playerName, String playerScore){
        myDB.execSQL("INSERT INTO scores (name, score) VALUES (" + playerName + "," + playerScore + ");");
    }

    public void selectDatabase(){
        Cursor cursor = myDB.rawQuery("SELECT * FROM scores", null);
    }
}