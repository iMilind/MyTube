package com.example.milindmahajan.mytube;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.milindmahajan.connectionutil.YouTubeConnector;
import com.example.milindmahajan.model.File;
import com.google.api.services.youtube.YouTube;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milind.mahajan on 10/4/15.
 */
public class FavoriteFragment extends Fragment {

    FavoriteFragmentListener favoriteFragmentListener;

    public interface  FavoriteFragmentListener {

        public void didSelectFavoriteResult(String videoId);
    }


    View rootView;
    private Handler handler;
    private ArrayList<File> searchResults;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        handler = new Handler();

        addClickListener();

        return rootView;
    }

    private void addClickListener(){

        ListView favoriteVideos = (ListView)rootView.findViewById(R.id.favorite_videos);
        favoriteVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {

                System.out.println("onItemClick Adapter View Favorite fragment");
                String videoId = searchResults.get(pos).getId();

                favoriteFragmentListener.didSelectFavoriteResult(videoId);
            }

        });
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        favoriteFragmentListener = (FavoriteFragmentListener)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {

        super.onStart();

        getFavorites();
    }


    private void getFavorites() {

        new Thread() {

            public void run() {

                searchResults = YouTubeConnector.getFavorites();

                if (searchResults.size() != 0) {

                    handler.post(new Runnable() {

                        public void run() {

                            updateVideosFound(searchResults);
                        }
                    });
                }
            }
        }.start();
    }

    private void updateVideosFound(List <File> videoList) {

        ArrayAdapter<File> adapter = new ArrayAdapter<File>(getActivity().getApplicationContext(), R.layout.search_item, videoList) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if(convertView == null) {

                    convertView = getActivity().getLayoutInflater().inflate(R.layout.search_item, parent, false);
                }

                File searchResult = searchResults.get(position);

                if (position % 2 == 0) {

                    convertView.setBackgroundColor(Color.WHITE);
                } else {

                    convertView.setBackgroundColor(Color.LTGRAY);
                }

                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                TextView publishedDate = (TextView)convertView.findViewById(R.id.publishedDate);
                TextView numberOfViews = (TextView)convertView.findViewById(R.id.numberOfViews);
                Button starButton = (Button)convertView.findViewById(R.id.star);
                starButton.setTag(searchResult.getId());

                starButton.setBackgroundResource(android.R.drawable.star_on);

                starButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                    }
                });

                Picasso.with(getActivity().getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                publishedDate.setText(searchResult.getPublishedDate());
                numberOfViews.setText(searchResult.getNumberOfViews());

                return convertView;
            }
        };

        ListView favoriteVideos = (ListView)rootView.findViewById(R.id.favorite_videos);
        favoriteVideos.setAdapter(adapter);
    }

    public void favoritesModified () {

        getFavorites();
    }
}