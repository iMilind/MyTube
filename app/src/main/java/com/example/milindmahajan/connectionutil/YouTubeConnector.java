package com.example.milindmahajan.connectionutil;

import android.content.Context;

import com.example.milindmahajan.model.File;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by milind.mahajan on 10/10/15.
 */
public class YouTubeConnector {

    public static final String KEY = "AIzaSyBEvnr-6nEtGMoVS-_MoK78Ne7iC1FPBr0";

    private YouTube youtube;
    private YouTube.Search.List query;

    public YouTubeConnector(Context context) {

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
            query.setFields("items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/default/url)");
        }catch(IOException e){

            e.printStackTrace();
        }
    }

    public List<File> search(String keywords) {

        query.setQ(keywords);

        try {

            SearchListResponse response = query.execute();
            List<SearchResult> results = response.getItems();

            List<File> items = new ArrayList<File>();
            for(SearchResult result:results) {

                File item = new File();
                item.setTitle(result.getSnippet().getTitle());
                item.setDescription(result.getSnippet().getDescription());
                item.setThumbnailURL(result.getSnippet().getThumbnails().getDefault().getUrl());
                item.setId(result.getId().getVideoId());
                items.add(item);
            }

            return items;
        }catch(IOException e){

            System.out.println("Exception while search in YouTubeConnector "+e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }
}