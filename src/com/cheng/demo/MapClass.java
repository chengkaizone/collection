package com.cheng.demo;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MKGeocoderAddressComponent;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKRoute;
import com.baidu.mapapi.MKRoutePlan;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKTransitRoutePlan;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.baidu.mapapi.PoiOverlay;
import com.baidu.mapapi.RouteOverlay;
import com.baidu.mapapi.TransitOverlay;
import com.cheng.handler.SearchHandler;
import com.cheng.overlay.MyItemOverlay;
import com.cheng.util.DialogUtil;

/**
 * 显示地图的主类 使用时请注意搜索地名；没结果请换一个地名
 * 
 * @author Think
 * 
 */
public class MapClass extends MapActivity implements OnClickListener {
	// 定义为静态主要是为了添加自定义覆盖物的点击事件
	public static MapView mapView;
	// 主界面控件
	private Button btn1, btn2, btn3, btn4, btn5;
	// 全局的 布局文件解析器
	// 定义第一个点为自己所在位置；当前点为查询的起点
	private GeoPoint first_point, current_point;
	// 当前地址信息
	private String current_addrinfo = "江苏路";
	private BMapManager manager; // 百度MapAPI的管理类
	private MKSearch mSearch; // 文字搜索模块-->需要关联地图管理器和相应的搜索监听器
	private SearchHandler handler;
	// onResume时注册此listener，onPause时需要Remove
	private LocationListener mLocationListener;
	private MyItemOverlay SecondOverlay; // 覆盖图层 定位图层
	private MapController controller;
	private boolean isShowTraffic = false;
	private Drawable marker;// 地图上的标记
	private MyLocationOverlay locoverlay;
	private String key = "699C7AC1348DE8A186B6726F07E8C7217537D128";
	private DialogUtil dialog;

	public void onCreate(Bundle save) {
		System.out.println("onCreate--->");
		super.onCreate(save);
		setContentView(R.layout.beatiful_layout);
		// 设置默认的按键来调用搜索条---搜索键
		setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
		btn1 = (Button) findViewById(R.id.beatiful_imgbtn1);
		btn2 = (Button) findViewById(R.id.beatiful_imgbtn2);
		btn3 = (Button) findViewById(R.id.beatiful_imgbtn3);
		btn4 = (Button) findViewById(R.id.beatiful_imgbtn4);
		btn5 = (Button) findViewById(R.id.beatiful_imgbtn5);
		mapView = (MapView) findViewById(R.id.beatiful_map);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		manager = new BMapManager(this);
		manager.init(key, new MyGeneralListener());
		super.initMapActivity(manager);
		controller = mapView.getController();
		controller.setZoom(15);
		mSearch = new MKSearch();// *1
		mSearch.init(manager, new MyMKSearchListener());// *2
		dialog = new DialogUtil(this, mSearch);// *3步骤不能乱
		locoverlay = new MyLocationOverlay(MapClass.this, mapView);

		marker = this.getResources().getDrawable(R.drawable.iconmarka);
		mLocationListener = new MyLocationListener();
		// 此处调用此方法是为了防止重新经历生命周期
		// doSearch(getIntent());
	}

	private void searchNear(String str) {
		if (str.equals("")) {
			showHint("请输入你感兴趣的地方!");
		} else {
			showHint("正在搜索!请稍后...");
			mSearch.poiSearchNearBy(str, current_point, 5000);
		}
	}

	private void showNear() {
		final String[] srr = { "公交站", "地铁站", "餐饮店", "美食店", "服装店" };
		Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.btn_check_buttonless_on).setTitle("搜索附近")
				.setItems(srr, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						searchNear(srr[which]);
					}
				}).create().show();
	}

	// 此方法由搜索按键触发---呼出输入框
	// public boolean onSearchRequested() {
	// System.out.println("onSearchRequested--->");
	// // 调用此方法是调用google的搜索条联网搜索
	// // this.startSearch("", true, null, true);
	// return true;
	// }
	//
	// /**
	// * 接收外部动作---搜索框按钮触发后调用此方法--->intent参数通常是getIntent（）方法返回的数据
	// * setIntent(intent);通常可以使用getIntent方法取得使用搜索框会重新经历生命周期
	// */
	// public void onNewIntent(Intent intent) {
	// System.out.println("onNew--->");
	// setIntent(intent);
	// doSearch(intent);
	// }
	private void updateLocation(Location loc) {
		GeoPoint geo = new GeoPoint((int) (loc.getLatitude() * 1E6),
				(int) (loc.getLongitude() * 1E6));
		controller.animateTo(geo);
		mapView.setBuiltInZoomControls(true);
		mapView.setDrawOverlayWhenZooming(true);
	}

	// 处理Intent中的关键字；如果是数据处理数据；此处只是简单的返回搜索框的内容
	// private void doSearch(Intent intent) {
	// System.out.println("doSearch--->");
	// if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	// String result = intent.getStringExtra(SearchManager.QUERY);
	// System.out.println(result + "--result--->");
	// searchNear(result);
	// }
	// }
	private void showHint(String str) {
		Toast.makeText(MapClass.this, str, 2000).show();
	}

	private void showHint(String str, int time) {
		if (time < 2000) {
			Toast.makeText(MapClass.this, str, 2000).show();
			return;
		}
		Toast.makeText(MapClass.this, str, time).show();
	}

	@Override
	protected void onResume() {
		System.out.println("onResume--->");
		// 恢复时注册监听器请求位置更新
		manager.getLocationManager().requestLocationUpdates(mLocationListener);
		locoverlay.enableMyLocation();
		locoverlay.enableCompass(); // 打开指南针
		mapView.getOverlays().add(locoverlay);
		manager.start();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		System.out.println("onDestroy--->");
		if (manager != null) {
			manager.destroy();
			manager = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onStart() {
		System.out.println("onStart--->");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		System.out.println("onRestart--->");
		super.onRestart();
	}

	@Override
	protected void onStop() {
		System.out.println("onStop--->");
		super.onStop();
	}

	@Override
	protected void onPause() {
		System.out.println("onPause--->");
		// 暂停时一定要移除监听器以节省点亮以及cpu及内存
		manager.getLocationManager().removeUpdates(mLocationListener);
		locoverlay.disableMyLocation();
		locoverlay.disableCompass(); // 关闭指南针
		manager.stop();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "菜单");
		menu.add(0, 1, 1, "交通图");
		menu.add(0, 2, 2, "常规图");
		menu.add(0, 3, 3, "退出");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case 0:
			menuDialog();
			break;
		case 1:
			mapView.setTraffic(true);
			break;
		case 2:
			mapView.setTraffic(false);
			break;
		case 3:
			dialog.exitDialog();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void menuDialog() {
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("请选择").setItems(
				new String[] { "返回我的位置", "显示当前位置信息", "搜索附近" },
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0: {
							if (first_point != null) {
								controller.animateTo(first_point);
								current_point = first_point;
							} else {
								showHint("当前网络不可用！");
							}
							break;
						}
						case 1:
							showHint(current_addrinfo, 5000);
							break;
						case 2:
							showNear();
							break;
						}
					}
				});
		builder.setNegativeButton("取消", null).create().show();
	}

	protected boolean isRouteDisplayed() {
		return true;
	}

	private void searchDialog() {
		LinearLayout lay = (LinearLayout) getLayoutInflater().inflate(
				R.layout.search_near, null);
		final EditText et1 = (EditText) lay.findViewById(R.id.dialog_near);
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("感兴趣的地方")
				.setPositiveButton("搜索", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String addr = et1.getText().toString().trim();
						if (addr.equals("")) {
							showHint("请输入完整信息");
							return;
						}
						showHint("正在搜索:" + "--->" + addr);
						searchNear(addr);
					}
				}).setNegativeButton("取消", null);
		builder.setView(lay);
		builder.create().show();
	}

	private class MyGeneralListener implements MKGeneralListener {
		public void onGetPermissionState(int arg0) {
			showHint("请输入正确的key！");
		}

		public void onGetNetworkState(int arg0) {
			showHint("当前网络信号不佳！请转到室外或检查你的网络设置!确保网络畅通！", 5000);
		}
	}

	private class MyLocationListener implements LocationListener {
		public void onLocationChanged(Location loc) {
			updateLocation(loc);
			first_point = new GeoPoint((int) (loc.getLatitude() * 1E6),
					(int) (loc.getLongitude() * 1E6));
			current_point = new GeoPoint((int) (loc.getLatitude() * 1E6),
					(int) (loc.getLongitude() * 1E6));
			manager.getLocationManager().removeUpdates(mLocationListener);// 不取消适用于导航
			mSearch.reverseGeocode(current_point);// 根据经纬度查询地址信息
		}
	}

	private class MyMKSearchListener implements MKSearchListener {
		// 本地搜索兴趣点
		@Override
		public void onGetPoiResult(MKPoiResult res, int type, int error) {
			System.out.println("搜索附近--->");
			try {
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					showHint("非常抱歉！未找到结果！");
					return;
				}
				// 将地图移动到第一个POI中心点
				if (res.getCurrentNumPois() > 0) {
					current_point = res.getPoi(0).pt;
					// 将poi结果显示到地图上
					PoiOverlay poiOverlay = new PoiOverlay(MapClass.this,
							mapView);
					poiOverlay.setData(res.getAllPoi());
					mapView.getController().animateTo(res.getPoi(0).pt);// 定位到第一个点
					mapView.getOverlays().clear();
					mapView.getOverlays().add(locoverlay);
					mapView.getOverlays().add(poiOverlay);
					mSearch.reverseGeocode(current_point);
				} else if (res.getCityListNum() > 0) {
					StringBuilder sb = new StringBuilder("在");
					for (int i = 0; i < res.getCityListNum(); i++) {
						sb.append(res.getCityListInfo(i).city + ",");
					}
					sb.append("找到了结果;请稍候...");
					showHint(sb.toString());
				}
			} catch (Exception e) {
				showHint("非常抱歉！未找到结果！");
				e.printStackTrace();
			}
		}

		// 搜索驾车路线
		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult res, int arg1) {
			System.out.println("驾车路线--->");
			try {
				if (res == null || res.getNumPlan() <= 0) {
					showHint("非常抱歉！未找到结果！建议您换一个地名查询！");
					return;
				}
				RouteOverlay ro = new RouteOverlay(MapClass.this, mapView);
				MKRoutePlan plan = res.getPlan(0);
				MKRoute route = plan.getRoute(0);
				if (route != null) {
					ro.setData(route);
					GeoPoint startPoint = res.getStart().pt;
					// GeoPoint endPoint = res.getEnd().pt;
					int dist = route.getDistance();
					int step = route.getNumSteps();
					controller.animateTo(startPoint);
					mapView.getOverlays().clear();
					mapView.getOverlays().add(locoverlay);
					mapView.getOverlays().add(ro);// 一定要定位到起点否则看不到路线效果
					if (startPoint != null) {
						current_point = startPoint;// 把起点设置为当前点一边搜索附近信息
						showHint("当前步行路线的距离:" + dist + "米\n" + "当前路线的关键站点数:"
								+ step, 5000);
						mSearch.reverseGeocode(current_point);
					} else {
						showHint("非常抱歉！未取得 起点坐标点；\n无法定位到此点！请重新查询\n或将地图滑到此点获取路线信息！");
					}
				} else {
					showHint("非常抱歉！未查到路线图！\n你可以选择通过输入附近的其他地标进行查询！");
				}
			} catch (Exception e) {
				// showHint("非常抱歉！未查到路线图！\n你可以选择通过输入附近的其他地标进行查询！");
				e.printStackTrace();
			}
		}

		// 回调步行路线结果
		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
			System.out.println("步行路线--->");
			try {
				if (res == null) {
					showHint("非常抱歉！未找到结果！建议您换一个地名查询！");
					return;
				}
				// showHint("解析步行数据成功！");
				RouteOverlay ro = new RouteOverlay(MapClass.this, mapView);
				MKRoutePlan plan = res.getPlan(0);
				MKRoute route = plan.getRoute(0);
				if (route != null) {
					ro.setData(route);
					int dist = route.getDistance();
					int step = route.getNumSteps();
					GeoPoint startPoint = res.getStart().pt;
					// GeoPoint endPoint = res.getEnd().pt;
					controller.animateTo(startPoint);
					mapView.getOverlays().clear();
					mapView.getOverlays().add(locoverlay);
					mapView.getOverlays().add(ro);
					if (startPoint != null) {
						current_point = startPoint;
						showHint("当前步行路线的距离:" + dist + "米", 5000);
						mSearch.reverseGeocode(current_point);
					} else {
						showHint("非常抱歉！未取得 起点坐标点；\n无法定位到此点！请重新查询\n或将地图滑到此点获取路线信息！");
					}
				} else {
					showHint("非常抱歉！未查到路线图！\n你可以选择通过输入附近的其他地标进行查询！");
				}
			} catch (Exception e) {
				// showHint("非常抱歉！未查到路线图！\n你可以选择通过输入附近的其他地标进行查询！");
				e.printStackTrace();
			}
		}

		// 回调乘车路线结果
		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult res, int arg1) {
			System.out.println("公交路线--->");
			try {
				if (res == null || res.getNumPlan() <= 0) {
					showHint("非常抱歉！未找到结果！建议您换一个地名查询！");
					return;
				}
				TransitOverlay to = new TransitOverlay(MapClass.this, mapView);
				MKTransitRoutePlan plan = res.getPlan(0);
				int dist = plan.getDistance();
				// String route_info=plan.getContent();
				to.setData(res.getPlan(0));
				String startName = res.getStart().name;
				String endName = res.getEnd().name;
				showHint(startName + "-->" + endName);
				GeoPoint startPoint = res.getStart().pt;
				// GeoPoint endPoint = res.getEnd().pt;//终点
				controller.animateTo(startPoint);
				mapView.getOverlays().clear();
				mapView.getOverlays().add(locoverlay);
				mapView.getOverlays().add(to);
				if (startPoint != null) {
					current_point = startPoint;
					showHint("当前公交路线的距离:" + dist + "米", 5000);
					mSearch.reverseGeocode(current_point);
				} else {
					showHint("非常抱歉！未取得 起点坐标点；\n无法定位到此点！请重新查询\n或将地图滑到此点获取路线信息！");
				}
			} catch (Exception e) {
				// showHint("非常抱歉！未取得 起点坐标点；\n无法定位到此点！请重新查询\n或将地图滑到此点获取路线信息！");
				e.printStackTrace();
			}
		}

		// 回调mSearch的解析结果；搜索其他城市时回调此方法
		@Override
		public void onGetAddrResult(MKAddrInfo res, int id) {
			System.out.println("搜索其他城市--以及注册监听时触发--->");
			try {
				if (res == null)
					return;
				if (id == 0 && res.poiList.size() > 0) {
					GeoPoint gp = res.geoPt;
					if (gp != null) {
						// 把搜索的当前地址记录下来
						current_point = gp;
					}
					StringBuilder sb = new StringBuilder();
					MKGeocoderAddressComponent com = res.addressComponents;
					sb.append("起点位置信息：\n");
					sb.append("省份：" + com.province + "\n");
					sb.append("城市：" + com.city + "\n");
					sb.append("地区：" + com.district + "\n");
					sb.append("街道：" + com.street + "\n");
					current_addrinfo = sb.toString();
					showHint(current_addrinfo);
					return;
				}
				showHint("非常抱歉！没有搜索到结果！\n你可以选择通过输入附近的其他地标进行查询！", 5000);
			} catch (Exception e) {
				// showHint("非常抱歉！没有搜索到结果！\n你可以选择通过输入附近的其他地标进行查询！", 5000);
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.beatiful_imgbtn1:
			// 此方法要过滤一个search动作;搜索框才会被触发---会重新经历生命周期
			// startSearch("", false, null, false);
			searchDialog();
			break;
		case R.id.beatiful_imgbtn2:
			// ok
			dialog.searchForCity();
			break;
		case R.id.beatiful_imgbtn3:
			// 无结果
			dialog.queryRouteDialog();
			break;
		case R.id.beatiful_imgbtn4:
			// 无结果
			dialog.queryWalkingDialog();
			break;
		case R.id.beatiful_imgbtn5:
			// 无结果
			dialog.queryTransitDialog();
			break;
		}
	}
}
