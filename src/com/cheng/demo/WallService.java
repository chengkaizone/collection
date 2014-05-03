package com.cheng.demo;

import java.io.IOException;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.IBinder;

public class WallService extends Service {
	int[] resIds = { R.drawable.skin1, R.drawable.skin2, R.drawable.skin3,
			R.drawable.skin4, R.drawable.skin5, };
	int current = 0;
	WallpaperManager wm;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		System.out.println("创建service");
		wm = WallpaperManager.getInstance(this);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		System.out.println("onStart");
		if (current > 4) {
			current = 0;
		}
		try {
			wm.setResource(resIds[current++]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// onstart适用于android系统的早期版本---高版本的都是回调onStartCommand方法
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("onStartCommand");
		if (current > 4) {
			current = 0;
		}
		try {
			wm.setResource(resIds[current++]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}

}
