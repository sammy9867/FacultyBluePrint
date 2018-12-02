package com.example.samue.facultyblueprint.Settings;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Classes.Teacher;
import com.example.samue.facultyblueprint.R;
import com.example.samue.facultyblueprint.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;


/**This is the rightmost activity displaying settings: User profile pic and ability to log out **/
public class SettingsActivity extends AppCompatActivity  {

    private static final String TAG="SettingsActivity";
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = SettingsActivity.this;


    public static ArrayList<Course> courses;

    public ArrayList<Course> getCourses() { return courses; }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupBottomNavigationView();
        courses = new ArrayList<Course>();

        /// TEMPORARY way to add courses  [TEST1]
        courses.add(new Course("Java",
                    "Lecture",
                    101,
                    new Teacher("Agnieszka", "Jasterszabska")));
        courses.add(new Course("C",
                               "Lab",
                               102,
                                new Teacher("Pawel", "Aszklar")));

    }

    private void setupBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}
