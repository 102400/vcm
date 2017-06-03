package com.vatcore.vcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vatcore.vcm.R;

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
 * Created by Administrator on 2017/6/3.
 */

public class StartPageActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        if (mSharedPreferences.getInt("userId", -1) != -1 || !"".equals(mSharedPreferences.getString("verify", ""))) {

            //服务器验证成功后跳转MainActivity
            new VerifyTask().execute();
        }
        else {
            Intent intent = new Intent(StartPageActivity.this, LoginAndRegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private class VerifyTask extends AsyncTask<Void,Void,List<Object>> implements Task {

        private static final String TAG = "VerifyTask";
        private String host = Config.HOST;
        int userId = mSharedPreferences.getInt("userId", -1);
        String verify = mSharedPreferences.getString("verify", "");

        @Override
        protected List<Object> doInBackground(Void... params) {
            List<Object> objectList = new ArrayList<>(1);
            try{
                String url = Uri.parse("http://" + host + "/mobile/verify")
                        .buildUpon()
                        .appendQueryParameter("userId", userId + "")
                        .appendQueryParameter("verify", verify)
                        .build().toString();
                Log.i(TAG, url);
                String jsonString = getUrlString(url);
                Log.i(TAG, "Received JSON: " + jsonString);
                JSONObject jsonBody = new JSONObject(jsonString);

                boolean isSuccess = jsonBody.getBoolean("isSuccess");

                objectList.add(isSuccess);
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

            try {
                isSuccess = (boolean) objectList.get(0);
            }
            catch (Exception e) {
                Log.e(TAG,"e",e);
            }

            if (isSuccess) {
                Intent intent = new Intent(StartPageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                mEditor.remove("userId");
                mEditor.remove("nickname");
                mEditor.remove("verify");
                mEditor.apply();

                Intent intent = new Intent(StartPageActivity.this, LoginAndRegisterActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

}
