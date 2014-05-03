package com.cheng.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.cheng.demo.R;

public class MatrixView extends View {
	private Context context;
	private Bitmap bitmap;
	// 初始化矩阵对象
	private Matrix matrix = new Matrix();
	// 初始化倾斜度
	private float skew = 0.0f;
	// 初始化缩放程度
	private float scale = 1.0f;
	private int width, height;
	private boolean isScale = false;

	public MatrixView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initParam(context);
	}

	public MatrixView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initParam(context);
	}

	public MatrixView(Context context) {
		super(context);
		initParam(context);
	}

	private void initParam(Context context) {
		this.context = context;
		BitmapDrawable draw = (BitmapDrawable) context.getResources()
				.getDrawable(R.drawable.lashou);
		bitmap = draw.getBitmap();
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		// 设置可获焦点
		this.setFocusable(true);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 监听上下左右按钮事件---向上放大
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isScale = true;
			if (scale < 2.0) {
				scale += 0.1f;
			}
			this.postInvalidate();
		}
		// 向下缩小
		else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isScale = true;
			if (scale > 0.5f) {
				scale -= 0.1f;
			}
			this.postInvalidate();
		}
		// 向左倾斜
		else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isScale = false;
			skew += 0.1f;
			this.postInvalidate();
		}
		// 向右倾斜
		else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			isScale = false;
			skew -= 0.1f;
			this.postInvalidate();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		matrix.reset();
		if (!isScale) {
			// 设置倾斜
			matrix.setSkew(skew, 0);
		} else {
			// 设置缩放
			matrix.setScale(scale, scale);
		}
		// 根据原位图与Matrix创建新位图
		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		// 绘制新位图
		canvas.drawBitmap(bitmap2, matrix, null);
	}
}
