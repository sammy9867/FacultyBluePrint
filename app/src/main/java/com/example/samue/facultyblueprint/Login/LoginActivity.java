package com.example.samue.facultyblueprint.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    /**
     * USAGE:
     *  1. Click GET PIN to get pin from USOS
     *  2. Click on a generated link written in Log.i()\
     *  3. Enter USOS's PIN in the Edit Text view
     *  4. Click Login
     * */

    public static EditText etPIN;
    public static Button getPinBtn;
    public static Button loginBtn;
    public static TextView refreshTextView;
    public static TextView noteTextView;
    public String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etPIN           = (EditText) findViewById(R.id.pinEditText);
        getPinBtn       = (Button)   findViewById(R.id.getPinButton);
        loginBtn        = (Button)   findViewById(R.id.loginButton);
        refreshTextView = (TextView) findViewById(R.id.refreshTextView);
        noteTextView    = (TextView) findViewById(R.id._noteTextView);
        result = "";
    }


    /** Method to be called on click Get PIN*/
    public void getPinButton_Click(View view) {
        RequestTokenLogin requestTokenLogin = new RequestTokenLogin();
        requestTokenLogin.execute();

//            FDataLogin fDataLogin = new FDataLogin();
//            fDataLogin.execute();
    }

    /**
     * Method to be called on click Login
     * Gets Access Token
     * */
    public void loginButton_Click(View view) {
        AccessTokenLogin accessTokenLogin = new AccessTokenLogin();
        accessTokenLogin.execute();
    }

    /**
     * Click on this label will download data from USOS
     * User's Name, Surname, and ID is downloaded first
     * Then the list of the courses is downloaded
     * @param view
     */
    public void Refresh_Click(View view) {

        GetUserID();

        GetUserCourses();

        ((TextView) view).setText("Hello "+ User.Name+" !");
        super.onBackPressed();

    }

    /**
     * Downloads Courses, which the User is involved, from USOS
     */
    private void GetUserCourses() {
        VolleyOAuthRequest volleyOAuthRequest =
                new VolleyOAuthRequest(0,User.requestUrl+"services/courses/user",
                null);

        String volleyURL = volleyOAuthRequest.getUrl();
        Log.i("GetUserCourses URL >>> ", volleyURL);

        FDataLogin fDataLogin = new FDataLogin(volleyURL.toString());

        try {
            fDataLogin.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String response = fDataLogin.getResponse();


        try {
            JSONObject global_object = new JSONObject(response);
            JSONObject course_editions = global_object.getJSONObject("course_editions");

            // TODO: Set up semester argument dynamically
            JSONArray semester_json_array = course_editions.getJSONArray("2018Z");
            for(int i=0; i<semester_json_array.length(); i++){
                JSONObject semester = (JSONObject) semester_json_array.get(i);
                JSONObject course_name = semester.getJSONObject("course_name");
                String en_course_name = course_name.getString("en");
                Course course = new Course(en_course_name);
                User.Courses.add(course);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Downloads Personal data of the User from USOS
     * Name, Surname, Usos_ID
     */
    private void GetUserID() {
        VolleyOAuthRequest volleyOAuthRequest =
                new VolleyOAuthRequest(0,User.requestUrl+"services/users/user",
                null);

        String volleyURL = volleyOAuthRequest.getUrl();
        Log.i("VOLLEY URL >>> ", volleyURL);

        FDataLogin fDataLogin = new FDataLogin(volleyURL. toString());

        try {
            fDataLogin.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String response = fDataLogin.getResponse();

        try {
            JSONObject object = new JSONObject(response);
            boolean isMessage = object.has("message");
            if(isMessage == true){
                // Something wrong [90%]
                Log.i("MESSAGE ", object.getString("message"));
                return;
            }
            User.Name    = object.getString("first_name");
            User.Surname = object.getString("last_name");
            User.Usos_Id      = object.getString("id");
            Log.i("SUCCESS >> ", User.Name+" "+User.Surname+" "+ User.Usos_Id);


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


}
