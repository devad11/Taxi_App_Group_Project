package com.taxiproject.group6.taxiapp.classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taxiproject.group6.taxiapp.R;

import java.util.ArrayList;

public class ReviewsCustomAdapter extends BaseAdapter {

    private static final String TAG = "CustomAdapter";

    private Context context;
    private int layout;
    private ArrayList<Review> reviewArrayList;

    public ReviewsCustomAdapter(Context context, int layout, ArrayList<Review> reviewArrayList) {
        this.context = context;
        this.layout = layout;
        this.reviewArrayList = reviewArrayList;
    }


    @Override
    public int getCount() {
        return reviewArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        LinearLayout starsLinearLayout;
        TextView commentTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ReviewsCustomAdapter.ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ReviewsCustomAdapter.ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate( layout, null);
            viewHolder.starsLinearLayout = convertView.findViewById(R.id.starsLayout);
            viewHolder.commentTextView = convertView.findViewById(R.id.reviewTextView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ReviewsCustomAdapter.ViewHolder) convertView.getTag();
        }

        Review review = reviewArrayList.get(position);
        int stars = review.getRating();
        int maxStars = Review.MAX_RATING;

        for(int i=1; i <= maxStars; i++){
            ImageView imageView = new ImageView(this.context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(100,100));
            if(i <= stars)
                imageView.setBackgroundResource(R.drawable.ic_star_full);
            else
                imageView.setBackgroundResource(R.drawable.ic_star_empty);
            viewHolder.starsLinearLayout.addView(imageView);
        }

        viewHolder.commentTextView.setText(review.getComment());
        Log.d(TAG, review.toString());

        return convertView;
    }
}
