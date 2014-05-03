package com.cheng.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ProgressBars extends ProgressBar {
	// 进度提示
	private String text;
	// 绘制文本的画笔
	private Paint mPaint;
	// 整体提示
	private String t2;
	// 提示的画笔
	private Paint p2;

	public ProgressBars(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initText();
	}

	public ProgressBars(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		initText();
	}

	public ProgressBars(Context context) {
		this(context, null, 0);
		initText();
	}

	public void initText() {
		this.mPaint = new Paint();
		this.mPaint.setColor(Color.WHITE);
		this.p2 = new Paint();
		int c = Color.argb(255, 255, 180, 234);
		this.p2.setColor(c);
	}

	@Override
	public synchronized void setProgress(int progress) {
		System.out.println("setProgress--->");
		super.setProgress(progress);// 此处一定要先条用父类方法；否则进度可能会出错
		int i = (progress * 100) / this.getMax();
		this.text = i + "%";
		this.t2 = "电影模拟流量条";
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		System.out.println("onDraw--->");
		super.onDraw(canvas);
		Rect rect = new Rect();
		// 通过画笔获取文本的边界信息；通过获取文本长度来计算
		this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
		this.p2.getTextBounds(this.t2, 0, this.t2.length(), rect);

		int x = (getWidth() / 2) - rect.centerX();
		int y = (getHeight() / 2) - rect.centerY();
		int x2 = getWidth() - rect.width() - 5;
		int y2 = (getHeight() / 2) - rect.centerY();
		// 由画布来调用画笔-->画笔在初始化时被创建并设置好颜色
		canvas.drawText(this.text, x, y, this.mPaint);
		canvas.drawText(this.t2, x2, y2, this.p2);
	}
}
