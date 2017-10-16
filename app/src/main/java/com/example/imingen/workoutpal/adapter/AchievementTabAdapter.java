package com.example.imingen.workoutpal.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imingen.workoutpal.R;
import com.example.imingen.workoutpal.models.Achievement;

import java.util.List;

/**
 * Created by Marius on 16.10.2017.
 */

public class AchievementTabAdapter extends RecyclerView.Adapter<AchievementTabAdapter.ViewHolder> {

    private List<Achievement> achievements;

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView achTitle;
        ImageView achImg;

        public ViewHolder(View itemView) {
            super(itemView);
            achTitle = itemView.findViewById(R.id.achievement_title);
            achImg = itemView.findViewById(R.id.achievement_image);
        }
    }

    public AchievementTabAdapter(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    @Override
    public AchievementTabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AchievementTabAdapter.ViewHolder holder, int position) {
        Achievement ach = achievements.get(position);
        holder.achTitle.setText(ach.getAchievementName());
        holder.achImg.setImageResource(ach.getLogoId());
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }
}
