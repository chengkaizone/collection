package com.cheng.overlay;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;

public class LabelOverlay extends ItemizedOverlay<OverlayItem> {
	private Context context;
	private Drawable marker;

	private double mLat1 = 39.90923; // point1纬度
	private double mLon1 = 116.357428; // point1经度
	private List<GeoPoint> points = new ArrayList<GeoPoint>();
	List<OverlayItem> overlays = new ArrayList<OverlayItem>();

	public LabelOverlay(Drawable arg0) {
		super(arg0);
	}

	public LabelOverlay(Context context, Drawable draw, List<GeoPoint> points) {
		this(draw);
		this.context = context;
		this.marker = marker;
		GeoPoint gp1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
		OverlayItem item1 = new OverlayItem(gp1, "测试点", "point1");
		overlays.add(item1);
		this.points = points;
		if (points != null) {
			for (int i = 0; i < points.size(); i++) {
				overlays.add(new OverlayItem(points.get(i), "测试点", "这是一个测试点"));
			}
		}
		this.populate();
	}

	@Override
	public void draw(Canvas canvas, MapView view, boolean shadom) {
		Projection pro = view.getProjection();
		for (int i = size() - 1; i >= 0; i--) {
			OverlayItem item = overlays.get(i);
			String title = item.getTitle();
			Point p = pro.toPixels(item.getPoint(), new Point());
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			paint.setTextSize(15);
			canvas.drawText(title, p.x - 30, p.y, paint);

		}
		super.draw(canvas, view, shadom);
		this.boundCenterBottom(marker);
	}

	// @Override
	// public boolean draw(Canvas canvas, MapView view, boolean shadom, long
	// arg3) {
	// Projection pro=view.getProjection();
	// for(int i=size()-1;i>=0;i--){
	// OverlayItem item=overlays.get(i);
	// String title=item.getTitle();
	// Point p=pro.toPixels(item.getPoint(),new Point());
	// Paint paint=new Paint();
	// paint.setColor(Color.RED);
	// paint.setTextSize(15);
	// canvas.drawText(title, p.x, p.y, paint);
	//
	// }
	// super.draw(canvas, view, shadom);
	// this.boundCenterBottom(marker);
	// return true;
	// }
	@Override
	protected OverlayItem createItem(int arg0) {
		return overlays.get(arg0);
	}

	@Override
	public int size() {
		return overlays.size();
	}

	@Override
	protected boolean onTap(int arg0) {
		// TODO Auto-generated method stub
		return super.onTap(arg0);
	}
}
