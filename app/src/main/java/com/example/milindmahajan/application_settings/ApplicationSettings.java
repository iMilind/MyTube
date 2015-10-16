package com.example.milindmahajan.application_settings;

/**
 * Created by milind.mahajan on 10/13/15.
 */
public class ApplicationSettings {

    private static ApplicationSettings sharedSettings = null;

    private String accessToken;
    private String refreshToken;
    private String favoritePlaylistId;
    private String oAuth2ClientId;

    private ApplicationSettings() {

        accessToken = "";
        refreshToken = "";
        favoritePlaylistId = "PLvHlrhuuRjgWjcspwO0ZapC42l-QKSHmU";
        oAuth2ClientId = "683723176888-luru1gedl425jc7vg12b43sg31vha84t.apps.googleusercontent.com";
    }

    public static ApplicationSettings getSharedSettings() {

        if (sharedSettings == null) {

            sharedSettings = new ApplicationSettings();
        }

        return sharedSettings;
    }

    public String getAccessToken() {

        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {

        this.accessToken = accessToken;
    }

    public String getRefreshToken() {

        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {

        this.refreshToken = refreshToken;
    }

    public String getFavoritePlaylistId () {

        return this.favoritePlaylistId;
    }

    public String getoAuth2ClientId () {

        return this.oAuth2ClientId;
    }
}