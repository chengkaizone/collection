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
		// ע�᷽λ�Ǵ���������Ӧ�¼���������0.12s����һ�α���
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
		// �ж�--->����Ƿ��򴫸���
		case Sensor.TYPE_ORIENTATION:
			// ȡ��һ��ֵ
			float de = event.values[0];
			System.out.println("ƫת�Ƕ�--->" + de);
			/*
			 * ʹ����ת����--->Χ���Լ���תx/y�ᶼ���е�--->�����ĵ���ת
			 * ����RotateAnimation��ת����ų���˳ʱ����������෴����ȡ��ֵ
			 */
			RotateAnimation rerote = new RotateAnimation(degree, -de,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rerote.setDuration(200);
			img.startAnimation(rerote);
			// �ѵ�ǰƫת�Ƕȼ�¼����--->�´νǶȷ����仯ʱ�Ե�ǰ�Ƕ�Ϊ���֮������ת
			degree = -de;
			break;
		}
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// ���������ľ��ȷ����仯ʱ�����÷���
	}
}
