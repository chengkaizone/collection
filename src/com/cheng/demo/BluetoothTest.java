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
		// ע�ᷢ�ֺͽ���
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(receiver, intentFilter);
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter != null && adapter.isDiscovering()) {
			hint.setText("���ڿɼ�״̬");
		} else {
			hint.setText("���ڲ��ɼ�״̬");
		}
		setAdapter();
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// �ɼ�
				if (isChecked) {
					if (adapter != null) {
						if (!adapter.isEnabled()) {
							// �����ѷ�ʽ���������豸
							Intent intent = new Intent(
									BluetoothAdapter.ACTION_REQUEST_ENABLE);
							startActivity(intent);
							System.out.println("������");
						}
						Intent converIntent = new Intent(
								BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
						converIntent.putExtra(
								BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
								500);
						startActivity(converIntent);
						hint.setText("���ڿɼ�״̬");
					} else {
						showHint("���豸û������");
						System.out.println("�豸û��������");
					}
				} else {// ����ɲ��ɼ�
					if (adapter != null) {
						if (adapter.isEnabled()) {
							// Intent converIntent = new Intent(
							// BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
							// startActivity(converIntent);
							// ȡ������
							adapter.cancelDiscovery();
							hint.setText("���ڲ��ɼ�״̬");
							showHint("��ȡ������");
						} else {
							showHint("����������ڲ�����״̬...");
						}
					} else {
						showHint("����豸û������");
						System.out.println("�豸û������");
					}
				}
			}
		});
		// listView.setOnItemLongClickListener(new OnItemLongClickListener() {
		// public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// //����ʱ���������Ĳ˵�
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
					// Ĭ�Ϸ�ʽ���������������û�
					adapter.enable();
					showHint("�������ڴ�!���Ժ�...");
				} else {
					showHint("����������ڴ�״̬");
				}
			} else {
				showHint("����豸û������");
			}
			break;
		case R.id.blue_set_off:
			if (adapter != null) {
				if (adapter.isEnabled()) {
					adapter.disable();
					showHint("�����ѹر�");
				} else {
					showHint("����������ڹر�״̬");
				}
			} else {
				showHint("����豸û������");
			}
			break;
		case R.id.blue_search_bond:
			if (adapter != null) {
				if (adapter.isEnabled()) {
					bondDevices.clear();
					dataAdapter.clear();
					adapter.startDiscovery();
					state = 0;
					showHint("����������������...");
				} else {
					showHint("����������ڹر�״̬");
				}
			} else {
				showHint("����豸û������");
			}
			break;
		case R.id.blue_search_all:
			if (adapter != null) {
				if (adapter.isEnabled()) {
					allDevices.clear();
					dataAdapter.clear();
					adapter.startDiscovery();
					state = 1;
					showHint("����������������...");
				} else {
					showHint("����������ڹر�״̬");
				}
			} else {
				showHint("����豸û������");
			}
			break;
		case R.id.select_file:
			if (!new File(fileName).exists()) {
				Toast.makeText(BluetoothTest.this, "��test.txt�ļ�����sd����Ŀ¼��", 2000)
						.show();
			} else {
				// �����ļ�
				System.out.println("�����ļ�");
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
		menu.add(0, 1, 0, "���");
		menu.add(0, 2, 0, "ȡ�����");
		// �������˵�����Ϊ��ѡ�˵���
		menu.setGroupCheckable(0, true, true);
		// ���������Ĳ˵��ı��⡢ͼ��
		menu.setHeaderIcon(R.drawable.tools);
		menu.setHeaderTitle("ѡ�񱳾�ɫ");
	}

	// �˵������ʱ�����÷�����
	@Override
	public boolean onContextItemSelected(MenuItem mi) {
		switch (mi.getItemId()) {
		case 1:
			System.out.println("�������");
			doPair();
			break;
		case 2:
			// System.out.println("�����ļ�");
			// sendFile(fileName);
			break;
		}
		return true;
	}

	private void doPair() {
		if (allDevices.size() > 0) {
			curDevice = allDevices.get(0);
		} else {
			System.out.println("û���豸");
			return;
		}
		// ���δ���
		if (curDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
			// ע���豸���״̬�ı������
			PairReceiver pairReceiver = new PairReceiver();
			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
			this.registerReceiver(pairReceiver, intentFilter);
			if (null == mOthHandler) {
				HandlerThread handlerThread = new HandlerThread("other_thread");
				handlerThread.start();
				mOthHandler = new Handler(handlerThread.getLooper());
			}
			// �������
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
			// �Ѿ�����豸���
			Toast.makeText(this, "���豸����ԣ������ظ�������", Toast.LENGTH_LONG).show();
		}
	}

	private void sendFile(final String file) {
		if (socket == null) {
			if (allDevices.size() > 0) {
				curDevice = allDevices.get(0);
			} else {
				System.out.println("û�������豸");
				return;
			}
			try {
				// ��Լ��о������ڽ���ˣ���ʵ�������Ӧ������android sdk��bug�ɣ�������Ϊ����sdk
				// 2.3���ϵ���createRfcommSocketToServiceRecord�������Ӳ��ϣ������createInsecureRfcommSocketToServiceRecord�������˷�����sdk2.3���ϲ��У����Ѵ�������£�
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
				adapter.cancelDiscovery();// ȡ������
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
					// System.out.println("ʹ��Opp");
					// startActivity(intent1);
					// } else if (isHas(BluetoothTest.this, intent2)) {
					// System.out.println("ʹ��BluetoothOpp");
					// startActivity(intent2);
					// } else if (isHas(BluetoothTest.this, intent3)) {
					// startActivity(intent3);
					// //startActivity(Intent.createChooser(intent3,
					// "��ѡ����������"));
					// }
				}
			});
		} else {
			Toast.makeText(this, "�ļ�������!", 1).show();
		}
	}

	private class BluetoothReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			System.out.println("����������");
			String action = intent.getAction();
			// �����豸
			if (action.equals(BluetoothDevice.ACTION_FOUND)) {
				System.out.println("�����豸");
				// ��ȡ���ֵ��豸
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// ����Ѿ��󶨵��豸
				Set<BluetoothDevice> sets = adapter.getBondedDevices();
				System.out.println("�Ѱ󶨵�����>>>>>" + sets.size());
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
					showHint("û�з��������豸...");
				}
			} else if (action
					.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
				Toast.makeText(getApplicationContext(), "�������", 1).show();
			}
		}
	}

	private class PairReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			System.out.println("�󶨽�����");
			if (intent.getAction().equals(
					BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
				System.out.println("ִ�а󶨽�����");
				// ȡ��״̬�ı���豸�������豸�б���Ϣ �����״̬��
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				unregisterReceiver(this);
				Toast.makeText(getApplicationContext(), "������", 1).show();
			}
		}
	}

	/**
	 * �Ƿ���ڴ����
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
