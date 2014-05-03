package com.cheng.demo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class TextSwitcherTest extends CommonActivity implements ViewFactory,
		View.OnClickListener {
	TextSwitcher ts;
	Button btn;
	int update = 0;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.text_switcher);
		ts = (TextSwitcher) findViewById(R.id.switcher_text);
		btn = (Button) findViewById(R.id.switcher_btn);
		btn.setOnClickListener(this);
		ts.setFactory(this);
		ts.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		ts.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));
		ts.setText("现在的内容是:" + update);
	}

	@Override
	public View makeView() {
		TextView tv = new TextView(this);
		tv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
		tv.setTextSize(36f);
		return tv;
	}

	@Override
	public void onClick(View v) {
		System.out.println("更新数据--->" + update);
		update++;
		ts.setText("现在的内容是:" + update);
	}

}
