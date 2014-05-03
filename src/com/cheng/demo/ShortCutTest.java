package com.cheng.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 桌面快捷方式需要添加快捷方式权限
 * 
 * @author Administrator
 * 
 */
public class ShortCutTest extends CommonActivity {
	Button btn;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.shortcut_main);
		btn = (Button) findViewById(R.id.shortcut_btn);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent addIntent = new Intent(
						"com.android.launcher.action.INSTALL_SHORTCUT");
				String title = getResources().getString(R.string.app_name);
				Parcelable icon = Intent.ShortcutIconResource.fromContext(
						ShortCutTest.this, R.drawable.lashou);
				Intent mIntent = new Intent(ShortCutTest.this,
						ShortCutTest.class);
				addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
				addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
				addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, mIntent);
				sendBroadcast(addIntent);
			}
		});
	}
}
