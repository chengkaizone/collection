package com.cheng.widgets;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class D3Gallery extends Gallery {
	private Camera camera;
	private float unSelectedScale = 2.0f;
	private float unSelectedAlpha = 1.0f;
	private int maxZoom = 0;
	private int center = 0;
	private int maxRotationAngle = 60;

	public D3Gallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public D3Gallery(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public D3Gallery(Context context) {
		this(context, null, 0);
	}

	public int getMaxZoom() {
		return maxZoom;
	}

	public void setMaxZoom(int maxZoom) {
		this.maxZoom = maxZoom;
	}

	public int getCenter() {
		return center;
	}

	public void setCenter(int center) {
		this.center = center;
	}

	public int getMaxRotationAngle() {
		return maxRotationAngle;
	}

	public void setMaxRotationAngle(int maxRotationAngle) {
		this.maxRotationAngle = maxRotationAngle;
	}

	private int getViewOfCenter(View view) {
		return view.getLeft() + view.getWidth() / 2;
	}

	// ����gallery��ÿ��ͼƬ����ת(��д��gallery�з���)
	@Override
	protected boolean getChildStaticTransformation(View child,
			Transformation trans) {
		final int newCenter = getViewOfCenter(child);
		final int width = child.getWidth();
		int angle = 0;
		trans.clear();
		trans.setTransformationType(Transformation.TYPE_MATRIX);
		// ���ͼƬλ������λ�ò���Ҫ������ת
		if (newCenter == center) {
			transImage((ImageView) child, trans, 0);
		} else {
			// ����ͼƬ��gallery�е�λ��������ͼƬ����ת�Ƕ�
			angle = (int) ((center - newCenter) / width * maxRotationAngle);
			if (Math.abs(angle) > maxRotationAngle) {
				angle = (angle < 0) ? -maxRotationAngle : maxRotationAngle;
			}
			this.transImage((ImageView) child, trans, angle);
		}
		return true;
	}

	private void transImage(ImageView img, Transformation trans, int angle) {
		camera.save();
		final Matrix trix = trans.getMatrix();
		final int width = img.getLayoutParams().width;
		final int height = img.getLayoutParams().height;
		final int newAngle = Math.abs(angle);
		camera.translate(0.0f, 0.0f, 50.0f);
		if (newAngle < maxRotationAngle) {
			float zoom = (float) (maxZoom + maxZoom * 1.5);
			camera.translate(width, height, zoom);
		}
		camera.rotateY(angle);
		camera.getMatrix(trix);
		trix.preTranslate(-(width / 2), -(height / 2));
		trix.postTranslate((width / 2), (height / 2));
		// �����ԭ
		camera.restore();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		center = getViewCenter();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private int getViewCenter() {
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
				+ getPaddingLeft();
	}
}
