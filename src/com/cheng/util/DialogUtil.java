package com.cheng.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mapapi.MKPlanNode;
import com.baidu.mapapi.MKSearch;
import com.cheng.demo.R;

public class DialogUtil {
	private LayoutInflater inflater;
	private Context context;
	private MKSearch mSearch;

	public DialogUtil(Context context, MKSearch mSearch) {
		this.context = context;
		this.mSearch = mSearch;
		this.inflater = LayoutInflater.from(context);
	}

	// 去其他城市OK
	public void searchForCity() {
		LinearLayout lay = (LinearLayout) inflater.inflate(R.layout.othercity,
				null);
		final EditText et1 = (EditText) lay.findViewById(R.id.other_city);
		final EditText et2 = (EditText) lay.findViewById(R.id.other_name);
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("前往城市信息")
				.setPositiveButton("前往", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String city = et1.getText().toString().trim();
						String addr = et2.getText().toString().trim();
						if (city.equals("") || addr.equals("")) {
							showHint("请输入完整信息");
							return;
						}
						showHint("你前往的地址:" + city + "--->" + addr);
						mSearch.poiSearchInCity(city, addr);
					}
				}).setNegativeButton("取消", null);
		builder.setView(lay);
		builder.create().show();
	}

	public void queryRouteDialog() {
		LinearLayout lay = (LinearLayout) inflater.inflate(R.layout.drive_walk,
				null);
		final EditText et1 = (EditText) lay.findViewById(R.id.start_city);
		final EditText et2 = (EditText) lay.findViewById(R.id.start_name);
		final EditText et3 = (EditText) lay.findViewById(R.id.end_city);
		final EditText et4 = (EditText) lay.findViewById(R.id.end_name);
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("请输入起始地点").setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String start_city = et1.getText().toString().trim();
						String start_name = et2.getText().toString().trim();
						String end_city = et3.getText().toString().trim();
						String end_name = et4.getText().toString().trim();
						if (start_city.equals("") || start_name.equals("")
								|| end_city.equals("") || end_name.equals("")) {
							showHint("请输入完整的路线信息!");
							return;
						}
						showHint("驾车出行:" + start_city + ":" + start_name
								+ "-->" + end_city + ":" + end_name);
						showHint("正在查询！请稍候...");
						MKPlanNode node1 = new MKPlanNode();
						MKPlanNode node2 = new MKPlanNode();
						node1.name = start_name;
						node2.name = end_name;
						// mSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST);
						mSearch.drivingSearch(start_city, node1, end_city,
								node2);
					}
				});
		builder.setNegativeButton("取消", null);
		builder.setView(lay);
		builder.create().show();
	}

	// 步行搜索--->
	public void queryWalkingDialog() {
		LinearLayout lay = (LinearLayout) inflater.inflate(R.layout.drive_walk,
				null);
		final EditText et1 = (EditText) lay.findViewById(R.id.start_city);
		final EditText et2 = (EditText) lay.findViewById(R.id.start_name);
		final EditText et3 = (EditText) lay.findViewById(R.id.end_city);
		final EditText et4 = (EditText) lay.findViewById(R.id.end_name);
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("请输入起始地点").setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String start_city = et1.getText().toString().trim();
						String start_name = et2.getText().toString().trim();
						String end_city = et3.getText().toString().trim();
						String end_name = et4.getText().toString().trim();
						if (start_city.equals("") || start_name.equals("")
								|| end_city.equals("") || end_name.equals("")) {
							showHint("请输入完整的步行信息!");
							return;
						}
						showHint("步行路线:" + start_city + ":" + start_name + "\n"
								+ "-->" + end_city + ":" + end_name);
						showHint("正在查询！请稍候...");
						MKPlanNode node1 = new MKPlanNode();
						MKPlanNode node2 = new MKPlanNode();
						node1.name = start_name;
						node2.name = end_name;
						mSearch.walkingSearch(start_city, node1, end_city,
								node2);
					}
				});
		builder.setNegativeButton("取消", null);
		builder.setView(lay);
		builder.create().show();
	}

	// 驾车搜索
	public void queryTransitDialog() {
		LinearLayout lay = (LinearLayout) inflater.inflate(R.layout.common,
				null);
		final EditText et0 = (EditText) lay.findViewById(R.id.common_input0);
		final EditText et1 = (EditText) lay.findViewById(R.id.common_input1);
		final EditText et2 = (EditText) lay.findViewById(R.id.common_input2);
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("请输入起始地点").setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String city = et0.getText().toString().trim();
						String s1 = et1.getText().toString().trim();
						String s2 = et2.getText().toString().trim();
						if (city.equals("") || s1.equals("") || s2.equals("")) {
							showHint("请输入乘车信息!");
							return;
						}
						showHint("公交出行:" + city + "--->" + s1 + "-->" + s2);
						showHint("正在查询！请稍候...");
						MKPlanNode start = new MKPlanNode();
						MKPlanNode end = new MKPlanNode();
						start.name = s1;
						end.name = s2;
						// mSearch.setTransitPolicy(MKSearch.EBUS_TRANSFER_FIRST);
						mSearch.transitSearch(city, start, end);
					}
				});
		builder.setNegativeButton("取消", null);
		builder.setView(lay);
		builder.create().show();
	}

	public void exitDialog() {
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("退出程序").setMessage("你确定退出程序吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						((Activity) context).finish();
					}
				}).setNegativeButton("取消", null).create().show();
	}

	public void showHint(String str) {
		Toast.makeText(context, str, 2000).show();
	}
}
