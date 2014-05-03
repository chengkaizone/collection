package com.cheng.util;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3DAnimation extends Animation {
	// 定义起始角度
	private float fromDegree;
	private float toDegree;
	// 平面中心点
	private float centerX;
	private float centerY;
	// 定义旋转深度
	private float depthZ;
	// 是否扭曲
	private boolean isReverse;
	// 摄像头用于平移变化操作
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
		// 必须调用
		super.initialize(width, height, parentWidth, parentHeight);
		// 初始化摄像头
		camera = new Camera();
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		// 可以不调用此方法
		super.applyTransformation(interpolatedTime, t);
		final float from = fromDegree;
		// 生成中间角度
		float degress = from + ((toDegree - from) * interpolatedTime);
		final float cX = centerX;
		final float cY = centerY;
		final Camera ca = camera;
		// 转化类获得矩阵类实例
		final Matrix matrix = t.getMatrix();
		// 保存
		camera.save();
		if (isReverse) {
			// 使用平移变化
			camera.translate(cX, cY, depthZ * interpolatedTime);
		} else {
			camera.translate(cX, cY, depthZ * (1.0f - interpolatedTime));
		}

		camera.getMatrix(matrix);
		camera.restore();
		matrix.preTranslate(-cX, -cY);
		matrix.postTranslate(cX, cY);
	}

	// 方法会在applyTransformation（）后调用
	public boolean getTransformation(long currentTime,
			Transformation outTransformation) {
		return super.getTransformation(currentTime, outTransformation);
	}

}
