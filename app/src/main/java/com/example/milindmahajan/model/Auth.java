package com.example.milindmahajan.model;

/**
 * Created by milind.mahajan on 10/13/15.
 */
import com.example.milindmahajan.application_settings.ApplicationSettings;
import com.google.android.gms.common.Scopes;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTubeScopes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;



public class Auth {

    public static final String KEY = "AIzaSyBEvnr-6nEtGMoVS-_MoK78Ne7iC1FPBr0";

    public static final String[] SCOPES = {Scopes.PROFILE, YouTubeScopes.YOUTUBE};
}