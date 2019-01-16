package com.example.pavel.monero.ui.fragment.adapter;

import com.robinhood.spark.SparkAdapter;

import java.util.ArrayList;

public class PlotAdapter extends SparkAdapter {

    private ArrayList<Long> longs;

    public PlotAdapter(ArrayList<Long> longs) {
        this.longs = longs;
    }

    @Override
    public int getCount() {
        return longs.size();
    }

    @Override
    public Object getItem(int index) {
        return longs.get(index);
    }

    @Override
    public float getY(int index) {
        return Float.valueOf(longs.get(index));
    }

    public void addTraffic(long traffic) {
        longs.add(traffic);
        notifyDataSetChanged();
    }
}
