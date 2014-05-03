package com.cheng.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 此例子的关键是ImageView的缩放类型和获取位图副本的用法
 * 
 * @author Administrator
 * 
 */
public class GestureScaleTest extends CommonActivity implements
		OnGestureListener {
	private GestureDetector detector;
	private ImageView img;
	private Bitmap bitmap;
	private int bWidth, bHeight;
	private float maxScale = 10;
	private float minScale = 0.01f;
	private float currentScale = 1.0f;
	private Matrix matrix;
	private Display screen;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.gesture_scale_main);
		initWidget();
	}

	private void initWidget() {
		img = (ImageView) findViewById(R.id.gesture_scale_img);
		// 设置源位图
		img.setImageBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.lashou));
		detector = new GestureDetector(this);
		matrix = new Matrix();
		screen = this.getWindowManager().getDefaultDisplay();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return detector.onTouchEvent(event);
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// 根据两点之间的距离缩放
		// 向右滑动缩小--向左滑动放大
		float disTmp = e1.getX() - e2.getX();
		if (disTmp >= 0) {
			currentScale = currentScale * disTmp / 10 > maxScale ? maxScale
					: currentScale * disTmp / 10;
		} else {
			disTmp = Math.abs(disTmp);
			currentScale = currentScale / disTmp * 10 < minScale ? minScale
					: currentScale / disTmp * 10;
		}
		// 根据滑动速度缩放
		// velocityX=velocityX>maxVelocity?maxVelocity:velocityX;
		// velocityY=velocityY>maxVelocity?maxVelocity:velocityY;
		// currentScale+=currentScale*velocityX/maxVelocity;
		// currentScale=currentScale<minScale?minScale:currentScale;
		BitmapFactory.Options option = new BitmapFactory.Options();
		// option.inJustDecodeBounds=true;
		if (currentScale >= 1) {
			// option.inSampleSize=(int)currentScale;//允许缩放的尺寸
			option.inSampleSize = 2;// 允许缩放的尺寸；此处一定要控制好尺寸--此处是得到位图样本
		}
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.lashou, option);
		bWidth = bitmap.getWidth();
		bHeight = bitmap.getHeight();
		System.out.println(bWidth + "----" + bHeight + "---" + currentScale
				+ "--" + option.inSampleSize);
		matrix.reset();
		matrix.setScale(currentScale, currentScale, screen.getWidth() / 2,
				(screen.getHeight() - 60) / 2);
		BitmapDrawable draw = (BitmapDrawable) img.getDrawable();
		// 必须判断回收情况；否则会导致内存溢出
		if (!draw.getBitmap().isRecycled()) {
			draw.getBitmap().recycle();
		}
		Bitmap tmp = Bitmap.createBitmap(bitmap, 0, 0, bWidth, bHeight, matrix,
				true);
		img.setImageBitmap(tmp);
		return true;
	}

	public boolean onDown(MotionEvent e) {
		return false;
	}

	public void onShowPress(MotionEvent e) {
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	public void onLongPress(MotionEvent e) {
	}

}
