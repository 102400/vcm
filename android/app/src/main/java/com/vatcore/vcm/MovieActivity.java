package com.vatcore.vcm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class MovieActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    private List<Movie> mMovieList;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mMovieList = (List<Movie>) bundle.getSerializable("movieList");
        int position = bundle.getInt("position");

        mViewPager = (ViewPager) findViewById(R.id.activity_movie_view_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                return MovieFragment.newInstance(mMovieList.get(position));
            }

            @Override
            public int getCount() {
                return mMovieList.size();
            }
        });

        mViewPager.setCurrentItem(position);
    }

}
