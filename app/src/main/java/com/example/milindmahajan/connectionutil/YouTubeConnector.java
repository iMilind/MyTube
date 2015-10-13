package com.example.milindmahajan.connectionutil;

import android.content.Context;

import com.example.milindmahajan.model.File;
import com.google.android.gms.auth.api.Auth;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.client.util.Joiner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by milind.mahajan on 10/10/15.
 */
public class YouTubeConnector {

    public static final String KEY = "AIzaSyBEvnr-6nEtGMoVS-_MoK78Ne7iC1FPBr0";
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    private YouTube youtube;
    private YouTube.Search.List query;

    public YouTubeConnector() {

        youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {

            @Override
            public void initialize(HttpRequest hr) throws IOException {

            }
        }).setApplicationName("CmpE277_Lab2").build();

        try{

            query = youtube.search().list("id,snippet");
            query.setKey(KEY);
            query.setType("video");
            query.setFields("items(id/videoId)");
            query.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
        }catch(IOException e){

            e.printStackTrace();
        }
    }

    public ArrayList<File> search(String keywords) {

        query.setQ(keywords);

        try {

            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            ArrayList<File> items = new ArrayList<File>();

            for(SearchResult result:results) {

                File item = new File();

                try {

                    List<String> videoIds = new ArrayList<String>();
                    videoIds.add(result.getId().getVideoId());
                    Joiner stringJoiner = Joiner.on(',');
                    String videoId = stringJoiner.join(videoIds);

                    YouTube.Videos.List listVideosRequest = youtube.videos().list("snippet, statistics").setId(videoId);
                    listVideosRequest.setKey(KEY);
                    VideoListResponse listResponse = listVideosRequest.execute();

                    item.setTitle(listResponse.getItems().get(0).getSnippet().getTitle());
                    item.setPublishedDate(convertDate(listResponse.getItems().get(0).getSnippet().getPublishedAt().toString()));
                    item.setThumbnailURL(listResponse.getItems().get(0).getSnippet().getThumbnails().getDefault().getUrl());
                    item.setId(result.getId().getVideoId());
                    item.setNumberOfViews(listResponse.getItems().get(0).getStatistics().getViewCount().toString());
                } catch (Exception exc) {

                    item.setTitle("Exception case");
                    item.setPublishedDate("Exception date");
                    item.setNumberOfViews("Exception count");
                }

                items.add(item);
            }

            return items;
        }catch(IOException e){

            System.out.println("Exception while search in YouTubeConnector "+e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String convertDate(String fromDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date convertedDate = null;

        try {

            convertedDate = sdf.parse(fromDate);
            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
            String convertedDateString = formatter.format(convertedDate);

            return convertedDateString;
        } catch(Exception ex){

            ex.printStackTrace();
            return null;
        }
    }
}