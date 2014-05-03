package com.cheng.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.cheng.adapter.SkinAdapter;
import com.cheng.util.SkinManager;

public class SetSkin extends CommonActivity implements OnItemClickListener {
	private Button btn1, btn2, btn3, btn4, btn5;
	SkinAdapter adapter;
	String[] srr = { "昏黄沙滩", "乡间风情", "金色田螺", "湛蓝海滩", "昏天暗地" };
	int[] irr = { R.drawable.skin5, R.drawable.skin2, R.drawable.skin3,
			R.drawable.skin4, R.drawable.skin1 };
	SharedPreferences share;
	SharedPreferences.Editor editor;
	private ListView lv;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.new_skin_set);
		lv = (ListView) findViewById(R.id.skin_list);
		lv.setOnItemClickListener(this);
		share = this.getSharedPreferences("skins", Context.MODE_PRIVATE);
		editor = share.edit();
		initList();
	}

	private void initList() {
		adapter = new SkinAdapter(this, srr, irr);
		lv.setAdapter(adapter);
		int loc = share.getInt("skin", 0);
		System.out.println("loc--->" + loc);
		adapter.selected(loc);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println("第" + position + "项被选");
		switch (position) {
		case 0:
			editor.putInt("skin", position);
			break;
		case 1:
			editor.putInt("skin", position);
			break;
		case 2:
			editor.putInt("skin", position);
			break;
		case 3:
			editor.putInt("skin", position);
			break;
		case 4:
			editor.putInt("skin", position);
			break;
		}
		editor.commit();
		adapter.selected(position);
		SetSkin.this.getWindow().setBackgroundDrawable(
				SkinManager.getDefDrawable(SetSkin.this, position));
	}

}
