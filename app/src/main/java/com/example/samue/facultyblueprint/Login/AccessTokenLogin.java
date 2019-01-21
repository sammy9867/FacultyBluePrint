package com.example.samue.facultyblueprint.Login;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.scribe.model.Verifier;

/**
 * Created by michael on 13.12.18.
 */

public class AccessTokenLogin extends AsyncTask<String, String, String> {


    @Override
    protected String doInBackground(String... voids) {

        try {
            if(User.service == null)
                return null;

            String PIN = "";
            PIN = EnterPINActivity.etPIN.getText().toString();
            Verifier verifier = new Verifier(PIN);
            Log.i("PIN:=", PIN);

            if(verifier != null && !PIN.isEmpty()) {

                User.accessToken =
                        User.service.getAccessToken(User.requestToken, verifier);
                Log.i("ACCESS TOKEN >> ", User.accessToken.getRawResponse() );
                Log.i("\tTOKEN >> ", User.accessToken.getToken());
                Log.i("\tTOKEN >> ", User.accessToken.getSecret());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
