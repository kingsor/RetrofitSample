package com.neetpiq.android.retrofitsample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.neetpiq.android.retrofitsample.R;
import com.neetpiq.android.retrofitsample.adapter.MoviesAdapter;
import com.neetpiq.android.retrofitsample.model.Movie;
import com.neetpiq.android.retrofitsample.model.MoviesResponse;
import com.neetpiq.android.retrofitsample.rest.ApiClient;
import com.neetpiq.android.retrofitsample.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    // insert your themoviedb.org API KEY here
    private final static String API_KEY = "9ad3ffdee95587d256af31cb3f0a9f16";

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(movies);
        moviesAdapter.setOnItemClickListener(new MoviesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Movie movie = movies.get(position);
                Toast.makeText(MainActivity.this, "Clicked " + movie.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(moviesAdapter);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        if (API_KEY.isEmpty()) {
            String message = "API KEY is missing. Please obtain it first from themoviedb.org";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            Log.e(TAG, message);
            return;
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

//        Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
        Call<MoviesResponse> call = apiService.getPopularMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                //List<Movie> movies = response.body().getResults();
                movies.addAll(response.body().getResults());
                Log.d(TAG, "Request URL: " + call.request().url());
                Log.d(TAG, "Status Code: " + response.code());
                Log.d(TAG, "Number of movies received: " + movies.size() + " Total movies: " + response.body().getTotalResults());
                //recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.item_movie, getApplicationContext()));
                moviesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
