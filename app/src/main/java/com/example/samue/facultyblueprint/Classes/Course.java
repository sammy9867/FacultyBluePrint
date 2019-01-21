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

    public String getDescriptions(){
        String result = "";
        if(type.equalsIgnoreCase("LEC")) result += "Lecture\n";
        if(type.equalsIgnoreCase("TUT")) result += "Tutorials\n";
        if(type.equalsIgnoreCase("LAB")) result += "Laboratories\n";
        if(room_number != -1 && room_number != 1051)
            result += "Room: "+room_number+"\n";
        if(1051 == room_number)
            result += "Room: 105A\n";

        if(teachers.size() > 0){
            result += "Lecturer(s):\n";
            for(Teacher t : teachers)
                result += ""+t+"\n";
        }

        return result;
    }

    public String listTeachers(){
        String result = "";
        for(int i=0; i<teachers.size(); i++)
            result += (i+1 == teachers.size()) ? ""+teachers.get(i) : ""+teachers.get(i)+"\n";
        return result;
    }
}
