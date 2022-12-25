package com.example.a2018261001_hamsongju_final_exam.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a2018261001_hamsongju_final_exam.R;

public class SettingsDashboard extends Fragment {

    TextView normal, data, auto, video_option, background, tv, history, new_function, membership, claim, notification, connect, chat, Subtitle, accessibility, info;

    public SettingsDashboard() {
        // Required empty public constructor
    }

    public static SettingsDashboard newInstance() {
        SettingsDashboard fragment = new SettingsDashboard();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dashboard_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        normal = view.findViewById(R.id.normal);
        data = view.findViewById(R.id.data);
        auto = view.findViewById(R.id.auto);
        video_option = view.findViewById(R.id.video_option);
        background = view.findViewById(R.id.background);
        tv = view.findViewById(R.id.tv);
        history = view.findViewById(R.id.history);
        new_function = view.findViewById(R.id.new_function);
        membership = view.findViewById(R.id.membership);
        claim = view.findViewById(R.id.claim);
        notification = view.findViewById(R.id.notification);
        connect = view.findViewById(R.id.connect);
        chat = view.findViewById(R.id.chat);
        Subtitle = view.findViewById(R.id.Subtitle);
        accessibility = view.findViewById(R.id.accessibility);
        info = view.findViewById(R.id.info);

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "일반", Toast.LENGTH_SHORT).show();
            }
        });

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "데이터 절약", Toast.LENGTH_SHORT).show();
            }
        });

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "자동재생", Toast.LENGTH_SHORT).show();
            }
        });

        video_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "동영상 화질 환경설정", Toast.LENGTH_SHORT).show();
            }
        });

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "백그라운드 및 오프라인 저장", Toast.LENGTH_SHORT).show();
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "TV로 시청하기", Toast.LENGTH_SHORT).show();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "기록 및 개인정보 보호", Toast.LENGTH_SHORT).show();
            }
        });

        new_function.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "새로운 기능 사용해 보기", Toast.LENGTH_SHORT).show();
            }
        });

        membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "구매 항목 및 멤버십", Toast.LENGTH_SHORT).show();
            }
        });

        claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "청구 및 결제", Toast.LENGTH_SHORT).show();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "알림", Toast.LENGTH_SHORT).show();
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "연결된 앱", Toast.LENGTH_SHORT).show();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "실시간 채팅", Toast.LENGTH_SHORT).show();
            }
        });

        Subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "자막", Toast.LENGTH_SHORT).show();
            }
        });

        accessibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "접근성", Toast.LENGTH_SHORT).show();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "정보", Toast.LENGTH_SHORT).show();
            }
        });
    }
}