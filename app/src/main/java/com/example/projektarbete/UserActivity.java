package com.example.projektarbete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UserActivity extends AppCompatActivity {

    public static String usNa;
    private String SAVE_OBJECT_FILE = "student.ser";
    private String TAG = "MYTAG";
    private final int USER_ID = 1;

    private User user = new User("", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TextView userBoard = (TextView) findViewById(R.id.userboard);
        EditText name = (EditText) findViewById(R.id.name);
        EditText userName = (EditText) findViewById(R.id.userName);

        try {
            FileInputStream file = openFileInput(SAVE_OBJECT_FILE);
            ObjectInput in = new ObjectInputStream(file);

            user = (User) in.readObject();

            in.close();
            file.close();

        } catch (IOException ex) {
            userBoard.setText("IOException is caught in reading");
        } catch (ClassNotFoundException ex) {
            userBoard.setText("ClassNotFoundException is caught");
        }
        if (user == null)
            userBoard.setText("Nothing on file, user is null");
        else
            userBoard.setText(user.getUser());
            name.setText(user.getName());
            userName.setText(user.getUserName());
    }

    public void saveUser(View caller) {
        EditText name = (EditText) findViewById(R.id.name);
        EditText userName = (EditText) findViewById(R.id.userName);
        TextView userBoard = (TextView) findViewById(R.id.userboard);

        String text = name.getText().toString();
        String text2 = userName.getText().toString();

        user.setName(text);
        user.setUserName(text2);

        try {
            FileOutputStream file = openFileOutput(SAVE_OBJECT_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(file);
            if (user != null) {
                user.setName(user.getName());
                user.setUserName(user.getUserName());
                userBoard.setText(user.getUser());
                out.writeObject(user);
            }

            out.close();
            file.close();
        } catch (IOException ex) {
            userBoard.setText("IOException is caught WRITING");
        }



    }


    public void goToMain(View caller) {
        usNa = user.getUserName();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("userName", usNa);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}