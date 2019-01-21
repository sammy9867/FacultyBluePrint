package com.example.samue.facultyblueprint.Classes;

import java.util.ArrayList;

public class Teacher {
    public String name;
    public String surname;
    public String id;

    public String email;
    public String website;
    public int room;



    public Teacher(String name, String surname, String id){
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    /**
     * toString() shall be called automatically
     * when the object treated as a String
     */
    public String toString(){
        return this.name+" "+this.surname;
    }
}
