package com.example.samue.facultyblueprint.Classes;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class fetchData extends AsyncTask<Void, Void, String> {

    String teachers_data = "";
    String courses_data = "";
    String rooms_data = "";

    ArrayList<Teacher> teachers = new ArrayList<Teacher>();
    ArrayList<Course> courses = new ArrayList<Course>();
    ArrayList<Room> rooms = new ArrayList<Room>();

    public ArrayList<Teacher> getTeachers(){
        return teachers;
    }

    public ArrayList<Course> getCourses(){
        return courses;
    }

    public ArrayList<Room> getRooms(){
        return rooms;
    }


    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(Void... voids) {

        try {

            URL teachers_url = new URL("https://api.myjson.com/bins/1gqb5i");
            URL rooms_url = new URL("https://api.myjson.com/bins/1dvfg6");
            URL courses_url = new URL("https://api.myjson.com/bins/707w6");

            HttpURLConnection http_teachers_url_connection = (HttpURLConnection) teachers_url.openConnection();
            HttpURLConnection http_rooms_url_connection = (HttpURLConnection) rooms_url.openConnection();
            HttpURLConnection http_courses_url_conneciton = (HttpURLConnection) courses_url.openConnection();

            InputStream teachers_inputStream = http_teachers_url_connection.getInputStream();
            InputStream rooms_inputStream = http_rooms_url_connection.getInputStream();
            InputStream courses_inputStream = http_courses_url_conneciton.getInputStream();

            BufferedReader teachers_bufferedReader = new BufferedReader(new InputStreamReader(teachers_inputStream));
            BufferedReader rooms_bufferedReader = new BufferedReader(new InputStreamReader(rooms_inputStream));
            BufferedReader courses_bufferedReader = new BufferedReader(new InputStreamReader(courses_inputStream));

            String teachers_line = "";
            String rooms_line = "";
            String courses_line = "";

            while (teachers_line != null) {
                teachers_line = teachers_bufferedReader.readLine();
                teachers_data += teachers_line;
            }

            while (rooms_line != null) {
                rooms_line = rooms_bufferedReader.readLine();
                rooms_data += rooms_line;
            }

            while (courses_line != null) {
                courses_line = courses_bufferedReader.readLine();
                courses_data += courses_line;
            }

            JSONArray teachers_JA = new JSONArray(teachers_data);
            JSONArray rooms_JA = new JSONArray(rooms_data);
            JSONArray courses_JA = new JSONArray(courses_data);

            for (int i = 0; i < rooms_JA.length(); i++) {
                JSONObject JO = (JSONObject) rooms_JA.get(i);
                rooms.add(new Room(Integer.parseInt(JO.get("number").toString()), JO.get("type").toString(), Integer.parseInt(JO.get("floor").toString())));
            }

            for (int i = 0; i < courses_JA.length(); i++) {
                ArrayList<Lesson> classes = new ArrayList<Lesson>();

                JSONObject JO = (JSONObject) courses_JA.get(i);
                String courses_classes = JO.get("classes").toString();
                JSONArray classesJA = new JSONArray(courses_classes);
                for (int j = 0; j < classesJA.length(); j++) {
                    JSONObject classesJO = (JSONObject) classesJA.get(j);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat hours = new SimpleDateFormat("HH:mm");
                    Date date = format.parse(classesJO.get("date").toString());
                    classes.add(new Lesson(
                            format.parse(classesJO.get("date").toString()),
                            hours.parse(classesJO.get("start").toString()),
                            hours.parse(classesJO.get("end").toString())));
                }
                courses.add(new Course(JO.get("course_name").toString(), JO.get("course_ID").toString(), JO.get("type").toString(),
                        Integer.parseInt(JO.get("room_number").toString()), JO.get("teacher_ID").toString(), classes));
            }

            for (int i = 0; i < teachers_JA.length(); i++) {
                JSONObject JO = (JSONObject) teachers_JA.get(i);
                ArrayList<Course> _courses = new ArrayList<Course>();

                String teachers_courses = JO.get("courses").toString();
                JSONArray coursesJA = new JSONArray(teachers_courses);
                for (int j = 0; j < coursesJA.length(); j++) {
                    JSONObject coursesJO = (JSONObject) coursesJA.get(j);
                    _courses.add(new Course(coursesJO.get("course_ID").toString(), coursesJO.get("type").toString()));

                }
                teachers.add(new Teacher(JO.get("name").toString(), JO.get("surname").toString(),
                        JO.get("id").toString(), JO.get("email").toString(), JO.get("website").toString(),
                        Integer.parseInt(JO.get("room").toString()), _courses));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result){

        delegate.processFinish(teachers, courses, rooms);
    }



}


