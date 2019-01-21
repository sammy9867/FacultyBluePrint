package com.example.samue.facultyblueprint.Classes;

import android.content.ContentUris;

public class Grade {

    public String grade;
    public String course_id;
    public String course_name;
    public String term_id;

    public Grade(String _course_id, String _grade){
        grade = _grade;
        course_id = _course_id;
        course_name = "";
        term_id = "";
    }
}
