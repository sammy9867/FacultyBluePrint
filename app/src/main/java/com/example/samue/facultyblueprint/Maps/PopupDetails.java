package com.example.samue.facultyblueprint.Maps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samue.facultyblueprint.Classes.Teacher;
import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.R;

import java.util.ArrayList;

public class PopupDetails extends Activity {

    public static ArrayList<Course> courses;
    public ArrayList<Course> getCourses() { return courses; }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        /// TEMPORARY way to add courses  [TEST1]
        courses = new ArrayList<Course>();
        courses.add(new Course("Java",
                "Lecture",
                101,
                new Teacher("Agnieszka", "Jasterszabska")));
        courses.add(new Course("C",
                "Lab",
                102,
                new Teacher("Pawel", "Aszklar")));



        //ImageView of the backarrow to leave popup details
        ImageView profileMenu = (ImageView) findViewById(R.id.backArrow);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        initWindowData();
    }

    public void initWindowData(){
        Intent i = getIntent();
        String roomNumber = (String) i.getSerializableExtra("roomNumber");

        TextView tv = findViewById(R.id.room_number);
        tv.setText(tv.getText() + "  " + roomNumber );
        int rm = Integer.parseInt(roomNumber);


       for (Course c :  courses){
           if(c.room_number  == rm){
               ((TextView)findViewById(R.id.course_name)).setText(c.name);
               ((TextView)findViewById(R.id.course_type)).setText(c.type);
               ((TextView)findViewById(R.id.teacher_name)).setText(""+c.teacher);
               break;
           }
       }

    }


}
