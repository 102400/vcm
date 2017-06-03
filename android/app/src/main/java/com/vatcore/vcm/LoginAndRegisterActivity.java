package com.vatcore.vcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class LoginAndRegisterActivity extends AppCompatActivity {

    private static final List<Fragment> sFragmentList = new ArrayList<>();

    private ViewPager mViewPager;
    private Button[] indexButton = new Button[2];

    static {
        sFragmentList.add(0, LoginFragment.newInstance());
        sFragmentList.add(1, RegisterFragment.newInstance());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);

        mViewPager = (ViewPager) findViewById(R.id.activity_login_and_register_view_pager);

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


        indexButton[0] = (Button) findViewById(R.id.activity_login_and_register_login_button);
        indexButton[1] = (Button) findViewById(R.id.activity_login_and_register_register_button);

        indexButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        indexButton[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });

    }

}
