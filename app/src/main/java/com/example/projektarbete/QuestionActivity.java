package com.example.projektarbete;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;

import static android.widget.Toast.makeText;

public class QuestionActivity extends AppCompatActivity {

    //en string som hämtar frågor från en länk
    private final String URL = "https://claratoll.se/app/";
    private final String LOGCAT_TAG = "log";
    private final int QUESTIONS_ID = 2;

    // vet ej varför detta är här
    private ArrayList<Statement> statements = new ArrayList<>();
    Score score = new Score();

    Button firstBn;
    Button secondBn;
    Button thirdBn;
    Button fourthBn;

    public String pressedButton;
    public String corrAnsw;

    // ändra
    TextView mScoreTextView;
    TextView mQuestionTextView;


    //håller index för det aktuella påståendet
    int mIndex;
    // antal rätta svar
    int mScore;

    Toast mToastMessage;
    private int inc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        firstBn = findViewById(R.id.button1);
        secondBn = findViewById(R.id.button2);
        thirdBn = findViewById(R.id.button3);
        fourthBn = findViewById(R.id.button4);
        mScoreTextView = findViewById(R.id.score);
        mQuestionTextView = findViewById(R.id.questionView);


        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("score", mScore);


        getQuestionsFromWebService();

        // Restores the 'state' of the app upon screen rotation:
        if (savedInstanceState != null) {

            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
            mScoreTextView.setText("Score " + mScore + "/" + statements.size());
        } else {
            mScore = 0;
            mIndex = 0;
        }
    }

    private void getQuestionsFromWebService() {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(URL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) { Log.d(LOGCAT_TAG, "Success! JSON: " + response.toString());

                JSONArray jsonArray = response.optJSONArray("questions");

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jobject = jsonArray.getJSONObject(i);
                        String q = jobject.optString("question");
                        String aA= jobject.optString("answerA");
                        String aB = jobject.optString("answerB");
                        String aC = jobject.optString("answerC");
                        String aD = jobject.optString("answerD");
                        String cA = jobject.optString("correctAnswer");
                        statements.add(new Statement(q, aA, aB, aC, aD, cA));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Collections.shuffle(statements);
                inc = (int) Math.ceil(100.0 / statements.size());

                mQuestionTextView.setText(statements.get(mIndex).getQuestion());
                firstBn.setText(statements.get(mIndex).getAnswerA());
                secondBn.setText(statements.get(mIndex).getAnswerB());
                thirdBn.setText(statements.get(mIndex).getAnswerC());
                fourthBn.setText(statements.get(mIndex).getAnswerD());

                firstBn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pressedButton = "A";
                        checkAnswer(pressedButton);
                        updateQuestion();
                    }
                });
                secondBn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pressedButton = "B";
                        checkAnswer(pressedButton);
                        updateQuestion();
                    }
                });
                thirdBn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pressedButton = "C";
                        checkAnswer(pressedButton);
                        updateQuestion();
                    }
                });
                fourthBn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pressedButton = "D";
                        checkAnswer(pressedButton);
                        updateQuestion();
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Log.e(LOGCAT_TAG, "Fail " + e.toString());
                Log.d(LOGCAT_TAG, "Status code" + statusCode);
//                Log.d(LOGCAT_TAG, "Here's what we got instead " + response.toString());
            }
        });
    }
   private void updateQuestion() {
       mIndex = (mIndex + 1) % statements.size();
       if (mIndex == 0) {
           AlertDialog.Builder alert = new AlertDialog.Builder(QuestionActivity.this);
           alert.setTitle("Game Over");
           alert.setCancelable(false);
           alert.setMessage("You scored " + mScore + " points!");
           alert.setPositiveButton("Gå tillbaka", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   //finish();
                   score.setIntScore(mScore);
               }
           });
           alert.show();
       }
       if (mIndex != 0)
           mQuestionTextView.setText(statements.get(mIndex).getQuestion());
           firstBn.setText(statements.get(mIndex).getAnswerA());
           secondBn.setText(statements.get(mIndex).getAnswerB());
           thirdBn.setText(statements.get(mIndex).getAnswerC());
           fourthBn.setText(statements.get(mIndex).getAnswerD());
           mScoreTextView.setText("Score " + mScore + "/" + statements.size());
   }

   private void checkAnswer(String userSelection) {
       if (mToastMessage != null) {
           mToastMessage.cancel();
       }
       corrAnsw = statements.get(mIndex).getCorrectAnswer();

       if (userSelection.equals(corrAnsw)) {
           String correctAnswer = getResources().getString(R.string.t_correct) + " ";
           mToastMessage = makeText(getApplicationContext(), correctAnswer, Toast.LENGTH_SHORT);
           mScore = mScore + 1;
       } else {
           String wrongAnswer = getResources().getString(R.string.t_incorrect) + " ";
           mToastMessage = Toast.makeText(getApplicationContext(), wrongAnswer, Toast.LENGTH_SHORT);
       }
       mToastMessage.show();
   }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);
    }

    public void goToMain(View caller) {
        int myScore = score.getIntScore();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("score", myScore);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

}