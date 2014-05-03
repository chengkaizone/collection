package com.cheng.demo;

import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LocationTest extends CommonActivity implements OnClickListener {
	Button start, stop, all, best;
	TextView tv, locInfo;
	LocationManager lm;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.location);
		start = (Button) findViewById(R.id.location_loc);
		stop = (Button) findViewById(R.id.location_no_loc);
		all = (Button) findViewById(R.id.location_no_loc_all);
		best = (Button) findViewById(R.id.location_no_loc_best);
		tv = (TextView) findViewById(R.id.location_tv);
		locInfo = (TextView) findViewById(R.id.location_loc_info);
		lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		all.setOnClickListener(this);
		best.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.location_loc:
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 30,
					listener);
			break;
		case R.id.location_no_loc:
			lm.removeUpdates(listener);
			break;
		case R.id.location_no_loc_all:
			Criteria ca = new Criteria();
			ca.setAltitudeRequired(true);
			ca.setCostAllowed(false);
			ca.setPowerRequirement(Criteria.POWER_LOW);
			ca.setSpeedRequired(false);
			String loc_info = lm.getBestProvider(ca, false);
			locInfo.setText("最佳位置：" + loc_info);
			break;
		case R.id.location_no_loc_best:
			List<String> locs = lm.getAllProviders();
			String tmp = "";
			for (int i = 0; i < locs.size(); i++) {
				tmp += "位置" + i + locs.get(i) + "\n";
			}
			locInfo.setText(tmp);
			break;
		}
	}

	LocationListener listener = new LocationListener() {
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}

		public void onLocationChanged(Location loc) {
			String str = "当前位置信息：\t纬度：" + loc.getLatitude() + "\t经度："
					+ loc.getLongitude();
			System.out.println(str);
			tv.setText(str);
		}
	};
}
