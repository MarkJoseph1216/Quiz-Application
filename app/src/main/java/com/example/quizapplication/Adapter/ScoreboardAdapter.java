package com.example.quizapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.quizapplication.Class.Items;

import java.util.List;

public class ScoreboardAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Items> scoreItems;

    public ScoreboardAdapter(Context context, List<Items>scoreItems){
        this.context = context;
        this.scoreItems = scoreItems;
    }

    @Override
    public int getCount() {
        return scoreItems.size();
    }

    @Override
    public Object getItem(int location) {
        return scoreItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        RecyclerView.ViewHolder holder;

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        return null;
    }
}
