package com.cheng.demo;

import android.os.Bundle;

import com.cheng.widgets.LeidianView;

public class LeidianActivity extends CommonActivity {
	LeidianView view;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		view = new LeidianView(this);
		setContentView(view);
	}
}
