package com.cheng.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class CustomListView extends Activity {
	private ListView list;

	public void onCreate(Bundle save) {
		super.onCreate(save);

		setContentView(R.layout.listview_main);
		list = (ListView) findViewById(R.id.listview_main);
	}
}
