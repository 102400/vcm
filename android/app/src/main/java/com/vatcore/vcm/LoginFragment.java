package com.vatcore.vcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class LoginFragment extends Fragment {

    private static LoginFragment sLoginFragment;

    public static synchronized LoginFragment newInstance() {
        if (sLoginFragment == null) {
            sLoginFragment = new LoginFragment();
        }
        return sLoginFragment;
    }

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private EditText mUsernameOrEmailEditText;
    private EditText mPasswordEditText;
    private TextView mMessageTextView;
    private Button mLoginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mEditor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();

        mUsernameOrEmailEditText = (EditText) v.findViewById(R.id.fragment_login_username_or_email_edit_text);
        mPasswordEditText = (EditText) v.findViewById(R.id.fragment_login_password_edit_text);
        mMessageTextView = (TextView) v.findViewById(R.id.fragment_login_message_text_view);
        mLoginButton = (Button) v.findViewById(R.id.fragment_login_login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String usernameOrEmail = mUsernameOrEmailEditText.getText().toString();
//                String password = mPasswordEditText.getText().toString();
                new LoginTask().execute();
            }
        });


        return v;
    }

    private class LoginTask extends AsyncTask<Void,Void,List<Object>> implements Task {

        private static final String TAG = "LoginTask";
        private String host = Config.HOST;
        String usernameOrEmail = mUsernameOrEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        @Override
        protected List<Object> doInBackground(Void... params) {
            List<Object> objectList = new ArrayList<>(5);
            try{
                String url = Uri.parse("http://" + host + "/mobile/login")
                        .buildUpon()
                        .appendQueryParameter("usernameOrEmail", usernameOrEmail + "")
                        .appendQueryParameter("password", password)
                        .build().toString();
                Log.i(TAG, url);
                String jsonString = getUrlString(url);
                Log.i(TAG, "Received JSON: " + jsonString);
                JSONObject jsonBody = new JSONObject(jsonString);

                boolean isLoginSuccess = jsonBody.getBoolean("isLoginSuccess");
                int userId = jsonBody.getInt("userId");
                String nickname = jsonBody.getString("nickname");
                String verify = jsonBody.getString("verify");
                String message = jsonBody.getString("message");

                objectList.add(isLoginSuccess);
                objectList.add(userId);
                objectList.add(nickname);
                objectList.add(verify);
                objectList.add(message);
            }
            catch(IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ",ioe);
            }
            catch (JSONException je) {
                Log.e(TAG, "Failed to parse JSON", je);
            }
            return objectList;

        }

        @Override
        protected void onPostExecute(List<Object> objectList) {

            boolean isLoginSuccess = false;
            int userId = -1;
            String nickname = "";
            String verify = "";
            String message = "";

            try {
                isLoginSuccess = (boolean) objectList.get(0);
                userId = (Integer) objectList.get(1);
                nickname = (String) objectList.get(2);
                verify = (String) objectList.get(3);
                message = (String) objectList.get(4);
            }
            catch (Exception e) {
                Log.e(TAG,"e",e);
            }

            if(isLoginSuccess) {

//                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                mEditor.putInt("userId", userId);
                mEditor.putString("nickname", nickname);
                mEditor.putString("verify", verify);
                mEditor.apply();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
            else {
                mMessageTextView.setText(message);
            }
        }
    }
}
