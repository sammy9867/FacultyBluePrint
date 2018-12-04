package com.example.samue.facultyblueprint.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.samue.facultyblueprint.R;


public class LoginActivity extends AppCompatActivity {

    private static final String APPLICATION_NAME  = "FacultyStaff12";
    private static final String CONSUMER_KEY      = "fXbK9PNe7wBVgbbwQ8Fr";
    private static final String CONSUMER_SECRET   = "2JU4Ka27ryAdQeqFfCUAReRN5U8aqvMsm8F5URBt";

    //Call Activity Login
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    /** Method to ne called on click Get PIN*/
    public void getPinButton_Click(View view) {

    }

    /** Method to be called on click Login*/
    public void loginButton_Click(View view) {
    }


}
