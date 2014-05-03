package com.cheng.demo;

import android.os.Bundle;

import com.cheng.widgets.WarpView;

public class WarpTest extends CommonActivity {
	WarpView view;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		view = new WarpView(this);
		setContentView(view);
	}
}
