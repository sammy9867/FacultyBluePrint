package com.example.samue.facultyblueprint.Classes;

import java.util.ArrayList;
import java.util.Date;

public class Course {


    public String name         = "";
    public String ID           = "";
    public String Unit_ID      = "";
    public String Term_ID      = "";
    public String type         = "";
    public int room_number     = -1;

    public String teacher_ID   = "";
    public String description  = "";
    public ArrayList<Teacher> teachers = new ArrayList<>();

    public Date date = null;

    public Course(String name){ this.name = name; }



    public Course(String _ID, String _type){
        this.ID=_ID;
        this.type=_type;
        this.name="";
        this.room_number=-1;
        this.teacher_ID="";
    }

    public Course(){}

    public String getName() {
        return name;
    }

    public String toString(){
        return type+" - "+name;
    }

}
