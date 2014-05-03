package com.cheng.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;

public class Hint extends CommonActivity implements OnClickListener {

	public void onCreate(Bundle save) {
		super.onCreate(save);
		int i = android.R.style.Theme;
		// setContentView(R.layout.smdialog);
		LayoutInflater mInflater = LayoutInflater.from(this);
		View layout = mInflater.inflate(R.layout.smdialog, null);
		ImageView imgCancel = (ImageView) layout.findViewById(R.id.dialog_exit);
		imgCancel.setOnClickListener(this);
		CheckBox ckShow = (CheckBox) layout.findViewById(R.id.ckShow);
		new AlertDialog.Builder(Hint.this).setView(layout)
				.setNegativeButton("ЭЌвт", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	public void onClick(View v) {
	}
}
