package com.itarusoft.movies.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itarusoft.movies.Database.MovieContract.MovieEntry;
import com.itarusoft.movies.MovieHolders;
import com.itarusoft.movies.R;
import com.squareup.picasso.Picasso;

public class FavoriteAdapter extends RecyclerView.Adapter<MovieHolders> {

    private Cursor favorite;
    private Context context;

    public FavoriteAdapter(Context context, Cursor cursor){
        this.context = context;
        this.favorite = cursor;
    }

    @Override
    public MovieHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.movie_list, null);

        return new MovieHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(MovieHolders holder, int position) {

        if (!favorite.moveToPosition(position)){
            return;
        }

        int idIndex = favorite.getColumnIndex(MovieEntry._ID);
        int titleIndex = favorite.getColumnIndex(MovieEntry.COLUMN_TITLE);
        int posterIndex = favorite.getColumnIndex(MovieEntry.COLUMN_POSTER);
        int releaseIndex = favorite.getColumnIndex(MovieEntry.COLUMN_RELEASE);
        int synopsisIndex = favorite.getColumnIndex(MovieEntry.COLUMN_SYNOPSIS);
        int voteIndex = favorite.getColumnIndex(MovieEntry.COLUMN_VOTE);

        String posterURL = favorite.getString(posterIndex);

        holder.movieTitle = favorite.getString(titleIndex);
        holder.moviePoster = posterURL;
        holder.movieRelease = favorite.getString(releaseIndex);
        holder.movieSynopsis = favorite.getString(synopsisIndex);
        holder.movieVote = favorite.getString(voteIndex);
        holder.movieId = favorite.getString(idIndex);

        Picasso
                .with(context)
                .load("http://image.tmdb.org/t/p/w185/"+posterURL)
                .placeholder(R.drawable.ic_clear_black_24dp)
                .error(R.drawable.ic_clear_black_24dp)
                .into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        if (favorite != null) {
            return favorite.getCount();
        }
        return 0;
    }
}
