package com.cheng.demo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cheng.widgets.ShakeButton;

public class ButtonActivity extends CommonActivity {
	ShakeButton btn;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.my_button);
		btn = (ShakeButton) findViewById(R.id.my_button_btn);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				System.out.println("����");
				// Toast.makeText(ButtonActivity.this, "����˼���͸�ʿ��", 2000).show();
			}
		});
	}

}
