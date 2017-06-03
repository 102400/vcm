package com.vatcore.vcm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/6/2.
 */

public class RecommendFragment extends Fragment {

    private static RecommendFragment sRecommendFragment;

    public static synchronized RecommendFragment newInstance() {
        if (sRecommendFragment == null) {
            sRecommendFragment = new RecommendFragment();
        }
        return sRecommendFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recommend, container, false);


        return v;
    }
}
