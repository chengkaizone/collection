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
	// 主题栏和标题栏高度
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
		// 开始画飞机的起点坐标
		planeX = (disWidth - planeWidth) / 2;
		planeY = (disHeight - planeHeight) - HEIGHT;
		System.out.println("初始宽高" + planeX + "----" + planeY);
		// planeX=160;
		// planeY=380;
		// 背景开始的Y坐标位置---背景高度减去屏幕高度再减去通知栏主题栏高度
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

	// 触摸监听效果不是很好；不能连续移动
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 重写触摸事件--松开事件
		planeX = (int) event.getX() - planeWidth / 2;
		planeY = (int) event.getY() - planeHeight / 2;
		this.invalidate();
		// 此处不能返回调用父类的方法的返回值；父类方法一般返回false;此时会向外传递；导致不能完全响应触摸事件
		return true;
	}

}
