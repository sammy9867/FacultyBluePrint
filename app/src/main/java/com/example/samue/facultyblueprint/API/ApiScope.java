package com.example.samue.facultyblueprint.API;


import java.util.List;
import java.util.stream.Stream;

/// <summary>
/// Description of a single USOS API scope type.
/// </summary>
public class ApiScope
{
    public String key;
    public String developers_description;

    public ApiScope(String key, String developers_description) {
        this.key = key;
        this.developers_description = developers_description;
    }
}


