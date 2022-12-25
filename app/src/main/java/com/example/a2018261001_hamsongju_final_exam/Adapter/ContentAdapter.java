package com.example.a2018261001_hamsongju_final_exam.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a2018261001_hamsongju_final_exam.Models.ContentModel;
import com.example.a2018261001_hamsongju_final_exam.PlayVideoActivity;
import com.example.a2018261001_hamsongju_final_exam.R;
import com.example.a2018261001_hamsongju_final_exam.ShortsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
    Context context;
    public static ArrayList<ContentModel> list;
    DatabaseReference reference;
    ContentModel contentModel;

    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void OnItemClick (int position);
    }

    public void OnRecyclerViewClickListener (OnRecyclerViewClickListener listener){
        this.listener = listener;
    }

    public ContentAdapter(Context context, ArrayList<ContentModel> list){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);

        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentModel model = list.get(position);
        if (model != null){
            try {
                Glide.with(context).asBitmap().load(model.getVideo_url()).into(holder.thumbnail);
            }catch (Exception e){
                Toast.makeText(context, "Couldn't load thumbnail", Toast.LENGTH_SHORT).show();
            }
            holder.video_title.setText(model.getVideo_title());
            holder.views.setText(model.getViews()+"Views");
            holder.date.setText(model.getDate());

            setData(model.getPublisher(), holder.channel_logo, holder.channel_name);
        }
    }

    private void setData(String publisher, CircleImageView logo, TextView channel_name) {
        reference = FirebaseDatabase.getInstance().getReference().child("Channels");
        reference.child(publisher).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String cName = snapshot.child("channel_name").getValue().toString();
                    String cLogo = snapshot.child("channel_logo").getValue().toString();
                    channel_name.setText(cName);
                    Picasso.get().load(cLogo).placeholder(R.drawable.ic_baseline_account_circle_24).into(logo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail, options;
        TextView video_title, channel_name, views, date;
        CircleImageView channel_logo;

        public ViewHolder(@NonNull View itemView, OnRecyclerViewClickListener listener){
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnail);
            video_title = itemView.findViewById(R.id.video_title);
            channel_name = itemView.findViewById(R.id.channel_name);
            views = itemView.findViewById(R.id.views_count);
            channel_logo = itemView.findViewById(R.id.channel_logo);
            date = itemView.findViewById(R.id.date);
            options = itemView.findViewById(R.id.options);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null && getAbsoluteAdapterPosition() != RecyclerView.NO_POSITION){
                        listener.OnItemClick(getAbsoluteAdapterPosition());
                    }
                    Intent intent= new Intent(context, PlayVideoActivity.class);
                    context.startActivity(intent);
                }
            });

            options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPublishContentDialog(view);
                }
            });
        }

        private void showPublishContentDialog(View view) {
            Dialog dialog = new Dialog(view.getContext());
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.video_dialog);
            dialog.setCanceledOnTouchOutside(true);

            LinearLayout delete_linearlayout = dialog.findViewById(R.id.delete_linearlayout);
            LinearLayout edit_linearlayout = dialog.findViewById(R.id.edit_linearlayout);
            LinearLayout share_linearlayout = dialog.findViewById(R.id.Share_linearlayout);

            delete_linearlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "영상 삭제", Toast.LENGTH_SHORT).show();
                }
            });

            edit_linearlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "영상 수정", Toast.LENGTH_SHORT).show();
                }
            });

            share_linearlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "영상 공유", Toast.LENGTH_SHORT).show();
                }
            });

            dialog.show();
        }
    }

    public void filterList(ArrayList<ContentModel> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
}
