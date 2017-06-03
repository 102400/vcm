package com.vatcore.vcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/3.
 */

public class MineFragment extends Fragment {

    private static MineFragment sMineFragment;

    public static synchronized MineFragment newInstance() {
        if (sMineFragment == null) {
            sMineFragment = new MineFragment();
        }
        return sMineFragment;
    }

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private TextView mNicknameTextView;
    private Button mLogoutButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mine, container, false);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mEditor = mSharedPreferences.edit();

        mNicknameTextView = (TextView) v.findViewById(R.id.fragment_mine_nickname_text_view);
        mLogoutButton = (Button) v.findViewById(R.id.fragment_mine_logout_button);

        mNicknameTextView.setText(mNicknameTextView.getText().toString() + mSharedPreferences.getString("nickname", ""));

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.remove("userId");
                mEditor.remove("nickname");
                mEditor.remove("verify");
                mEditor.apply();

                Intent intent = new Intent(getActivity(), LoginAndRegisterActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        return v;
    }

}
