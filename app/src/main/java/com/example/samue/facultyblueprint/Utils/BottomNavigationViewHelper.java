package com.example.samue.facultyblueprint.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.example.samue.facultyblueprint.SubjectsLists.SubjectsListActivity;
import com.example.samue.facultyblueprint.Maps.MapsActivity;
import com.example.samue.facultyblueprint.Settings.SettingsActivity;
import com.example.samue.facultyblueprint.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {
    private static final String TAG= "BottomNavigationViewHel";


    //Calling up some basic methods from the library
    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    //Responsible for switching between activities
    public static void enableNavigation(final Context context, BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                //Retrieves the images from the drawable folder and assigning an activity to each image in the bottom navigation bar
                switch(item.getItemId()){
                    case R.id.ic_subjects_list:  //ACTIVITY_NUM = 0
                        Intent intent1 = new Intent(context, SubjectsListActivity.class);
                        context.startActivity(intent1);
                        break;

                    case R.id.ic_maps:  //ACTIVITY_NUM = 1
                        Intent intent2 = new Intent(context, MapsActivity.class);
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_settings: //ACTIVITY_NUM = 2
                        Intent intent3 = new Intent(context, SettingsActivity.class);
                        context.startActivity(intent3);
                        break;


                }
                return false;
            }
        });
    }
}
