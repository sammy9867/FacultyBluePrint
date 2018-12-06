package com.example.samue.facultyblueprint.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.samue.facultyblueprint.R;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    public static final String APPLICATION_NAME  = "FacultyStaff12";
    public static final String CONSUMER_KEY      = "fXbK9PNe7wBVgbbwQ8Fr";
    public static final String CONSUMER_SECRET   = "2JU4Ka27ryAdQeqFfCUAReRN5U8aqvMsm8F5URBt";

    public static String TOKEN = "";
    public static String TOKEN_SECRET = "";


    public static String response;
    //Call Activity Login
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        response = "";
    }


    /** Method to be called on click Get PIN*/
    public void getPinButton_Click(View view) {
            FDataLogin fDataLogin = new FDataLogin();
            fDataLogin.execute();
    }

    /** Method to be called on click Login*/
    public void loginButton_Click(View view) {

    }


    public void Refresh_Click(View view) {

        ((TextView) view).setText("response:\n"+response + "\n");
    }
}
