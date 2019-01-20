package com.example.samue.facultyblueprint.Settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Login.User;
import com.example.samue.facultyblueprint.Maps.MapsActivity;
import com.example.samue.facultyblueprint.R;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GradesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        //ImageView of the backarrow to leave popup details
        final ImageView profileMenu = (ImageView) findViewById(R.id.backArrowGrades);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        setupPieChart();

    }

    private void setupPieChart(){
        AnimatedPieView mAnimatedPieView = findViewById(R.id.gradesDistribution);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();



        Iterator it = User.Grades.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            String grade = pair.getValue().toString();

            String grade2 = grade.replace(',','.');
            String grade3 = grade2.replace("ZAL","0");


            double val = Double.parseDouble(grade3);
            config.startAngle(-90)
                    .addData(new SimplePieInfo(val, getColor(R.color.colorPrimaryDark), pair.getKey().toString()))
                    .duration(2000)
                    .drawText(true)
                    .splitAngle(0.2f);// draw pie animation duration

        }





// The following two sentences can be replace directly 'mAnimatedPieView.start (config); '
        mAnimatedPieView.applyConfig(config);
        mAnimatedPieView.start();
    }
}
