package com.cheng.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Toast;

public class SlidingDrawerTest extends Activity implements OnItemClickListener,
		OnDrawerOpenListener, OnDrawerCloseListener {
	String[] srr = { "刷新", "搜索", "下载管理", "文件管理", "全屏", "设置", "退出", "刷新", "搜索",
			"下载管理", "文件管理", "全屏", "设置", "退出", "刷新", "搜索", "下载管理", "文件管理", "全屏",
			"设置", "退出", "刷新", "搜索", "下载管理", "文件管理", "全屏", "设置", "退出" };
	int[] drr = { R.drawable.alert_dialog_icon, R.drawable.close,
			R.drawable.icon_menu_about, R.drawable.icon_menu_help,
			R.drawable.icon_menu_home, R.drawable.icon_menu_skin,
			R.drawable.icon_menu_exit, R.drawable.alert_dialog_icon,
			R.drawable.close, R.drawable.icon_menu_about,
			R.drawable.icon_menu_help, R.drawable.icon_menu_home,
			R.drawable.icon_menu_skin, R.drawable.icon_menu_exit,
			R.drawable.alert_dialog_icon, R.drawable.close,
			R.drawable.icon_menu_about, R.drawable.icon_menu_help,
			R.drawable.icon_menu_home, R.drawable.icon_menu_skin,
			R.drawable.icon_menu_exit, R.drawable.alert_dialog_icon,
			R.drawable.close, R.drawable.icon_menu_about,
			R.drawable.icon_menu_help, R.drawable.icon_menu_home,
			R.drawable.icon_menu_skin, R.drawable.icon_menu_exit, };
	private SlidingDrawer drawer;
	private ImageView handle;
	private GridView content;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.launcher_main);
		drawer = (SlidingDrawer) findViewById(R.id.launcher_drawer);
		// ScaleAnimation animation=new
		// ScaleAnimation(0.1f,1.0f,0.1f,1.0f,0.5f,0.5f);
		// animation.setDuration(500);
		// drawer.setAnimation(animation);
		handle = (ImageView) findViewById(R.id.launcher_handle);

		content = (GridView) findViewById(R.id.launcher_content);
		content.setOnItemClickListener(this);
		setAnimation(content);
		setGridAdapter();
		drawer.setOnDrawerOpenListener(this);
		drawer.setOnDrawerCloseListener(this);
	}

	private void setGridAdapter() {
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
		content.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(SlidingDrawerTest.this, position + "--->被单击", 2000)
				.show();
	}

	public void onDrawerClosed() {
		// 此方法等于监听其他控件响应SlidingDrawer.close（）方法
		handle.setImageResource(R.drawable.icon_menu_update);
	}

	public void onDrawerOpened() {
		// 此方法等于监听其他控件响应SlidingDrawer.open（）方法
		handle.setImageResource(R.drawable.icon_menu_home);
	}

	private void setAnimation(GridView grid) {
		ScaleAnimation scale = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, 0.5f,
				0.5f);
		scale.setDuration(500);
		LayoutAnimationController lac = new LayoutAnimationController(scale);
		lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
		lac.setDelay(0f);
		grid.setLayoutAnimation(lac);
	}
}