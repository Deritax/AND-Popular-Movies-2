package com.itarusoft.movies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itarusoft.movies.Objects.Review;
import com.itarusoft.movies.R;

import java.util.List;

public class ReviewAdapter extends ArrayAdapter<Review> {

    public ReviewAdapter(Context context, int textViewResourceId,
                         List<Review> reviews) {
        super(context, textViewResourceId, reviews);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.review_list, null);

        TextView tvContent = (TextView)view.findViewById(R.id.tv_content);

        TextView tvAuthor = (TextView)view.findViewById(R.id.tv_author);

        final Review currentReview = getItem(position);

        tvContent.setText(currentReview.getContent());

        tvAuthor.setText(currentReview.getAuthor());

        return view;
    }

}