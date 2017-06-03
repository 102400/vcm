package com.vatcore.vcm;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/2.
 */

public class SearchFragment extends Fragment {

    private static SearchFragment sSearchFragment;

    public static synchronized SearchFragment newInstance() {
        if (sSearchFragment == null) {
            sSearchFragment = new SearchFragment();
        }
        return sSearchFragment;
    }

    private EditText mSearchEditText;
    private Button mSearchButton;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchEditText = (EditText) v.findViewById(R.id.fragment_search_search_edit_text);
        mSearchButton = (Button) v.findViewById(R.id.fragment_search_search_button);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_search_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(mSearchEditText.getText().toString())) {
                    new SearchTask().execute();
                }
            }
        });

        return v;
    }

    private class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mRatingsAndUsersTextView;
        private TextView mNameZhTextView;

        private Movie mMovie;

        public MovieHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mRatingsAndUsersTextView = (TextView) itemView.findViewById(R.id.list_item_movie_ratings_and_users_text_view);
            mNameZhTextView = (TextView) itemView.findViewById(R.id.list_item_movie_name_zh_text_view);
        }

        public void bindMovie(Movie movie) {
            mMovie = movie;
            mRatingsAndUsersTextView.setText(mMovie.getRatings() + "(" + mMovie.getUsers() + ")");
            mNameZhTextView.setText(mMovie.getNameZh());
        }

        @Override
        public void onClick(View v) {


            //跳转activity_movie
        }

    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

        private List<Movie> mMovieList;

        public MovieAdapter(List<Movie> movieList) {
            mMovieList = movieList;
        }

        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_movie,parent,false);
            return new MovieHolder(view);
        }

        @Override
        public void onBindViewHolder(MovieHolder holder, int position) {
            Movie movie = mMovieList.get(position);
            holder.bindMovie(movie);
        }

        @Override
        public int getItemCount() {
            return mMovieList.size();
        }

        public void setCrimes(List<Movie> movieList) {
            mMovieList = movieList;
        }
    }

    private class SearchTask extends AsyncTask<Void,Void,List<Object>> implements Task {

        private static final String TAG = "SearchTask";
        private String host = Config.HOST;
        private String q = mSearchEditText.getText().toString();

        @Override
        protected List<Object> doInBackground(Void... params) {
            List<Object> objectList = new ArrayList<>(1);
            try{
                String url = Uri.parse("http://" + host + "/mobile/search")
                        .buildUpon()
                        .appendQueryParameter("q", q)
                        .build().toString();
                Log.i(TAG, url);
                String jsonString = getUrlString(url);
                Log.i(TAG, "Received JSON: " + jsonString);
                JSONObject jsonBody = new JSONObject(jsonString);

                JSONArray movieArray = jsonBody.getJSONArray("movieList");
                List<Movie> movieList = new ArrayList<>(movieArray.length());

                for (int i=0;i <movieArray.length(); i++) {
                    JSONObject jsonObject = movieArray.getJSONObject(i);

                    Movie movie = new Movie();
                    movie.setMovieId(jsonObject.getInt("movieId"));
                    movie.setDoubanId(jsonObject.getInt("doubanId"));
                    movie.setImdbId(jsonObject.getInt("imdbId"));
                    movie.setNameZh(jsonObject.getString("nameZh"));
                    movie.setNameO(jsonObject.getString("nameO"));
                    movie.setRatings((float) jsonObject.getDouble("ratings"));
                    movie.setUsers(jsonObject.getInt("users"));
                    movie.setReleaseDate(jsonObject.getString("releaseDate"));
                    movie.setRuntime((short) jsonObject.getInt("runtime"));
                    movie.setStoryline(jsonObject.getString("storyline"));

                    movieList.add(i, movie);
                }
                objectList.add(movieList);
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

            List<Movie> movieList = null;

            try {
                movieList = (List<Movie>) objectList.get(0);
//                for (int i=0; i<movieList.size(); i++) {
//                    Log.i(TAG, movieList.get(i).getNameZh());
//                }
            }
            catch (Exception e) {
                Log.e(TAG,"e",e);
            }

            if (movieList != null) {
                mRecyclerView.setAdapter(new MovieAdapter(movieList));

            }
        }
    }
}
