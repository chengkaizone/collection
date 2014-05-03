package com.cheng.util;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3DAnimation extends Animation {
	// ������ʼ�Ƕ�
	private float fromDegree;
	private float toDegree;
	// ƽ�����ĵ�
	private float centerX;
	private float centerY;
	// ������ת���
	private float depthZ;
	// �Ƿ�Ť��
	private boolean isReverse;
	// ����ͷ����ƽ�Ʊ仯����
	private Camera camera;

	public Rotate3DAnimation(float fromDegree, float toDegree, float centerX,
			float centerY, float depthZ, boolean isReverse) {
		this.fromDegree = fromDegree;
		this.toDegree = toDegree;
		this.centerX = centerX;
		this.centerY = centerY;
		this.depthZ = depthZ;
		this.isReverse = isReverse;
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		// �������
		super.initialize(width, height, parentWidth, parentHeight);
		// ��ʼ������ͷ
		camera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		// ���Բ����ô˷���
		super.applyTransformation(interpolatedTime, t);
		final float from = fromDegree;
		// �����м�Ƕ�
		float degress = from + ((toDegree - from) * interpolatedTime);
		final float cX = centerX;
		final float cY = centerY;
		final Camera ca = camera;
		// ת�����þ�����ʵ��
		final Matrix matrix = t.getMatrix();
		// ����
		camera.save();
		if (isReverse) {
			// ʹ��ƽ�Ʊ仯
			camera.translate(cX, cY, depthZ * interpolatedTime);
		} else {
			camera.translate(cX, cY, depthZ * (1.0f - interpolatedTime));
		}

		camera.getMatrix(matrix);
		camera.restore();
		matrix.preTranslate(-cX, -cY);
		matrix.postTranslate(cX, cY);
	}

	// ��������applyTransformation���������
	public boolean getTransformation(long currentTime,
			Transformation outTransformation) {
		return super.getTransformation(currentTime, outTransformation);
	}

}
