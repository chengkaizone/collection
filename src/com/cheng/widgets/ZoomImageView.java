package com.cheng.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ZoomImageView extends ImageView {
	private Matrix matrix;
	private Matrix savePreMatrix;
	private PointF start;// ��һ��������
	private PointF mid;
	private float oldDist = 1.0f;
	// ͼƬ������״̬
	private final int NONE = 0;// ��״̬
	private final int DRAG = 1;// ��ҷ״̬
	private final int ZOOM = 2;// ����״̬
	private int MODE = NONE;
	private int width = 0;
	private int height = 0;
	private Bitmap bitmap;
	private float minScale = 0.5f;
	private float maxScale = 2.0f;

	public ZoomImageView(Context context) {
		super(context);
		Display dis = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		width = dis.getWidth();
		height = dis.getHeight();
		bitmap = ((BitmapDrawable) this.getDrawable()).getBitmap();
		init();
	}

	public ZoomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Display dis = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		width = dis.getWidth();
		height = dis.getHeight();
		bitmap = ((BitmapDrawable) this.getDrawable()).getBitmap();
		System.out.println(bitmap.getHeight() + "===" + bitmap.getWidth());
		init();
	}

	private void init() {
		matrix = new Matrix();
		savePreMatrix = new Matrix();
		start = new PointF();
		mid = new PointF();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// ���㶯������
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		switch (action) {
		// ���㰴��ʱ�������¼�
		case MotionEvent.ACTION_DOWN:
			savePreMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			MODE = DRAG;// ת��Ϊ��ҷ״̬
			break;
		case MotionEvent.ACTION_MOVE:
			if (MODE == DRAG) {
				matrix.set(savePreMatrix);
				// ˮƽ�ƶ�
				matrix.postTranslate(event.getX() - start.x, event.getY()
						- start.y);
			} else if (MODE == ZOOM) {
				float newDist = calDistance(event);
				float scale = newDist / oldDist;
				matrix.set(savePreMatrix);
				matrix.postScale(scale, scale, mid.x, mid.y);

			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:// ���״̬
			oldDist = calDistance(event);// ��������֮��ľ���
			savePreMatrix.set(matrix);
			mid = this.getMidPoint(mid, event);
			MODE = ZOOM;// ת��Ϊ����״̬
			break;
		// ͬʱ����������״̬
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			MODE = NONE;
			break;
		}
		this.setImageMatrix(matrix);
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		bitmap = ((BitmapDrawable) this.getDrawable()).getBitmap();
		System.out.println(bitmap.getHeight() + "===" + bitmap.getWidth());
		super.onDraw(canvas);
	}

	// ��������֮��ľ���
	private float calDistance(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	// ������������ĵ�
	private PointF getMidPoint(PointF p, MotionEvent event) {
		float midX = (event.getX(0) + event.getX(1)) / 2;
		float midY = (event.getY(0) + event.getY(1)) / 2;
		p.set(midX, midY);
		return p;
	}

}
