package com.itarusoft.movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.iv_mini_poster) ImageView ivMiniPoster;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_release) TextView tvRelease;
    @BindView(R.id.tv_vote) TextView tvVote;
    @BindView(R.id.tv_synapsis) TextView tvSynapsis;
    @BindView(R.id.toolbar_detail) Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();

        String movieTitle = intent.getStringExtra("MOVIE_TITLE");
        String movieRelease = intent.getStringExtra("MOVIE_RELEASE");
        String moviePoster = intent.getStringExtra("MOVIE_POSTER");
        String movieVote = intent.getStringExtra("MOVIE_VOTE");
        String movieSynopsis = intent.getStringExtra("MOVIE_SYNOPSIS");

        Picasso
                .with(this)
                .load("http://image.tmdb.org/t/p/w92/"+moviePoster)
                .placeholder(R.drawable.ic_clear_black_24dp)
                .error(R.drawable.ic_clear_black_24dp)
                .into(ivMiniPoster);

        tvTitle.setText(movieTitle);
        tvRelease.setText(movieRelease);
        tvVote.setText(movieVote);
        tvSynapsis.setText(movieSynopsis);
    }
}
