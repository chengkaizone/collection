package com.cheng.demo;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cheng.app.AlertDialogs;

public class LoginDeclare extends CommonActivity {
	private Display dis;
	private View view;
	private AlertDialogs hintDialog;
	Button btn1, btn2;
	TextView tv;
	CheckBox cb;

	public void onCreate(Bundle save) {
		super.onCreate(save);
		setContentView(R.layout.kai_main);
		initHint();
		createDialog();
	}

	public void initHint() {
		dis = this.getWindowManager().getDefaultDisplay();
		view = View.inflate(this, R.layout.check_hint, null);
		btn1 = (Button) view.findViewById(R.id.check_agree);
		btn2 = (Button) view.findViewById(R.id.check_noagree);
		tv = (TextView) view.findViewById(R.id.check_clare);
		btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				hintDialog.dismiss();
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				hintDialog.dismiss();
			}
		});
		tv.setText("\t快点说过了快升级到南方科技sdk链接升级到付款了时间的反抗精神s"
				+ "dk解放路的思考几分是记录sdk积分多思考了几分了sdk肌肤上电脑开机费");
	}

	private void createDialog() {
		hintDialog = new AlertDialogs(this, R.style.progress_dialog);
		hintDialog.setProperty(0, 200, dis.getWidth(), 150);// 设置坐标和宽高
		hintDialog.setCanceledOnTouchOutside(true);// 设置点击非dialog区域时取消此dialog
		hintDialog.show();// 显示
		// addView的方法可以在设置属性之前调用；此方法中的内容都会被添加上边框
		// setContentView只能在设置完属性之后调用
		hintDialog.setContentView(view);// 此方法必须在show方法之后调用
	}
}
