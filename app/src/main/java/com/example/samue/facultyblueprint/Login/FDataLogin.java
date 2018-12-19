package com.example.samue.facultyblueprint.Login;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by michael on 04.12.18.
 */

public class FDataLogin extends AsyncTask<Void, Void, Void> {

    private String response = "";
    private String url;

    public FDataLogin(String url){
        this.url = url;
    }

    public String getResponse(){
        return response;
    }

    @Override
    protected Void doInBackground(Void... voids ){
        Log.i("url >>>", this.url);

        URL requestTokenURL;
        HttpURLConnection connection;
        try {

            requestTokenURL = new URL(this.url);
            connection = (HttpURLConnection) requestTokenURL.openConnection();

            int response_code = connection.getResponseCode();
            Log.i("Responce code >> ", "" + response_code);
            Log.i("Responce msge >> ", "" + connection.getResponseMessage());
            Log.i("Request  msge >> ", "" + connection.getRequestMethod());


            InputStream is = connection.getInputStream();
            BufferedReader iBR = new BufferedReader(new InputStreamReader(is));
            String input = "";


            while (true){
                input = iBR.readLine();
                if(input != null)
                    response+= input;
                if(input == null)
                    break;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("FData Call >>>", url);
        Log.i("FData Response", response);

        return null;
    }


}
