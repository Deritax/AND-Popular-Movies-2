package com.itarusoft.movies.Loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.itarusoft.movies.Objects.Video;
import com.itarusoft.movies.QueryUtils;

import java.util.List;

public class VideoLoader extends AsyncTaskLoader<List<Video>> {

    private String url;

    public VideoLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Video> loadInBackground() {
        if(this.url == null)
        {
            return null;
        }

        return QueryUtils.fetchVideoData(this.url);
    }
}
