package com.cheng.widgets;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.cheng.demo.R;

public class LeidianView extends View {
	Context context;
	// �������ͱ������߶�
	final int HEIGHT = 60;
	private Bitmap plane, back_img;
	int back_width, back_height, startY, disWidth, disHeight;
	public int planeX, planeY, planeWidth, planeHeight;

	public LeidianView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initParam(context);
	}

	public LeidianView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initParam(context);
	}

	public LeidianView(Context context) {
		super(context);
		initParam(context);
	}

	public void initParam(Context context) {
		this.context = context;
		plane = ((BitmapDrawable) context.getResources().getDrawable(
				R.drawable.plane)).getBitmap();
		back_img = ((BitmapDrawable) context.getResources().getDrawable(
				R.drawable.back_img)).getBitmap();
		back_width = back_img.getWidth();
		back_height = back_img.getHeight();
		Display dis = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		disWidth = dis.getWidth();
		disHeight = dis.getHeight();
		planeWidth = plane.getWidth();
		planeHeight = plane.getHeight();
		// ��ʼ���ɻ����������
		planeX = (disWidth - planeWidth) / 2;
		planeY = (disHeight - planeHeight) - HEIGHT;
		System.out.println("��ʼ���" + planeX + "----" + planeY);
		// planeX=160;
		// planeY=380;
		// ������ʼ��Y����λ��---�����߶ȼ�ȥ��Ļ�߶��ټ�ȥ֪ͨ���������߶�
		startY = back_height - disHeight - HEIGHT;
		System.out.println(planeX + "---" + planeY);
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 0x123) {
					if (startY > 0) {
						startY -= 3;
					} else {
						startY = back_height - disHeight - HEIGHT;
					}
				}
				invalidate();
			}
		};
		new Timer().schedule(new TimerTask() {
			public void run() {
				handler.sendEmptyMessage(0x123);
			}
		}, 0, 100);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Bitmap new_back = Bitmap.createBitmap(back_img, 0, startY, disWidth,
				disHeight);
		canvas.drawBitmap(new_back, 0, 0, null);
		canvas.drawBitmap(plane, planeX, planeY, null);
	}

	// ��������Ч�����Ǻܺã����������ƶ�
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// ��д�����¼�--�ɿ��¼�
		planeX = (int) event.getX() - planeWidth / 2;
		planeY = (int) event.getY() - planeHeight / 2;
		this.invalidate();
		// �˴����ܷ��ص��ø���ķ����ķ���ֵ�����෽��һ�㷵��false;��ʱ�����⴫�ݣ����²�����ȫ��Ӧ�����¼�
		return true;
	}

}
