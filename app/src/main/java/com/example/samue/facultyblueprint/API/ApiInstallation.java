package com.example.samue.facultyblueprint.API;

import java.util.List;
import java.util.stream.Stream;

public class ApiInstallation
{
    /// <summary>
    /// Base URL of the Installation. Should end with a slash.
    /// </summary>
    public String base_url;

    /// <summary>
    /// USOS API version string (or null if unknown).
    /// </summary>
    public String version;

    public ApiInstallation(String base_url) {
        this.base_url = base_url;
    }
}



