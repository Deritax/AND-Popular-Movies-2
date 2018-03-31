package com.itarusoft.movies;

import android.text.TextUtils;
import android.util.Log;

import com.itarusoft.movies.Objects.Movie;
import com.itarusoft.movies.Objects.Review;
import com.itarusoft.movies.Objects.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String KEY_RESULTS = "results";
    private static final String KEY_TITLE = "title";
    private static final String KEY_RELEASE = "release_date";
    private static final String KEY_POSTER = "poster_path";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_VOTE = "vote_average";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_KEY = "key";
    private static final String KEY_SITE = "site";
    private static final String KEY_TYPE = "type";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";


    private QueryUtils(){
    }

    public static List<Review> fetchReviewData(String requestUrl){

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        return extractReviewFromJson(jsonResponse);
    }

    private static List<Review> extractReviewFromJson(String reviewJSON) {

        if (TextUtils.isEmpty(reviewJSON)) {
            return null;
        }

        List<Review> reviews = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(reviewJSON);

            JSONArray reviewArray = baseJsonResponse.getJSONArray(KEY_RESULTS);

            for (int i = 0; i < reviewArray.length(); i++) {

                JSONObject currentReview = reviewArray.getJSONObject(i);

                String reviewAuthor = currentReview.getString(KEY_AUTHOR);

                String reviewContent = currentReview.getString(KEY_CONTENT);

                Review review = new Review(reviewAuthor,reviewContent);

                reviews.add(review);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the review JSON results", e);
        }
        return reviews;
    }

    public static List<Video> fetchVideoData(String requestUrl){

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        return extractVideoFromJson(jsonResponse);
    }

    private static List<Video> extractVideoFromJson(String videoJSON) {

        if (TextUtils.isEmpty(videoJSON)) {
            return null;
        }

        List<Video> videos = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(videoJSON);

            JSONArray videoArray = baseJsonResponse.getJSONArray(KEY_RESULTS);

            for (int i = 0; i < videoArray.length(); i++) {

                JSONObject currentVideo = videoArray.getJSONObject(i);

                String videoName = currentVideo.getString(KEY_NAME);

                String videoKey = currentVideo.getString(KEY_KEY);

                String videoSite = currentVideo.getString(KEY_SITE);

                String videoType = currentVideo.getString(KEY_TYPE);

                Video video = new Video(videoName, videoKey, videoSite, videoType);

                videos.add(video);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the video JSON results", e);
        }
        return videos;
    }

    public static List<Movie> fetchMovieData(String requestUrl){

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        return extractMovieFromJson(jsonResponse);
    }

    private static List<Movie> extractMovieFromJson(String movieJSON) {

        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(movieJSON);

            JSONArray movieArray = baseJsonResponse.getJSONArray(KEY_RESULTS);

            for (int i = 0; i < movieArray.length(); i++) {

                JSONObject currentMovie = movieArray.getJSONObject(i);

                String movieTitle = currentMovie.getString(KEY_TITLE);

                String moviePoster = currentMovie.getString(KEY_POSTER);

                String movieRelease = currentMovie.getString(KEY_RELEASE);

                String movieVote = currentMovie.getString(KEY_VOTE);

                String movieOverview = currentMovie.getString(KEY_OVERVIEW);

                String movieId = currentMovie.getString(KEY_ID);

                Movie movie = new Movie(movieTitle, movieRelease, moviePoster, movieVote, movieOverview, movieId);

                movies.add(movie);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the movie JSON results", e);
        }
        return movies;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the movie JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
}
