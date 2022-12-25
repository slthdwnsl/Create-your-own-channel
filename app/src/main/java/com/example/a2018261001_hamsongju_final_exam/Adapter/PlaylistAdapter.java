package com.example.a2018261001_hamsongju_final_exam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2018261001_hamsongju_final_exam.Models.PlaylistModel;
import com.example.a2018261001_hamsongju_final_exam.R;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    Context context;
    ArrayList<PlaylistModel> list;
    OnitemClickListener listener;

    public PlaylistAdapter(Context context, ArrayList<PlaylistModel> list, OnitemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.playlist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.ViewHolder holder, int position) {
        holder.blind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_playlist_name, txt_videos_count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_playlist_name = itemView.findViewById(R.id.txt_playlist_name);
            txt_videos_count = itemView.findViewById(R.id.txt_videos_count);
        }

        public void blind(final PlaylistModel model, final OnitemClickListener listener){
            txt_playlist_name.setText(model.getPlaylist_name());
            txt_videos_count.setText("Videos" + model.getVideos());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(model);
                }
            });
        }
    }

    public interface OnitemClickListener{
        void onItemClick(PlaylistModel model);
    }
}
