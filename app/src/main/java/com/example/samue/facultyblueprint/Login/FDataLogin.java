package com.example.samue.facultyblueprint.Login;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.commons.codec.binary.Hex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

    private static final String BASE_URL = "https://apps.usos.pw.edu.pl/";

    private String normalized_url;
    private String normalized_params;


    @Override
    protected Void doInBackground(Void... voids ){
        nonce = GenerateNonce();
        timestamp =  GenerateTimeStamp();
        String request_token = GetMethodURL("request_token");
        /*"https://usosapps.chat.edu.pl/services/oauth/request_token" +
                "?oauth_callback=oob" +
                "&oauth_consumer_key=" +LoginActivity.CONSUMER_KEY+
                "&oauth_nonce=" + nonce +
                "&oauth_signature_method=HMAC-SHA1" +
                "&oauth_timestamp=" + timestamp +
                "&oauth_version=1.0" +
                "&oauth_signature="+GenerateSignature(); */
        URL requestTokenURL;
        try {
            Log.i("url>", request_token);
            requestTokenURL = new URL(request_token);
            HttpURLConnection connection = (HttpURLConnection) requestTokenURL.openConnection();
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
     * Generates URL for a choosen method
     * @param method - String name of the method
     * @return
     */
    private String GetMethodURL(String method) {
        String url = null;

        String timestamp         = "";
        String nonce             = "";
        String signature         = "";
        String signatureType     = "HMACSHA1"; // default

        String method_name       = "";
        String token             = "";
        String token_secret      = "";

        boolean use_ssl = false;
        // can be changed on Map or HashMap or Dictiunary later :-)
        ArrayList<String> args = new ArrayList<String>();


        switch (method){
        case "request_token":
        {
            /* USED: CONSUMER_KEY, CONSUMER_SECRET, use_ssel */
            use_ssl = true;
            args.add("oauth_callback=oob"); // permament
            method_name = "services/oauth/request_token";
            signatureType = "HMACSHA1";
            /* NOT USED: TOKEN, TOKEN_SECRET*/
            token = "";
            token_secret = "";
        }
        default:
        }

        /* Setting the base URL */
        url = BASE_URL + method_name;
//        if(true == use_ssl)
            url.replace("http://", "https://");

        /* Add arguments to url if requires */
        if(args.size() > 0)
            url += "?";
        for(int i=0; i<args.size(); i++) {
            url += args.get(i).replace("[","").replace("]","")
                    .replace(",","").trim();
            if(i+1 != args.size())
                url += "&";
        }

        /*  Add standard OAuth stuff and sign in it given Consumer Secret
         * and optionally also with token secret */

        timestamp = GenerateTimeStamp();
        nonce = GenerateNonce();

        signature = GenerateSignature(url, LoginActivity.CONSUMER_KEY, LoginActivity.CONSUMER_SECRET,
                token, token_secret, "GET", timestamp, nonce, signatureType/*, normalized_url, normalized_params*/);
        Log.i("signature >> ", signature);

        if(normalized_params == null || normalized_url == null){
            Log.i(">>>>>>>>>>", "JJJOOOOPPPPAAAAA");
        }

        Log.i("NORM_URL  >> ",normalized_url);
        Log.i("NORM_PAR  >> ", normalized_params);

        url = BASE_URL;
//        if(true == use_ssl)
            url.replace("http://", "https://");

        url += method_name+"?"+normalized_params+"&oauth_signature="+URLEncoder.encode(signature);

        return url;
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


    private String GenerateSignature(String url, String consumerKey, String consumerSecret,
                                     String token, String token_secret, String httpMethod,
                                     String timestamp, String nonce, String signatureType
                                     /*String normalized_url, String normalized_params*/) {
       normalized_url = null;
       normalized_params = null;

       switch (signatureType) {
           case "PLAINTEXT":
               // TODO: Implement PLAINTEXT if needed
           case "HMACSHA1": {
               // as HMACSHA1 and PLAINTEXT
               String signatureBase = GenerateSignatureBase(url, consumerKey, token, token_secret, httpMethod,
                       timestamp, nonce, "HMAC-SHA1" /*, normalized_url, normalized_params*/);

               String key = URLEncoder.encode(consumerSecret);
               if(token_secret != null)
                   if(!token_secret.equals(""))
                       key+=URLEncoder.encode(token_secret);

               byte[] bytes;
               bytes = key.getBytes(java.nio.charset.Charset.forName("US-ASCII")); // Hope it gets ASCII bytes

//               return GenerateSignatureUsingSHA1(signatureBase, bytes);

               Log.i(">>>>>> ","Before encoding\n\n");
               Log.i("key >> ", key);
               Log.i("sigbase", signatureBase);
               return generateHashWithHmac1(key, signatureBase);
           }
           default:
               return null;
       }

    }

    private String GenerateSignatureUsingSHA1(String signatureBase, byte[] key) {
        byte[] dataBuffer = signatureBase.getBytes(java.nio.charset.Charset.forName("US-ASCII")); // Hope
        //  HmacSHA1 - Algorithm
        byte[] hashBytes = dataBuffer;  // TODO Compute Hash

        return hashBytes.toString();

    }

    private String generateHashWithHmac1(String message, String key) {
        try {
            final String hashingAlgorithm = "HmacSHA1"; //or "HmacSHA1", "HmacSHA512"

            byte[] bytes = hmac(hashingAlgorithm, key.getBytes(java.nio.charset.Charset.forName("US-ASCII")),
                    message.getBytes(java.nio.charset.Charset.forName("US-ASCII")));

            String messageDigest = Base64.encodeToString(bytes, Base64.DEFAULT);


            Log.i("Tag", "message digest: " + messageDigest);
            return  messageDigest;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    /****************
    public static String encode(String key, String data)  {
        Mac sha256_HMAC = null;
        try {
            sha256_HMAC = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret_key = null;
            secret_key = new SecretKeySpec(key.getBytes(java.nio.charset.Charset.forName("US-ASCII")), "HmacSHA256");
            sha256_HMAC.init(secret_key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Log.i("ASS >>>>>> ", data);
        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes(java.nio.charset.Charset.forName("US-ASCII"))));
    }
    */

    /**
     * Generate Signature Base
     * @param url
     * @param consumerKey
     * @param token
     * @param token_secret
     * @param httpMethod
     * @param timestamp
     * @param nonce
     * @param signatureType
     * @return
     */
    private String GenerateSignatureBase(String url, String consumerKey, String token, String token_secret,
                                         String httpMethod, String timestamp, String nonce, String signatureType
                                        /*, String normalized_url, String normalized_params */) {
        if(null == token)
            token = "";

        if(null == token_secret)
            token_secret = "";

        if(null == consumerKey || consumerKey.equals(""))
            return null;

        if(null == httpMethod || httpMethod.equals(""))
            return null;

        if(null == signatureType || signatureType.equals(""))
            return null;


        normalized_params = null;
        normalized_url = null;

        ArrayList<String> parameters = new ArrayList<String>();
            parameters.add("oauth_callback=oob"); /// TODO: make GetParametersFromURL();

        // Add version
        parameters.add("oauth_version=1.0");
        // Add nonce
        parameters.add("oauth_nonce="+nonce);
        // Add timestamp
        parameters.add("oauth_timestamp="+timestamp);
        // Add signature method
        parameters.add("oauth_signature_method="+signatureType);
        // Add consumer keu
        parameters.add("oauth_consumer_key="+consumerKey);
        // Add token
        if(! token.equals(""))
            parameters.add("oauth_token="+token);

        normalized_url = url;
        Log.i("normalized URL >> ", normalized_url);

        normalized_params = "";
        for(int i=0; i<parameters.size(); i++){
            normalized_params += parameters.get(i).replace("[","").replace("]","")
                    .replace(",","").trim();
            if(i+1 != parameters.size())
                normalized_params += "&";
        }
        Log.i("normalized_params  >> ", normalized_params);


        String result = "";
        result += httpMethod.toUpperCase()+"&";
        result += URLEncoder.encode(normalized_url)+"&";
        result += URLEncoder.encode(normalized_params);
        Log.i("Signature base: ", result);

        return result;
    }

}
