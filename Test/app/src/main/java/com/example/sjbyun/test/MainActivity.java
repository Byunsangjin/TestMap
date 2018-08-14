package com.example.sjbyun.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

public class MainActivity extends NMapActivity {

    private ViewGroup mapLayout;
    private NMapView mMapView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();

    }
    private void init(){
        mapLayout = findViewById(R.id.mapLayout);

        mMapView = new NMapView(this);
        mMapView.setClientId("_P9FZJMc0rQnFAcwWwC0"); // 클라이언트 아이디 값 설정
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.setScalingFactor(1.5f);
        mMapView.requestFocus();

        mapLayout.addView(mMapView);


    }


}
