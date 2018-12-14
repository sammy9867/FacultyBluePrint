package com.example.samue.facultyblueprint.Classes;

import java.util.ArrayList;

public class Course {


    public String name         = "";
    String ID                  = "";
    public String type         = "";
    public int room_number     = -1;
    String teacher_ID          = "";
    ArrayList<Lesson> classes  = null;

    public Teacher teacher;

    public Course(String name){ this.name = name; }

    public Course(String name, String type, int rm, Teacher teacher){
        this.name = name;
        this.type = type;
        this.room_number = rm;
        this.teacher = teacher;
        // time might be added later
    }

    public Course(String _name, String _ID, String _type, int _room_number,
                  String _teacher_ID, ArrayList<Lesson> _classes){

        this.name=_name;
        this.ID=_ID;
        this.type=_type;
        this.room_number=_room_number;
        this.teacher_ID=_teacher_ID;

        classes.addAll(_classes);
    }

    public Course(String _ID, String _type){
        this.ID=_ID;
        this.type=_type;
        this.name="";
        this.room_number=-1;
        this.teacher_ID="";
        this.classes=null;
    }

    public String getName() {
        return name;
    }

}
