package com.cheng.demo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.cheng.adapter.ImageAdapter;
import com.cheng.widgets.CustomGallery;

public class Photos extends CommonActivity implements OnItemClickListener,
		OnItemLongClickListener {
	private int preLoc = 0;
	private CustomGallery gallery;
	int[] resIds = { R.drawable.skin1, R.drawable.skin2, R.drawable.skin3,
			R.drawable.skin4, R.drawable.skin5, R.drawable.alert_dialog_icon,
			R.drawable.app_icon, R.drawable.lashou, R.drawable.se };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kai_gallery_main);
		gallery = (CustomGallery) findViewById(R.id.kai_main_gallery);
		ImageAdapter adapter = new ImageAdapter(this, resIds);
		gallery.setAdapter(adapter);
		gallery.setOnItemClickListener(this);
		gallery.setOnItemLongClickListener(this);
		// init();
	}

	// 初始化画廊对象；不使用布局文件时调用该方法
	private void init() {
		gallery = new CustomGallery(this);
		ImageAdapter adapter = new ImageAdapter(this, resIds);
		gallery.setAdapter(adapter);
		// 添加动画效果，每次持续1500毫秒
		gallery.setAnimationDuration(1500);
		gallery.setSpacing(3);
		// gallery.setBackgroundResource(R.drawable.gallery_bg);
		setContentView(gallery);
		gallery.setOnItemClickListener(this);
		System.out.println("图片数量--->" + gallery.getCount());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println("单击事件" + preLoc + "---" + position);
		ImageView img = (ImageView) gallery.getChildAt(position);
		Bitmap bitmap = (Bitmap) gallery.getItemAtPosition(position);
		if (preLoc == position) {
			if (img != null) {
				System.out.println("图片还原");
			}
		} else {
			preLoc = position;
			if (img != null) {
				System.out.println("图片放大");
			}
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		System.out.println(position + "项被长击");
		Toast.makeText(this, position + "项被长击", 2000);
		return true;
	}
}