package com.maciejgontar.zoogs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.GifViewHolder> {
    private Context context;
    private ArrayList<String> imageUrls;

    public static class GifViewHolder extends RecyclerView.ViewHolder {
        public ImageView gifView;
        public GifViewHolder(ImageView v) {
            super(v);
            gifView = v;
        }
    }

    public GifAdapter(final Context context, final ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
        this.context = context;
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
    public void onBindViewHolder(GifViewHolder holder, int position) {
//        if (null == convertView) {
//            convertView = inflater.inflate(R.layout.gif_simple, parent, false);
//        }
        Glide
                .with(context)
                .load(imageUrls.get(position))
                .into(holder.gifView);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public int getSomething() {
        return imageUrls.size();
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (null == convertView) {
//            convertView = inflater.inflate(R.layout.gif_simple, parent, false);
//        }
//
//        Glide
//                .with(context)
//                .load(imageUrls.get(position))
//                .into((ImageView) convertView);
//
//        return convertView;
//    }
}