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

	// private double mLat1 = 39.90923; // point1γ��
	// private double mLon1 = 116.357428; // point1����
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
		// �ø����ľ�γ�ȹ���GeoPoint����λ��΢�� (�� * 1E6)
		// GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
		// GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
		// GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
		//
		// // ����OverlayItem��������������Ϊ��item��λ�ã������ı�������Ƭ��
		// mGeoList.add(new OverlayItem(p1, "P1", "point1"));
		// mGeoList.add(new OverlayItem(p2, "P2", "point2"));
		// mGeoList.add(new OverlayItem(p3, "P3", "point3"));
		// mGeoList.add(new OverlayItem(point, "��һ����","��һ���������"));
		// if(points!=null){
		// for(int i=4;i<points.size();i++){
		// mGeoList.add(new OverlayItem(points.get(i),"p"+i,"point"+i));
		// }
		// }
		populate(); // createItem(int)��������item��һ���������ݣ��ڵ�����������ǰ�����ȵ����������
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {

		// Projection�ӿ�������Ļ��������;�γ������֮��ı任
		Projection projection = mapView.getProjection();
		for (int index = size() - 1; index >= 0; index--) { // ����mGeoList
			OverlayItem overLayItem = this.getItem(index); // �õ�����������item

			String title = overLayItem.getTitle();
			// �Ѿ�γ�ȱ任�������MapView���Ͻǵ���Ļ��������
			Point point = projection.toPixels(overLayItem.getPoint(),
					new Point());

			// ���ڴ˴�������Ļ��ƴ���
			Paint paintText = new Paint();
			paintText.setColor(Color.BLUE);
			paintText.setTextSize(10);
			canvas.drawText(title, point.x + 5, point.y - 5, paintText); // �����ı�
		}

		super.draw(canvas, mapView, shadow);
		// ����һ��drawable�߽磬ʹ�ã�0��0�������drawable�ײ����һ�����ĵ�һ�����ص�
		boundCenterBottom(marker);
	}

	public void addPoint(GeoPoint point) {
		OverlayItem temp = new OverlayItem(point, "����", "����");
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
	// ��������¼�
	protected boolean onTap(int i) {
		setFocus(mGeoList.get(i));
		// ��������λ��,��ʹ֮��ʾ
		// GeoPoint pt = mGeoList.get(i).getPoint();
		// MainClass.mapView.updateViewLayout( MainClass.mPopView,
		// new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT,
		// pt, MapView.LayoutParams.BOTTOM_CENTER));
		// MainClass.popView.setVisibility(View.VISIBLE);
		// // TextView
		// info=(TextView)MainClass.popView.findViewById(R.id.pop_info);
		// // info.setText("�����ˣ�"+mGeoList.get(i).getSnippet());
		return true;
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1) {
		// ��ȥ����������
		// MainClass.popView.setVisibility(View.GONE);
		return super.onTap(arg0, arg1);
	}
}
