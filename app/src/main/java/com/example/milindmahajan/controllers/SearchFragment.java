package com.example.milindmahajan.mytube;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.milindmahajan.connectionutil.YouTubeConnector;
import com.example.milindmahajan.model.File;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milind.mahajan on 10/4/15.
 */
public class SearchFragment extends Fragment {

    SearchFragmentListener searchFragmentListener;

    public interface  SearchFragmentListener {

        public void didSelectSearchResult(String videoId);
        public void didAddVideoToFavorites();
    }


    View rootView;
    private Handler handler;
    private Handler favoriteModifiedHandler;
    private ArrayList<File> searchResults;


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        searchFragmentListener = (SearchFragmentListener)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("onCreateView SearchFragment");
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        handler = new Handler();
        favoriteModifiedHandler = new Handler();

        addTextChangeListener();
        addClickListener();

        return rootView;
    }

    private void addTextChangeListener() {

        EditText searchEditText = (EditText) rootView.findViewById(R.id.search_input);

        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                searchOnYoutube(s.toString());
            }
        });
    }

    private void addClickListener(){

        ListView searchvideos = (ListView)rootView.findViewById(R.id.search_videos);
        searchvideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {

                System.out.println("onItemClick Adapter View");
                String videoId = searchResults.get(pos).getId();

                searchFragmentListener.didSelectSearchResult(videoId);
            }

        });
    }

    private void searchOnYoutube(final String keywords) {

        new Thread() {

            public void run() {

                searchResults = YouTubeConnector.searchVideoWithKeywords(keywords);

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

    public void reloadListView (List <File> savedState) {

        if (savedState.size() != 0) {

            updateVideosFound(savedState);
        }
    }


    private void updateVideosFound(List <File> videoList) {

        ArrayAdapter<File> adapter = new ArrayAdapter<File>(getActivity().getApplicationContext(), R.layout.search_item, videoList) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if(convertView == null) {

                    convertView = getActivity().getLayoutInflater().inflate(R.layout.search_item, parent, false);
                }

                final File searchResult = searchResults.get(position);

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
                starButton.setTag(position);

                if (searchResult.isFavorite()) {

                    starButton.setBackgroundResource(android.R.drawable.star_on);
                } else {

                    starButton.setBackgroundResource(android.R.drawable.star_off);
                }

                starButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        int selectedIndex = (int)v.getTag();

                        final File selectedVideo = searchResults.get(selectedIndex);
                        new Thread() {

                            public void run() {

                                final boolean isAdded = YouTubeConnector.insertIntoFavorites(selectedVideo);
                                favoriteModifiedHandler.post(new Runnable() {

                                    public void run() {

                                        if (isAdded) {

                                            searchFragmentListener.didAddVideoToFavorites();
                                        }
                                    }
                                });
                            }
                        }.start();

//                        if (isAdded) {
//
//                            searchFragmentListener.didAddVideoToFavorites();
//                        }
                    }
                });

                Picasso.with(getActivity().getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                publishedDate.setText(searchResult.getPublishedDate());
                numberOfViews.setText(searchResult.getNumberOfViews());

                return convertView;
            }
        };

        ListView searchvideos = (ListView)rootView.findViewById(R.id.search_videos);
        searchvideos.setAdapter(adapter);
    }
}