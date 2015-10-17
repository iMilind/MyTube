package com.example.milindmahajan.mytube;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class HomeActivity
        extends AppCompatActivity
        implements SearchFragment.SearchFragmentListener, FavoriteFragment.FavoriteFragmentListener {

    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.tabFrameLayout);

        tabHost.addTab(
                tabHost.newTabSpec("Search")
                        .setIndicator(getTabIndicator(tabHost.getContext(), R.string.search, android.R.drawable.ic_menu_search)),
                SearchFragment.class, null);
        tabHost.addTab(
                tabHost.newTabSpec("Favorite")
                        .setIndicator(getTabIndicator(tabHost.getContext(), R.string.favorites, android.R.drawable.star_on)),
                FavoriteFragment.class, null);
    }

    private View getTabIndicator(Context context, int title, int icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageResource(icon);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title);
        return view;
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


    @Override
    public void didSelectSearchResult(String videoId) {

        loadVideo(videoId);
    }

    @Override
    public void didAddVideoToFavorites() {

//        FavoriteFragment favoriteFragment = (FavoriteFragment) tabsPagerAdapter.getFragmentAtPosition(1);
//        favoriteFragment.favoritesModified();
    }

    @Override
    public void didSelectFavoriteResult(String videoId) {

        loadVideo(videoId);
    }

    @Override
    public void didModifyFavorites () {

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