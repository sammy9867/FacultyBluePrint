package com.example.samue.facultyblueprint.Login;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;


public class oAuthProvider extends DefaultApi10a {

    private static final String AUTHORIZE_URL=
            "https://apps.usos.pw.edu.pl/services/oauth/authorize";
    @Override
    public String getRequestTokenEndpoint() {
        return "https://apps.usos.pw.edu.pl/services/oauth/request_token";
    }

    @Override
    public String getAccessTokenEndpoint() {
        return  "https://apps.usos.pw.edu.pl/services/oauth/access_token";
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return AUTHORIZE_URL +"?oauth_token=" + requestToken.getToken();
    }
}