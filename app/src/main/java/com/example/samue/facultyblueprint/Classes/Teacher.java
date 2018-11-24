package com.example.samue.facultyblueprint.Classes;

import java.util.ArrayList;

public class Teacher {
    String name;
    String surname;
    String id;
    String email;
    String website;
    int room;
    ArrayList<Course> courses = new ArrayList<Course>();

    public Teacher(String _name, String _surname, String _id, String _email, String _website,
                   int _room, ArrayList<Course> _courses){

        this.name=_name;
        this.surname=_surname;
        this.id=_id;
        this.email=_email;
        this.website=_website;
        this.room=_room;
        courses.addAll(_courses);
    }


    public Teacher(String name, String surname){
        this.name = name;
        this.surname = surname;
    }

    /**
     * toString() shall be called automatically
     * when the object treated as a String
     */
    public String toString(){
        return this.name+" "+this.surname;
    }
}
