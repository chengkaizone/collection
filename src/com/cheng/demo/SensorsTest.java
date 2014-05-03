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
		// ��ȡ�������������
		sensor = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
	}

	public void onResume() {
		super.onResume();
		// ע����ٴ�����---��������������仯Ƶ��
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		// ע�᷽�򴫸���
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL);
		// ע����ߴ�����
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_LIGHT),
				SensorManager.SENSOR_DELAY_NORMAL);
		// ע��ѹ��������
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_PRESSURE),
				SensorManager.SENSOR_DELAY_NORMAL);
		// ע���¶ȴ�����
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_TEMPERATURE),
				SensorManager.SENSOR_DELAY_NORMAL);
		// ע��ų�������
		sensor.registerListener(this,
				sensor.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	public void onPause() {
		// ȡ��ע����ٴ�����
		sensor.unregisterListener(this);
		super.onPause();
	}

	// ��������ֵ�����ı�ʱ�ص��˷���
	public void onSensorChanged(SensorEvent event) {
		int sensorType = event.sensor.getType();
		switch (sensorType) {
		case Sensor.TYPE_LIGHT:
			float[] lights = event.values;
			light.setText("��ǰ����ǿ�ȣ�" + lights[0]);
			break;
		case Sensor.TYPE_PRESSURE:
			float[] presss = event.values;
			press.setText("��ǰѹ����" + presss[0]);
			break;
		case Sensor.TYPE_TEMPERATURE:
			float[] temps = event.values;
			temp.setText("��ǰ�¶ȣ�" + temps[0]);
			break;
		case Sensor.TYPE_ACCELEROMETER:
			float[] accels = event.values;
			accel.setText("����--->X����:" + accels[0] + "\nY����" + accels[1]
					+ "\nZ����" + accels[2]);
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			float[] magns = event.values;
			magnetic.setText("�ų�--->X����Ƕȣ�" + magns[0] + "\nY����Ƕȣ�" + magns[1]
					+ "\nZ����Ƕȣ�" + magns[2]);
			break;
		case Sensor.TYPE_ORIENTATION:
			float[] orients = event.values;
			orient.setText("����--->Z��Ƕȣ�" + orients[0] + "\nX��Ƕ�" + orients[1]
					+ "\nY��Ƕ�" + orients[2]);
			break;
		}
	}

	// ���ȷ����ı��ǻص��˷���
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
}
