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
import android.widget.LinearLayout;
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

        public void didSelectVideo(String videoId, ArrayList<File> result);
    }


    View rootView;
    private Handler handler;
    private ListView videosFound;
    private ArrayList<File> searchResults;


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        searchFragmentListener = (SearchFragmentListener)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
    }

    @Override
    public void onStart() {

        super.onStart();
        System.out.println("onStart");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("onCreateView");
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        handler = new Handler();

        EditText searchEditText = (EditText) rootView.findViewById(R.id.search_input);
        videosFound = (ListView)rootView.findViewById(R.id.videos_found);

        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                System.out.println("beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                System.out.println("onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {

                System.out.println("afterTextChanged");
                searchOnYoutube(s.toString());
            }
        });

        addClickListener();

        return rootView;
    }

    private void addClickListener(){

        videosFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {

                String videoId = searchResults.get(pos).getId();

                searchFragmentListener.didSelectVideo(videoId, searchResults);
            }

        });
    }

    private void searchOnYoutube(final String keywords) {

        new Thread() {

            public void run() {

                YouTubeConnector yc = new YouTubeConnector();
                searchResults = yc.search(keywords);

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

        videosFound.setAdapter(adapter);
    }
}