package com.example.a2018261001_hamsongju_final_exam.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2018261001_hamsongju_final_exam.Models.Post;
import com.example.a2018261001_hamsongju_final_exam.R;
import com.example.a2018261001_hamsongju_final_exam.Adapter.TimelineAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    RecyclerView timelineRv;
    List<Post> mPostList = new ArrayList<>();
    TimelineAdapter timelineAdapter;
    FirebaseFirestore firestore;

    public CommunityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timelineRv = view.findViewById(R.id.timeline_rv);
        timelineAdapter = new TimelineAdapter(mPostList);

        timelineRv.setLayoutManager(new LinearLayoutManager(getContext()));
        timelineRv.setAdapter(timelineAdapter);

        firestore = FirebaseFirestore.getInstance();

        firestore.collection("posts")
                .orderBy("uploadDate", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot:documents){
                            Post post = snapshot.toObject(Post.class);
                            mPostList.add(post);
                        }
                        timelineAdapter.notifyDataSetChanged();
                    }
                });
    }
}