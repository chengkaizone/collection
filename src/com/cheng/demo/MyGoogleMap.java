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
 * һ��Ҫʹ��googleģ���������򱨴�
 * 
 * @author Administrator
 * 
 */
public class MyGoogleMap extends MapActivity {
	// ��������ϵĵĿ��ӻ��ؼ�
	Button locBn;
	RadioGroup mapType;
	MapView mv;
	EditText etLng, etLat;
	// ����MapController����
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
		// ��ý�����MapView����
		mv = (MapView) findViewById(R.id.google_mv);
		// ��ȡ�����������ı���
		etLng = (EditText) findViewById(R.id.lng);
		etLat = (EditText) findViewById(R.id.google_lat);
		// ������ʾ�Ŵ���С�Ŀ��ư�ť
		mv.setBuiltInZoomControls(true);
		// ����MapController����
		controller = mv.getController();
		// ���Button����
		locBn = (Button) findViewById(R.id.google_loc);
		locBn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View source) {
				// ��ȡ�û�����ľ��ȡ�γ��ֵ
				String lng = etLng.getEditableText().toString().trim();
				String lat = etLat.getEditableText().toString().trim();
				if (lng.equals("") || lat.equals("")) {
					Toast.makeText(MyGoogleMap.this, "��������Ч�ľ��ȡ�γ��!",
							Toast.LENGTH_LONG).show();
				} else {
					double dLong = Double.parseDouble(lng);
					double dLat = Double.parseDouble(lat);
					// ���÷�������MapView
					updateMapView(dLong, dLat);
				}
			}
		});
		// ������ť�ĵ����¼�
		locBn.performClick();
		// ���RadioGroup����
		mapType = (RadioGroup) findViewById(R.id.google_rg);
		// ΪRadioGroup��ѡ��״̬�ı���Ӽ�����
		mapType.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				// �����ѡ����"������ͼ"�ĵ�ѡ��
				case R.id.normal:
					mv.setSatellite(false);
					break;
				// �����ѡ����"������ͼ"�ĵ�ѡ��
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

	// ���ݾ��ȡ�γ�Ƚ�MapView��λ��ָ���ص�ķ���
	private void updateMapView(double lng, double lat) {
		// ����γ����Ϣ��װ��GeoPoint����
		GeoPoint gp = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		// ������ʾ�Ŵ���С��ť
		mv.displayZoomControls(true);
		// ����ͼ�ƶ���ָ���ĵ���λ��
		controller.animateTo(gp);
		// ���MapView��ԭ�е�Overlay����
		List<Overlay> ol = mv.getOverlays();
		// ���ԭ�е�Overlay����
		ol.clear();
		// ���һ���µ�OverLay����
		ol.add(createOverlay(gp));
	}

	private FirstOverlay createOverlay(GeoPoint point) {
		FirstOverlay first = new FirstOverlay(this, draw);
		// ���������������ͼ��
		OverlayItem item = new OverlayItem(point, "��ǰλ��", "��ǰλ����Ϣ");
		first.addOverlay(item);
		return first;
	}
}