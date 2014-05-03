package com.cheng.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class DownService extends Service {
	int total = 0;
	int currentNum = 0;
	MyBinder binder;

	@Override
	public IBinder onBind(Intent intent) {
		return new MyBinder();
	}

	// 绑定器是主界面为了得到后台服务的数据信息
	public class MyBinder extends Binder {
		public int getCurrentNum() {
			return currentNum;
		}

		public int getTotal() {
			return total;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("service---被启动");
	}

	@Override
	public void onStart(Intent intent, int startId) {
		System.out.println(intent == null);
		if (intent != null) {
			total = intent.getIntExtra("total", 0);
		}
		new Thread() {
			public void run() {
				for (int i = 1; i <= total; i++) {
					try {
						Thread.sleep(500);
						currentNum = i;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		super.onStart(intent, startId);
	}

}
