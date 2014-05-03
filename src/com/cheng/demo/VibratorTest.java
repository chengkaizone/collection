package com.cheng.demo;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;

public class VibratorTest extends CommonActivity {
	Vibrator vibrator;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.audiomanager_main);
		vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		vibrator.vibrate(100);
		return super.onTouchEvent(event);
	}

}
