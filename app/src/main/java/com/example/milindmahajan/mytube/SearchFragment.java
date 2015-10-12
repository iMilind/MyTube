package com.example.milindmahajan.mytube;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.milindmahajan.connectionutil.YouTubeConnector;
import com.example.milindmahajan.model.File;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by milind.mahajan on 10/4/15.
 */
public class SearchFragment extends Fragment {

    SearchFragmentListener searchFragmentListener;

    public interface  SearchFragmentListener {

        public void didSelectVideo(String videoId);
    }


    View rootView;
    private Handler handler;
    private ListView videosFound;
    private List<File> searchResults;


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

                searchFragmentListener.didSelectVideo(videoId);
            }

        });
    }

    private void searchOnYoutube(final String keywords) {

        new Thread() {

            public void run() {

                YouTubeConnector yc = new YouTubeConnector(rootView.getContext());
                searchResults = yc.search(keywords);

                if (searchResults.size() != 0) {

                    handler.post(new Runnable() {

                        public void run() {

                            updateVideosFound();
                        }
                    });
                }
            }
        }.start();
    }


    private void updateVideosFound() {

        ArrayAdapter<File> adapter = new ArrayAdapter<File>(getActivity().getApplicationContext(), R.layout.search_item, searchResults) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if(convertView == null) {

                    convertView = getActivity().getLayoutInflater().inflate(R.layout.search_item, parent, false);
                }

                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                TextView description = (TextView)convertView.findViewById(R.id.video_description);

                File searchResult = searchResults.get(position);

                Picasso.with(getActivity().getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                description.setText(searchResult.getDescription());

                return convertView;
            }
        };

        videosFound.setAdapter(adapter);
    }
}