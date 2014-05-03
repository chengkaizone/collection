package com.cheng.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class ActivityMenu extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// -------------��menu������Ӳ˵�-------------
		SubMenu prog = menu.addSubMenu("��������");
		// ���ò˵���ͼ��
		prog.setIcon(R.drawable.alert_dialog_icon);
		// ���ò˵�ͷ��ͼ��
		prog.setHeaderIcon(R.drawable.alert_dialog_icon);
		// ���ò˵�ͷ�ı���
		prog.setHeaderTitle("ѡ����Ҫ�����ĳ���");
		// ��Ӳ˵���
		MenuItem item = prog.add("�鿴����Java EE");
		// Ϊ�˵������ù�����Activity
		item.setIntent(new Intent(this, OtherActivity.class));
		return super.onCreateOptionsMenu(menu);
	}
}