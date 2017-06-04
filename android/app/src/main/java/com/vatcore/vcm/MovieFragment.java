package com.vatcore.vcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class MovieFragment extends Fragment {

//    private static MovieFragment sMovieFragment;
//
    public static MovieFragment newInstance(Movie movie) {

        MovieFragment movieFragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", movie);
        movieFragment.setArguments(bundle);

        return movieFragment;
    }

    private SharedPreferences mSharedPreferences;

    private ImageView mCoverImageView;
    private TextView mNameZhAndNameOTextView;
    private TextView mRatingsAndUsersTextView;
    private TextView mReleaseDateTextView;
    private TextView mRuntimeTextView;
    private TextView mImdbIdTextView;
    private TextView mDoubanIdTextView;
    private TextView mStorylineTextView;

    private TextView mYourRatingTextView;
    private TextView mCurrentRatingTextView;
    private SeekBar mRatingSeekBar;
    private EditText mCommentEditText;
    private Button mSubmitButton;

    private Movie mMovie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_movie, container, false);

        mMovie = (Movie) getArguments().getSerializable("movie");

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        mCoverImageView = (ImageView) v.findViewById(R.id.fragment_movie_cover_image_view);
        mNameZhAndNameOTextView = (TextView) v.findViewById(R.id.fragment_movie_name_zh_and_name_o_text_view);
        mRatingsAndUsersTextView = (TextView) v.findViewById(R.id.fragment_movie_ratings_and_users_text_view);
        mReleaseDateTextView = (TextView) v.findViewById(R.id.fragment_movie_release_date_text_view);
        mRuntimeTextView = (TextView) v.findViewById(R.id.fragment_movie_runtime_text_view);
        mImdbIdTextView = (TextView) v.findViewById(R.id.fragment_movie_imdb_id_text_view);
        mDoubanIdTextView = (TextView) v.findViewById(R.id.fragment_movie_douban_id_text_view);
        mStorylineTextView = (TextView) v.findViewById(R.id.fragment_movie_storyline_text_view);

        mYourRatingTextView = (TextView) v.findViewById(R.id.fragment_movie_your_rating_text_view);
        mCurrentRatingTextView = (TextView) v.findViewById(R.id.fragment_movie_current_rating_text_view);
        mRatingSeekBar = (SeekBar) v.findViewById(R.id.fragment_movie_rating_seek_bar);
        mCommentEditText = (EditText) v.findViewById(R.id.fragment_movie_comment_edit_text);
        mSubmitButton = (Button) v.findViewById(R.id.fragment_movie_submit_button);

        mNameZhAndNameOTextView.setText(mMovie.getNameZh() + " " + mMovie.getNameO());
        mRatingsAndUsersTextView.setText("评分:" + mMovie.getRatings() + "(" + mMovie.getUsers() + ")");
        mReleaseDateTextView.setText("上映时间:" + mMovie.getReleaseDate());
        mRuntimeTextView.setText("时长:" + mMovie.getRuntime() + "分钟");
        mImdbIdTextView.setText("imdb:tt" + mMovie.getImdbId());
        mDoubanIdTextView.setText("douban:" + mMovie.getDoubanId());
//        mImdbIdTextView.setText(Html.fromHtml("<a href='http://www.imdb.com/title/tt" + mMovie.getImdbId() + "'>imdb:tt" + mMovie.getImdbId() + "</a>", Html.FROM_HTML_MODE_LEGACY));
//        mDoubanIdTextView.setText(Html.fromHtml("<a href='https://movie.douban.com/subject/" + mMovie.getDoubanId() + "'>douban:" + mMovie.getDoubanId() + "</a>", Html.FROM_HTML_MODE_LEGACY));

        mStorylineTextView.setText("简介:\n" + mMovie.getStoryline());

        mRatingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCurrentRatingTextView.setText("当前评分:" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostRatingTask().execute();
            }
        });

        new CoverTask().execute();
        new GetRatingTask().execute();

        return v;
    }

    private class CoverTask extends AsyncTask<Void,Void,Bitmap> implements Task {

        private static final String TAG = "CoverTask";
        private String host = Config.HOST;
        private int doubanId = mMovie.getDoubanId();
//        private int userId = mSharedPreferences.getInt("userId", -1);
//        private String verify = mSharedPreferences.getString("verify", "");

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = null;
            try{
                String url = Uri.parse("http://" + host + "/movie/cover/" + doubanId)
                        .buildUpon()
                        .build().toString();
                Log.i(TAG, url);
                InputStream inputStream = getUrlInputStream(url);
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
            catch(IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ",ioe);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if (bitmap != null) {
                mCoverImageView.setImageBitmap(bitmap);
            }
        }
    }

    private class GetRatingTask extends AsyncTask<Void,Void,List<Object>> implements Task {

        private static final String TAG = "GetRatingTask";
        private String host = Config.HOST;
        private int userId = mSharedPreferences.getInt("userId", -1);
        private String verify = mSharedPreferences.getString("verify", "");
        private int movieId = mMovie.getMovieId();

        @Override
        protected List<Object> doInBackground(Void... params) {
            List<Object> objectList = new ArrayList<>(2);
            try{
                String url = Uri.parse("http://" + host + "/mobile/rating/get")
                        .buildUpon()
                        .appendQueryParameter("userId", userId + "")
                        .appendQueryParameter("verify", verify)
                        .appendQueryParameter("movieId", movieId + "")
                        .build().toString();
                Log.i(TAG, url);
                String jsonString = getUrlString(url);
                Log.i(TAG, "Received JSON: " + jsonString);
                JSONObject jsonBody = new JSONObject(jsonString);

                int rating = jsonBody.getInt("rating");
                String comment = jsonBody.getString("comment");

                objectList.add(rating);
                objectList.add(comment);
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

            int rating = -1;
            String comment = "";

            try {
                rating = (int) objectList.get(0);
                comment = (String) objectList.get(1);
            }
            catch (Exception e) {
                Log.e(TAG,"e",e);
            }

            if (rating >=0 && rating <= 10) {
                mYourRatingTextView.setText("你的评分:" + rating);
                mCurrentRatingTextView.setText("当前评分:" + rating);
                mRatingSeekBar.setProgress(rating);
                mCommentEditText.setText(comment);
            }
        }
    }

    private class PostRatingTask extends AsyncTask<Void,Void,List<Object>> implements Task {

        private static final String TAG = "PostRatingTask";
        private String host = Config.HOST;
        private int userId = mSharedPreferences.getInt("userId", -1);
        private String verify = mSharedPreferences.getString("verify", "");
        private int movieId = mMovie.getMovieId();
        private int rating = mRatingSeekBar.getProgress();
        private String comment = mCommentEditText.getText().toString();

        @Override
        protected List<Object> doInBackground(Void... params) {
            List<Object> objectList = new ArrayList<>(1);
            try{
                String url = Uri.parse("http://" + host + "/mobile/rating/post")
                        .buildUpon()
                        .appendQueryParameter("userId", userId + "")
                        .appendQueryParameter("verify", verify)
                        .appendQueryParameter("movieId", movieId + "")
                        .appendQueryParameter("rating", rating + "")
                        .appendQueryParameter("comment", comment)
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

                mYourRatingTextView.setText("你的评分:" + rating);
                mCurrentRatingTextView.setText("当前评分:" + rating);
                mRatingSeekBar.setProgress(rating);
                mCommentEditText.setText(comment);
            }
        }
    }


}
