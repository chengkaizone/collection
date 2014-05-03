package com.cheng.demo;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmTest extends CommonActivity implements OnClickListener {
	TextView tv;
	Button setAlarm;
	Calendar cal;
	AlarmManager am;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.alarm_main);
		initWidget();
	}

	private void initWidget() {
		tv = (TextView) findViewById(R.id.alarm_main_info);
		setAlarm = (Button) findViewById(R.id.alarm_set);
		am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		setAlarm.setOnClickListener(this);
		cal = Calendar.getInstance();
		tv.setText("当前时间：" + cal.YEAR + "年" + cal.MONTH + "月"
				+ cal.DAY_OF_MONTH + "日");
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.alarm_set) {
			cal.setTimeInMillis(System.currentTimeMillis());
			new TimePickerDialog(this, new OnTimeSetListener() {
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					Calendar c = Calendar.getInstance();
					Intent intent = new Intent(AlarmTest.this, AlarmAct.class);
					PendingIntent pi = PendingIntent.getActivity(
							AlarmTest.this, 0, intent, 0);
					c.setTimeInMillis(System.currentTimeMillis());
					c.set(Calendar.HOUR, hourOfDay);
					c.set(Calendar.MINUTE, minute);
					// 设置关机状态下也能触发闹铃
					am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
					Toast.makeText(AlarmTest.this, "设置成功", 2000).show();
				}
			}, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false)
					.show();
		}
	}
}
