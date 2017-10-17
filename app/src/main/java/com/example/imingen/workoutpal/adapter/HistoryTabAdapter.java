package com.example.imingen.workoutpal.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.models.Run;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Marius on 16.10.2017.
 */

public class HistoryTabAdapter extends RecyclerView.Adapter<HistoryTabAdapter.ViewHolder>{
    private List<Run> runs;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, distance;

        public ViewHolder(View view){
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            distance = (TextView) view.findViewById(R.id.distance);
        }
    }


    public HistoryTabAdapter(List<Run> data) {
        this.runs = data;
    }

    @Override
    public HistoryTabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);

        return new ViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Run run = runs.get(position);
        holder.date.setText(run.getDateOfRun().toString());
        holder.distance.setText(String.valueOf(run.getDistance()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return runs.size();
    }
}
