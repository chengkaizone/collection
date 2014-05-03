package com.cheng.demo;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Get extends CommonActivity {
	Button btn;
	TextView tv;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.get);
		btn = (Button) findViewById(R.id.get_btn);
		tv = (TextView) findViewById(R.id.get_text);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// String s=MedaData.getString(Get.this,"author");
				// tv.append(s);
				getData();
			}
		});
	}

	private void getData() {
		try {
			ApplicationInfo info = this.getPackageManager().getApplicationInfo(
					this.getPackageName(), PackageManager.GET_META_DATA);

			Bundle b = info.metaData;
			String s = b.getString("author");
			tv.append(s + "--->");
		} catch (Exception e) {
			System.out.println("Å×³öÒì³££¡");
			e.printStackTrace();
		}
	}

}
