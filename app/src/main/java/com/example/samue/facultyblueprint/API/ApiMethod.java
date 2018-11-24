package com.example.samue.facultyblueprint.API;

import java.util.List;
import java.util.stream.Stream;

/// <summary>
/// Brief description of a single USOS API method.
/// </summary>
public class ApiMethod
{
    /// <summary>
    /// Fully-qualified (begins with "services/") name of the method.
    /// </summary>
    public String name;

    /// <summary>
    /// Brief (single line), plain-text description of what the method does.
    /// </summary>
    public String brief_description;

    /// <summary>
    /// Short name of the method (last element of the fully-qualified name).
    /// </summary>
    public String short_name;
    public String getShort_name(){
            String []arr = this.name.split("/");
             //arr = this.name.Split('/');
            return arr[arr.length-1];
    }

    public ApiMethod(String name, String brief_description) {
        this.name = name;
        this.brief_description = brief_description;
    }
}

