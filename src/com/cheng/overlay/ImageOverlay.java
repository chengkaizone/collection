package com.cheng.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.Overlay;
import com.baidu.mapapi.Projection;
import com.cheng.demo.R;

public class ImageOverlay extends Overlay {
	private Bitmap bitmap;
	private Context context;
	private double dLat, dLong;

	public ImageOverlay(Context context, double dLat, double dLong) {
		this.context = context;
		this.bitmap = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.iconmarka);
		this.dLat = dLat;
		this.dLong = dLong;
	}

	@Override
	public boolean draw(Canvas canvas, MapView arg1, boolean arg2, long arg3) {
		if (!arg2) {
			Projection pro = arg1.getProjection();
			GeoPoint geo = new GeoPoint((int) (dLat * 1E6), (int) (dLong * 1E6));
			Point point = new Point();
			pro.toPixels(geo, point);
			Rect src = new Rect(0, 0, 50, 50);
			Rect des = new Rect(point.x - 25, point.y - 25, point.x + 25,
					point.y + 25);
			Paint paint = new Paint();
			canvas.drawBitmap(bitmap, src, des, paint);
		}
		return super.draw(canvas, arg1, arg2, arg3);
	}

}
