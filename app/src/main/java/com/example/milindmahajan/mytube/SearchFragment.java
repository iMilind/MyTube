package com.example.milindmahajan.mytube;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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

    View rootView;
    private Handler handler;
    private ListView videosFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        handler = new Handler();

        EditText searchEditText = (EditText) rootView.findViewById(R.id.search_input);

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    searchOnYoutube(v.getText().toString());

                    return false;
                }

                return true;
            }
        });

        return rootView;
    }

    private List<File> searchResults;

    private void searchOnYoutube(final String keywords) {

        new Thread(){

            public void run(){

                YouTubeConnector yc = new YouTubeConnector(rootView.getContext());
                searchResults = yc.search(keywords);
                handler.post(new Runnable(){

                    public void run(){

                        updateVideosFound();
                    }
                });
            }
        }.start();
    }

    private void updateVideosFound() {

        ArrayAdapter<File> adapter = new ArrayAdapter<File>(rootView.getContext(), R.layout.search_item, searchResults) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if(convertView == null){

                    convertView = getActivity().getLayoutInflater().inflate(R.layout.search_item, parent, false);
                }

                ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView)convertView.findViewById(R.id.video_title);
                TextView description = (TextView)convertView.findViewById(R.id.video_description);

                File searchResult = searchResults.get(position);

                Picasso.with(rootView.getContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                description.setText(searchResult.getDescription());

                return convertView;
            }
        };

        videosFound.setAdapter(adapter);
    }
}