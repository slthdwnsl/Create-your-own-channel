package com.example.a2018261001_hamsongju_final_exam;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    TextView normal, data, auto, video_option, background, tv, history, new_function, membership, claim, notification, connect, chat, Subtitle, accessibility, info;

    ImageView exit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        normal = findViewById(R.id.normal);
        data = findViewById(R.id.data);
        auto = findViewById(R.id.auto);
        video_option = findViewById(R.id.video_option);
        background = findViewById(R.id.background);
        tv = findViewById(R.id.tv);
        history = findViewById(R.id.history);
        new_function = findViewById(R.id.new_function);
        membership = findViewById(R.id.membership);
        claim = findViewById(R.id.claim);
        notification = findViewById(R.id.notification);
        connect = findViewById(R.id.connect);
        chat = findViewById(R.id.chat);
        Subtitle = findViewById(R.id.Subtitle);
        accessibility = findViewById(R.id.accessibility);
        info = findViewById(R.id.info);

        exit = findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "일반", Toast.LENGTH_SHORT).show();
            }
        });

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "데이터 절약", Toast.LENGTH_SHORT).show();
            }
        });

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "자동재생", Toast.LENGTH_SHORT).show();
            }
        });

        video_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "동영상 화질 환경설정", Toast.LENGTH_SHORT).show();
            }
        });

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "백그라운드 및 오프라인 저장", Toast.LENGTH_SHORT).show();
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "TV로 시청하기", Toast.LENGTH_SHORT).show();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "기록 및 개인정보 보호", Toast.LENGTH_SHORT).show();
            }
        });

        new_function.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "새로운 기능 사용해 보기", Toast.LENGTH_SHORT).show();
            }
        });

        membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "구매 항목 및 멤버십", Toast.LENGTH_SHORT).show();
            }
        });

        claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "청구 및 결제", Toast.LENGTH_SHORT).show();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "알림", Toast.LENGTH_SHORT).show();
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "연결된 앱", Toast.LENGTH_SHORT).show();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "실시간 채팅", Toast.LENGTH_SHORT).show();
            }
        });

        Subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "자막", Toast.LENGTH_SHORT).show();
            }
        });

        accessibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "접근성", Toast.LENGTH_SHORT).show();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "정보", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
