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
		tv.setText("\t���˵���˿��������Ϸ��Ƽ�sdk����������������ʱ��ķ�������s"
				+ "dk���·��˼�������Ǽ�¼sdk���ֶ�˼���˼�����sdk�����ϵ��Կ�����");
	}

	private void createDialog() {
		hintDialog = new AlertDialogs(this, R.style.progress_dialog);
		hintDialog.setProperty(0, 200, dis.getWidth(), 150);// ��������Ϳ��
		hintDialog.setCanceledOnTouchOutside(true);// ���õ����dialog����ʱȡ����dialog
		hintDialog.show();// ��ʾ
		// addView�ķ�����������������֮ǰ���ã��˷����е����ݶ��ᱻ����ϱ߿�
		// setContentViewֻ��������������֮�����
		hintDialog.setContentView(view);// �˷���������show����֮�����
	}
}
