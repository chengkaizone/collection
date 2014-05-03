package com.cheng.demo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class CompassTest extends CommonActivity implements SensorEventListener {
	ImageView img;
	float degree = 0.0f;
	SensorManager sensor;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.compass_main);
		img = (ImageView) findViewById(R.id.compass_img);
		sensor = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
	}

	public void onResume() {
		super.onResume();
		// 注册方位角传感器，感应事件的周期是0.12s产生一次报告
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);
	}

	public void onPause() {
		sensor.unregisterListener(this);
		super.onPause();
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		int sensorType = event.sensor.getType();
		switch (sensorType) {
		// 判断--->如果是方向传感器
		case Sensor.TYPE_ORIENTATION:
			// 取第一个值
			float de = event.values[0];
			System.out.println("偏转角度--->" + de);
			/*
			 * 使用旋转动画--->围绕自己旋转x/y轴都在中点--->在中心点自转
			 * 但是RotateAnimation的转角与磁场的顺时针概念正好相反；故取负值
			 */
			RotateAnimation rerote = new RotateAnimation(degree, -de,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rerote.setDuration(200);
			img.startAnimation(rerote);
			// 把当前偏转角度记录下来--->下次角度发生变化时以当前角度为起点之后再旋转
			degree = -de;
			break;
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// 当传感器的精度发生变化时触发该方法
	}
}
