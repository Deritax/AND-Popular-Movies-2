package com.itarusoft.movies;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.itarusoft.movies.Adapters.ReviewAdapter;
import com.itarusoft.movies.Adapters.VideoAdapter;
import com.itarusoft.movies.Database.MovieContract.MovieEntry;
import com.itarusoft.movies.Loaders.ReviewLoader;
import com.itarusoft.movies.Loaders.VideoLoader;
import com.itarusoft.movies.Objects.Review;
import com.itarusoft.movies.Objects.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.itarusoft.movies.BuildConfig.API_KEY;

public class DetailActivity extends AppCompatActivity {

    Context context = this;

    private static final int LOADER_ID_VIDEO = 1;

    private static final int LOADER_ID_REVIEW = 2;

    private Uri currentItemUri;

    private String movieId;
    private String movieTitle;
    private String movieRelease;
    private String moviePoster;
    private String movieVote;
    private String movieSynopsis;

    @BindView(R.id.iv_mini_poster) ImageView ivMiniPoster;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_release) TextView tvRelease;
    @BindView(R.id.tv_vote) TextView tvVote;
    @BindView(R.id.tv_synapsis) TextView tvSynapsis;
    @BindView(R.id.toolbar_detail) Toolbar toolbar;
    @BindView(R.id.bt_favorite) Button btFavorite;

    private LoaderManager.LoaderCallbacks<List<Video>> videoLoader = new LoaderManager.LoaderCallbacks<List<Video>>() {

        @Override
        public Loader<List<Video>> onCreateLoader(int id, Bundle args) {

            String requestUrl = "http://api.themoviedb.org/3/movie/"+movieId+"/videos?api_key=";

            requestUrl += API_KEY;

            return new VideoLoader(context, requestUrl);
        }

        @Override
        public void onLoadFinished(Loader<List<Video>> loader, List<Video> videos) {

            ListView lvVideos = (ListView)findViewById(R.id.lv_videos);

            VideoAdapter videoAdapter = new VideoAdapter(context, R.layout.video_list, videos);
            lvVideos.setAdapter(videoAdapter);

        }

        @Override
        public void onLoaderReset(Loader<List<Video>> loader) {
            getLoaderManager().restartLoader(LOADER_ID_VIDEO, null, this);
        }
    };


    private LoaderManager.LoaderCallbacks<List<Review>> reviewLoader = new LoaderManager.LoaderCallbacks<List<Review>>() {
        @Override
        public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
            String requestUrl = "http://api.themoviedb.org/3/movie/"+movieId+"/reviews?api_key=";

            requestUrl += API_KEY;

            return new ReviewLoader(context, requestUrl);
        }

        @Override
        public void onLoadFinished(Loader<List<Review>> loader, List<Review> reviews) {

            ListView lvReview = (ListView)findViewById(R.id.lv_reviews);

            ReviewAdapter reviewAdapter = new ReviewAdapter(context, R.layout.review_list, reviews);
            lvReview.setAdapter(reviewAdapter);

        }

        @Override
        public void onLoaderReset(Loader<List<Review>> loader) {
            getLoaderManager().restartLoader(LOADER_ID_REVIEW, null, this);
        }
    };


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

        movieTitle = "";
        movieRelease = "";
        moviePoster = "";
        movieVote = "";
        movieSynopsis = "";
        movieId = "";

        Intent intent = getIntent();

        currentItemUri = intent.getData();

        Bundle extras = intent.getExtras();

        if (extras != null) {
            if (extras.containsKey("MOVIE_TITLE")) {
                movieTitle = intent.getStringExtra("MOVIE_TITLE");
            }
            if (extras.containsKey("MOVIE_RELEASE")) {
                movieRelease = intent.getStringExtra("MOVIE_RELEASE");
            }
            if (extras.containsKey("MOVIE_POSTER")) {
                moviePoster = intent.getStringExtra("MOVIE_POSTER");
            }
            if (extras.containsKey("MOVIE_VOTE")) {
                movieVote = intent.getStringExtra("MOVIE_VOTE");
            }
            if (extras.containsKey("MOVIE_SYNOPSIS")) {
                movieSynopsis = intent.getStringExtra("MOVIE_SYNOPSIS");
            }
            if (extras.containsKey("MOVIE_ID")) {
                movieId = intent.getStringExtra("MOVIE_ID");
            }

        }

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

        btFavorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put(MovieEntry._ID, movieId);
                values.put(MovieEntry.COLUMN_TITLE, movieTitle);
                values.put(MovieEntry.COLUMN_RELEASE, movieRelease);
                values.put(MovieEntry.COLUMN_POSTER, moviePoster);
                values.put(MovieEntry.COLUMN_VOTE, movieVote);
                values.put(MovieEntry.COLUMN_SYNOPSIS, movieSynopsis);

                if (currentItemUri == null) {

                    Uri newUri = getContentResolver().insert(MovieEntry.CONTENT_URI, values);

                    if (newUri == null) {
                        Toast.makeText(context, getString(R.string.editor_insert_movie_failed),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, getString(R.string.editor_insert_movie_successful),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            getLoaderManager().initLoader(LOADER_ID_VIDEO, null, videoLoader);
            getLoaderManager().initLoader(LOADER_ID_REVIEW, null, reviewLoader);
        }

    }

}
