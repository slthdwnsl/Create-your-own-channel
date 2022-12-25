package com.example.a2018261001_hamsongju_final_exam.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2018261001_hamsongju_final_exam.Adapter.ContentAdapter;
import com.example.a2018261001_hamsongju_final_exam.ChannelDashboardFragment;
import com.example.a2018261001_hamsongju_final_exam.Models.ContentModel;
import com.example.a2018261001_hamsongju_final_exam.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class VideosDashboard extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ContentModel> list;
    ContentAdapter adapter;

    DatabaseReference reference;

    public VideosDashboard() {
        // Required empty public constructor
    }

    public static VideosDashboard newInstance() {
        VideosDashboard fragment = new VideosDashboard();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.dashboard_videos, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        reference = FirebaseDatabase.getInstance().getReference().child("Content");

        getAllVideos();

        return view;
    }

    private void getAllVideos() {
        list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ContentModel model = dataSnapshot.getValue(ContentModel.class);
                        list.add(model);
                    }

                    Collections.shuffle(list);

                    adapter = new ContentAdapter(getActivity(), list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}