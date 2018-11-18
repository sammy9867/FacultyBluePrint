package com.example.samue.facultyblueprint.AddSubjects;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.samue.facultyblueprint.Classes.AsyncResponse;
import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Classes.Room;
import com.example.samue.facultyblueprint.Classes.Teacher;
import com.example.samue.facultyblueprint.Classes.fetchData;
import com.example.samue.facultyblueprint.R;
import com.example.samue.facultyblueprint.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.lang.reflect.Array;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private static final String TAG="MainActivity";
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = MainActivity.this;

    private ArrayList<Course> courses = new ArrayList<Course>();
    private ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private fetchData fetch = new fetchData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomNavigationView();

        fetch.delegate=this;
        fetch.execute();
    }

    private void setupBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
    @Override
    public void processFinish(ArrayList<Teacher> _teachers, ArrayList<Course> _courses, ArrayList<Room> _rooms) {

        teachers.addAll(_teachers);
        courses.addAll(_courses);
        rooms.addAll(_rooms);

        for(Course c: courses){
            Log.i(c.toString(), "course");

        }

        for(int i = 0; i < teachers.size();i++){
            Log.i(teachers.get(i).toString(),"string t");
        }

        for(int i = 0; i < courses.size();i++){
            Log.i(rooms.get(i).toString(),"string room");
        }


    }
}
