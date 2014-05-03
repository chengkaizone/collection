package com.cheng.demo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ant.liao.GifView;

public class GifViewTest extends CommonActivity implements OnClickListener {
	private GifView gifView;
	private Button btn1;
	boolean flag = false;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.gifview);
		gifView = (GifView) findViewById(R.id.gifview_gif);
		btn1 = (Button) findViewById(R.id.gifview_btn1);
		gifView.setGifImage(R.drawable.switcher);
		gifView.setOnClickListener(this);
		btn1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (flag) {
			gifView.showAnimation();
			flag = !flag;
		} else {
			gifView.showCover();
			flag = !flag;
		}
	}
}
