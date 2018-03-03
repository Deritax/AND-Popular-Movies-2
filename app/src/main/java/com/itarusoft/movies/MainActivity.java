package com.itarusoft.movies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>{

    private static final String API_KEY = BuildConfig.API_KEY;

    private static final String REQUEST_POPULAR = "http://api.themoviedb.org/3/movie/popular?api_key=";

    private static final String REQUEST_RATED = "http://api.themoviedb.org/3/movie/top_rated?api_key=";

    private static final int LOADER_ID = 1;

    private int order = 0;

    @BindView(R.id.empty) TextView emptyView;
    @BindView(R.id.rv_movies) RecyclerView rvMovies;
    @BindView(R.id.toolbar) Toolbar topToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        int spanCount = 2;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 3;
        }

        setSupportActionBar(topToolBar);

        GridLayoutManager movieLayout = new GridLayoutManager(MainActivity.this, spanCount);

        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(movieLayout);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(LOADER_ID, null, this);
        } else {

            emptyView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_popular){
            order = 0;
        }
        if(id == R.id.action_rated){
            order = 1;
        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().restartLoader(0, null, MainActivity.this);

        } else {
            emptyView.setText(R.string.no_internet_connection);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

        String requestUrl;

        if (order == 0){
            requestUrl = REQUEST_POPULAR;
        } else {
            requestUrl = REQUEST_RATED;
        }

        requestUrl += API_KEY;

        return new MovieLoader(this, requestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        if (movies != null && !movies.isEmpty()) {
            MovieAdapter moviesAdapter = new MovieAdapter(MainActivity.this, movies);
            rvMovies.setAdapter(moviesAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
    }
}
