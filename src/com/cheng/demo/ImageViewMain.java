package com.cheng.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.cheng.util.PhotoUtil;

/**
 * µπ”∞≤‚ ‘
 * 
 * @author Administrator
 */
public class ImageViewMain extends CommonActivity implements OnClickListener {
	Button btn;
	ImageView image;
	Bitmap bitmap, tmp;
	boolean flag = false, showing = false;
	View menuView, view;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.image_test);
		btn = (Button) findViewById(R.id.image_main_btn);
		image = (ImageView) findViewById(R.id.image_main_img);
		btn.setOnClickListener(this);
		image.setOnClickListener(this);
		view = LayoutInflater.from(this).inflate(R.layout.image_test, null);
		bitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.circle);
		tmp = PhotoUtil.createReflectImage(bitmap);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_main_btn: {
			if (!flag) {
				image.setImageBitmap(tmp);
				flag = !flag;
			} else {
				image.setImageBitmap(bitmap);
				flag = !flag;
			}
		}
			break;
		}
	}
}
