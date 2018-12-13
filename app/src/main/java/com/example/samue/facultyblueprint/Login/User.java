package com.example.samue.facultyblueprint.Login;

import android.widget.EditText;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;


/**
 * Created by michael on 13.12.18.
 *
 * User class
 * Current version supports one user per app
 */

public final class User {

    public static OAuthService service;

    public static final String APPLICATION_NAME  = "FacultyStaff12";
    public static final String CONSUMER_KEY      = "fXbK9PNe7wBVgbbwQ8Fr";
    public static final String CONSUMER_SECRET   = "2JU4Ka27ryAdQeqFfCUAReRN5U8aqvMsm8F5URBt";

    public static final String CALLBACK   = "oob";
    public static final String requestUrl = "https://apps.usos.pw.edu.pl/";

    public static Token requestToken = null;
    public static Token accessToken  = null;
    public static String authorizationUrl;

    public static String Name;
    public static String Surname;
    public static String Id;



}
