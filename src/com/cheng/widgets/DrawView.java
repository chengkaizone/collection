package com.cheng.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {

	private float preX;
	private float preY;
	private Path path;
	public Paint cachePaint;
	private Context context;
	private Bitmap cacheBitmap;
	private Canvas cacheCanvas;
	int width, height;

	public DrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initParam(context);
	}

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initParam(context);
	}

	public DrawView(Context context) {
		super(context);
		this.context = context;
		initParam(context);
	}

	public void initParam(Context context) {
		Display dis = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		width = dis.getWidth();
		height = dis.getHeight();
		// 初始化画布
		cacheCanvas = new Canvas();
		System.out.println("宽--->" + width + "\t高" + height);
		cacheBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);// 256设位图
		path = new Path();
		// 设置画布绘制到内存的cacheBitmap上
		cacheCanvas.setBitmap(cacheBitmap);
		//
		cachePaint = new Paint(Paint.DITHER_FLAG);
		// 画笔颜色
		cachePaint.setColor(Color.RED);
		// 设置画笔宽度
		cachePaint.setStrokeWidth(1.0f);
		// 画笔风格--抖动
		cachePaint.setStyle(Paint.Style.STROKE);
		// 设置抗锯齿
		cachePaint.setAntiAlias(true);
		cachePaint.setDither(true);
		this.invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float pointX = event.getX();
		float pointY = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(pointX, pointY);
			preX = pointX;
			preY = pointY;
			break;
		case MotionEvent.ACTION_MOVE:
			path.quadTo(preX, preY, pointX, pointY);
			preX = pointX;
			preY = pointY;
			break;
		case MotionEvent.ACTION_UP:
			// 缓存画布画路径
			cacheCanvas.drawPath(path, cachePaint);
			// 重设路径
			path.reset();
			break;
		}
		this.invalidate();// 通知组件绘图
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint tmpPaint = new Paint();// 创建默认画笔
		// 将缓存位图绘制到View控件上
		canvas.drawBitmap(cacheBitmap, 0, 0, tmpPaint);
		// 沿着路径画
		canvas.drawPath(path, cachePaint);

	}

	/**
	 * 保存图像
	 * 
	 * @param canvas
	 */
	public void saveBitmap() {
		Canvas canvas = new Canvas(cacheBitmap);
		canvas.save(Canvas.ALL_SAVE_FLAG);// 保存所有图层
		canvas.restore();// 还原画布
	}

	/**
	 * 返回缓存的位图对象
	 * 
	 * @return
	 */
	public Bitmap getBitmap() {
		return cacheBitmap;
	}
}
