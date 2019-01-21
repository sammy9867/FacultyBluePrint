package com.example.samue.facultyblueprint.Login;

import android.os.AsyncTask;
import android.util.Log;

import org.scribe.builder.ServiceBuilder;


public class RequestTokenLogin extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... voids) {

        try {
            User.service = new ServiceBuilder()
                    .provider(oAuthProvider.class)
                    .apiKey(User.CONSUMER_KEY)
                    .apiSecret(User.CONSUMER_SECRET)
                    .callback(User.CALLBACK)
                    .scope("cards,grades,email,offline_access,personal,photo")
                    .build();

            //REQUEST TOKEN
            Log.i("REQUESTING TOKEN", "TOKEN");
            User.requestToken = User.service.getRequestToken();
            Log.i("RT RAW RESPONSE",User.requestToken.getRawResponse());


            //REQUEST URL
            Log.i("REQUESTING URL", "URL");

            if(User.requestToken.getToken() != null
                    && User.requestToken.getSecret() != null) {
                User.authorizationUrl =
                        User.service.getAuthorizationUrl(User.requestToken);
                Log.i("URL PIN >>>> ",User.authorizationUrl);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}