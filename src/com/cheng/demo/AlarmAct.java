package com.cheng.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class AlarmAct extends CommonActivity {

	public void onCreate(Bundle save) {
		super.onCreate(save);
		new AlertDialog.Builder(this).setTitle("����").setMessage("������")
				.setPositiveButton("ȷ��", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).create().show();
	}
}
