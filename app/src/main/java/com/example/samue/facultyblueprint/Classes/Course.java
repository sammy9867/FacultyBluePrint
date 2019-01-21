package com.example.samue.facultyblueprint.Classes;

import java.util.ArrayList;
import java.util.Date;

public class Course {

    private static int id_counter = 1;
    public int object_id;

    public String name         = "";
    public String ID           = "";
    public String Unit_ID      = "";
    public String Term_ID      = "";
    public String type         = "";
    public int room_number     = -1;

    public String description  = "";
    public ArrayList<Teacher> teachers = new ArrayList<>();

    public Date date = null;

    public Course(){
        object_id = id_counter;
        id_counter++;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return type+" - "+name;
    }

}
