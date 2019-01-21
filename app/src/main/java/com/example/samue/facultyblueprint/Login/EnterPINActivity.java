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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Classes.Grade;
import com.example.samue.facultyblueprint.Classes.Schedule;
import com.example.samue.facultyblueprint.Classes.Teacher;
import com.example.samue.facultyblueprint.Maps.MapsActivity;
import com.example.samue.facultyblueprint.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class EnterPINActivity  extends AppCompatActivity {

    public static Button loginBtn;
    public static TextView refreshTextView;
    public static EditText etPIN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn        = (Button)   findViewById(R.id.loginButton);
        refreshTextView = (TextView) findViewById(R.id.refreshTextView);
        etPIN           = (EditText) findViewById(R.id.pinEditText);

        loadUrlWithWebView(User.authorizationUrl);


    }
    public void loadUrlWithWebView(String url) {
        WebView webView = findViewById(R.id.webview1);
        webView.loadUrl(url);
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



        GetUserCourses();
        User.SortSemesters();

        GetAllGrades();

        GetWeeksSchedule();

//        GetUserPhoto();

        ((TextView) view).setText("Hello "+ User.Name+" !");


        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);
        finish();

        FancyToast.makeText(getApplicationContext(),"Success!",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
    }

    private void GetAllGrades() {
        for(String semester : User.Semesters)
            if(!semester.isEmpty())
                GetSemesterGrades(semester);

        parseGrades();
    }

    private void parseGrades() {
        for(Grade grade : User.Grades){
            grade.grade = grade.grade.replace(",", ".");

            for(Course course : User.Courses){
                if (grade.course_id.equalsIgnoreCase(course.ID)){
                    grade.course_name = course.name;
                    grade.term_id = course.Term_ID;
                    break;
                }
            }
        }

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
                    Grade grade = new Grade(key, grade_value);
                    User.Grades.add(grade);

                    k=1;
                    Log.i(semester +"" +key,grade_value);
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void GetWeeksSchedule(){
        VolleyOAuthRequest volleyOAuthRequest =
                new VolleyOAuthRequest(0,User.requestUrl+"services/tt/user",
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
            JSONArray jsonArray = new JSONArray(response);

            for(int i = 0;i < jsonArray.length();i++){
                JSONObject dates = (JSONObject) jsonArray.get(i);
                String start_time = dates.getString("start_time");
                String end_time = dates.getString("end_time");

                JSONObject name = (JSONObject) dates.getJSONObject("name");
                String subject_name = name.getString("en");

                Schedule schedule = new Schedule(start_time,end_time,subject_name);
                User.Schedule.add(schedule);



                Log.i("START" +start_time +" END"+ end_time," " + subject_name);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void GetUserPhoto(){

        VolleyOAuthRequest volleyOAuthRequest =
                new VolleyOAuthRequest(0,User.requestUrl+"services/users/photo",
                        null);

        String volleyURL = volleyOAuthRequest.getUrl();
        Log.i("VOLLEY URL >>> ", volleyURL);

        DownloadImageTask dit = new DownloadImageTask(User.Profile_Pic);
        try {
            dit.execute(volleyURL).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
/*
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


        } catch (JSONException e) {
            e.printStackTrace();
        }*/

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

                JSONArray semester_json_array = course_editions.getJSONArray(key);
                for(int i=0; i<semester_json_array.length(); i++){
                    JSONObject subject = (JSONObject) semester_json_array.get(i);

                    JSONArray user_groups = subject.getJSONArray("user_groups");
                    for(int j=0; j < user_groups.length(); j++) {
                        JSONObject user_group = (JSONObject) user_groups.get(j);

                        String class_type_id = user_group.getString("class_type_id");
                        if(! class_type_id.equalsIgnoreCase("CWI") &&
                                ! class_type_id.equalsIgnoreCase("WYK") &&
                                ! class_type_id.equalsIgnoreCase("LAB"))
                            continue;

                        Course course  = new Course();

                        JSONObject course_name = user_group.getJSONObject("course_name");
                        String en_course_name = course_name.getString("en");

                        course.name    = en_course_name;
                        course.type    = class_type_id
                                .replace("CWI","TUT")
                                .replace("WYK","LEC");
                        course.Unit_ID = user_group.getString("course_unit_id");
                        course.ID      = user_group.getString("course_id");
                        course.Term_ID = user_group.getString("term_id");

                        JSONArray lecturers = user_group.getJSONArray("lecturers");
                        for(int l=0; l<lecturers.length(); l++){
                            JSONObject lecturer = (JSONObject) lecturers.get(l);
                            String name = lecturer.getString("first_name");
                            String sname = lecturer.getString("last_name");
                            String teacher_id = lecturer.getString("id");
                            Teacher teacher = new Teacher(name, sname, teacher_id);
                            course.teachers.add(teacher);
                        }
                        User.Courses.add(course);
                    }
                }
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
}
