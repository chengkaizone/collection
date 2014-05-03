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
 * ��ʾ��ͼ������ ʹ��ʱ��ע������������û����뻻һ������
 * 
 * @author Think
 * 
 */
public class MapClass extends MapActivity implements OnClickListener {
	// ����Ϊ��̬��Ҫ��Ϊ������Զ��帲����ĵ���¼�
	public static MapView mapView;
	// ������ؼ�
	private Button btn1, btn2, btn3, btn4, btn5;
	// ȫ�ֵ� �����ļ�������
	// �����һ����Ϊ�Լ�����λ�ã���ǰ��Ϊ��ѯ�����
	private GeoPoint first_point, current_point;
	// ��ǰ��ַ��Ϣ
	private String current_addrinfo = "����·";
	private BMapManager manager; // �ٶ�MapAPI�Ĺ�����
	private MKSearch mSearch; // ��������ģ��-->��Ҫ������ͼ����������Ӧ������������
	private SearchHandler handler;
	// onResumeʱע���listener��onPauseʱ��ҪRemove
	private LocationListener mLocationListener;
	private MyItemOverlay SecondOverlay; // ����ͼ�� ��λͼ��
	private MapController controller;
	private boolean isShowTraffic = false;
	private Drawable marker;// ��ͼ�ϵı��
	private MyLocationOverlay locoverlay;
	private String key = "699C7AC1348DE8A186B6726F07E8C7217537D128";
	private DialogUtil dialog;

	public void onCreate(Bundle save) {
		System.out.println("onCreate--->");
		super.onCreate(save);
		setContentView(R.layout.beatiful_layout);
		// ����Ĭ�ϵİ���������������---������
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
		dialog = new DialogUtil(this, mSearch);// *3���費����
		locoverlay = new MyLocationOverlay(MapClass.this, mapView);

		marker = this.getResources().getDrawable(R.drawable.iconmarka);
		mLocationListener = new MyLocationListener();
		// �˴����ô˷�����Ϊ�˷�ֹ���¾�����������
		// doSearch(getIntent());
	}

	private void searchNear(String str) {
		if (str.equals("")) {
			showHint("�����������Ȥ�ĵط�!");
		} else {
			showHint("��������!���Ժ�...");
			mSearch.poiSearchNearBy(str, current_point, 5000);
		}
	}

	private void showNear() {
		final String[] srr = { "����վ", "����վ", "������", "��ʳ��", "��װ��" };
		Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.btn_check_buttonless_on).setTitle("��������")
				.setItems(srr, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						searchNear(srr[which]);
					}
				}).create().show();
	}

	// �˷�����������������---���������
	// public boolean onSearchRequested() {
	// System.out.println("onSearchRequested--->");
	// // ���ô˷����ǵ���google����������������
	// // this.startSearch("", true, null, true);
	// return true;
	// }
	//
	// /**
	// * �����ⲿ����---������ť��������ô˷���--->intent����ͨ����getIntent�����������ص�����
	// * setIntent(intent);ͨ������ʹ��getIntent����ȡ��ʹ������������¾�����������
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

	// ����Intent�еĹؼ��֣���������ݴ������ݣ��˴�ֻ�Ǽ򵥵ķ��������������
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
		// �ָ�ʱע�����������λ�ø���
		manager.getLocationManager().requestLocationUpdates(mLocationListener);
		locoverlay.enableMyLocation();
		locoverlay.enableCompass(); // ��ָ����
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
		// ��ͣʱһ��Ҫ�Ƴ��������Խ�ʡ�����Լ�cpu���ڴ�
		manager.getLocationManager().removeUpdates(mLocationListener);
		locoverlay.disableMyLocation();
		locoverlay.disableCompass(); // �ر�ָ����
		manager.stop();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "�˵�");
		menu.add(0, 1, 1, "��ͨͼ");
		menu.add(0, 2, 2, "����ͼ");
		menu.add(0, 3, 3, "�˳�");
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
		builder.setTitle("��ѡ��").setItems(
				new String[] { "�����ҵ�λ��", "��ʾ��ǰλ����Ϣ", "��������" },
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0: {
							if (first_point != null) {
								controller.animateTo(first_point);
								current_point = first_point;
							} else {
								showHint("��ǰ���粻���ã�");
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
		builder.setNegativeButton("ȡ��", null).create().show();
	}

	protected boolean isRouteDisplayed() {
		return true;
	}

	private void searchDialog() {
		LinearLayout lay = (LinearLayout) getLayoutInflater().inflate(
				R.layout.search_near, null);
		final EditText et1 = (EditText) lay.findViewById(R.id.dialog_near);
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("����Ȥ�ĵط�")
				.setPositiveButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String addr = et1.getText().toString().trim();
						if (addr.equals("")) {
							showHint("������������Ϣ");
							return;
						}
						showHint("��������:" + "--->" + addr);
						searchNear(addr);
					}
				}).setNegativeButton("ȡ��", null);
		builder.setView(lay);
		builder.create().show();
	}

	private class MyGeneralListener implements MKGeneralListener {
		public void onGetPermissionState(int arg0) {
			showHint("��������ȷ��key��");
		}

		public void onGetNetworkState(int arg0) {
			showHint("��ǰ�����źŲ��ѣ���ת��������������������!ȷ�����糩ͨ��", 5000);
		}
	}

	private class MyLocationListener implements LocationListener {
		public void onLocationChanged(Location loc) {
			updateLocation(loc);
			first_point = new GeoPoint((int) (loc.getLatitude() * 1E6),
					(int) (loc.getLongitude() * 1E6));
			current_point = new GeoPoint((int) (loc.getLatitude() * 1E6),
					(int) (loc.getLongitude() * 1E6));
			manager.getLocationManager().removeUpdates(mLocationListener);// ��ȡ�������ڵ���
			mSearch.reverseGeocode(current_point);// ���ݾ�γ�Ȳ�ѯ��ַ��Ϣ
		}
	}

	private class MyMKSearchListener implements MKSearchListener {
		// ����������Ȥ��
		@Override
		public void onGetPoiResult(MKPoiResult res, int type, int error) {
			System.out.println("��������--->");
			try {
				// ����ſɲο�MKEvent�еĶ���
				if (error != 0 || res == null) {
					showHint("�ǳ���Ǹ��δ�ҵ������");
					return;
				}
				// ����ͼ�ƶ�����һ��POI���ĵ�
				if (res.getCurrentNumPois() > 0) {
					current_point = res.getPoi(0).pt;
					// ��poi�����ʾ����ͼ��
					PoiOverlay poiOverlay = new PoiOverlay(MapClass.this,
							mapView);
					poiOverlay.setData(res.getAllPoi());
					mapView.getController().animateTo(res.getPoi(0).pt);// ��λ����һ����
					mapView.getOverlays().clear();
					mapView.getOverlays().add(locoverlay);
					mapView.getOverlays().add(poiOverlay);
					mSearch.reverseGeocode(current_point);
				} else if (res.getCityListNum() > 0) {
					StringBuilder sb = new StringBuilder("��");
					for (int i = 0; i < res.getCityListNum(); i++) {
						sb.append(res.getCityListInfo(i).city + ",");
					}
					sb.append("�ҵ��˽��;���Ժ�...");
					showHint(sb.toString());
				}
			} catch (Exception e) {
				showHint("�ǳ���Ǹ��δ�ҵ������");
				e.printStackTrace();
			}
		}

		// �����ݳ�·��
		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult res, int arg1) {
			System.out.println("�ݳ�·��--->");
			try {
				if (res == null || res.getNumPlan() <= 0) {
					showHint("�ǳ���Ǹ��δ�ҵ��������������һ��������ѯ��");
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
					mapView.getOverlays().add(ro);// һ��Ҫ��λ�������򿴲���·��Ч��
					if (startPoint != null) {
						current_point = startPoint;// ���������Ϊ��ǰ��һ������������Ϣ
						showHint("��ǰ����·�ߵľ���:" + dist + "��\n" + "��ǰ·�ߵĹؼ�վ����:"
								+ step, 5000);
						mSearch.reverseGeocode(current_point);
					} else {
						showHint("�ǳ���Ǹ��δȡ�� �������㣻\n�޷���λ���˵㣡�����²�ѯ\n�򽫵�ͼ�����˵��ȡ·����Ϣ��");
					}
				} else {
					showHint("�ǳ���Ǹ��δ�鵽·��ͼ��\n�����ѡ��ͨ�����븽���������ر���в�ѯ��");
				}
			} catch (Exception e) {
				// showHint("�ǳ���Ǹ��δ�鵽·��ͼ��\n�����ѡ��ͨ�����븽���������ر���в�ѯ��");
				e.printStackTrace();
			}
		}

		// �ص�����·�߽��
		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
			System.out.println("����·��--->");
			try {
				if (res == null) {
					showHint("�ǳ���Ǹ��δ�ҵ��������������һ��������ѯ��");
					return;
				}
				// showHint("�����������ݳɹ���");
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
						showHint("��ǰ����·�ߵľ���:" + dist + "��", 5000);
						mSearch.reverseGeocode(current_point);
					} else {
						showHint("�ǳ���Ǹ��δȡ�� �������㣻\n�޷���λ���˵㣡�����²�ѯ\n�򽫵�ͼ�����˵��ȡ·����Ϣ��");
					}
				} else {
					showHint("�ǳ���Ǹ��δ�鵽·��ͼ��\n�����ѡ��ͨ�����븽���������ر���в�ѯ��");
				}
			} catch (Exception e) {
				// showHint("�ǳ���Ǹ��δ�鵽·��ͼ��\n�����ѡ��ͨ�����븽���������ر���в�ѯ��");
				e.printStackTrace();
			}
		}

		// �ص��˳�·�߽��
		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult res, int arg1) {
			System.out.println("����·��--->");
			try {
				if (res == null || res.getNumPlan() <= 0) {
					showHint("�ǳ���Ǹ��δ�ҵ��������������һ��������ѯ��");
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
				// GeoPoint endPoint = res.getEnd().pt;//�յ�
				controller.animateTo(startPoint);
				mapView.getOverlays().clear();
				mapView.getOverlays().add(locoverlay);
				mapView.getOverlays().add(to);
				if (startPoint != null) {
					current_point = startPoint;
					showHint("��ǰ����·�ߵľ���:" + dist + "��", 5000);
					mSearch.reverseGeocode(current_point);
				} else {
					showHint("�ǳ���Ǹ��δȡ�� �������㣻\n�޷���λ���˵㣡�����²�ѯ\n�򽫵�ͼ�����˵��ȡ·����Ϣ��");
				}
			} catch (Exception e) {
				// showHint("�ǳ���Ǹ��δȡ�� �������㣻\n�޷���λ���˵㣡�����²�ѯ\n�򽫵�ͼ�����˵��ȡ·����Ϣ��");
				e.printStackTrace();
			}
		}

		// �ص�mSearch�Ľ��������������������ʱ�ص��˷���
		@Override
		public void onGetAddrResult(MKAddrInfo res, int id) {
			System.out.println("������������--�Լ�ע�����ʱ����--->");
			try {
				if (res == null)
					return;
				if (id == 0 && res.poiList.size() > 0) {
					GeoPoint gp = res.geoPt;
					if (gp != null) {
						// �������ĵ�ǰ��ַ��¼����
						current_point = gp;
					}
					StringBuilder sb = new StringBuilder();
					MKGeocoderAddressComponent com = res.addressComponents;
					sb.append("���λ����Ϣ��\n");
					sb.append("ʡ�ݣ�" + com.province + "\n");
					sb.append("���У�" + com.city + "\n");
					sb.append("������" + com.district + "\n");
					sb.append("�ֵ���" + com.street + "\n");
					current_addrinfo = sb.toString();
					showHint(current_addrinfo);
					return;
				}
				showHint("�ǳ���Ǹ��û�������������\n�����ѡ��ͨ�����븽���������ر���в�ѯ��", 5000);
			} catch (Exception e) {
				// showHint("�ǳ���Ǹ��û�������������\n�����ѡ��ͨ�����븽���������ر���в�ѯ��", 5000);
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.beatiful_imgbtn1:
			// �˷���Ҫ����һ��search����;������Żᱻ����---�����¾�����������
			// startSearch("", false, null, false);
			searchDialog();
			break;
		case R.id.beatiful_imgbtn2:
			// ok
			dialog.searchForCity();
			break;
		case R.id.beatiful_imgbtn3:
			// �޽��
			dialog.queryRouteDialog();
			break;
		case R.id.beatiful_imgbtn4:
			// �޽��
			dialog.queryWalkingDialog();
			break;
		case R.id.beatiful_imgbtn5:
			// �޽��
			dialog.queryTransitDialog();
			break;
		}
	}
}
