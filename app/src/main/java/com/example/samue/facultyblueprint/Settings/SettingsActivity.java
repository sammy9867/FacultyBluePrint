package com.example.samue.facultyblueprint.Settings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Login.LoginActivity;
import com.example.samue.facultyblueprint.Login.User;
import com.example.samue.facultyblueprint.R;
import com.example.samue.facultyblueprint.Utils.BottomNavigationViewHelper;
import com.example.samue.facultyblueprint.Utils.SettingsListAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/** This is the rightmost activity displaying settings: User profile pic and ability to log out **/
public class SettingsActivity extends AppCompatActivity  {

    private static final String TAG="SettingsActivity";
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = SettingsActivity.this;

    ListView list;
    String[] itemname ={
            "Stats",
            "Grades",
            "Log Out"
    };

    Integer[] imgid={
            R.drawable.ic_maps,
            R.drawable.ic_maps,
            R.drawable.ic_maps
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        setupSettingsList();
        setupBottomNavigationView();


    }

    private void setupSettingsList(){

        TextView textView = (TextView) findViewById(R.id.UsosUserProfileName);
        textView.setText(User.Name + " " + User.Surname);



        Picasso.with(getBaseContext()).load(R.drawable.default_profile_image)
                .resize(80,80)
                .into((CircleImageView)findViewById(R.id.UsosUserProfileImage));

        SettingsListAdapter adapter=new SettingsListAdapter(this, itemname, imgid);
        list=(ListView)findViewById(R.id.ListViewSettings);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= itemname[+position];
             //   Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                if(position == 1){
                    checkGrades();
                }
                else if(position == 2){
                    Logout_Click();
                }
            }
        });
    }


    /**
     * Setting bottom navigation buttons
     */
    private void setupBottomNavigationView(){

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext,bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

    /**
     * By clicking on log out all User elements are reset,
     * Course list is cleared up and user is redirected to login page.
     * Result: user is logout
     */
    public void Logout_Click() {
        User.Logout();
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
      //  finish();
    }

    private void checkGrades(){
        Intent intent = new Intent(mContext, GradesActivity.class);
        startActivity(intent);
    }
}
