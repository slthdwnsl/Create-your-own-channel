package com.example.a2018261001_hamsongju_final_exam;


import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayVideoActivity extends AppCompatActivity {

    VideoView videoView;
    ImageView channel_logo;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView video_title,channel_name,views,date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayer);

        MediaController mediaController = new MediaController(this);

        videoView = findViewById(R.id.vid);
        video_title = findViewById(R.id.video_title);
        channel_name = findViewById(R.id.channel_name);
        views = findViewById(R.id.views_count);
        date = findViewById(R.id.date);
        channel_logo = findViewById(R.id.channel_logo);

        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/finalexam-c01c2.appspot.com/o/Content%2FojmaQkkh8xWQRNkG9UyugszIUkw1%2F1670944987143%2Cmp4?alt=media&token=5dde63f6-48e7-4eaf-bc7e-6a7485b851e2");
        videoView.setVideoURI(uri);
        videoView.start();

        setDataTitle();
        setChannelName();
        getProfileImage();
    }


    private void setDataTitle(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Content").child("-NJB4Htcpqavu8TA8EQr");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String title = snapshot.child("video_title").getValue().toString();
                String view = snapshot.child("views").getValue().toString();
                String dates = snapshot.child("date").getValue().toString();
                video_title.setText(title);
                views.setText(view);
                date.setText(dates);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setChannelName() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Channels").child("ojmaQkkh8xWQRNkG9UyugszIUkw1");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("channel_name").getValue().toString();
                channel_name.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getProfileImage() {
        if(user==null){
            return;
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child("ojmaQkkh8xWQRNkG9UyugszIUkw1");
        reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String p = snapshot.child("profile").getValue().toString();

                    Picasso.get().load(p).placeholder(R.drawable.ic_baseline_account_circle_24)
                            .into(channel_logo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PlayVideoActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
