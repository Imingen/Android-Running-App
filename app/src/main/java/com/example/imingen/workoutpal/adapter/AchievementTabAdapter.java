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

public class AchievementTabAdapter extends RecyclerView.Adapter<AchievementTabAdapter.ViewHolder> {

    private List<Achievement> achievements;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView achTitle;
        ImageView achImg;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            achTitle = itemView.findViewById(R.id.achievement_title);
            achImg = itemView.findViewById(R.id.achievement_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClicked(position);
                        }
                    }
                }
            });
        }
    }

    public AchievementTabAdapter(List<Achievement> achievements) {
        this.achievements = achievements;
    }

    @Override
    public AchievementTabAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_list_item, parent, false);
        return new ViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(AchievementTabAdapter.ViewHolder holder, int position) {
        Achievement ach = achievements.get(position);
        holder.achTitle.setText(ach.getAchievementName());
        holder.achImg.setImageResource(R.drawable.trophy);
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    public void updateAdapter(List<Achievement> newList) {
        this.achievements = newList;
        notifyDataSetChanged();
    }
}
