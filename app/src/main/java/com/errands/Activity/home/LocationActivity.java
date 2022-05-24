package com.errands.Activity.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.errands.Sophix.R;

public class LocationActivity extends AppCompatActivity {
private MapView mapView;
    private AMap aMap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mapView=findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        //初始化地图控制器对象
            if (aMap == null) {
            aMap = mapView.getMap();
        }
//        initAMap();
    }



    /**
     * 移动到指定经纬度
     * @para i 经度
     * @param y 维度
     * @param zoo 缩放倍数
     */
    private void initAMap(double i, double y, float zoo) {
        //22.83542400, 108.35450500
        AMap mAMap = mapView.getMap();
        CameraPosition cameraPosition = new CameraPosition(new LatLng(i, y), zoo, 0, 30);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mAMap.moveCamera(cameraUpdate);
        drawMarkers(i, y);
    }
    /**
     * 画定位标记图
     * @para i 经度
     * @param y 维度
     */
    public void drawMarkers(double i, double y) {
        aMap.clear(true);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(i, y))
                .draggable(true);
        Marker marker = aMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }
}