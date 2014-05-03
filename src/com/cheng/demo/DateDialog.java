package com.cheng.demo;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class DateDialog extends CommonActivity implements OnClickListener {
	private Button set;
	private TextView tv;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.date_set);
		set = (Button) findViewById(R.id.date_btn);
		tv = (TextView) findViewById(R.id.date_tv);
		set.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.date_btn:
			DateDialog.this.showDialog(123);
			break;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == 123) {
			return new DatePickerDialog(this, new OnDateSetListener() {
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					String str = "当前时间：" + year + "年/" + (monthOfYear + 1)
							+ "月/" + dayOfMonth + "日";
					tv.setText(str);
				}
			}, 2012, 2, 11);
		}
		return super.onCreateDialog(id);
	}

}
