package com.itarusoft.movies.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itarusoft.movies.Objects.Video;
import com.itarusoft.movies.R;

import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class VideoAdapter extends ArrayAdapter<Video> {

    private Context context;

    public VideoAdapter(Context context, int textViewResourceId,
                        List<Video> videos) {
        super(context, textViewResourceId, videos);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.video_list, null);

        TextView tvVideo = (TextView)view.findViewById(R.id.tv_video);

        final Video currentVideo = getItem(position);

        tvVideo.setText(currentVideo.getTitle());

        final String videoKey = currentVideo.getVideoKey();

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoKey));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + videoKey));
                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }
            }
        });

        return view;
    }

}