package com.itarusoft.movies.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.itarusoft.movies.objects.Movie;
import com.itarusoft.movies.QueryUtils;

import java.util.List;

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private String url;

    public MovieLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if(this.url == null)
        {
            return null;
        }

        return QueryUtils.fetchMovieData(this.url);
    }
}
