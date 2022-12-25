package com.example.a2018261001_hamsongju_final_exam.Adapter;

import android.app.Dialog;
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
import com.example.a2018261001_hamsongju_final_exam.Models.Post;
import com.example.a2018261001_hamsongju_final_exam.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import xyz.hanks.library.bang.SmallBangView;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private List<Post> mPostList;
    FirebaseAuth auth;
    FirebaseUser user;
    CircleImageView user_profile_image;

    public TimelineAdapter(List<Post> postList){
        this.mPostList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPostList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView useridTv;
        TextView textTv;
        ImageView imageIv, options, Comments, Send;
        SmallBangView Likes, Bookmark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            useridTv = itemView.findViewById(R.id.userId_tv);
            textTv = itemView.findViewById(R.id.text_tv);
            imageIv = itemView.findViewById(R.id.image_iv);
            user_profile_image = itemView.findViewById(R.id.user_profile_image);
            options = itemView.findViewById(R.id.options);
            Likes = itemView.findViewById(R.id.imageViewAnimation);
            Comments = itemView.findViewById(R.id.Comments);
            Send = itemView.findViewById(R.id.Send);
            Bookmark = itemView.findViewById(R.id.imageViewAnimation1);
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            getProfileImage();

            Likes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Likes.isSelected()) {
                        Likes.setSelected(false);
                    } else {
                        Likes.setSelected(true);
                        Likes.likeAnimation();
                    }
                }
            });

            Comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "댓글", Toast.LENGTH_SHORT).show();
                }
            });

            Send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "전송", Toast.LENGTH_SHORT).show();
                }
            });

            Bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Bookmark.isSelected()) {
                        Bookmark.setSelected(false);
                    } else {
                        Bookmark.setSelected(true);
                        Bookmark.likeAnimation();
                    }
                }
            });

            options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPublishContentDialog(view);
                }
            });
        }

        public void bind(Post post) {
            useridTv.setText(post.getUserId());
            textTv.setText(post.getText());
            Glide.with(imageIv.getContext()).load(post.getImageUrl()).into(imageIv);
        }
    }
    private void getProfileImage() {
        if(user==null){
            return;
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String p = snapshot.child("profile").getValue().toString();

                    Picasso.get().load(p).placeholder(R.drawable.ic_baseline_account_circle_24)
                            .into(user_profile_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void showPublishContentDialog(View view) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.post_dialogue);
        dialog.setCanceledOnTouchOutside(true);

        LinearLayout delete_linearlayout = dialog.findViewById(R.id.delete_linearlayout);
        LinearLayout edit_linearlayout = dialog.findViewById(R.id.edit_linearlayout);
        LinearLayout share_linearlayout = dialog.findViewById(R.id.Share_linearlayout);
        
        delete_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "게시물 삭제", Toast.LENGTH_SHORT).show();
            }
        });
        
        edit_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "게시물 수정", Toast.LENGTH_SHORT).show();
            }
        });

        share_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "게시물 공유", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
