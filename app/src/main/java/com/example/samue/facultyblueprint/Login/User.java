package com.example.samue.facultyblueprint.Login;

import android.widget.EditText;

import com.example.samue.facultyblueprint.Classes.Course;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import java.util.ArrayList;


/**
 * Created by michael on 13.12.18.
 *
 * User class
 * Current version supports one user per app
 */

public final class User {

    public static final String APPLICATION_NAME  = "FacultyStaff12";
    public static final String CONSUMER_KEY      = "fXbK9PNe7wBVgbbwQ8Fr";
    public static final String CONSUMER_SECRET   = "2JU4Ka27ryAdQeqFfCUAReRN5U8aqvMsm8F5URBt";

    public static final String CALLBACK   = "oob";
    public static final String requestUrl = "https://apps.usos.pw.edu.pl/";

    public static OAuthService service = null;

    public static Token requestToken      = null;
    public static Token accessToken       = null;
    public static String authorizationUrl = null;

    public static String Name     = "";
    public static String Surname  = "";
    public static String Usos_Id  = "";
    public static String Email_Id = "";

    public static String Profile_Pic;
    public static boolean has_profile_pic;


    public static ArrayList<Course> Courses =
                                new ArrayList<Course>();

    public static void Logout(){
        service          = null;
        requestToken     = null;
        accessToken      = null;
        authorizationUrl = null;

        Name             = "";
        Surname          = "";
        Usos_Id          = "";
        Email_Id         = "";

        Courses.clear();
    }

}
