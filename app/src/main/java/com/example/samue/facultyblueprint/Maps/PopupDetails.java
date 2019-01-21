package com.example.samue.facultyblueprint.Maps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samue.facultyblueprint.Classes.Teacher;
import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Login.User;
import com.example.samue.facultyblueprint.R;
import com.example.samue.facultyblueprint.SubjectsLists.SubjectDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class PopupDetails extends Activity {

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        //ImageView of the backarrow to leave popup details
        final ImageView profileMenu = (ImageView) findViewById(R.id.backArrow);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
                Animation animRotateAclk = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_arrow_anticlockwise);
                profileMenu.startAnimation(animRotateAclk);
                overridePendingTransition( R.anim.slide_in_down, R.anim.slide_out_down );
            }
        });

        initWindowData();
    }

    public void initWindowData(){
        Intent i = getIntent();
        String roomNumber = (String) i.getSerializableExtra("roomNumber");

        String roomText = "Room number:  " + roomNumber;
        int rm;
        if(roomNumber.equalsIgnoreCase("105a")){ rm = 1051; }
        else { rm = Integer.parseInt(roomNumber); }

        final ArrayList<Course> courses_on_list = new ArrayList<>();
        List<String> subjects = new ArrayList<String>();

        for (Course c : User.Courses){
            if(c.room_number  == rm){
                courses_on_list.add(c);
                subjects.add(roomText + "\n" + c.toString());
            }
        }

        ListView listView = findViewById(R.id.roomsubjects_listview);

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


}
