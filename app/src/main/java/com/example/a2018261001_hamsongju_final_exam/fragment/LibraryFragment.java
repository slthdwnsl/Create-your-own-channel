package com.example.a2018261001_hamsongju_final_exam.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2018261001_hamsongju_final_exam.Adapter.PlaylistAdapter;
import com.example.a2018261001_hamsongju_final_exam.Dashboard.VideosDashboard;
import com.example.a2018261001_hamsongju_final_exam.MainActivity;
import com.example.a2018261001_hamsongju_final_exam.Models.PlaylistModel;
import com.example.a2018261001_hamsongju_final_exam.PublishContentActivity;
import com.example.a2018261001_hamsongju_final_exam.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LibraryFragment extends Fragment {

    RecyclerView recyclerView, playlist_recyclerView;
    ArrayList<PlaylistModel> list = new ArrayList<>();
    PlaylistAdapter adapter;
    TextView text3, txt_choose_playlist, txt_add, myvideo, download_video, history, later_video, favourite_video;
    Dialog dialog;
    String selectedPlaylist;
    int videosCount;
    EditText input_playlist_name;

    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView1);
        playlist_recyclerView = view.findViewById(R.id.playlist_recyclerView);
        recyclerView.setHasFixedSize(true);
        playlist_recyclerView.setHasFixedSize(true);
        showAllPlaylists(adapter, list);
        text3 = view.findViewById(R.id.text3);
        myvideo = view.findViewById(R.id.myvideo);
        download_video = view.findViewById(R.id.download_video);
        history = view.findViewById(R.id.history);
        later_video = view.findViewById(R.id.later_video);
        favourite_video = view.findViewById(R.id.favourite_video);


        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlaylistDialogue();
            }
        });

        myvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"내 동영상",Toast.LENGTH_SHORT).show();
            }
        });

        download_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"저장 동영상",Toast.LENGTH_SHORT).show();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity()," 기록",Toast.LENGTH_SHORT).show();
            }
        });

        later_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"나중에 볼 동영상",Toast.LENGTH_SHORT).show();
            }
        });

        favourite_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"좋아요 표시한 동영상",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showAllPlaylists(PlaylistAdapter adapter, ArrayList<PlaylistModel> list) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Playlists");
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PlaylistModel model = dataSnapshot.getValue(PlaylistModel.class);
                        list.add(model);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPlaylistDialogue() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.play_list_dialog);
        dialog.setCancelable(true);

        input_playlist_name = dialog.findViewById(R.id.input_playlist_name);
        txt_add = dialog.findViewById(R.id.txt_add);

        ArrayList<PlaylistModel> list = new ArrayList<>();
        RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        adapter = new PlaylistAdapter(getActivity(), list, new PlaylistAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(PlaylistModel model) {
                dialog.dismiss();
                selectedPlaylist = model.getPlaylist_name();
                videosCount = model.getVideos();
                txt_choose_playlist.setText("Playlist : " + model.getPlaylist_name());
            }
        });
    }
}