package com.example.sjbyun.test;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;

public class MainActivity extends NMapActivity {

    private ViewGroup mapLayout;
    private NMapView mMapView;

    NMapController mMapController;

    //오버레이 객체 관리 클래스
    NMapOverlayManager mOverlayManager;

    NMapMyLocationOverlay mMyLocationOverlay;
    NMapLocationManager mMapLocationManager;
    NMapCompassManager mMapCompassManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        init();

        //위치 관리 메니저 객체 생성
        mMapLocationManager = new NMapLocationManager(this);
        //현재 위치 변경 시 호출되는 콜백 인터페이스를 설정한다.
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);
        //NMapMyLocationOverlay 객체 생성
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager,
                mMapCompassManager);
        startMyLocation(); //내 위치 찾기 함수 호출

    }


    private void init() {
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

    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener =
            new NMapLocationManager.OnLocationChangeListener() { //위치 변경 콜백 인터페이스 정의
                // 위치가 변경되면 호출
                @Override
                public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {
                    if (mMapController != null) {
                        mMapController.animateTo(myLocation);
                        //지도 중심을 현재 위치로 이동
                    }
                    return true;
                }

                //정해진 시간 내에 위치 탐색 실패 시 호출
                @Override
                public void onLocationUpdateTimeout(NMapLocationManager locationManager) {
                }

                //현재 위치가 지도 상에 표시할 수 있는 범위를 벗어나는 경우 호출
                @Override
                public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {
                    stopMyLocation(); //내 위치 찾기 중지 함수 호출
                }

            };


    private void startMyLocation() {
        if (mMapLocationManager.isMyLocationEnabled()) { //현재 위치를 탐색 중인지 확인
            if (!mMapView.isAutoRotateEnabled()) { //지도 회전기능 활성화 상태 여부 확인
                mMyLocationOverlay.setCompassHeadingVisible(true); //나침반 각도 표시
                mMapCompassManager.enableCompass(); //나침반 모니터링 시작
                mMapView.setAutoRotateEnabled(true, false); //지도 회전기능 활성화
            }

            mMapView.invalidate();
        } else { //현재 위치를 탐색 중이 아니면
            Boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(false); //현재 위치 탐색 시작 if (!isMyLocationEnabled) { //위치 탐색이 불가능하면
            Toast.makeText(MainActivity.this, "Please enable a My Location source in system settings", Toast.LENGTH_LONG).show();
            Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(goToSettings);
            return;
        }
    }

    private void stopMyLocation() {
        mMapLocationManager.disableMyLocation(); //현재 위치 탐색 종료
        if (mMapView.isAutoRotateEnabled()) { //지도 회전기능이 활성화 상태라면
            mMyLocationOverlay.setCompassHeadingVisible(false); //나침반 각도표시 제거
            mMapCompassManager.disableCompass(); //나침반 모니터링 종료
            mMapView.setAutoRotateEnabled(false, false); //지도 회전기능 중지
        }
    }
}

