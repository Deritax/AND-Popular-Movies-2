package com.itarusoft.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolders> {

    private List<Movie> movies;
    private Context context;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MovieHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.movie_list, null);

        return new MovieHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(MovieHolders holder, int position) {

        String posterURL = movies.get(position).getPoster();

        holder.movieTitle = movies.get(position).getTitle();
        holder.moviePoster = posterURL;
        holder.movieRelease = movies.get(position).getRelease();
        holder.movieSynopsis = movies.get(position).getSynopsis();
        holder.movieVote = movies.get(position).getVote();

        Picasso
                .with(context)
                .load("http://image.tmdb.org/t/p/w185/"+posterURL)
                .placeholder(R.drawable.ic_clear_black_24dp)
                .error(R.drawable.ic_clear_black_24dp)
                .into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }
}
