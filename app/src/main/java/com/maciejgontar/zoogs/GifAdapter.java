package com.maciejgontar.zoogs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.GifViewHolder> {
    private Context context;
    private ArrayList<String> imageUrls;
    private OnGifTap mCallback;

    public static class GifViewHolder extends RecyclerView.ViewHolder {
        public ImageView gifView;
        public GifViewHolder(ImageView v) {
            super(v);
            gifView = v;
        }
    }

    public GifAdapter(final Context context, final ArrayList<String> imageUrls, OnGifTap listener) {
        this.imageUrls = imageUrls;
        this.context = context;
        this.mCallback = listener;
    }

    public ArrayList<String> getUrlList() {
        return imageUrls;
    }

    @Override
    public GifAdapter.GifViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                     int viewType) {
        ImageView v = (ImageView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.gif_simple, viewGroup, false);
        GifViewHolder vh = new GifViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GifViewHolder holder, final int position) {
//        if (null == convertView) {
//            convertView = inflater.inflate(R.layout.gif_simple, parent, false);
//        }
        final String url = imageUrls.get(position);
        Glide
                .with(context)
                .load(url)
                .into(holder.gifView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onTap(url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public int getSomething() {
        return imageUrls.size();
    }
}