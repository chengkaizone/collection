/**
 * 
 */
package com.cheng.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.cheng.demo.R;

public class GradienterView extends View {
	// 定义水平仪仪表盘图片
	public Bitmap back;
	// 定义水平仪中的气泡图标
	public Bitmap bubble;
	// 定义水平仪中气泡 的X、Y座标
	public int bubbleX, bubbleY;

	public GradienterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 加载水平仪图片和气泡图片
		back = BitmapFactory.decodeResource(getResources(), R.drawable.back);
		bubble = BitmapFactory
				.decodeResource(getResources(), R.drawable.bubble);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 绘制水平仪表盘图片
		canvas.drawBitmap(back, 0, 0, null);
		// 根据气泡座标绘制气泡
		canvas.drawBitmap(bubble, bubbleX, bubbleY, null);
	}
}
