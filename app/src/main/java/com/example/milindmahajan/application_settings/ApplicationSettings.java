package com.example.milindmahajan.application_settings;

/**
 * Created by milind.mahajan on 10/13/15.
 */
public class ApplicationSettings {

    private static ApplicationSettings sharedSettings = null;

    private String accessToken;
    private String refreshToken;
    private String favoritePlaylistId;

    private ApplicationSettings() {

        accessToken = "";
        refreshToken = "";
        favoritePlaylistId = "PLvHlrhuuRjgWjcspwO0ZapC42l-QKSHmU";
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
}