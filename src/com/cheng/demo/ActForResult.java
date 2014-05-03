package com.cheng.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/**
 * 以startActivityResult方式跳转另一个界面 可以解决携带数据和经历生命周期的问题
 * 
 * @author Administrator
 * 
 */
public class ActForResult extends CommonActivity {
	EditText et;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.act_for_result);
		et = (EditText) findViewById(R.id.for_result_et);
		et.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ActForResult.this,
						ResultActTest.class);
				startActivityForResult(intent, 0);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		System.out.println("返回结果--->");
		if (requestCode == 0 && resultCode == 0) {
			Bundle bundle = intent.getExtras();
			String city = bundle.getString("city");
			et.setText(city);
		}
	}

}
