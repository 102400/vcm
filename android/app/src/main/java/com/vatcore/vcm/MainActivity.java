package com.vatcore.vcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    private static final List<Fragment> sFragmentList = new ArrayList<>();

    static {
        sFragmentList.add(0, SearchFragment.newInstance());
        sFragmentList.add(1, RecommendFragment.newInstance());
        sFragmentList.add(2, MineFragment.newInstance());
    }

    private ViewPager mViewPager;
    private Button[] indexButton = new Button[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.activity_main_view_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                return sFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return sFragmentList.size();
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                indexButton[position].setBackgroundColor(Color.GREEN);
                for (int i=0; i<indexButton.length; i++) {
                    if (i != position) {
                        indexButton[i].setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });

        mViewPager.setCurrentItem(0);


        indexButton[0] = (Button) findViewById(R.id.activity_main_search_button);
        indexButton[1] = (Button) findViewById(R.id.activity_main_recommend_button);
        indexButton[2] = (Button) findViewById(R.id.activity_main_mine_button);

        for (final int[] i={0}; i[0]<indexButton.length; i[0]++) {
            indexButton[i[0]].setOnClickListener(new View.OnClickListener() {
                private int a = i[0];
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(a);
                }
            });
        }


    }


}