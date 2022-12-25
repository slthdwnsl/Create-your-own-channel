package com.example.a2018261001_hamsongju_final_exam;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2018261001_hamsongju_final_exam.Adapter.ContentAdapter;
import com.example.a2018261001_hamsongju_final_exam.Adapter.TimelineAdapter;
import com.example.a2018261001_hamsongju_final_exam.Models.ContentModel;
import com.example.a2018261001_hamsongju_final_exam.Models.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView, timelineRv;
    SearchView searchView;
    ImageView back;
    ArrayList<ContentModel> list;
    ContentAdapter adapter;

    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        back = findViewById(R.id.back);

        reference = FirebaseDatabase.getInstance().getReference().child("Content");

        getAllVideos();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

                    adapter = new ContentAdapter(SearchActivity.this, list);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(SearchActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filter(newText);
                    return true;
                }
            });
        }
    }

    protected void filter(String newText){
        List<ContentModel> filterdList = new ArrayList<>();
        for (ContentModel item : list) {
            if (item.getVideo_title().toLowerCase().contains(newText.toLowerCase())){
                filterdList.add(item);
            }
        }
        adapter.filterList((ArrayList<ContentModel>) filterdList);
    }
}
