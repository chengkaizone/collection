package com.cheng.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cheng.demo.R;

public class WarpView extends View {
	private Bitmap bitmap;
	private int bWidth, bHeight;
	private int meshX = 20, meshY = 20;
	private int count = (meshX + 1) * (meshY + 1);
	private float[] orign = new float[count * 2];
	private float[] verts = new float[count * 2];

	public WarpView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initParam(context);
	}

	public WarpView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initParam(context);
	}

	public WarpView(Context context) {
		super(context);
		initParam(context);
	}

	// ��ʼ������
	private void initParam(Context context) {
		// ���ÿ��Ի�ý���
		this.setFocusable(true);
		bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.lashou);
		bWidth = bitmap.getWidth();
		bHeight = bitmap.getHeight();
		System.out.println("λͼ���---" + bWidth + "---" + bHeight);
		/**
		 * ��ʼ������ֵ---x/y��ֵ���Էֿ���ʼ�� ��������y��������--˫������x��������
		 */
		int index = 0;
		for (int i = 0; i <= meshY; i++) {
			// ѭ����ʼ��y����ֵ
			float y = i * bHeight / meshY;
			for (int j = 0; j <= meshX; j++) {
				// ѭ����ʼ��x����ֵ
				float x = j * bWidth / meshX;
				// ���������е�������Ϊy��������ֵ--˫��Ϊx����ֵ
				orign[index * 2 + 0] = verts[index * 2 + 0] = x;
				orign[index * 2 + 1] = verts[index * 2 + 1] = y;
				index++;
			}
		}
		this.setBackgroundColor(Color.WHITE);
	}

	// ���߷���--���ݴ�������������¼�������
	public void handlePoint(float cx, float cy) {
		for (int i = 0; i < count * 2; i += 2) {
			float calX = cx - orign[i + 0];
			float calY = cy - orign[i + 1];
			// ����ÿ�������뵱ǰ��ľ���--���귨����
			float area = calX * calX + calY * calY;
			float distance = (float) Math.sqrt(area);
			// ����Ť����--����ԽԶ��Ť��ԽС--���㷨�Զ��������Լ���
			float pull = 80000 / ((float) (area * distance));
			// �������������¸�ֵ
			if (pull >= 1) {
				verts[i + 0] = cx;
				verts[i + 1] = cy;
			} else {
				// ���Ƹ�����������ƫ��
				verts[i + 0] = orign[i + 0] + calX * pull;
				verts[i + 1] = orign[i + 1] + calY * pull;
			}
		}
		this.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmapMesh(bitmap, meshX, meshY, verts, 0, null, 0, null);
	}

	// ������߳�����Ӵ����������˷���������Ӧ
	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("��Ӧ�ؼ����������¼�---<");
		handlePoint(event.getX(), event.getY());
		return true;// ���෽��һ�㷵��false;�˴�һ��Ҫ��ȷ����true��ʾ�Ѵ���
	}
}
