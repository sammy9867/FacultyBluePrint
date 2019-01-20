package com.example.samue.facultyblueprint.Login;

import android.graphics.Bitmap;

import com.example.samue.facultyblueprint.Classes.Course;
import com.example.samue.facultyblueprint.Classes.Grade;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    public static Bitmap Profile_Pic;

    public static ArrayList<Course> Courses =
                                new ArrayList<Course>();

    /**For getting grades from different semester**/
//    public static List<Map<String, String>> ListOfGrades = new ArrayList<>();
//    public static Map<String, String> Grades = new HashMap<>();
    public static ArrayList<Grade> Grades = new ArrayList<>();

    public static ArrayList<String> Semesters = new ArrayList<>();


    public static String GetCurrentSemester(){
        String ZL = "";
        String Year;
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy/MM/dd");
        String s = sdf.format(new Date());
        String[] split = s.split("/");
        switch (split[1])
        {
            case "01": ZL += "Z"; break;
            case "02": ZL += "L"; break;
            case "03": ZL += "L"; break;
            case "04": ZL += "L"; break;
            case "05": ZL += "L"; break;
            case "06": ZL += "L"; break;
            case "07": ZL += "L"; break;
            case "08": ZL += "L"; break;
            case "09": ZL += "L"; break;
            case "10": ZL += "Z"; break;
            case "11": ZL += "Z"; break;
            case "12": ZL += "Z"; break;
        }
        if(split[1].equalsIgnoreCase("01")){
            int year = Integer.parseInt(split[0]);
            year = year-1;
            Year = ""+year;
        }
        else
            Year = split[0];
        return Year+""+ZL;
    }

    public static void Logout(){
        service          = null;
        requestToken     = null;
        accessToken      = null;
        authorizationUrl = null;
        Semesters.clear();
        Courses.clear();
    }

    public static void SortSemesters() {

        Collections.sort(Semesters);
    }

}
