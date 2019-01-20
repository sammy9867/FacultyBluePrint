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
     * @param view ERR unauthorized
     */
    public void Refresh_Click(View view) {

        GetUserID();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GetUserICal();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GetUserCourses();

        GetAllGrades();

        ((TextView) view).setText("Hello "+ User.Name+" !");
        super.onBackPressed();

    }

    private void GetAllGrades() {

        GetSemesterGrades("2016Z");
        GetSemesterGrades("2017L");
        GetSemesterGrades("2017Z");
        GetSemesterGrades("2018L");
        GetSemesterGrades("2018Z");

        /*for(String semester : User.Semesters)
            if(!semester.isEmpty())
                GetSemesterGrades(semester);*/
    }

    private void GetSemesterGrades(String semester){
        VolleyOAuthRequest volleyOAuthRequest =
                new VolleyOAuthRequest(0,User.requestUrl+"services/grades/terms2",
                        null);
        volleyOAuthRequest.addParameter("term_ids", semester);
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
            JSONObject global_object = new JSONObject(response);
            JSONObject semester_json_object = global_object.getJSONObject(semester);

            //1
            Iterator<String> keys = (Iterator<String>) semester_json_object.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject value = semester_json_object.getJSONObject(key);
                JSONArray grades_json_array = value.getJSONArray("course_grades");
                int k=1;
                for(int i = 0; i < grades_json_array.length();i++){
                    JSONObject grades, course_grades_1=null;
                    grades = (JSONObject) grades_json_array.get(i);
                    if(k == 3){
                        k=1; continue;
                    }
                    try {
                        course_grades_1 = grades.getJSONObject("" + k++);
                    }catch(Exception e){
                        Log.i("debug >> ", "i = "+i+" | k = " + k);
                        i--;
                        continue;
                    }
                    String grade_value = course_grades_1.getString("value_symbol");
                    User.Grades.put(key,grade_value);
                    User.ListOfGrades.add(User.Grades);
                    k=1;
                    Log.i(semester +"" +key,grade_value);
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
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

            Iterator<String> keys = (Iterator<String>) course_editions.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Log.i("SEM:",key);
                User.Semesters.add(key);
            }

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

     //   volleyOAuthRequest.addParameter("user_id", User.Usos_Id);
     //   Log.i("\n\nUSER_ID >>>>>", User.Usos_Id+"\n\n\n");
    //    volleyOAuthRequest.addParameter("lang", "en");

        String volleyURL = volleyOAuthRequest.getUrl()+"&user_id="+User.Usos_Id+"&lang=en";
     //   String volleyURL = volleyOAuthRequest.getUrl();
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
                        Log.i("\n\n\n Room  >> ", course.room_number+"");

                    }
                    catch (Exception e){
                        course.room_number = -1;
                        Log.i("\n\n\n Room unparsable >> ", room);
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

