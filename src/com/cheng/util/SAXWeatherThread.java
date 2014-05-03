package com.cheng.util;

import android.graphics.Bitmap;
import android.os.Handler;

import com.cheng.adapter.SAXWeatherAdapter;

public class SAXWeatherThread extends Thread {
	private Handler handler;
	private SAXWeatherAdapter adapter;

	public SAXWeatherThread(Handler handler, SAXWeatherAdapter adapter) {
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
