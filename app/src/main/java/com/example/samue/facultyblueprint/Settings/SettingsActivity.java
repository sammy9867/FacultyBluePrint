package com.example.samue.facultyblueprint.Settings;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        setupSettingsList();
        setupBottomNavigationView();


    }

    /** Sets up the list view in the Settings Activity consisting of student id no, email id and log out.**/
    private void setupSettingsList(){
        ListView listView = (ListView) findViewById(R.id.ListViewSettings);

        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.student_id_number));
        options.add(getString(R.string.student_email_id));
        options.add(getString(R.string.log_out));

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);
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
