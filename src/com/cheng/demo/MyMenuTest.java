package com.cheng.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.ViewFlipper;

import com.cheng.app.AlertDialogs;

public class MyMenuTest extends CommonActivity {
	String[] srr = { "刷新", "搜索", "下载管理", "文件管理", "全屏", "设置", "退出" };
	int[] drr = { R.drawable.alert_dialog_icon, R.drawable.close,
			R.drawable.icon_menu_about, R.drawable.icon_menu_help,
			R.drawable.icon_menu_home, R.drawable.icon_menu_skin,
			R.drawable.icon_menu_exit, };
	int curIndex = 0;
	private View menuView;
	private ViewFlipper flipper;
	private GridView gv0, gv1, gv2;
	private AlertDialogs menuDialog;
	private Button btn0, btn1, btn2, prebtn, curbtn;
	int[] colors = { Color.argb(0, 0, 0, 0), Color.RED };

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.declare_test);
		menuView = View.inflate(this, R.layout.gridview_menu, null);
		flipper = (ViewFlipper) menuView.findViewById(R.id.menu_flipper);
		btn0 = (Button) menuView.findViewById(R.id.menu_tab_btn0);
		btn1 = (Button) menuView.findViewById(R.id.menu_tab_btn1);
		btn2 = (Button) menuView.findViewById(R.id.menu_tab_btn2);
		btn0.setBackgroundColor(colors[0]);
		btn1.setBackgroundColor(colors[1]);
		btn2.setBackgroundColor(colors[1]);
		btn0.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				flipper.setDisplayedChild(0);
				curIndex = 0;
				check();
			}
		});
		btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				flipper.setDisplayedChild(1);
				curIndex = 1;
				check();
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				flipper.setDisplayedChild(2);
				curIndex = 2;
				check();
			}
		});
		gv0 = (GridView) menuView.findViewById(R.id.menu_gridview0);
		gv1 = (GridView) menuView.findViewById(R.id.menu_gridview1);
		gv2 = (GridView) menuView.findViewById(R.id.menu_gridview2);
		setMenuAdapter();
		prebtn = btn0;
		curbtn = btn0;
		// createDialog();
	}

	private void check() {
		switch (curIndex) {
		case 0:
			prebtn = curbtn;
			curbtn = btn0;
			break;
		case 1:
			prebtn = curbtn;
			curbtn = btn1;
			break;
		case 2:
			prebtn = curbtn;
			curbtn = btn2;
			break;
		}
		curbtn.setBackgroundColor(Color.argb(0, 0, 0, 0));
		if (curbtn != prebtn) {
			prebtn.setBackgroundColor(Color.RED);
		}
	}

	private void createDialog() {
		menuDialog = new AlertDialogs(this, R.style.progress_dialog);
		menuDialog.setProperty(0, 200, 200, 100);// 设置坐标和宽高
		menuDialog.setCanceledOnTouchOutside(true);// 设置点击非dialog区域时取消此dialog
		menuDialog.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_MENU) {
					dialog.dismiss();
				}
				return false;
			}
		});
		menuDialog.show();// 显示
		menuDialog.setContentView(menuView);// 添加一个ListView在此dialog
	}

	private void setMenuAdapter() {
		{
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < srr.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("img", drr[i]);
				map.put("hint", srr[i]);
				data.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(this, data,
					R.layout.grid_item, new String[] { "img", "hint" },
					new int[] { R.id.grid_item_img, R.id.grid_item_text });
			gv0.setAdapter(adapter);
		}
		{
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			for (int i = srr.length - 1; i >= 0; i--) {
				if (i == 2 | i == 3) {
					continue;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("img", drr[i]);
				map.put("hint", srr[i]);
				data.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(this, data,
					R.layout.grid_item, new String[] { "img", "hint" },
					new int[] { R.id.grid_item_img, R.id.grid_item_text });
			gv1.setAdapter(adapter);
		}
		{
			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			for (int i = srr.length - 1; i >= 0; i--) {
				if (i == 7 | i == 6) {
					continue;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("img", drr[i]);
				map.put("hint", srr[i]);
				data.add(map);
			}
			SimpleAdapter adapter = new SimpleAdapter(this, data,
					R.layout.grid_item, new String[] { "img", "hint" },
					new int[] { R.id.grid_item_img, R.id.grid_item_text });
			gv2.setAdapter(adapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");// 必须创建一项才能触发菜单事件
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	// 拦截系统菜单；打开自定义菜单此方法；如果需要被调用必须重写菜单才能被触发
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		System.out.println("菜单按键被打开--->");
		super.onMenuOpened(featureId, menu);
		if (menuDialog == null) {
			createDialog();
		} else {
			menuDialog.show();
		}
		flipper.setDisplayedChild(0);
		curIndex = 0;
		check();
		return false;// 返回true会向外传递；触发显示系统菜单；否则显示自定义菜单
	}
}
