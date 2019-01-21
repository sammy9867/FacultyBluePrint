package com.example.samue.facultyblueprint.Login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Classes.Grade;
import com.example.samue.facultyblueprint.Classes.Schedule;
import com.example.samue.facultyblueprint.Classes.Teacher;
import com.example.samue.facultyblueprint.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    /**
     * USAGE:
     *  1. Click GET PIN to get pin from USOS
     *  2. Click on a generated link written in Log.i()\
     *  3. Enter USOS's PIN in the Edit Text view
     *  4. Click Login
     * */


    public static Button getPinBtn;

    public String result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_pin);

        getPinBtn       = (Button)   findViewById(R.id.getPinButton);
        result = "";
    }


    /** Method to be called on click Get PIN*/
    public void getPinButton_Click(View view) {
        RequestTokenLogin requestTokenLogin = new RequestTokenLogin();
        requestTokenLogin.execute();

        Intent intent = new Intent(getApplicationContext(),EnterPINActivity.class);
        startActivity(intent);
        finish();
    }



}

