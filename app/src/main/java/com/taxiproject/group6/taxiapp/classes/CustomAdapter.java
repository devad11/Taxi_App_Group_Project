package com.taxiproject.group6.taxiapp.classes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taxiproject.group6.taxiapp.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private static final String TAG = "CustomAdapter";

    private Context context;
    private int layout;
    private ArrayList<JourneyDetails> journeyDetailsArrayList;

    public CustomAdapter(Context context, int layout, ArrayList<JourneyDetails> journeyDetailsArrayList) {
        this.context = context;
        this.layout = layout;
        this.journeyDetailsArrayList = journeyDetailsArrayList;
    }

    @Override
    public int getCount() {
        return journeyDetailsArrayList.size();
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
        TextView fromTextView, toTextView, costTextView, dateTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate( layout, null);
            viewHolder.fromTextView = convertView.findViewById(R.id.journeyFromTextView);
            viewHolder.toTextView = convertView.findViewById(R.id.journeyToTextView);
            viewHolder.costTextView = convertView.findViewById(R.id.costTextView);
            viewHolder.dateTextView = convertView.findViewById(R.id.dateAndTimeTextView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        JourneyDetails journeyDetails = journeyDetailsArrayList.get(position);
        viewHolder.fromTextView.setText(journeyDetails.getStart().getName());
        viewHolder.toTextView.setText(journeyDetails.getEnd().getName());
        String cost = "€" + journeyDetails.getCost();
        viewHolder.costTextView.setText(cost);
        String date = journeyDetails.getDate() + " , " + journeyDetails.getTime();
        viewHolder.dateTextView.setText(date);
        Log.d(TAG, journeyDetails.toString());

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}

