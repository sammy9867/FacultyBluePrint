package com.example.samue.facultyblueprint.SubjectsLists;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Login.User;
import com.example.samue.facultyblueprint.R;
import com.example.samue.facultyblueprint.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

/**This is the leftmost activity where list of subject of the user are added from USOS.**/
public class SubjectsListActivity extends AppCompatActivity { //implements AsyncResponse {

    private static final String TAG="SubjectsListsActivity";
    private static final int ACTIVITY_NUM = 0;  //leftmost activities index
    private Context mContext = SubjectsListActivity.this;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        setupBottomNavigationView();


        ListView listView = findViewById(R.id.sujectlist_listview);
        List<String> subjects = new ArrayList<String>();
        for(Course c : User.Courses){
            subjects.add(c.getName());
        }

        arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, subjects);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
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



