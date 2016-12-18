package com.neetpiq.android.retrofitsample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.neetpiq.android.retrofitsample.R;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ITEM = "com.neetpiq.android.retrofitsample.EXTRA_MOVIE_ITEM";

    public static Intent getStartIntent(Context context, String movieTitle) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ITEM, movieTitle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        String movieTitle = getIntent().getStringExtra(EXTRA_MOVIE_ITEM);

        TextView movieTitleView = (TextView) findViewById(R.id.movieTitle);
        movieTitleView.setText(movieTitle);

        setupToolbar();
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setTitle(mWebmark.getTitle());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
