package com.example.samue.facultyblueprint.Settings;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Login.User;
import com.example.samue.facultyblueprint.R;
import com.example.samue.facultyblueprint.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**This is the rightmost activity displaying settings: User profile pic and ability to log out **/
public class SettingsActivity extends AppCompatActivity  {

    private static final String TAG="SettingsActivity";
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = SettingsActivity.this;


    public static ArrayList<Course> courses;

    public ArrayList<Course> getCourses() { return courses; }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupSettingsList();
        setupBottomNavigationView();


    }

    /** Sets up the list view in the Settings Activity consisting of student id no, email id and log out.**/
    private void setupSettingsList(){
        ListView listView = (ListView) findViewById(R.id.ListViewSettings);

        TextView textView = (TextView) findViewById(R.id.UsosUserProfileName);
        textView.setText(User.Name + " " + User.Surname);



        if(User.has_profile_pic){
        Picasso.with(getBaseContext()).load(User.Profile_Pic)
                .resize(80,80)
                .into((CircleImageView)findViewById(R.id.UsosUserProfileImage));}


        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.student_id_number) + ": " + User.Usos_Id);
        options.add(getString(R.string.student_email_id)+ ": " +User.Email_Id);
        options.add(getString(R.string.log_out));

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);
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
