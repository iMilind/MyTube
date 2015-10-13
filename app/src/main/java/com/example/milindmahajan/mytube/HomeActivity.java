package com.example.milindmahajan.mytube;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.milindmahajan.model.File;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity
        extends AppCompatActivity
    implements SearchFragment.SearchFragmentListener {

    ArrayList <File> savedResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager()));

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setViewPager(viewPager);

        if(savedInstanceState != null) {

        }
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
    }



    private class TabsPagerAdapter extends FragmentPagerAdapter {

        private String[] tabs = { "Search", "My Favorites" };

        public TabsPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            if (i == 0) {

                return new SearchFragment();
            }
            else {

                return new FavoriteFragment();
            }
        }

        @Override
        public int getCount() {

            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabs[position];
        }
    }


    @Override
    public void didSelectVideo(String videoId, ArrayList <File> result) {

        try {

            savedResult = result;

            System.out.println("Selected video Id "+videoId);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
            intent.putExtra("VIDEO_ID", videoId);
            intent.putExtra("force_fullscreen",true);
            startActivity(intent);
        } catch (ActivityNotFoundException ex){

            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+videoId));
            startActivity(intent);
        }
    }
}