package com.example.a2018261001_hamsongju_final_exam.Dashboard;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2018261001_hamsongju_final_exam.Adapter.PlaylistAdapter;
import com.example.a2018261001_hamsongju_final_exam.ChannelDashboardFragment;
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

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class PlaylistDashboard extends Fragment {

    RecyclerView recyclerView;
    ArrayList<PlaylistModel> list = new ArrayList<>();
    PlaylistAdapter adapter;


    public PlaylistDashboard() {
        // Required empty public constructor
    }

    public static PlaylistDashboard newInstance() {
        PlaylistDashboard fragment = new PlaylistDashboard();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dashboard_playlist, container, false);
        recyclerView = (RecyclerView) view.findViewById(android.R.id.list);
        showAllPlaylists(adapter,list);

        return view;

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
}