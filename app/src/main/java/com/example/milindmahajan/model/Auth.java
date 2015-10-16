package com.example.milindmahajan.model;

/**
 * Created by milind.mahajan on 10/13/15.
 */
import com.google.android.gms.common.Scopes;
import com.google.api.services.youtube.YouTubeScopes;

public class Auth {
    // Register an API key here: https://console.developers.google.com
    public static final String KEY = "AIzaSyBEvnr-6nEtGMoVS-_MoK78Ne7iC1FPBr0";

    public static final String[] SCOPES = {Scopes.PROFILE, YouTubeScopes.YOUTUBE};
}