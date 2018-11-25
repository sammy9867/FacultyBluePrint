package com.example.samue.facultyblueprint.API;

import java.util.List;
import java.util.stream.Stream;

/// <summary>
/// Description of a single argument of an USOS API method.
/// </summary>
public class ApiMethodArgument
{
    public String name;
    public Boolean is_required;
    public String description;
    public String default_value;

    public ApiMethodArgument(String name, Boolean is_required, String description, String default_value) {
        this.name = name;
        this.is_required = is_required;
        this.description = description;
        this.default_value = default_value;
    }
}


