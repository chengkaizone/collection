package com.cheng.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class KaiMainClass extends CommonActivity {
	private String[] srr = { "3D效果相册", "startForResult", "蓝牙操作", "可展开项",
			"自动控制设置", "显示gif图片", "文字转换器", "提示", "会动的按钮", "自定义菜单", "登录提示",
			"流量提示条", "获取元数据", "通知管理器", "皮肤管理" };
	private Class[] crr = { Photos.class, ActForResult.class,
			BluetoothTest.class, ExpandableListViewTest.class,
			PreferScreenTest.class, GifViewTest.class, TextSwitcherTest.class,
			LoginDeclare.class, ButtonActivity.class, MyMenuTest.class,
			Hint.class, HorizontalTest.class, Get.class,
			NotifyCationTest.class, SetSkin.class };
	private ListView list;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.root_kai_main);
		list = (ListView) findViewById(R.id.root_kai_list);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(KaiMainClass.this, crr[position]));
			}
		});
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, srr);
		list.setAdapter(adapter);
	}
}
