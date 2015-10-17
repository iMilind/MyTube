package com.example.milindmahajan.model;

/**
 * Created by milind.mahajan on 10/13/15.
 */

import com.google.android.gms.common.Scopes;
import com.google.api.services.youtube.YouTubeScopes;


public class Auth {

    public static final String APIKEY = "AIzaSyBEvnr-6nEtGMoVS-_MoK78Ne7iC1FPBr0";

    public static final String OAuthKEY = "683723176888-luru1gedl425jc7vg12b43sg31vha84t.apps.googleusercontent.com";
    public static final String[] SCOPES = {"oauth2:"+Scopes.PROFILE, "oauth2:"+YouTubeScopes.YOUTUBE};
}