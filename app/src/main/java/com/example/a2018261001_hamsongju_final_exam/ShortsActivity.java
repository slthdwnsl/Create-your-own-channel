package com.example.a2018261001_hamsongju_final_exam;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.a2018261001_hamsongju_final_exam.Adapter.videoadapter;
import com.example.a2018261001_hamsongju_final_exam.Models.videomodel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ShortsActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    videoadapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorts);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager2 = (ViewPager2) findViewById(R.id.vpager);

        FirebaseRecyclerOptions<videomodel> options =
                new FirebaseRecyclerOptions.Builder<videomodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Content").child("video_url"), videomodel.class)
                        .build();

        adapter = new videoadapter(options);
        viewPager2.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
