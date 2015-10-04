package com.example.milindmahajan.mytube;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by milind.mahajan on 10/4/15.
 */
public class SearchFragment extends android.support.v4.app.Fragment {

    private String title;
    private int page;
    private View parentView;

    public static SearchFragment newInstance(int page, String title) {

        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putInt("0", page);
        args.putString("Search", title);
        searchFragment.setArguments(args);

        return searchFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        page = getArguments().getInt("0", 0);
        title = getArguments().getString("Search");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_search, container, false);

        return parentView;
    }
//
//    @Override
//    public void onAttach(Context context) {
//
//        super.onAttach(context);
//
//        try {
//
//        }
//        catch (ClassCastException exception) {
//
//            throw new ClassCastException(context.toString());
//        }
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//
//        super.onActivityCreated(savedInstanceState);
//
//        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
//    }
}