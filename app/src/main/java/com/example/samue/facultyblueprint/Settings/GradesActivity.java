package com.example.samue.facultyblueprint.Settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.samue.facultyblueprint.Login.User;
import com.example.samue.facultyblueprint.R;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import java.util.Iterator;
import java.util.Map;

public class GradesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        setupPieChart();

    }

    private void setupPieChart(){
        AnimatedPieView mAnimatedPieView = findViewById(R.id.gradesDistribution);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();

        Iterator it = User.Grades.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            config.startAngle(-90)
                    .addData(new SimplePieInfo(1, getColor(R.color.colorPrimaryDark), pair.getKey().toString()))
                    .duration(2000)
                    .drawText(true)
                    .splitAngle(0.2f);// draw pie animation duration
            it.remove(); // avoids a ConcurrentModificationException
        }





// The following two sentences can be replace directly 'mAnimatedPieView.start (config); '
        mAnimatedPieView.applyConfig(config);
        mAnimatedPieView.start();
    }
}
