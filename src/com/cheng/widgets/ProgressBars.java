package com.cheng.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ProgressBars extends ProgressBar {
	// ������ʾ
	private String text;
	// �����ı��Ļ���
	private Paint mPaint;
	// ������ʾ
	private String t2;
	// ��ʾ�Ļ���
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
		super.setProgress(progress);// �˴�һ��Ҫ�����ø��෽����������ȿ��ܻ����
		int i = (progress * 100) / this.getMax();
		this.text = i + "%";
		this.t2 = "��Ӱģ��������";
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		System.out.println("onDraw--->");
		super.onDraw(canvas);
		Rect rect = new Rect();
		// ͨ�����ʻ�ȡ�ı��ı߽���Ϣ��ͨ����ȡ�ı�����������
		this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
		this.p2.getTextBounds(this.t2, 0, this.t2.length(), rect);

		int x = (getWidth() / 2) - rect.centerX();
		int y = (getHeight() / 2) - rect.centerY();
		int x2 = getWidth() - rect.width() - 5;
		int y2 = (getHeight() / 2) - rect.centerY();
		// �ɻ��������û���-->�����ڳ�ʼ��ʱ�����������ú���ɫ
		canvas.drawText(this.text, x, y, this.mPaint);
		canvas.drawText(this.t2, x2, y2, this.p2);
	}
}
