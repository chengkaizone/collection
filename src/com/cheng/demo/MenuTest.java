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
	String[] srr = { "刷新", "搜索", "下载管理", "文件管理", "全屏", "设置", "退出" };
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
		// 创建AlertDialog
		menuDialog = new AlertDialog.Builder(this).create();
		Window w = menuDialog.getWindow();
		WindowManager.LayoutParams params = w.getAttributes();
		params.x = 0;
		params.y = 0;// 0代表中轴：上负下正
		menuDialog.getWindow().setAttributes(params);// 必须重新设置参数
		menuDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU)// 监听按键
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
		menu.add("menu");// 必须创建一项
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	// 拦截系统菜单；打开自定义菜单
	public boolean onMenuOpened(int featureId, Menu menu) {
		System.out.println("菜单被打开--->");
		super.onMenuOpened(featureId, menu);
		if (menuDialog == null) {
			menuDialog = new AlertDialog.Builder(this).setView(menuView).show();
		} else {
			menuDialog.show();
		}
		return false;// 返回true会向外传递；触发显示系统菜单；否则显示自定义菜单
	}
}
