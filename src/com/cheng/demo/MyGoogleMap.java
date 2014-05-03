package com.cheng.demo;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.cheng.overlay.FirstOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * 一定要使用google模拟器；否则报错
 * 
 * @author Administrator
 * 
 */
public class MyGoogleMap extends MapActivity {
	// 定义界面上的的可视化控件
	Button locBn;
	RadioGroup mapType;
	MapView mv;
	EditText etLng, etLat;
	// 定义MapController对象
	MapController controller;
	// Bitmap posBitmap;
	Drawable draw;

	@Override
	protected void onCreate(Bundle status) {
		super.onCreate(status);
		setContentView(R.layout.google_main);
		// posBitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.pos);
		draw = this.getResources().getDrawable(R.drawable.pos);
		// 获得界面上MapView对象
		mv = (MapView) findViewById(R.id.google_mv);
		// 获取界面上两个文本框
		etLng = (EditText) findViewById(R.id.lng);
		etLat = (EditText) findViewById(R.id.google_lat);
		// 设置显示放大、缩小的控制按钮
		mv.setBuiltInZoomControls(true);
		// 创建MapController对象
		controller = mv.getController();
		// 获得Button对象
		locBn = (Button) findViewById(R.id.google_loc);
		locBn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View source) {
				// 获取用户输入的经度、纬度值
				String lng = etLng.getEditableText().toString().trim();
				String lat = etLat.getEditableText().toString().trim();
				if (lng.equals("") || lat.equals("")) {
					Toast.makeText(MyGoogleMap.this, "请输入有效的经度、纬度!",
							Toast.LENGTH_LONG).show();
				} else {
					double dLong = Double.parseDouble(lng);
					double dLat = Double.parseDouble(lat);
					// 调用方法更新MapView
					updateMapView(dLong, dLat);
				}
			}
		});
		// 触发按钮的单击事件
		locBn.performClick();
		// 获得RadioGroup对象
		mapType = (RadioGroup) findViewById(R.id.google_rg);
		// 为RadioGroup的选中状态改变添加监听器
		mapType.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				// 如果勾选的是"正常视图"的单选框
				case R.id.normal:
					mv.setSatellite(false);
					break;
				// 如果勾选的是"卫星视图"的单选框
				case R.id.google_satellite:
					mv.setSatellite(true);
					break;
				}
			}
		});
	}

	protected boolean isRouteDisplayed() {
		return true;
	}

	// 根据经度、纬度将MapView定位到指定地点的方法
	private void updateMapView(double lng, double lat) {
		// 将经纬度信息包装成GeoPoint对象
		GeoPoint gp = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		// 设置显示放大缩小按钮
		mv.displayZoomControls(true);
		// 将地图移动到指定的地理位置
		controller.animateTo(gp);
		// 获得MapView上原有的Overlay对象
		List<Overlay> ol = mv.getOverlays();
		// 清除原有的Overlay对象
		ol.clear();
		// 添加一个新的OverLay对象
		ol.add(createOverlay(gp));
	}

	private FirstOverlay createOverlay(GeoPoint point) {
		FirstOverlay first = new FirstOverlay(this, draw);
		// 根据坐标点来创建图层
		OverlayItem item = new OverlayItem(point, "当前位置", "当前位置信息");
		first.addOverlay(item);
		return first;
	}
}