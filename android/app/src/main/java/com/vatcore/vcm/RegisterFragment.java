package com.vatcore.vcm;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class RegisterFragment extends Fragment {

    private static RegisterFragment sRegisterFragment;

    public static synchronized RegisterFragment newInstance() {
        if (sRegisterFragment == null) {
            sRegisterFragment = new RegisterFragment();
        }
        return sRegisterFragment;
    }

    private EditText mNicknameEditText;
    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private TextView mMessageTextView;
    private Button mRegisterButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);

        mNicknameEditText = (EditText) v.findViewById(R.id.fragment_register_nickname_edit_text);
        mUsernameEditText = (EditText) v.findViewById(R.id.fragment_register_username_edit_text);
        mEmailEditText = (EditText) v.findViewById(R.id.fragment_register_email_edit_text);
        mPasswordEditText = (EditText) v.findViewById(R.id.fragment_register_password_edit_text);
        mMessageTextView = (TextView) v.findViewById(R.id.fragment_register_message_text_view);
        mRegisterButton = (Button) v.findViewById(R.id.fragment_register_register_button);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegisterTask().execute();
            }
        });


        return v;
    }

    private class RegisterTask extends AsyncTask<Void,Void,List<Object>> implements Task {

        private static final String TAG = "RegisterTask";
        private String host = Config.HOST;
        String nickname = mNicknameEditText.getText().toString();
        String username = mUsernameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        @Override
        protected List<Object> doInBackground(Void... params) {
            List<Object> objectList = new ArrayList<>(2);
            try{
                String url = Uri.parse("http://" + host + "/mobile/register")
                        .buildUpon()
                        .appendQueryParameter("nickname", nickname)
                        .appendQueryParameter("username", username)
                        .appendQueryParameter("email", email)
                        .appendQueryParameter("password", password)
                        .build().toString();
                Log.i(TAG, url);
                String jsonString = getUrlString(url);
                Log.i(TAG, "Received JSON: " + jsonString);
                JSONObject jsonBody = new JSONObject(jsonString);

                boolean isSuccess = jsonBody.getBoolean("isSuccess");
                String message = jsonBody.getString("info");

                objectList.add(isSuccess);
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

            boolean isSuccess = false;
            String message = "";

            try {
                isSuccess = (boolean) objectList.get(0);
                message = (String) objectList.get(1);
            }
            catch (Exception e) {
                Log.e(TAG,"e",e);
            }

            if(isSuccess) {
                mMessageTextView.setText("注册成功");
            }
            else {
                mMessageTextView.setText("注册失败");
            }
        }
    }

}
