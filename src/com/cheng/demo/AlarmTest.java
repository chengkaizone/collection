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
		tv.setText("��ǰʱ�䣺" + cal.YEAR + "��" + cal.MONTH + "��"
				+ cal.DAY_OF_MONTH + "��");
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
					// ���ùػ�״̬��Ҳ�ܴ�������
					am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
					Toast.makeText(AlarmTest.this, "���óɹ�", 2000).show();
				}
			}, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false)
					.show();
		}
	}
}
