package com.example.quizapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.quizapplication.Utils.Rand;

import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class QuestionAndAnswer extends AppCompatActivity {

    TextView tvScore, tvQuestions,txtScore1;
    Button btnNewGame,btnNext;
    Dialog newGame;

    private Questions questions = new Questions();

    private String mAnswer;
    private String values;
    private TextView tvLevel, tvSubCat;
    private RadioButton mRadioButtonChoice1;
    private RadioButton mRadioButtonChoice2;
    private RadioButton mRadioButtonChoice3;
    private RadioButton mRadioButtonChoice4;
    private RadioGroup mRadioGroupChoices;
    private int mScore = 0;
    private int mQuestionsLength = questions.mQuestions.length;
    private List<Integer>valRand;
    private List<Integer>valRand1;
    int counter = 0;
    int level = 0;
    int wrongValue = 0;

    int minRand, maxRand;

    private String radioValue ="";
    Random r;

    private Rand rand;
    ImageView imgCheckCorrect;
    TextView txtTimer;
    CountDownTimer countDownTimerTicker;
    TextInputEditText edtPlayerName;
    Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_question);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC,am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);

        newGame = new Dialog(this);
        rand = new Rand();
        methods = new Methods(QuestionAndAnswer.this);

        tvScore = (TextView) findViewById(R.id.tvScore);
        tvQuestions = (TextView) findViewById(R.id.tvQuestions);

        imgCheckCorrect = (ImageView) findViewById(R.id.imgCheckCorrect);

        btnNext = (Button) findViewById(R.id.btnNext);

        mRadioButtonChoice1 = (RadioButton)findViewById(R.id.mRadioButtonChoice1);
        mRadioButtonChoice2 = (RadioButton)findViewById(R.id.mRadioButtonChoice2);
        mRadioButtonChoice3 = (RadioButton)findViewById(R.id.mRadioButtonChoice3);
        mRadioButtonChoice4 = (RadioButton)findViewById(R.id.mRadioButtonChoice4);

        tvLevel = (TextView) findViewById(R.id.tvLevel);
        tvSubCat = (TextView) findViewById(R.id.tvSubCat);
        txtTimer = (TextView) findViewById( R.id.tvTimer);

        try {
            Resetter(Questions.questCounter);
            categorySub();
            timerTicked();
        } catch (Exception e){
        }

        mRadioGroupChoices = findViewById(R.id.mRadioGroupChoices);

        if(counter==0){firstQuestionStart();}
        if(questions.questionC==7){mRadioFalse();}

        mRadioGroupChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {
                    case R.id.mRadioButtonChoice1:
                        radioValue = mRadioButtonChoice1.getText().toString();
                        break;
                    case R.id.mRadioButtonChoice2:
                        radioValue = mRadioButtonChoice2.getText().toString();
                        break;
                    case R.id.mRadioButtonChoice3:
                        radioValue = mRadioButtonChoice3.getText().toString();
                        break;
                    case R.id.mRadioButtonChoice4:
                        radioValue = mRadioButtonChoice4.getText().toString();
                        break;
                }
                btnNext.setEnabled(true);
                Log.d("OnClick: ",radioValue);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mRadioEmpty();

                    if(radioValue.equals(mAnswer)){
                        mScore++;
//                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.correctanswer);
//                        mp.start();
//                        Glide.with(QuestionAndAnswer.this).load(R.drawable.correctcheck).into(imgCheckCorrect);
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                imgCheckCorrect.setVisibility(View.INVISIBLE);
//                            }
//                        }, 1500);
                        imgCheckCorrect.setVisibility(View.GONE);
                        tvScore.setText("Score: " + mScore);
                    } else {
                        wrongValue++;
                     }
                    if(counter<=valRand.size() -1)
                    {
                        int index = valRand.get(counter);
                        counter++;
                        updateQuestion(index);

                        Log.d("Index: ", String.valueOf(index));
                        Log.d("Counter", String.valueOf(counter));
                    }
                    else {
                        caseLevel(questions.questionC);
                    }
                } catch (Exception e){
                }
            }
        });
    }
    private void updateQuestion(int num) {
        stopTimerTicked();
        timerTicked();

        if(wrongValue == 3){
            wrongValue = 0;
            gameOver();
        }

        final Animation myAnim = AnimationUtils.loadAnimation(QuestionAndAnswer.this, R.anim.bounce_once);
        tvQuestions.startAnimation(myAnim);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);
        tvQuestions.startAnimation(myAnim);

        tvQuestions.setText(Html.fromHtml(questions.getQuestion(num)));
        mRadioButtonChoice1.setText(questions.getChoices1(num));
        mRadioButtonChoice2.setText(questions.getChoices2(num));
        mRadioButtonChoice3.setText(questions.getChoices3(num));
        mRadioButtonChoice4.setText(questions.getChoices4(num));

        mAnswer = questions.getAnswer(num);
    }

    private void gameOver() {
        newGame.setContentView(R.layout.game_over);
        btnNewGame = (Button) newGame.findViewById(R.id.btnNewGame);
        txtScore1 = (TextView) newGame.findViewById(R.id.txtScore1);
        edtPlayerName = (TextInputEditText) newGame.findViewById(R.id.edtPlayerName);

        if(mScore <= 1){
            txtScore1.setText("Your score is "+ mScore + " point.");
        } else {
            txtScore1.setText("Your score is " + mScore + " points.");
        }

        stopTimerTicked();
        tvScore.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.GONE);

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPlayerName.getText().toString().equals("")) {
                    Toast.makeText(QuestionAndAnswer.this, "Please enter your name first!", Toast.LENGTH_SHORT).show();
                } else {
                    newGame.dismiss();
                    methods.createDatabase("Leaderboards");
                    methods.insertDatabase(edtPlayerName.getText().toString(), String.valueOf(mScore));
                    methods.intentMethod(QuestionAndAnswer.class);
                    finish();
                }
            }
        });

        newGame.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        newGame.setCanceledOnTouchOutside(false);
        newGame.setCancelable(false);
        newGame.show();
    }

    private void firstQuestionStart(){
        int index = valRand.get(counter);
        counter++;
        updateQuestion(index);
        Log.d("index: ",String.valueOf(index));

    }
    private void mRadioEmpty(){
        mRadioButtonChoice1.setChecked(false);
        mRadioButtonChoice2.setChecked(false);
        mRadioButtonChoice3.setChecked(false);
        mRadioButtonChoice4.setChecked(false);
        btnNext.setEnabled(false);
    }
    private void mRadioFalse(){
        mRadioButtonChoice3.setVisibility(View.INVISIBLE);
        mRadioButtonChoice4.setVisibility(View.INVISIBLE);

    }
    private void Resetter(int questCounter){
        StringBuilder stringBuilder = new StringBuilder();

        values = questions.levelQuestion(questCounter);
        tvLevel.setText(values);
        wrongValue = 0;

        String items = questions.numberItems(questCounter);

        StringTokenizer stringTokenizer = new StringTokenizer(items, "-");
        String from = stringTokenizer.nextToken();
        String to = stringTokenizer.nextToken();

        Log.d("Level Question: ", String.valueOf(values));
        Log.d("Questions Counter: ", String.valueOf(questCounter));

        Log.d("From: ", String.valueOf(from));
        Log.d("From: ", String.valueOf(to));

        valRand = rand.randomNumber(Integer.parseInt(from), Integer.parseInt(to));
        Log.d("Val RandSize: ", String.valueOf(valRand.size()));

        for(int ii : valRand){
            stringBuilder.append(ii).append(" ");
        }
        Log.d("RandomQuestions: ",String.valueOf(stringBuilder));

    }
    private void categorySub(){
        String cat = questions.getCateg(questions.categoryCount);
        Log.d("Sub Cat: ",cat);
        tvSubCat.setText(cat);
    }
    private void caseQuest(int index){
        Log.d("Debug: ",String.valueOf(index));
        switch (index) {
            case 0:
                Questions.questCounter = 0;
                break;
            case 3:
                Questions.questCounter = 3;
                break;
        }
    }
    private void caseLevel(int index){
        switch (index){
            case 0:
                if (level < 2){
                    Questions.questCounter++;
                    level++;
                    counter = 0;
                    Resetter(Questions.questCounter);
                } else {
                    gameOver();
                    level = 0;
                    caseQuest(questions.questionC);
                }
                break;
            case 3:
                if (level < 2){
                    Questions.questCounter++;
                    level++;
                    counter = 0;
                    Resetter(Questions.questCounter);
                } else {
                    gameOver();
                    level = 0;
                    caseQuest(questions.questionC);
                }
                break;
            case 6:
                if (level < 0&&questions.questionC==6){
                    Questions.questCounter++;
                    level++;
                    counter = 0;
                    Resetter(Questions.questCounter);
                } else {
                    gameOver();
                    level = 0;
                    caseQuest(questions.questionC);
                }
                break;
            case 7:
                if (level < 0&&questions.questionC==7){
                    Questions.questCounter++;
                    level++;
                    counter = 0;
                    Resetter(Questions.questCounter);
                } else {
                    gameOver();
                    level = 0;
                    caseQuest(questions.questionC);
                }
                break;
        }
    }

    private void timerTicked() {
        try {
            countDownTimerTicker = new CountDownTimer(31000, 1000) {
                public void onTick(long millisUntilFinished) {
                    txtTimer.setText("Timer: "+ String.valueOf(millisUntilFinished / 1000) + " sec");
                }
                public void onFinish() {
                    gameOver();
                    tvScore.setVisibility(View.VISIBLE);
                    txtTimer.setText("Timer: 0 sec");
                    txtTimer.setTextColor(getResources().getColor(R.color.red));
                    txtTimer.setVisibility(View.GONE);
                }
            }.start();
        } catch (Exception e){
            e.getMessage();
        }
    }

    private void stopTimerTicked(){
        if(countDownTimerTicker != null) {
            countDownTimerTicker.cancel();
            countDownTimerTicker = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimerTicked();
    }
}
