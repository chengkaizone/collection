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
	// ����ˮƽ���Ǳ���ͼƬ
	public Bitmap back;
	// ����ˮƽ���е�����ͼ��
	public Bitmap bubble;
	// ����ˮƽ�������� ��X��Y����
	public int bubbleX, bubbleY;

	public GradienterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// ����ˮƽ��ͼƬ������ͼƬ
		back = BitmapFactory.decodeResource(getResources(), R.drawable.back);
		bubble = BitmapFactory
				.decodeResource(getResources(), R.drawable.bubble);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// ����ˮƽ�Ǳ���ͼƬ
		canvas.drawBitmap(back, 0, 0, null);
		// �������������������
		canvas.drawBitmap(bubble, bubbleX, bubbleY, null);
	}
}
