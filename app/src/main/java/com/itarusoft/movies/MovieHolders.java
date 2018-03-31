package com.itarusoft.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class MovieHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView ivPoster;
    private Context context;
    public String movieTitle;
    public String movieRelease;
    public String moviePoster;
    public String movieVote;
    public String movieSynopsis;
    public String movieId;

    public MovieHolders(View itemView) {
        super(itemView);

        context = itemView.getContext();

        itemView.setOnClickListener(this);

        ivPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("MOVIE_TITLE",movieTitle);
        intent.putExtra("MOVIE_RELEASE",movieRelease);
        intent.putExtra("MOVIE_POSTER",moviePoster);
        intent.putExtra("MOVIE_VOTE",movieVote);
        intent.putExtra("MOVIE_SYNOPSIS",movieSynopsis);
        intent.putExtra("MOVIE_ID",movieId);
        context.startActivity(intent);
    }
}
