package com.cheng.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class MenuTest extends CommonActivity {
	String[] srr = { "ˢ��", "����", "���ع���", "�ļ�����", "ȫ��", "����", "�˳�" };
	int[] drr = { R.drawable.alert_dialog_icon, R.drawable.icon_menu_about,
			R.drawable.icon_menu_help, R.drawable.icon_menu_home,
			R.drawable.icon_menu_skin, R.drawable.icon_menu_exit,
			R.drawable.close, };
	// private Display dis;
	private AlertDialog menuDialog;
	private View menuView;
	private GridView gv;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		// setContentView(R.layout.kai_main);
		// dis=getWindow().getWindowManager().getDefaultDisplay();
		menuView = View.inflate(this, R.layout.gridview_menu, null);
		gv = (GridView) menuView.findViewById(R.id.menu_gridview0);
		setMenuAdapter();
		initDialog();
	}

	private void initDialog() {
		// ����AlertDialog
		menuDialog = new AlertDialog.Builder(this).create();
		Window w = menuDialog.getWindow();
		WindowManager.LayoutParams params = w.getAttributes();
		params.x = 0;
		params.y = 0;// 0�������᣺�ϸ�����
		menuDialog.getWindow().setAttributes(params);// �����������ò���
		menuDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU)// ��������
					dialog.dismiss();
				return true;
			}
		});
		menuDialog.setContentView(menuView);
	}

	private SimpleAdapter setMenuAdapter() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < srr.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("img", drr[i]);
			map.put("hint", srr[i]);
			data.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, data,
				R.layout.grid_item, new String[] { "img", "hint" }, new int[] {
						R.id.grid_item_img, R.id.grid_item_text });
		gv.setAdapter(adapter);
		return adapter;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");// ���봴��һ��
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	// ����ϵͳ�˵������Զ���˵�
	public boolean onMenuOpened(int featureId, Menu menu) {
		System.out.println("�˵�����--->");
		super.onMenuOpened(featureId, menu);
		if (menuDialog == null) {
			menuDialog = new AlertDialog.Builder(this).setView(menuView).show();
		} else {
			menuDialog.show();
		}
		return false;// ����true�����⴫�ݣ�������ʾϵͳ�˵���������ʾ�Զ���˵�
	}
}
