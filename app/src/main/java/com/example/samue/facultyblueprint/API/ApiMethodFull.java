package com.example.samue.facultyblueprint.API;

import java.util.List;
import java.util.stream.Stream;

/// <summary>
/// Full description of an USOS API method.
/// </summary>
public class ApiMethodFull extends ApiMethod{
/// <summary>
/// A list of all possible arguments the method can be called with.
/// This does not include standard OAuth signing arguments.
/// </summary>
public List<ApiMethodArgument> arguments;


public String description;
public String returns;
public String ref_url;

/// <summary>
/// "required", "optional", "ignored"
/// </summary>
public String auth_options_consumer;

/// <summary>
/// "required", "optional", "ignored"
/// </summary>
public String auth_options_token;

/// <summary>
/// Is secure connection required to execute this method?
/// </summary>
public Boolean auth_options_ssl_required;

    public ApiMethodFull(String name, String brief_description) {
        super(name, brief_description);
    }

    public ApiMethodFull(String name, String brief_description, String description, String returns, String ref_url, String auth_options_consumer, String auth_options_token, Boolean auth_options_ssl_required) {
        super(name, brief_description);
        this.description = description;
        this.returns = returns;
        this.ref_url = ref_url;
        this.auth_options_consumer = auth_options_consumer;
        this.auth_options_token = auth_options_token;
        this.auth_options_ssl_required = auth_options_ssl_required;
    }
}


