package com.cheng.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class ShaderView extends View {
	private Context context;
	private Paint paint;

	public Paint getPaint() {
		return paint;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public ShaderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initParam(context);
	}

	public ShaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initParam(context);
	}

	public ShaderView(Context context) {
		super(context);
		initParam(context);
	}

	private void initParam(Context context) {
		this.context = context;
		this.paint = new Paint(Color.RED);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Rect r = new Rect(0, 0, getWidth(), getHeight());
		canvas.drawRect(r, paint);
	}
}
