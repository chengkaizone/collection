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
	// ��ʼ���������
	private Matrix matrix = new Matrix();
	// ��ʼ����б��
	private float skew = 0.0f;
	// ��ʼ�����ų̶�
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
		// ���ÿɻ񽹵�
		this.setFocusable(true);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// �����������Ұ�ť�¼�---���ϷŴ�
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			isScale = true;
			if (scale < 2.0) {
				scale += 0.1f;
			}
			this.postInvalidate();
		}
		// ������С
		else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			isScale = true;
			if (scale > 0.5f) {
				scale -= 0.1f;
			}
			this.postInvalidate();
		}
		// ������б
		else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			isScale = false;
			skew += 0.1f;
			this.postInvalidate();
		}
		// ������б
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
			// ������б
			matrix.setSkew(skew, 0);
		} else {
			// ��������
			matrix.setScale(scale, scale);
		}
		// ����ԭλͼ��Matrix������λͼ
		Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		// ������λͼ
		canvas.drawBitmap(bitmap2, matrix, null);
	}
}
