package com.cheng.widgets;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class CustomGallery extends Gallery {
	private Camera mCamera = new Camera();
	// 最大转换角度
	private int mMaxRotationAngle = 60;
	// 最大缩放
	private int mMaxZoom = -500;

	private int mCoveflowCenter;
	// 缩放模式
	private boolean mAlphaMode = true;
	// 循环模式
	private boolean mCircleMode = false;

	public CustomGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setStaticTransformationsEnabled(true);
	}

	public CustomGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setStaticTransformationsEnabled(true);
	}

	public CustomGallery(Context context) {
		super(context);
		this.setStaticTransformationsEnabled(true);
	}

	public int getMaxRotationAngle() {
		return mMaxRotationAngle;
	}

	public void setMaxRotationAngle(int maxRotationAngle) {
		mMaxRotationAngle = maxRotationAngle;
	}

	public boolean getCircleMode() {
		return mCircleMode;
	}

	public void setCircleMode(boolean isCircle) {
		mCircleMode = isCircle;
	}

	public boolean getAlphaMode() {
		return mAlphaMode;
	}

	public void setAlphaMode(boolean isAlpha) {
		mAlphaMode = isAlpha;
	}

	public int getMaxZoom() {
		return mMaxZoom;
	}

	public void setMaxZoom(int maxZoom) {
		mMaxZoom = maxZoom;
	}

	// 获取自身控件的中心X坐标
	private int getCenterOfCoverflow() {
		int centerX = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
				+ getPaddingLeft();
		return centerX;
	}

	// 获取显示子视图控件的中心点X坐标
	private static int getCenterOfView(View view) {
		int childViewCenter = view.getLeft() + view.getWidth() / 2;
		return childViewCenter;
	}

	/**
	 * 重写Gallery方法 ，产生层叠和放大效果---->transformation转换类的使用
	 */
	protected boolean getChildStaticTransformation(View child, Transformation t) {
		final int childCenter = getCenterOfView(child);
		final int childWidth = child.getWidth();
		// 初始化旋转角度
		int rotationAngle = 0;
		// 重置transformation对象
		t.clear();
		// 设置转换类型--->矩阵类型
		t.setTransformationType(Transformation.TYPE_MATRIX);
		if (childCenter == mCoveflowCenter) {
			transformImageBitmap((ImageView) child, t, 0, 0);
		} else {
			rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);
			if (Math.abs(rotationAngle) > mMaxRotationAngle) {
				rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle
						: mMaxRotationAngle;
			}
			transformImageBitmap(
					(ImageView) child,
					t,
					rotationAngle,
					(int) Math.floor((mCoveflowCenter - childCenter)
							/ (childWidth == 0 ? 1 : childWidth)));
		}
		return true;
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mCoveflowCenter = getCenterOfCoverflow();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private void transformImageBitmap(ImageView child, Transformation t,
			int rotationAngle, int d) {
		mCamera.save();
		final Matrix imageMatrix = t.getMatrix();
		final int imageHeight = child.getLayoutParams().height;
		final int imageWidth = child.getLayoutParams().width;
		final int rotation = Math.abs(rotationAngle);
		mCamera.translate(0.0f, 0.0f, 100.0f);
		// 得到控件的偏转角度和缩放水平
		if (rotation <= mMaxRotationAngle) {
			float zoomAmount = (float) (mMaxZoom + (rotation * 1.5));
			mCamera.translate(0.0f, 0.0f, zoomAmount);
			if (mCircleMode) {
				if (rotation < 40) {
					mCamera.translate(0.0f, 155, 0.0f);
				} else {
					mCamera.translate(0.0f, (255 - rotation * 2.5f), 0.0f);
				}
			}
			if (mAlphaMode) {
				((ImageView) (child)).setAlpha((int) (255 - rotation * 2.5));
			}
		}
		mCamera.rotateY(rotationAngle);
		mCamera.getMatrix(imageMatrix);
		imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
		imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
		mCamera.restore();
	}
}
