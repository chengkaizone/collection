package com.cheng.demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class NotifyCationTest extends CommonActivity {
	NotificationManager ncm;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		ncm = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		LinearLayout lay = new LinearLayout(this);
		Button btn = new Button(this);
		btn.setText("下载");
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(NotifyCationTest.this,
						DownService.class);
				intent.putExtra("total", 100);
				startService(intent);
				sendNotifyInfo();
			}
		});
		lay.addView(btn);
		setContentView(lay);
	}

	private void sendNotifyInfo() {
		Notification noti = new Notification();
		noti.icon = R.drawable.icon;
		noti.defaults = Notification.DEFAULT_SOUND;
		Intent intent = new Intent(NotifyCationTest.this, DownPage.class);
		PendingIntent pi = PendingIntent.getActivity(NotifyCationTest.this, 0,
				intent, 0);
		noti.setLatestEventInfo(NotifyCationTest.this, "下载信息", "文件下载", pi);
		ncm.notify(100, noti);
	}
}
