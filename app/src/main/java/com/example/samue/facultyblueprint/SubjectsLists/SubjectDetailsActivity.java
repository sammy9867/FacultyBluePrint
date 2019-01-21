package com.example.samue.facultyblueprint.SubjectsLists;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Login.User;
import com.example.samue.facultyblueprint.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.w3c.dom.Text;

/**
 * Created by michael on 21.01.19.
 */

public class SubjectDetailsActivity extends Activity {

    Course selected_course = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_details);



        final ImageView profileMenu = (ImageView) findViewById(R.id.backArrowSubjectDetails);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


//        intent.putExtra("SELECTED_COURSE_ID", selected_course.object_id);
        int selected_object_id = (int) getIntent().getSerializableExtra("SELECTED_COURSE_ID");

        for(Course c : User.Courses)
            if(c.object_id == selected_object_id) {
                selected_course = c;
                break;
        }

        if(null == selected_course)
            finish();

        showDetails();

    }

    private void showDetails() {
        if(null == selected_course)
            finish();

        TextView tvName = (TextView) findViewById(R.id.tvNameOfSubject);
        TextView tvDesc = (TextView) findViewById(R.id.tvDescriptionOfSubject);

        tvName.setText(selected_course.toString());
        tvDesc.setText(selected_course.getDescriptions());
    }

    public void Apply_Click(View view) {
        if(null == selected_course)
            return;
        EditText et = findViewById(R.id.etRoom);
        String sRoom = et.getText().toString();
        int room;
        if(sRoom.equalsIgnoreCase("105A"))
            selected_course.room_number = 1051;
        else{
            try{
                room = Integer.parseInt(sRoom);
                if(room >= 400) throw new Exception();
                if(room <= 0 )  throw new Exception();
                selected_course.room_number = room;
            }catch (Exception e){
                FancyToast.makeText(getApplicationContext(), "Invalid room number",
                        FancyToast.ERROR, Toast.LENGTH_SHORT,false);
                return;
            }
        }

        finish();

    }
}


