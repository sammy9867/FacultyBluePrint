package com.example.samue.facultyblueprint.Login;

import android.content.Context;
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
        setContentView(R.layout.activity_get_pin);


        etPIN           = (EditText) findViewById(R.id.pinEditText);
        getPinBtn       = (Button)   findViewById(R.id.getPinButton);
        loginBtn        = (Button)   findViewById(R.id.loginButton);
        refreshTextView = (TextView) findViewById(R.id.refreshTextView);
//        noteTextView    = (TextView) findViewById(R.id._noteTextView);
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

        GetUserICal();

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

        FDataLogin fDataLogin = new FDataLogin(volleyURL);

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
    //        User.has_profile_pic = true;

            Log.i("SUCCESS >> ", User.Name+" "+User.Surname+" "+ User.Usos_Id);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    /**
     * Download ICalendar
     * Gets schedule for a few weeks
     * Saves data in the file ICAL_FILE_NAME
     */
    private void GetUserICal(){
        VolleyOAuthRequest volleyOAuthRequest =
                new VolleyOAuthRequest(0,User.requestUrl+"services/tt/upcoming_ical",
                        null);

        volleyOAuthRequest.addParameter("user_id", User.Usos_Id);
        Log.i("\n\nUSER_ID >>>>>", User.Usos_Id+"\n\n\n");
        volleyOAuthRequest.addParameter("lang", "en");

        String volleyURL = volleyOAuthRequest.getUrl();
        Log.i("VOLLEY URL [ICal]>>> ", volleyURL);

        FDataLogin fDataLogin = new FDataLogin(volleyURL, true);

        try {
            fDataLogin.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String response = fDataLogin.getResponse();

        /* Catch Error Message*/
        if(!response.startsWith("BEGIN") ) {
            try {
                JSONObject object = new JSONObject(response);
                boolean isMessage = object.has("message");
                if (isMessage == true) {
                    // Something wrong [90%]
                    Log.i("MESSAGE ", object.getString("message"));
                    return;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("ICAL_ERR >> ", response);
            return ;
        }

        ParseICal(response);
    }

    private void ParseICal(String ical) {
        String[] lines = ical.split("!NL");
        for(int i=0; i<lines.length; i++) {
            Log.i("Parsing >> ", lines[i]);
            if(lines[i].equalsIgnoreCase("BEGIN:VEVENT"))
                createSubject(lines, i);
        }
    }

    private void createSubject(String[] lines, int index) {
        int i=index+1;
        Course course = new Course();

        while(! lines[i].equalsIgnoreCase("END:VEVENT") && i<lines.length){
            String line = lines[i];
            line = line.replace("\\n","");
            line = line.replace("\\","");

            if(line.startsWith("SUMMARY:")){
                String s = line.substring("SUMMARY:".length());
                course.name = s;
            }

            if(line.startsWith("DESCRIPTION:")) {
                String s = line.substring("DESCRIPTION:".length());

                if(s.startsWith("Room: ")) {
//                    String[] room = s.split(" ");
                    String room = s.substring("Room: ".length(), "Room: ".length()+3);
                    try {
                        course.room_number = Integer.parseInt(room);
                        Log.i("\n\n\n Room  >> ", course.room_number+"\n\n\n");

                    }
                    catch (Exception e){
                        course.room_number = -1;
                        Log.i("\n\n\n Room unparsable >> ", room+"\n\n\n");
                    }
                    Log.i(course.name+" > ", ""+course.room_number);
                }
                course.description = s;
            }

            if(line.startsWith("DTSTART;VALUE=DATE-TIME:")) {
            //    json += ("\"dt_start\":" + '"' + line.substring("DTSTART;VALUE=DATE-TIME:".length()) + '"' + ',');
                course.date = null;  // TODO: make date time from YYYYMMDDThhmmss
            }

//            if(line.startsWith("DTEND;VALUE=DATE-TIME:"))
//                json += ("\"dt_end\":"+'"'+ line.substring("DTEND;VALUE=DATE-TIME:".length()) + '"' + ',');

//            if(line.startsWith("DTSTAMP;VALUE=DATE-TIME:"))
//                json += ("\"dt_stamp\":"+'"'+ line.substring("DTSTAMP;VALUE=DATE-TIME:".length()) + '"' + ',');


            if(line.startsWith("LOCATION:")) {
            //    json += ("\"location\":" + '"' + line.substring("LOCATION:".length()) + '"');
                course.description += "\n"+line.substring("LOCATION:".length());
            }

            i++;
        }

        User.Courses.add(course);
    }
}

