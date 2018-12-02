package com.example.samue.facultyblueprint.Maps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.samue.facultyblueprint.Settings.SettingsActivity;
import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.R;

public class PopupDetails extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        initWindowData();
    }

    public void initWindowData(){
        Intent i = getIntent();
        String roomNumber = (String) i.getSerializableExtra("roomNumber");

        TextView tv = findViewById(R.id.room_number);
        tv.setText(tv.getText() + "  " + roomNumber );
        int rm = Integer.parseInt(roomNumber);


       for (Course c :  SettingsActivity.courses){
           if(c.room_number  == rm){
               ((TextView)findViewById(R.id.course_name)).setText(c.name);
               ((TextView)findViewById(R.id.course_type)).setText(c.type);
               ((TextView)findViewById(R.id.teacher_name)).setText(""+c.teacher);
               break;
           }
       }



    }
}
