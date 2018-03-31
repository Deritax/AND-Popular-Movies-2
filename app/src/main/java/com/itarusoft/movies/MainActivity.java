package com.itarusoft.movies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.itarusoft.movies.Adapters.FavoriteAdapter;
import com.itarusoft.movies.Adapters.MovieAdapter;
import com.itarusoft.movies.Database.MovieContract.MovieEntry;
import com.itarusoft.movies.Loaders.MovieLoader;
import com.itarusoft.movies.Objects.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>{

    private final String KEY_RECYCLER_STATE = "recycler_state";

    private final String KEY_ORDER_STATE = "order_state";

    private static final String API_KEY = BuildConfig.API_KEY;

    private static final String REQUEST_POPULAR = "http://api.themoviedb.org/3/movie/popular?api_key=";

    private static final String REQUEST_RATED = "http://api.themoviedb.org/3/movie/top_rated?api_key=";

    private static final int LOADER_ID = 1;

    private static final int LOADER_FAVORITE = 2;

    private int order;

    private Context context;

    @BindView(R.id.empty) TextView emptyView;
    @BindView(R.id.rv_movies) RecyclerView rvMovies;
    @BindView(R.id.toolbar) Toolbar topToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        order = 0;

        ButterKnife.bind(this);

        int spanCount = 2;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3;
        }

        setSupportActionBar(topToolBar);


        if (savedInstanceState != null) {
            order = savedInstanceState.getInt(KEY_ORDER_STATE);

            Parcelable listState = savedInstanceState.getParcelable(KEY_RECYCLER_STATE);

            rvMovies.getLayoutManager().onRestoreInstanceState(listState);

        } else{


            GridLayoutManager movieLayout = new GridLayoutManager(MainActivity.this, spanCount);

            rvMovies.setHasFixedSize(true);
            rvMovies.setLayoutManager(movieLayout);

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected() && order != 2) {

                LoaderManager loaderManager = getLoaderManager();

                loaderManager.initLoader(LOADER_ID, null, this);
            } else {

                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(LOADER_FAVORITE, null, favoriteLoader);
            }
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
        if(id == R.id.action_favorite){
            order = 2;
        }

        if(order < 2) {
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                getLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);

            } else {
                emptyView.setText(R.string.no_internet_connection);
            }
        }
        else {
            getLoaderManager().initLoader(LOADER_FAVORITE, null, favoriteLoader);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

        String requestUrl = "";

        if (order == 0){
            requestUrl = REQUEST_POPULAR;
        }
        if (order == 1){
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
        getLoaderManager().restartLoader(LOADER_ID,null,this);
    }



    private LoaderManager.LoaderCallbacks<Cursor> favoriteLoader = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            String[] projection = {
                    MovieEntry._ID,
                    MovieEntry.COLUMN_TITLE,
                    MovieEntry.COLUMN_RELEASE,
                    MovieEntry.COLUMN_POSTER,
                    MovieEntry.COLUMN_VOTE,
                    MovieEntry.COLUMN_SYNOPSIS};

            return new CursorLoader(context,
                    MovieEntry.CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

            if (data != null) {
                FavoriteAdapter favoriteAdapter = new FavoriteAdapter(MainActivity.this, data);
                rvMovies.setAdapter(favoriteAdapter);
            }

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            getLoaderManager().restartLoader(LOADER_FAVORITE,null,this);
        }
    };


    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(KEY_ORDER_STATE,order);

        Parcelable listState = rvMovies.getLayoutManager().onSaveInstanceState();

        outState.putParcelable(KEY_RECYCLER_STATE, listState);

        super.onSaveInstanceState(outState);
    }
}
