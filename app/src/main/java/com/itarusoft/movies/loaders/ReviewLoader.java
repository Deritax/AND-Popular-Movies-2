package com.itarusoft.movies.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.itarusoft.movies.objects.Review;
import com.itarusoft.movies.QueryUtils;

import java.util.List;

public class ReviewLoader extends AsyncTaskLoader<List<Review>> {

    private String url;

    public ReviewLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Review> loadInBackground() {
        if(this.url == null)
        {
            return null;
        }

        return QueryUtils.fetchReviewData(this.url);
    }
}
