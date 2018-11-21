package com.example.samue.facultyblueprint.SubjectsLists;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.samue.facultyblueprint.AddSubjects.MainActivity;
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
import java.util.Arrays;
import java.util.List;

public class SubjectsListActivity extends AppCompatActivity implements AsyncResponse {

    private static final String TAG="SubjectsListsActivity";
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = SubjectsListActivity.this;


    ////
    private fetchData fetch = new fetchData();
    private ArrayList<Course> courses = new ArrayList<Course>();
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    ////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjectlist);
        setupBottomNavigationView();

        // //
        fetch.delegate=this;
        fetch.execute();

        ListView listView = findViewById(R.id.sujectlist_listview);
        List<String> subjects = new ArrayList<String>();
        for(Course c : courses){
            subjects.add(c.getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, subjects);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        // //
    }

    private void setupBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    public void processFinish(ArrayList<Teacher> _teachers, ArrayList<Course> _courses, ArrayList<Room> _rooms) {

        teachers.addAll(_teachers);
        courses.addAll(_courses);
        rooms.addAll(_rooms);

        ListView listView = findViewById(R.id.sujectlist_listview);
        List<String> subjects = new ArrayList<String>();
        for(Course c : courses){
            if(c.getName()!=null)
                subjects.add(c.getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, subjects);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }
}



