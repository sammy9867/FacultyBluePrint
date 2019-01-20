package com.example.samue.facultyblueprint.Settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Classes.Grade;
import com.example.samue.facultyblueprint.Login.User;
import com.example.samue.facultyblueprint.Maps.MapsActivity;
import com.example.samue.facultyblueprint.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GradesActivity extends AppCompatActivity {

    private TextView tvCurrentSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        // Text View
        tvCurrentSemester = findViewById(R.id.currentSemLabel);

        //ImageView of the backarrow to leave grades
        final ImageView profileMenu = (ImageView) findViewById(R.id.backArrowGrades);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        String actualSemester = User.GetCurrentSemester();
        int index = User.Semesters.indexOf(actualSemester);
        index = index - 1;
        // TODO: Prev Semester might NOT exist
        String semester = User.Semesters.get(index); // previous semester !!!

        setupPieChart(semester);

    }

    public void NextSemester_Click(View v){
        String text = tvCurrentSemester.getText().toString();
        text = text.substring(text.length()-6, text.length()-1);
        int currentIdx = User.Semesters.indexOf(text);
        if(currentIdx == User.Semesters.size()-1)
            return; // Mo Next Semester
        // Set pie chart for the next semester
        setupPieChart(User.Semesters.get(currentIdx+1));

    }

    public void PrevSemester_Click(View view) {
        String text = tvCurrentSemester.getText().toString();
        text = text.substring(text.length()-6, text.length()-1);
        int currentIdx = User.Semesters.indexOf(text);
        if(currentIdx == 0)
            return; // Mo Prev Semester
        // Set pie chart for the previous semester
        setupPieChart(User.Semesters.get(currentIdx-1));
    }

    private void setupPieChart(String semester){
      //  AnimatedPieView mAnimatedPieView = findViewById(R.id.gradesDistribution);
    //    AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        BarChart barChart = findViewById(R.id.gradesDistribution);
        barChart.getDescription().setEnabled(false);


        int index = User.Semesters.indexOf(semester);
        index = index + 1;
        tvCurrentSemester.setText("Semester #"+index+" ["+semester+"]");

        // Grade counters
        int[] counters = new int[6];

        for (Grade grade : User.Grades) {
            if (!grade.term_id.equalsIgnoreCase(semester)) continue;
            double val;
            try {
                val = Double.parseDouble(grade.grade);
            } catch (Exception e) {
                val = 0;
            }
            if (2.0 == val) counters[0]++;
            if (3.0 == val) counters[1]++;
            if (3.5 == val) counters[2]++;
            if (4.0 == val) counters[3]++;
            if (4.5 == val) counters[4]++;
            if (5.0 == val) counters[5]++;
        }

        List<BarEntry> barEntryList = new ArrayList<>();
        for(int i=0; i<counters.length; i++) {
            BarEntry barEntry;
            barEntry = new BarEntry(i, counters[i]);
            barEntryList.add(barEntry);
        }

        BarDataSet set = new BarDataSet(barEntryList,"Grades");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData barData = new BarData(set);
        barChart.setData(barData);
        barChart.invalidate();
        barChart.animateY(500);
       /* config.startAngle(-90)
                .addData(new SimplePieInfo(val, getColor(R.color.colorPrimaryDark), grade.course_name))
                .duration(2000)
                .drawText(true)
                .splitAngle(0.2f);// draw pie animation duration*/

// The following two sentences can be replace directly 'mAnimatedPieView.start (config); '
      //  mAnimatedPieView.applyConfig(config);
       // mAnimatedPieView.start();
    }



}
