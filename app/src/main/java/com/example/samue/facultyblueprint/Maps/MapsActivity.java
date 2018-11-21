package com.example.samue.facultyblueprint.Maps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.samue.facultyblueprint.R;
import com.example.samue.facultyblueprint.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MapsActivity extends AppCompatActivity {

    private static final String TAG="MapsActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = MapsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setupBottomNavigationView();
        buttonClick();
    }

    //Demo on how to go to PopUpActivity for each button
    public void buttonClick(){
        int[] arr = {R.id.room101, R.id.room102};
        for(int i : arr){
            final Button room = (Button) findViewById(i);
            room.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PopupDetails.class);

                    // putExtra() is required to send the room number to the new activity
                    intent.putExtra("roomNumber", room.getText()) ;

                    // Start a new activity for the clicked room
                    //HEY
                    startActivity(intent);
                }
            });
        }
    }


    private void setupBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}
