package com.cheng.demo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorsTest extends CommonActivity implements SensorEventListener {
	TextView accel, orient, light, press, temp, magnetic;
	SensorManager sensor;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.sensors_main);
		accel = (TextView) findViewById(R.id.sensors_accel);
		orient = (TextView) findViewById(R.id.sensors_orient);
		light = (TextView) findViewById(R.id.sensors_light);
		press = (TextView) findViewById(R.id.sensors_press);
		temp = (TextView) findViewById(R.id.sensors_temp);
		magnetic = (TextView) findViewById(R.id.sensors_magnetic);
		// 获取传感器管理服务
		sensor = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
	}

	public void onResume() {
		super.onResume();
		// 注册加速传感器---第三个参数代表变化频率
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		// 注册方向传感器
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL);
		// 注册光线传感器
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_LIGHT),
				SensorManager.SENSOR_DELAY_NORMAL);
		// 注册压力传感器
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_PRESSURE),
				SensorManager.SENSOR_DELAY_NORMAL);
		// 注册温度传感器
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_TEMPERATURE),
				SensorManager.SENSOR_DELAY_NORMAL);
		// 注册磁场传感器
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	public void onPause() {
		// 取消注册加速传感器
		sensor.unregisterListener(this);
		super.onPause();
	}

	// 传感器的值发生改变时回调此方法
	public void onSensorChanged(SensorEvent event) {
		int sensorType = event.sensor.getType();
		switch (sensorType) {
		case Sensor.TYPE_LIGHT:
			float[] lights = event.values;
			light.setText("当前光线强度：" + lights[0]);
			break;
		case Sensor.TYPE_PRESSURE:
			float[] presss = event.values;
			press.setText("当前压力：" + presss[0]);
			break;
		case Sensor.TYPE_TEMPERATURE:
			float[] temps = event.values;
			temp.setText("当前温度：" + temps[0]);
			break;
		case Sensor.TYPE_ACCELEROMETER:
			float[] accels = event.values;
			accel.setText("加速--->X方向:" + accels[0] + "\nY方向：" + accels[1]
					+ "\nZ方向：" + accels[2]);
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			float[] magns = event.values;
			magnetic.setText("磁场--->X方向角度：" + magns[0] + "\nY方向角度：" + magns[1]
					+ "\nZ方向角度：" + magns[2]);
			break;
		case Sensor.TYPE_ORIENTATION:
			float[] orients = event.values;
			orient.setText("方向--->Z轴角度：" + orients[0] + "\nX轴角度" + orients[1]
					+ "\nY轴角度" + orients[2]);
			break;
		}
	}

	// 精度放生改变是回调此方法
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
}
