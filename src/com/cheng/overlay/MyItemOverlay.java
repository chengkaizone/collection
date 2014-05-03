package com.cheng.overlay;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MKPoiInfo;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;
import com.cheng.demo.MapClass;

public class MyItemOverlay extends ItemizedOverlay<OverlayItem> {

	private List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
	private Drawable marker;
	private Context mContext;

	// private List<GeoPoint> points;
	// private GeoPoint current_point;

	// private double mLat1 = 39.90923; // point1纬度
	// private double mLon1 = 116.357428; // point1经度
	//
	// private double mLat2 = 39.90923;
	// private double mLon2 = 116.397428;
	//
	// private double mLat3 = 39.90923;
	// private double mLon3 = 116.437428;
	public MyItemOverlay(Drawable arg0) {
		super(arg0);
	}

	// public MyItemOverlay(Context context,Drawable marker,List<GeoPoint>
	// points){
	// this.marker = marker;
	// this.mContext = context;
	// this.points=points;
	// }
	public MyItemOverlay(Context context, Drawable marker) {
		super(boundCenterBottom(marker));

		this.marker = marker;
		this.mContext = context;
		// this.current_point=point;
		// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
		// GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
		// GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
		// GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
		//
		// // 构造OverlayItem的三个参数依次为：item的位置，标题文本，文字片段
		// mGeoList.add(new OverlayItem(p1, "P1", "point1"));
		// mGeoList.add(new OverlayItem(p2, "P2", "point2"));
		// mGeoList.add(new OverlayItem(p3, "P3", "point3"));
		// mGeoList.add(new OverlayItem(point, "第一个点","第一个点的内容"));
		// if(points!=null){
		// for(int i=4;i<points.size();i++){
		// mGeoList.add(new OverlayItem(points.get(i),"p"+i,"point"+i));
		// }
		// }
		populate(); // createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {

		// Projection接口用于屏幕像素坐标和经纬度坐标之间的变换
		Projection projection = mapView.getProjection();
		for (int index = size() - 1; index >= 0; index--) { // 遍历mGeoList
			OverlayItem overLayItem = this.getItem(index); // 得到给定索引的item

			String title = overLayItem.getTitle();
			// 把经纬度变换到相对于MapView左上角的屏幕像素坐标
			Point point = projection.toPixels(overLayItem.getPoint(),
					new Point());

			// 可在此处添加您的绘制代码
			Paint paintText = new Paint();
			paintText.setColor(Color.BLUE);
			paintText.setTextSize(10);
			canvas.drawText(title, point.x + 5, point.y - 5, paintText); // 绘制文本
		}

		super.draw(canvas, mapView, shadow);
		// 调整一个drawable边界，使得（0，0）是这个drawable底部最后一行中心的一个像素点
		boundCenterBottom(marker);
	}

	public void addPoint(GeoPoint point) {
		OverlayItem temp = new OverlayItem(point, "主题", "内容");
		mGeoList.add(temp);
	}

	public void setData(ArrayList<MKPoiInfo> data) {
		for (int i = 0; i < data.size(); i++) {
			GeoPoint gp = data.get(i).pt;
			String add_name = data.get(i).name;
			String add_info = data.get(i).address;
			System.out.println(add_name + "--->" + add_info);
			mGeoList.add(new OverlayItem(gp, add_name, add_info));
		}
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mGeoList.get(i);
	}

	@Override
	public int size() {
		return mGeoList.size();
	}

	@Override
	// 处理当点击事件
	protected boolean onTap(int i) {
		setFocus(mGeoList.get(i));
		// 更新气泡位置,并使之显示
		// GeoPoint pt = mGeoList.get(i).getPoint();
		// MainClass.mapView.updateViewLayout( MainClass.mPopView,
		// new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT,
		// pt, MapView.LayoutParams.BOTTOM_CENTER));
		// MainClass.popView.setVisibility(View.VISIBLE);
		// // TextView
		// info=(TextView)MainClass.popView.findViewById(R.id.pop_info);
		// // info.setText("你点击了："+mGeoList.get(i).getSnippet());
		return true;
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1) {
		// 消去弹出的气泡
		// MainClass.popView.setVisibility(View.GONE);
		return super.onTap(arg0, arg1);
	}
}
