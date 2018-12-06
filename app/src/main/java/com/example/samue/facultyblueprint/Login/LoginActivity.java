package com.example.samue.facultyblueprint.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.samue.facultyblueprint.R;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    public static final String APPLICATION_NAME  = "Faculty";
    public static final String CONSUMER_KEY      = "Lds4czKt8zVVAEp98EjG";
    public static final String CONSUMER_SECRET   = "ZDVtrxrkaNbjaN958d5bGPBx2WjE87br6gm7ZF8h";

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
