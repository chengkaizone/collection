package com.cheng.demo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WallpaperTest extends CommonActivity implements OnClickListener {
	Button set, cancel;
	AlarmManager am;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.wall_main);
		set = (Button) findViewById(R.id.wall_set);
		cancel = (Button) findViewById(R.id.wall_cancel);
		set.setOnClickListener(this);
		cancel.setOnClickListener(this);
		cancel.setEnabled(false);
		am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Intent intent = new Intent(WallpaperTest.this, WallService.class);
		// ע��˴�ʹ�õķ���---��Ҫ��������Service��Activity��Ҳ����ʹ�ù㲥�ķ���-->�ṩ���ַ���
		PendingIntent pi = PendingIntent.getService(WallpaperTest.this, 0,
				intent, 0);
		switch (id) {
		case R.id.wall_set:
			// ����ÿ5�����һ��ǽֽ
			am.setRepeating(AlarmManager.RTC_WAKEUP, 0, 5000, pi);
			set.setEnabled(false);
			cancel.setEnabled(true);
			break;
		case R.id.wall_cancel:
			am.cancel(pi);
			set.setEnabled(true);
			cancel.setEnabled(false);
			break;
		}
	}
}
