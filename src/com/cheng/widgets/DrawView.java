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
		// ��ʼ������
		cacheCanvas = new Canvas();
		System.out.println("��--->" + width + "\t��" + height);
		cacheBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);// 256��λͼ
		path = new Path();
		// ���û������Ƶ��ڴ��cacheBitmap��
		cacheCanvas.setBitmap(cacheBitmap);
		//
		cachePaint = new Paint(Paint.DITHER_FLAG);
		// ������ɫ
		cachePaint.setColor(Color.RED);
		// ���û��ʿ��
		cachePaint.setStrokeWidth(1.0f);
		// ���ʷ��--����
		cachePaint.setStyle(Paint.Style.STROKE);
		// ���ÿ����
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
			// ���滭����·��
			cacheCanvas.drawPath(path, cachePaint);
			// ����·��
			path.reset();
			break;
		}
		this.invalidate();// ֪ͨ�����ͼ
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint tmpPaint = new Paint();// ����Ĭ�ϻ���
		// ������λͼ���Ƶ�View�ؼ���
		canvas.drawBitmap(cacheBitmap, 0, 0, tmpPaint);
		// ����·����
		canvas.drawPath(path, cachePaint);

	}

	/**
	 * ����ͼ��
	 * 
	 * @param canvas
	 */
	public void saveBitmap() {
		Canvas canvas = new Canvas(cacheBitmap);
		canvas.save(Canvas.ALL_SAVE_FLAG);// ��������ͼ��
		canvas.restore();// ��ԭ����
	}

	/**
	 * ���ػ����λͼ����
	 * 
	 * @return
	 */
	public Bitmap getBitmap() {
		return cacheBitmap;
	}
}
