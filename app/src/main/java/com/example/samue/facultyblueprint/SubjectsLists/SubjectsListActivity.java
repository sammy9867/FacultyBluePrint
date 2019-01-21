package com.example.samue.facultyblueprint.SubjectsLists;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
        final ArrayList<Course> courses_on_list = new ArrayList<>();

        String currentSemester = User.GetCurrentSemester();
        for(Course c : User.Courses){
            // List subjects only from Current Semester
            if(c.Term_ID.equalsIgnoreCase(currentSemester)) {
                subjects.add(c.toString());
                courses_on_list.add(c);
            }
        }




        arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, subjects);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Course selected_course = courses_on_list.get(position);

                Intent intent = new Intent(getApplicationContext(), SubjectDetailsActivity.class);
                intent.putExtra("SELECTED_COURSE_ID", selected_course.object_id);
                startActivity(intent);

            }
        });

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



