package com.cheng.demo;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.adapter.BluetoothDataAdapter;

public class BluetoothTest extends CommonActivity implements OnClickListener {
	int state = 0;
	Button on, off, bond, search, selectFile;
	ListView listView;
	TextView hint, showFile;
	CheckBox cb;
	BluetoothAdapter adapter;
	BluetoothDataAdapter dataAdapter;
	BluetoothReceiver receiver;
	String fileName = "/mnt/sdcard/test.txt";
	List<BluetoothDevice> bondDevices = new ArrayList<BluetoothDevice>();
	List<BluetoothDevice> allDevices = new ArrayList<BluetoothDevice>();

	BluetoothDevice curDevice;
	BluetoothSocket socket;

	List<String> data = new ArrayList<String>();
	Handler mOthHandler;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.bluetooth);
		on = (Button) findViewById(R.id.blue_set_on);
		off = (Button) findViewById(R.id.blue_set_off);
		bond = (Button) findViewById(R.id.blue_search_bond);
		search = (Button) findViewById(R.id.blue_search_all);
		selectFile = (Button) findViewById(R.id.select_file);
		showFile = (TextView) findViewById(R.id.show_file);

		listView = (ListView) findViewById(R.id.blue_lv);
		cb = (CheckBox) findViewById(R.id.blue_cb);
		hint = (TextView) findViewById(R.id.blue_hint);
		receiver = new BluetoothReceiver();
		on.setOnClickListener(this);
		off.setOnClickListener(this);
		bond.setOnClickListener(this);
		search.setOnClickListener(this);
		selectFile.setOnClickListener(this);
		showFile.setText(fileName);
		// 注册发现和结束
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(receiver, intentFilter);
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter != null && adapter.isDiscovering()) {
			hint.setText("处于可见状态");
		} else {
			hint.setText("处于不可见状态");
		}
		setAdapter();
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// 可见
				if (isChecked) {
					if (adapter != null) {
						if (!adapter.isEnabled()) {
							// 以提醒方式开启蓝牙设备
							Intent intent = new Intent(
									BluetoothAdapter.ACTION_REQUEST_ENABLE);
							startActivity(intent);
							System.out.println("打开蓝牙");
						}
						Intent converIntent = new Intent(
								BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
						converIntent.putExtra(
								BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
								500);
						startActivity(converIntent);
						hint.setText("处于可见状态");
					} else {
						showHint("此设备没有蓝牙");
						System.out.println("设备没有蓝牙！");
					}
				} else {// 处理成不可见
					if (adapter != null) {
						if (adapter.isEnabled()) {
							// Intent converIntent = new Intent(
							// BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
							// startActivity(converIntent);
							// 取消搜索
							adapter.cancelDiscovery();
							hint.setText("处于不可见状态");
							showHint("已取消发现");
						} else {
							showHint("你的蓝牙处于不可用状态...");
						}
					} else {
						showHint("你的设备没有蓝牙");
						System.out.println("设备没有蓝牙");
					}
				}
			}
		});
		// listView.setOnItemLongClickListener(new OnItemLongClickListener() {
		// public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// //长击时创建上下文菜单
		// registerForContextMenu(arg1);
		// if(state==0){
		// curDevice=bondDevices.get(arg2);
		// }else if(state==1){
		// curDevice=allDevices.get(arg2);
		// }
		// return true;
		// }
		// });
		this.registerForContextMenu(listView);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.blue_set_on:
			if (adapter != null) {
				if (!adapter.isEnabled()) {
					// 默认方式开启蓝牙不提醒用户
					adapter.enable();
					showHint("蓝牙正在打开!请稍后...");
				} else {
					showHint("你的蓝牙处于打开状态");
				}
			} else {
				showHint("你的设备没有蓝牙");
			}
			break;
		case R.id.blue_set_off:
			if (adapter != null) {
				if (adapter.isEnabled()) {
					adapter.disable();
					showHint("蓝牙已关闭");
				} else {
					showHint("你的蓝牙处于关闭状态");
				}
			} else {
				showHint("你的设备没有蓝牙");
			}
			break;
		case R.id.blue_search_bond:
			if (adapter != null) {
				if (adapter.isEnabled()) {
					bondDevices.clear();
					dataAdapter.clear();
					adapter.startDiscovery();
					state = 0;
					showHint("正在搜索附近蓝牙...");
				} else {
					showHint("你的蓝牙处于关闭状态");
				}
			} else {
				showHint("你的设备没有蓝牙");
			}
			break;
		case R.id.blue_search_all:
			if (adapter != null) {
				if (adapter.isEnabled()) {
					allDevices.clear();
					dataAdapter.clear();
					adapter.startDiscovery();
					state = 1;
					showHint("正在搜索附近蓝牙...");
				} else {
					showHint("你的蓝牙处于关闭状态");
				}
			} else {
				showHint("你的设备没有蓝牙");
			}
			break;
		case R.id.select_file:
			if (!new File(fileName).exists()) {
				Toast.makeText(BluetoothTest.this, "将test.txt文件放在sd卡根目录下", 2000)
						.show();
			} else {
				// 发送文件
				System.out.println("发送文件");
				sendFile(fileName);
			}
			break;
		}
	}

	private void setAdapter() {
		dataAdapter = new BluetoothDataAdapter(this, data);
		listView.setAdapter(dataAdapter);
	}

	@Override
	protected void onDestroy() {
		this.unregisterReceiver(receiver);
		super.onDestroy();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View source,
			ContextMenu.ContextMenuInfo menuInfo) {
		menu.add(0, 1, 0, "配对");
		menu.add(0, 2, 0, "取消配对");
		// 将三个菜单项设为单选菜单项
		menu.setGroupCheckable(0, true, true);
		// 设置上下文菜单的标题、图标
		menu.setHeaderIcon(R.drawable.tools);
		menu.setHeaderTitle("选择背景色");
	}

	// 菜单项被单击时触发该方法。
	@Override
	public boolean onContextItemSelected(MenuItem mi) {
		switch (mi.getItemId()) {
		case 1:
			System.out.println("进行配对");
			doPair();
			break;
		case 2:
			// System.out.println("发送文件");
			// sendFile(fileName);
			break;
		}
		return true;
	}

	private void doPair() {
		if (allDevices.size() > 0) {
			curDevice = allDevices.get(0);
		} else {
			System.out.println("没有设备");
			return;
		}
		// 如果未配对
		if (curDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
			// 注册设备配对状态改变监听器
			PairReceiver pairReceiver = new PairReceiver();
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
			this.registerReceiver(pairReceiver, intentFilter);
			if (null == mOthHandler) {
				HandlerThread handlerThread = new HandlerThread("other_thread");
				handlerThread.start();
				mOthHandler = new Handler(handlerThread.getLooper());
			}
			// 加入对列
			mOthHandler.post(new Runnable() {
				public void run() {
					try {
						if (curDevice != null) {
							Method m = curDevice.getClass().getMethod(
									"createRfcommSocket",
									new Class[] { int.class });
							socket = (BluetoothSocket) m.invoke(curDevice, 1);
							socket.connect();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			// 已经与该设备配对
			Toast.makeText(this, "该设备已配对，无需重复操作！", Toast.LENGTH_LONG).show();
		}
	}

	private void sendFile(final String file) {
		if (socket == null) {
			if (allDevices.size() > 0) {
				curDevice = allDevices.get(0);
			} else {
				System.out.println("没有蓝牙设备");
				return;
			}
			try {
				// 额，自己研究下终于解决了，其实这个问题应该算是android sdk的bug吧（个人认为），sdk
				// 2.3以上的用createRfcommSocketToServiceRecord方法连接不上，须采用createInsecureRfcommSocketToServiceRecord方法（此方法需sdk2.3以上才有）。把代码分享下：
				// UUID uuid =
				// UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
				// int sdk = Integer.parseInt(Build.VERSION.SDK);
				// if (sdk >= 10) {
				// socket =
				// curDevice.createInsecureRfcommSocketToServiceRecord(uuid);
				// } else {
				// socket = curDevice.createRfcommSocketToServiceRecord(uuid);
				// }
				Method m = curDevice.getClass().getMethod("createRfcommSocket",
						new Class[] { int.class });
				socket = (BluetoothSocket) m.invoke(curDevice, 1);
				adapter.cancelDiscovery();// 取消查找
				socket.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (new File(file).exists()) {
			if (null == mOthHandler) {
				HandlerThread handlerThread = new HandlerThread("other_thread");
				handlerThread.start();
				mOthHandler = new Handler(handlerThread.getLooper());
			}
			mOthHandler.post(new Runnable() {
				public void run() {
					File localFile = new File(file);
					Intent intent3 = new Intent(Intent.ACTION_SEND);
					intent3.setType("*/*");
					intent3.putExtra(Intent.EXTRA_STREAM,
							Uri.fromFile(localFile));
					if (isHas(BluetoothTest.this, intent3)) {
						startActivity(intent3);
					}
					// Intent intent1 = new Intent(Intent.ACTION_SEND);
					// intent1.setType("*/*");
					// intent1.putExtra("android.intent.extra.STREAM",
					// Uri.fromFile(localFile));
					// intent1.setClassName("com.android.bluetooth",
					// "com.broadcom.bt.app.opp.OppLauncherActivity");
					// Intent intent2 = new Intent(Intent.ACTION_SEND);
					// intent2.setType("*/*");
					// intent2.putExtra("android.intent.extra.STREAM",
					// Uri.fromFile(localFile));
					// intent2.setClassName("com.android.bluetooth",
					// "com.android.bluetooth.opp.BluetoothOppLauncherActivity");
					// if (isHas(BluetoothTest.this, intent1)) {
					// System.out.println("使用Opp");
					// startActivity(intent1);
					// } else if (isHas(BluetoothTest.this, intent2)) {
					// System.out.println("使用BluetoothOpp");
					// startActivity(intent2);
					// } else if (isHas(BluetoothTest.this, intent3)) {
					// startActivity(intent3);
					// //startActivity(Intent.createChooser(intent3,
					// "请选择蓝牙发送"));
					// }
				}
			});
		} else {
			Toast.makeText(this, "文件不存在!", 1).show();
		}
	}

	private class BluetoothReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			System.out.println("触发接收器");
			String action = intent.getAction();
			// 发现设备
			if (action.equals(BluetoothDevice.ACTION_FOUND)) {
				System.out.println("发现设备");
				// 获取发现的设备
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// 获得已经绑定的设备
				Set<BluetoothDevice> sets = adapter.getBondedDevices();
				System.out.println("已绑定的数量>>>>>" + sets.size());
				if (device != null) {
					if (state == 0 && sets.contains(device)) {
						String name = device.getName();
						bondDevices.add(device);
						dataAdapter.add(name + "  " + device.getAddress());
					}
					if (state == 1) {
						String name = device.getName();
						allDevices.add(device);
						dataAdapter.add(name + "  " + device.getAddress());
					}
				} else {
					showHint("没有发现蓝牙设备...");
				}
			} else if (action
					.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
				Toast.makeText(getApplicationContext(), "搜索完毕", 1).show();
			}
		}
	}

	private class PairReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			System.out.println("绑定接收器");
			if (intent.getAction().equals(
					BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
				System.out.println("执行绑定结束！");
				// 取得状态改变的设备，更新设备列表信息 （配对状态）
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				unregisterReceiver(this);
				Toast.makeText(getApplicationContext(), "配对完毕", 1).show();
			}
		}
	}

	/**
	 * 是否存在此组件
	 * 
	 * @param paramContext
	 * @param paramIntent
	 * @return
	 */
	private boolean isHas(Context context, Intent intent) {
		int k = PackageManager.MATCH_DEFAULT_ONLY;
		if (context.getPackageManager().queryIntentActivities(intent, k).size() > 0) {
			return true;
		}
		return false;
	}

}
