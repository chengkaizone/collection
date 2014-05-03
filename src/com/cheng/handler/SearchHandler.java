package com.cheng.handler;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MKPlanNode;
import com.baidu.mapapi.MKSearch;

public class SearchHandler extends Handler {
	private MKSearch mSearch;
	private Context context;

	public SearchHandler(Context context, MKSearch search) {
		this.mSearch = search;
		this.context = context;
	}

	public void handleMessage(Message msg) {
		Toast.makeText(context, "正在等待返回数据...", 2000).show();
		if (msg.arg1 == 1) {// 驾车信息
			Bundle bundle = msg.getData();
			// 通过工具类将实际地址转化为经纬度数组
			double[] arr_start = bundle.getDoubleArray("start");
			double[] arr_end = bundle.getDoubleArray("end");
			MKPlanNode start = new MKPlanNode();
			MKPlanNode end = new MKPlanNode();
			if (arr_start != null && arr_end != null) {
				// 包装为经纬度--->百度提供的直接用地址方法暂时不可用；不被执行
				GeoPoint p1 = new GeoPoint((int) (arr_start[0] * 1E6),
						(int) (arr_start[1] * 1E6));
				GeoPoint p2 = new GeoPoint((int) (arr_end[0] * 1E6),
						(int) (arr_end[1] * 1E6));
				start.pt = p1;
				end.pt = p2;
				mSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST);
				mSearch.drivingSearch(null, start, null, end);
			} else {
				Toast.makeText(context, "定位失败！", 2000).show();
			}
		} else if (msg.arg1 == 2) {// 步行信息
			Bundle bundle = msg.getData();
			String city = bundle.getString("city");
			double[] arr_start = bundle.getDoubleArray("start");
			double[] arr_end = bundle.getDoubleArray("end");
			MKPlanNode start = new MKPlanNode();
			MKPlanNode end = new MKPlanNode();
			// 包装为经纬度--->百度提供的直接用地址方法暂时不可用；不被执行
			if (arr_start != null && arr_end != null) {
				GeoPoint p1 = new GeoPoint((int) (arr_start[0] * 1E6),
						(int) (arr_start[1] * 1E6));
				GeoPoint p2 = new GeoPoint((int) (arr_end[0] * 1E6),
						(int) (arr_end[1] * 1E6));

				start.pt = p1;
				end.pt = p2;
				mSearch.walkingSearch(city, start, null, end);
			} else {
				Toast.makeText(context, "定位失败！", 2000).show();
			}
		} else if (msg.arg1 == 0) {// 错误信息
			Toast.makeText(context, "地址解析错误！", 2000).show();
		}
	}
}
