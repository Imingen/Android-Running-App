package com.example.imingen.workoutpal.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.models.Run;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryTabAdapter extends RecyclerView.Adapter<HistoryTabAdapter.ViewHolder>{
    private List<Run> runs;
    String numlaps;
    String lengthlaps;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, numberOfLaps, lapLength;

        public ViewHolder(View view){
            super(view);
            numlaps = view.getResources().getString(R.string.numlaps_historytab);
            lengthlaps = view.getResources().getString(R.string.lengthlap_historytab);

            date = view.findViewById(R.id.date);
            numberOfLaps = view.findViewById(R.id.numberOfLaps);
            lapLength = view.findViewById(R.id.lapLength);
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
        Date date = run.getDateOfRun();
        String dateString = new SimpleDateFormat("E MMM dd, yyyy - HH:mm").format(date);
        holder.date.setText(dateString);
        holder.numberOfLaps.setText(numlaps + String.valueOf(run.getNumberOfLaps()));
        holder.lapLength.setText(lengthlaps + String.valueOf(run.getTime()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return runs.size();
    }
}
