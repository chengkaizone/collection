package com.cheng.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class KaiMainClass extends CommonActivity {
	private String[] srr = { "3DЧ�����", "startForResult", "��������", "��չ����",
			"�Զ���������", "��ʾgifͼƬ", "����ת����", "��ʾ", "�ᶯ�İ�ť", "�Զ���˵�", "��¼��ʾ",
			"������ʾ��", "��ȡԪ����", "֪ͨ������", "Ƥ������" };
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
