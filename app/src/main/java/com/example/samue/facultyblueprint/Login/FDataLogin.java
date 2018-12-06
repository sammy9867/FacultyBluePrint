package com.example.samue.facultyblueprint.Login;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by michael on 04.12.18.
 */

public class FDataLogin extends AsyncTask<Void, Void, Void> {

    private String response = "";

    private String nonce;
    private String timestamp;


    @Override
    protected Void doInBackground(Void... voids ){
        nonce = GenerateNonce();
        timestamp =  GenerateTimeStamp();
        String request_token = "https://apps.usos.pw.edu.pl/services/oauth/request_token" +
                "?oauth_callback=oob" +
                "&oauth_consumer_key=" +LoginActivity.CONSUMER_KEY+
                "&oauth_nonce=" + nonce +
                "&oauth_signature_method=HMAC-SHA1" +
                "&oauth_timestamp=" + timestamp +
                "&oauth_version=1.0" +
                "&oauth_signature="+GenerateSignature();
        URL requestTokenURL;
        try {
            Log.i("url>", request_token);
            requestTokenURL = new URL(request_token);
            HttpURLConnection connection = (HttpURLConnection) requestTokenURL.openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode >= 400 && responseCode <= 499) {
                Log.i("Bad auth", Integer.toString(responseCode)); //provide a more meaningful exception message
            } else {
                InputStream is = connection.getInputStream();
                BufferedReader iBR = new BufferedReader(new InputStreamReader(is));
                String input = "";


                while (true) {
                    input = iBR.readLine();
                    if (input != null)
                        response += input;
                    if (input == null)
                        break;
                }
            }

            } catch(MalformedURLException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
                // TODO: Handle exception
                // possible error 400 and higher */
                Log.i("IOException> ", "Possible error 400 and higher");
            }

        Log.i("response", response);
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        LoginActivity.response = this.response;
    }

    /**
     * Generates Time Stamp for URL requests
     * @return String timestamp
     */
    public String GenerateTimeStamp(){
        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }

    /**
     * Generates Nonce for URL requests
     * UUID is a class that represents an immutable universally unique identifier.
     * A UUID represents a 128-bit value.
     * @return String nonce
     */
    private String GenerateNonce() {
        int r = new Random(System.currentTimeMillis()).nextInt(9_999_999-123400);
        r += 123400;
//        return UUID.randomUUID().toString();
        return ""+r;
    }

    /**
     * Generates Signature for URL requests.
     * @return String signature
     */
    private String GenerateSignature() {
        try {

            return hmacSha1("GET&https%3A%2F%2Fapps.usos.pw.edu.pl%2" +
                    "Fservices%2Foauth%2Frequest_token&oauth_callback%3Doob%26" +
                    "oauth_consumer_key%3D" +
                    LoginActivity.CONSUMER_KEY +
                    "%26oauth_nonce%3D" + nonce +
                    "%26" +
                    "oauth_signature_method%3DHMAC-SHA1%26" +
                    "oauth_timestamp%3D"+timestamp+"%26oauth_version%3D1.0", LoginActivity.CONSUMER_KEY);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Stackoverflow code to generate the signature
     * @param value
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private static String hmacSha1(String value, String key)
            throws UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {
        String type = "HmacSHA1";
        SecretKeySpec secret = new SecretKeySpec(key.getBytes(), type);
        Mac mac = Mac.getInstance(type);
        mac.init(secret);
        byte[] bytes = mac.doFinal(value.getBytes());
        return bytesToHex(bytes);
    }

    private final static char[] hexArray = "0123456789abcdef".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    /// end of Stackoverflow code
}
