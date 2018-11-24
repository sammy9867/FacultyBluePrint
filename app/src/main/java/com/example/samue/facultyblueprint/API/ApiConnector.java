package com.example.samue.facultyblueprint.API;

import android.app.usage.UsageEvents;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.EventListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.net.ssl.HttpsURLConnection;

import static java.lang.System.in;

/// <summary>
/// Implenentation of a simple USOS API connector. It cas generate properly signed
/// OAuth requests and provides some USOS-API-specific helper functions.
/// </summary>
public class ApiConnector
{
    /// <summary>
    /// Occurs when ApiConnector instance begins a web request.
    /// </summary>
    //
    //
    //
    // ??
    public UsageEvents.Event BeginRequest;
    //public event EventHandler BeginRequest;

    /// <summary>
    /// Occurs when ApiConnector instance ends previously started web request.
    /// </summary>
    //
    //
    //
    // ??
    public UsageEvents.Event EndRequest;
   // public event EventHandler EndRequest;

    /// <summary>
    /// USOS API installation which the ApiConnector uses for method calls.
    /// </summary>
    public ApiInstallation currentInstallation;

    /// <summary>
    /// Create new USOS API connector.
    /// </summary>
    /// <param name="installation">
    ///     USOS API Installation which to initally use. This can be
    ///     switched later.
    /// </param>
    public ApiConnector(ApiInstallation installation)
    {
        this.currentInstallation = installation;
    }

    /// <summary>
    /// Switch connector to a different USOS API installation.
    /// </summary>
    public void SwitchInstallation(ApiInstallation apiInstallation)
    {
        this.currentInstallation = apiInstallation;
    }

    /// <summary>
    /// Read a WebResponse and return it's content as a string.
    /// </summary>
    public static String ReadResponse(HttpURLConnection response)
    {
        if (response == null)
            return "";
        String stream;
        try{
            stream = response.getResponseMessage();
           // InputStreamReader reader = new InputStreamReader(stream);
            //String s = reader.ReadToEnd();
            return stream;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return "";
    }

    /// <summary>
    /// Make a request for the specified URL, read the response and return
    /// it's content as a string. Will throw a WebException if response is
    /// not status 200.
    /// </summary>
    public String GetResponse(String link) throws Exception {

        //
        //
        //
        // ??
        //this.BeginRequest(this, null);
        try
        {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
           // WebRequest request = WebRequest.Create(url);
            connection.setReadTimeout(15000);
           // request.Timeout = 15000;

            //request.Proxy = null;
           // String response = connection.getResponseMessage();
            return ReadResponse(connection);
//            using (WebResponse response = request.GetResponse())
//            {
//                return ReadResponse(response);
//            }
        }
        catch(MalformedURLException e) {
            throw new Exception("Wrong url");
        }

        finally
        {
            //
            //
            // ??
            //this.EndRequest(this, null);
        }
    }

    /// <summary>
    /// Construct a signed USOS API URL which points to a given method with
    /// given arguments.
    /// </summary>
    /// <param name="method">USOS API method to call.</param>
    /// <param name="args">A dictionary of method argument values for this call.</param>
    /// <param name="consumer_key">Your Consumer Key (if you want to sign this request).</param>
    /// <param name="consumer_secret">Your Consumer Secret (if you want to sign this request).</param>
    /// <param name="token">Your Token (if you want to sign this request).</param>
    /// <param name="token_secret">Your Token Secret (if you want to sign this request).</param>
    /// <returns></returns>

    /////
    ///// LET Dictionary be a HashMap

    public String GetURL(ApiMethod method, HashMap<String, String> args,
                         String consumer_key, String consumer_secret, String token,
                         String token_secret, Boolean use_ssl) throws IOException
    //public String GetURL(ApiMethod method, Dictionary<String, String> args,
    //                     String consumer_key, String consumer_secret, String token,
    //                     String token_secret, Boolean use_ssl)
    {
        args = null;
        consumer_key = "";
        consumer_secret = "";
        token = "";
        token_secret = "";
        use_ssl = false;

        OAuthBase oauth = new OAuthBase();
        if (args == null)
            args = new HashMap<String, String>();

        LinkedList<String> argPairsEncoded = new LinkedList<String>();
//        var argPairsEncoded = new List<String>();
        for(Map.Entry<String, String> pair : args.entrySet())
        //foreach (var pair in args)
        {
            argPairsEncoded.add(oauth.UrlEncode(pair.getKey()) + "=" + oauth.UrlEncode(pair.getValue()));
//            argPairsEncoded.Add(oauth.UrlEncode(pair.Key) + "=" + oauth.UrlEncode(pair.Value));
        }
        String url = this.currentInstallation.base_url + method.name;
        if (use_ssl)
            url = url.replace("http://", "https://");
        if (argPairsEncoded.size() > 0)
        {
            url += "?";
            for(int i =0; i<argPairsEncoded.size(); i++)
            {
                if(i == argPairsEncoded.size()-1)
                    url +=  argPairsEncoded;
                else
                    url += argPairsEncoded + "&";
            }
        }
            //url += "?" + String.join("&", argPairsEncoded);

        // We have our base version of the URL, with no OAuth arguments. Now we will
        // add standard OAuth stuff and sign it given Consumer Secret (and optionally
        // also with Token Secret).

        if (consumer_key == "")
            return url;

        String timestamp = oauth.GenerateTimeStamp();
        String nonce = oauth.GenerateNonce();
        String normalized_url="";
        String normalized_params="";
        String signature = oauth.GenerateSignature( Uri.parse(url), consumer_key,
        //String signature = oauth.GenerateSignature( Uri(url), consumer_key,
                consumer_secret, token, token_secret, "GET", timestamp, nonce, normalized_url,
                normalized_params);
        url = this.currentInstallation.base_url;
        if (use_ssl)
            url = url.replace("http://", "https://");
        url += method.name + "?" + normalized_params + "&oauth_signature=" + URLEncoder.encode(signature);
        //url += method.name + "?" + normalized_params + "&oauth_signature=" + HttpUtility.UrlEncode(signature);

        return url;
    }

    /// <summary>
    /// Get a list of all public USOS API Installations. This list is propagated
    /// among all the installations, so it should be the same, no matter which
    /// installation you call this method on.
    /// </summary>
    public List<ApiInstallation> GetInstallations() throws Exception {
        ArrayList<ApiInstallation> results = new ArrayList<ApiInstallation>();
        String json = GetResponse(this.currentInstallation.base_url + "services/apisrv/installations");
        JSONArray installations = new JSONArray(json);
        for(int i=0; i<installations.length(); i++)
        {
            JSONObject installation = installations.getJSONObject(i);
            results.add(new ApiInstallation(installation.getString("base_url")));
        }
        return results;
//        var results = new List<ApiInstallation>();
//        var json = GetResponse(this.currentInstallation.base_url + "services/apisrv/installations");
//        var installations = JArray.Parse(json);
//        foreach (JObject installation in installations)
//        {
//            results.Add(new ApiInstallation{ base_url = (string)installation["base_url"] });
//        }
//        return results;
    }

    /// <summary>
    /// Get a list of all methods available in this installation.
    /// </summary>
    public List<ApiMethod> GetMethods() throws Exception {
        ArrayList<ApiMethod> methods = new ArrayList<ApiMethod>();
        //var methods = new List<ApiMethod>();
        String json = GetResponse(this.currentInstallation.base_url + "services/apiref/method_index");
        //var json = GetResponse(this.currentInstallation.base_url + "services/apiref/method_index");
        JSONArray jmethods = new JSONArray(json);
        //var jmethods = JArray.Parse(json);
        for(int i=0; i<jmethods.length(); i++)
        //foreach (JObject jmethod in jmethods)
        {
            JSONObject jmethod = jmethods.getJSONObject(i);
            methods.add(new ApiMethod(jmethod.get("name").toString(), jmethod.get("brief_description").toString()));
//            methods.add(new ApiMethod((String))
//            {
//                name = (String)jmethod["name"],
//                        brief_description = (string)jmethod["brief_description"]
//            })
        }
        return methods;
    }

    /// <summary>
    /// Get a full description of a specified method.
    /// </summary>
    /// <param name="method_name">Fully-qualified (should start with "services/") name of a method.</param>
    public ApiMethodFull GetMethod(String method_name) throws Exception {
        String json = GetResponse(this.currentInstallation.base_url + "services/apiref/method?name=" + method_name);
        JSONObject jmethod = new JSONObject(json);
        JSONObject jauthoptions = (JSONObject) jmethod.get("auth_options");
        ApiMethodFull method = new ApiMethodFull( jmethod.get("name").toString(),jmethod.get("brief_description").toString(),
                jmethod.get("description").toString(), jmethod.get("returns").toString(), jmethod.get("ref_url").toString(),
                jauthoptions.get("consumer").toString(), jauthoptions.get("token").toString(),
                        (Boolean) jauthoptions.get("ssl_required"));
        String arguments = jmethod.get("arguments").toString();
        JSONArray jargs = new JSONArray(arguments);
        for(int i=0; i< jargs.length(); i++)
        {
            JSONObject jarg = jargs.getJSONObject(i);
            method.arguments.add(new ApiMethodArgument(jarg.get("name").toString(), (Boolean) jarg.get("is_required"),
                   jarg.get("description").toString(), jarg.get("default_value").toString()));
        }
        return method;
//        var json = GetResponse(this.currentInstallation.base_url + "services/apiref/method?name=" + method_name);
//        var jmethod = JObject.Parse(json);
//        var jauthoptions = (JObject)jmethod["auth_options"];
//        var method = new ApiMethodFull {
//        name = (String)jmethod["name"],
//                brief_description = (string)jmethod["brief_description"],
//                description = (string)jmethod["description"],
//                returns = (string)jmethod["returns"],
//                ref_url = (string)jmethod["ref_url"],
//                auth_options_consumer = (string)jauthoptions["consumer"],
//                auth_options_token = (string)jauthoptions["token"],
//                auth_options_ssl_required = (bool)jauthoptions["ssl_required"]
//    };
//        foreach (JObject jarg in (JArray)jmethod["arguments"])
//        {
//            method.arguments.Add(new ApiMethodArgument {
//            name = (string)jarg["name"],
//                    is_required = (bool)jarg["is_required"],
//                    description = (string)jarg["description"],
//                    default_value = jarg["default_value"].ToString()
//        });
//        }
//        return method;
    }

    /// <summary>
    /// Get a list of all valid scopes (permissions which a Consumer may request access to).
    /// </summary>
    public List<ApiScope> GetScopes() throws Exception {

        String json = GetResponse(this.currentInstallation.base_url + "services/apiref/scopes");
        JSONArray jscopes = new JSONArray(json);
        ArrayList<ApiScope> scopes = new ArrayList<ApiScope>();
        for(int i=0; i<jscopes.length(); i++)
        {
            JSONObject jscope = jscopes.getJSONObject(i);
            scopes.add(new ApiScope( jscope.getString("key"), jscope.getString("developers_description")));
        }
        return scopes;
//        var json = GetResponse(this.currentInstallation.base_url + "services/apiref/scopes");
//        var jscopes = JArray.Parse(json);
//        var scopes = new List<ApiScope>();
//        foreach (JObject jscope in jscopes)
//        {
//            scopes.Add(new ApiScope
//            {
//                key = (string)jscope["key"],
//                        developers_description = (string)jscope["developers_description"],
//            });
//        }
//        return scopes;
    }
}
