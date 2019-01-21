package com.example.samue.facultyblueprint.Settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.samue.facultyblueprint.Classes.Schedule;
import com.example.samue.facultyblueprint.Login.User;
import com.example.samue.facultyblueprint.R;

import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    public ListView listView;
    ArrayAdapter<String> arrayAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        listView = findViewById(R.id.scheduleList);
        setSchedule();

        //ImageView of the backarrow to leave grades
        final ImageView profileMenu = (ImageView) findViewById(R.id.backArrowSchedule);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void setSchedule(){

        List<String> list  = new ArrayList<>();

        for(Schedule s : User.Schedule){
            list.add(s.toString());
        }

        arrayAdapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

}
