package com.cheng.util;

import java.util.List;

import android.graphics.Bitmap;
import android.os.Handler;

import com.cheng.adapter.WeatherAdapter;

public class WeatherThread extends Thread {
	private Handler handler;
	private WeatherAdapter adapter;

	public WeatherThread(Handler handler, WeatherAdapter adapter) {
		this.handler = handler;
		this.adapter = adapter;
	}

	public void run() {
		try {
			int len = adapter.getCount();
			System.out.println("len--->" + len);
			for (int i = 0; i < len; i++) {
				String icon = adapter.getItem(i).getIcon();
				System.out.println("icon--->" + icon);
				Bitmap bitmap = FileUtil.getBitmap(icon);
				adapter.getItem(i).setBitmap(bitmap);
				handler.sendEmptyMessage(0x999);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
