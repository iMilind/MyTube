package com.example.milindmahajan.mytube;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;


public class HomeActivity extends AppCompatActivity {

    private String[] tabs = { "Search", "My Favorites" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabsPagerAdapter());

        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setViewPager(viewPager);
    }


    public class TabsPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {

            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {

            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabs[position].toString();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view = getLayoutInflater().inflate(R.layout.activity_home,
                    container, false);

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }
    }
}