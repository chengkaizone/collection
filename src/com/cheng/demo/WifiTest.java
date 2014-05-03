package com.cheng.demo;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WifiTest extends CommonActivity implements OnClickListener {
	private Button btn1, btn2, btn3;
	private WifiManager wifi;
	private TextView head, tv;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn1 = (Button) findViewById(R.id.main_btn1);
		btn2 = (Button) findViewById(R.id.main_btn2);
		btn3 = (Button) findViewById(R.id.main_btn3);
		head = (TextView) findViewById(R.id.main_head);
		tv = (TextView) findViewById(R.id.main_show_state);
		wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.main_btn1:
			wifi.setWifiEnabled(true);
			int state1 = wifi.getWifiState();
			System.out.println("wifi������ǰ״̬��" + wifi.getWifiState());
			tv.setText(checkState(wifi.getWifiState()));
			break;
		case R.id.main_btn2:
			wifi.setWifiEnabled(false);
			System.out.println("wifi������ǰ״̬��" + wifi.getWifiState());
			tv.setText(checkState(wifi.getWifiState()));
			break;
		case R.id.main_btn3:
			System.out.println("wifi������ǰ״̬��" + wifi.getWifiState());
			tv.setText(checkState(wifi.getWifiState()));
			break;
		}
	}

	private String checkState(int state) {
		String str = "��ǰwifi����״̬��\t";
		switch (state) {
		case WifiManager.WIFI_STATE_DISABLED:
			str += "�ѹر�";
			break;
		case WifiManager.WIFI_STATE_DISABLING:
			str += "���ڹر�";
			break;
		case WifiManager.WIFI_STATE_ENABLED:
			str += "�ѿ���";
			break;
		case WifiManager.WIFI_STATE_ENABLING:
			str += "���ڿ���";
			break;
		case WifiManager.WIFI_STATE_UNKNOWN:
			str += "δ֪״̬";
			break;
		}
		return str;
	}
}