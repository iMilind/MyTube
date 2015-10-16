package com.example.milindmahajan.mytube;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity
        extends AppCompatActivity
        implements SearchFragment.SearchFragmentListener, FavoriteFragment.FavoriteFragmentListener {

    TabsPagerAdapter tabsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(tabsPagerAdapter);

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.logout_button:

                super.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
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

    @Override
    public void onBackPressed() {

    }

    private class TabsPagerAdapter extends FragmentPagerAdapter {

        private List <Fragment> fragments = new ArrayList<Fragment>();
        private String[] tabs = { "Search", "My Favorites" };

        public TabsPagerAdapter(FragmentManager fm) {

            super(fm);

            fragments.add(new SearchFragment());
            fragments.add(new FavoriteFragment());
        }

        @Override
        public Fragment getItem(int i) {

            return fragments.get(i);
        }

        @Override
        public int getCount() {

            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabs[position];
        }

        public Fragment getFragmentAtPosition (int position) {

            return fragments.get(position);
        }
    }

    @Override
    public void didSelectSearchResult(String videoId) {

        loadVideo(videoId);
    }

    @Override
    public void didAddVideoToFavorites() {

        FavoriteFragment favoriteFragment = (FavoriteFragment) tabsPagerAdapter.getFragmentAtPosition(1);
        favoriteFragment.favoritesModified();
    }

    @Override
    public void didSelectFavoriteResult(String videoId) {

        loadVideo(videoId);
    }

    private void loadVideo (String videoId) {

        try {
            System.out.println("Selected video Id "+videoId);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
            intent.putExtra("force_fullscreen",true);
            startActivity(intent);
        } catch (ActivityNotFoundException ex){

            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+videoId));
            startActivity(intent);
        }
    }
}