package com.example.samue.facultyblueprint.Maps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samue.facultyblueprint.Classes.Teacher;
import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Login.User;
import com.example.samue.facultyblueprint.R;

import java.util.ArrayList;

public class PopupDetails extends Activity {


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

        TextView tv = findViewById(R.id.room_number);
        tv.setText(tv.getText() + "  " + roomNumber );
        int rm;

        if(roomNumber.equals("105a")){
            rm = 1051;
        }else {
            rm = Integer.parseInt(roomNumber);
        }



       for (Course c : User.Courses){
           if(c.room_number  == rm){
               ((TextView)findViewById(R.id.course_name)).setText(c.toString());
               ((TextView)findViewById(R.id.teacher_name)).setText("Teachers:\n"+c.listTeachers());
               break;
           }
       }

    }


}
