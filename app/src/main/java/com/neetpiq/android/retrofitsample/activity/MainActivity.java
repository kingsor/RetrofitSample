package com.neetpiq.android.retrofitsample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.neetpiq.android.retrofitsample.R;
import com.neetpiq.android.retrofitsample.adapter.MoviesAdapter;
import com.neetpiq.android.retrofitsample.model.Movie;
import com.neetpiq.android.retrofitsample.model.MoviesResponse;
import com.neetpiq.android.retrofitsample.rest.ApiClient;
import com.neetpiq.android.retrofitsample.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    // insert your themoviedb.org API KEY here
    private final static String API_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                Log.d(TAG, "Request URL: " + call.request().url());
                Log.d(TAG, "Status Code: " + response.code());
                Log.d(TAG, "Number of movies received: " + movies.size() + " Total movies: " + response.body().getTotalResults());
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
