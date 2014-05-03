package com.cheng.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResultActTest extends CommonActivity {
	Button btn;
	EditText et;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.result_test);
		btn = (Button) findViewById(R.id.result_btn);
		et = (EditText) findViewById(R.id.result_et1);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String str = et.getText().toString().trim();
				if (str.equals("")) {
					Toast.makeText(ResultActTest.this, "请输入城市名", 2000).show();
				} else {
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putString("city", str);
					intent.putExtras(bundle);
					setResult(0, intent);
					finish();
				}
			}
		});
	}
}
