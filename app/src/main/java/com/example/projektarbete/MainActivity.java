package com.example.projektarbete;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;


public class MainActivity extends AppCompatActivity {

    public static final int receiveUserScore = 000;
    private final int QUESTIONS_ID = 2;
    private final int USER_ID = 1;

    Button questions;
    Button createUser;
    TextView showScore;
    public String userName;
    int userScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questions = findViewById(R.id.questionBn);
        createUser = findViewById(R.id.createUser);
        showScore = findViewById(R.id.showScore);
    }


    public void toQuestions(View caller) {
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivityForResult(intent, QUESTIONS_ID);
    }

    public void toCreateUser(View caller) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivityForResult(intent, USER_ID);
    }

    /*public static Map<String, Integer> updateScoreBoard(Map<String, Integer> unsortedScoreBoard) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortedScoreBoard.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<String, Integer> sortedScoreBoard = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedScoreBoard.put(entry.getKey(), entry.getValue());
        }
        return sortedScoreBoard;
    }


    public <K, V> void printScoreBoard(Map<K, V> map) {
        TextView showScore = findViewById(R.id.showScore);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            showScore.setText("Name: " + entry.getKey()
                    + " Score: " + entry.getValue());
        }
    }

    public void ifSorted(String userName, int userScore) {

        Map<String, Integer> unsortedScoreBoard = new HashMap<String, Integer>();
        unsortedScoreBoard.put(userName, userScore);

        Map<String, Integer> sortedScoreBoard = updateScoreBoard(unsortedScoreBoard);

        printScoreBoard(sortedScoreBoard);
    }

*/

    public void quit(View caller) {
        finish();
    }

  /*  public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            String user = this.getIntent().getStringExtra(receiveUserName);
            int score = this.getIntent().getIntExtra("score", receiveUserScore);

            if (user != null){
                String userName = user;
            }
            if (score != 0) {
                int userScore = score;
            } else


            showScore.setText(userName + " " + userScore);

        }
    }*/

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == USER_ID)
                userName = data.getStringExtra("userName");
            if (requestCode == QUESTIONS_ID)
                userScore = data.getIntExtra("score", receiveUserScore);
            showScore.setText("Last played game: \n" + userName + " " +  userScore);
        }
    }

}