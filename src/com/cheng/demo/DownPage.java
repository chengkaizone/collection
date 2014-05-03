package com.cheng.demo;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownPage extends CommonActivity {

	TextView tv;
	ProgressBar pb;
	DownService.MyBinder binder;
	NotificationManager ncm;
	Handler h = new Handler() {
		public void handleMessage(Message msg) {
			int i = msg.what;
			tv.setText("ÒÑÏÂÔØ--->" + i);
			pb.setProgress(i);
		}
	};
	private ServiceConnection conn = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (DownService.MyBinder) service;
		}

		public void onServiceDisconnected(ComponentName name) {
		}
	};

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.down_page);
		pb = (ProgressBar) findViewById(R.id.down_pb);
		tv = (TextView) findViewById(R.id.down_info);
		ncm = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
		Intent intent = new Intent(DownPage.this, DownService.class);
		this.bindService(intent, conn, BIND_AUTO_CREATE);
		new Thread() {
			public void run() {
				while (true) {
					if (binder != null) {
						int i = binder.getCurrentNum();
						h.sendEmptyMessage(i);
						if (i == 100) {
							ncm.cancel(100);
							return;
						}
					}
				}
			}
		}.start();
	}

}
