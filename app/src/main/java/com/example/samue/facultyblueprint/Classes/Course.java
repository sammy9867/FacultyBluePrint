package com.example.samue.facultyblueprint.Classes;

import java.util.ArrayList;

public class Course {
    String name;
    String ID;
    String type;
    int room_number;
    String teacher_ID;
    ArrayList<Lesson> classes = new ArrayList<Lesson>();

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
}
